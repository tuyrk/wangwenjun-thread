package com.tuyrk.chapter13;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * 自定义简单线程池
 * 任务队列、拒绝策略
 *
 * @author tuyrk
 */
public class SimpleThreadPool extends Thread {
    /**
     * 最小线程数量
     */
    private int min;
    /**
     * 活跃线程数量
     */
    private int active;
    /**
     * 最大线程数量
     */
    private int max;
    /**
     * 传参线程池大小(当前线程池中线程的数量)
     */
    private int size;
    /**
     * 线程队列的大小
     */
    private final int queueSize;

    /**
     * 拒绝策略
     */
    private final DiscardPolicy discardPolicy;

    /**
     * 线程池是否被销毁
     */
    private volatile boolean destroy = false;

    /**
     * 默认最小线程数量
     */
    private static final int DEFAULT_MIN_SIZE = 4;
    /**
     * 默认活跃线程数量
     */
    private static final int DEFAULT_ACTIVE_SIZE = 8;
    /**
     * 默认最大线程数量
     */
    private static final int DEFAULT_MAX_SIZE = 12;
    /**
     * 默认线程队列的大小
     */
    private static final int DEFAULT_TASK_QUEUE_SIZE = 2000;

    /**
     * 默认拒绝策略-抛出异常
     */
    public static final DiscardPolicy DEFAULT_DISCARD_POLICY = () -> {
        throw new DiscardException("Discard This Task.");
    };

    /**
     * 线程名自增序号
     */
    private static AtomicInteger sequence = new AtomicInteger();

    /**
     * 线程名前缀
     */
    private static final String THREAD_PREFIX = "SIMPLE_THREAD_POOL-";

    /**
     * 线程组
     */
    private static final ThreadGroup GROUP = new ThreadGroup("Pool_Group");

    /**
     * 任务队列
     */
    private static final LinkedList<Runnable> TASK_QUEUE = new LinkedList<>();

    /**
     * 线程队列
     */
    private static final List<WorkerTask> THREAD_QUEUE = new ArrayList<>();

    public SimpleThreadPool() {
        this(DEFAULT_MIN_SIZE, DEFAULT_ACTIVE_SIZE, DEFAULT_MAX_SIZE, DEFAULT_TASK_QUEUE_SIZE, DEFAULT_DISCARD_POLICY);
    }

    /**
     * 构造方法
     *
     * @param min           最小线程数量
     * @param active        活跃线程数量
     * @param max           最大线程数量
     * @param queueSize     线程队列的大小
     * @param discardPolicy 拒绝策略
     */
    public SimpleThreadPool(int min, int active, int max, int queueSize, DiscardPolicy discardPolicy) {
        this.min = min;
        this.active = active;
        this.max = max;
        this.queueSize = queueSize;
        this.discardPolicy = discardPolicy;
        init();
    }

    @Override
    public void run() {
        while (!destroy) {
            System.out.printf("Pool#Min:%d,Active:%d,Max:%d,Current:%d,QueueSize:%d\n",
                    this.min, this.active, this.max, this.size, TASK_QUEUE.size());
            try {
                Thread.sleep(5_000L);
                // 任务队列数量过大，线程队列的数量扩充到active
                if (size < active && active < TASK_QUEUE.size()) {
                    IntStream.range(size, active).forEach(i -> createWorkTask());
                    size = active;
                    System.out.println("The Pool incremented to active.");
                }
                // 任务队列数量过大，线程队列的数量扩充到max
                else if (size < max && max < TASK_QUEUE.size()) {
                    IntStream.range(active, max).forEach(i -> createWorkTask());
                    size = max;
                    System.out.println("The Pool incremented to max.");
                }
                // 防止新插入任务队列的任务使用线程队列中的线程执行任务。
                synchronized (THREAD_QUEUE) {
                    // 任务队列数量为空，线程队列的数量减少到active
                    if (size > active && TASK_QUEUE.isEmpty()) {
                        System.out.println("=========Reduce=========");
                        int releaseSize = size - active;
                        for (Iterator<WorkerTask> it = THREAD_QUEUE.iterator(); it.hasNext(); ) {
                            if (releaseSize <= 0) {
                                break;
                            }
                            WorkerTask task = it.next();
                            task.interrupt();
                            task.close();
                            it.remove();
                            releaseSize--;
                        }
                        size = active;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void init() {
        IntStream.range(0, this.min).forEach(i -> createWorkTask());
        this.size = this.min;
        this.start();
    }

    private void createWorkTask() {
        WorkerTask task = new WorkerTask(GROUP, THREAD_PREFIX + (sequence.getAndIncrement()));
        task.start();
        THREAD_QUEUE.add(task);
    }

    /**
     * 提交任务
     *
     * @param runnable 任务
     */
    public void submit(Runnable runnable) {
        // 如果线程池被销毁，不能添加任务到任务队列
        if (destroy) {
            throw new IllegalThreadStateException("The thread pool already destroy and not allow submit task.");
        }
        synchronized (TASK_QUEUE) {
            // 任务队列数量超过阈值，则执行拒绝策略
            if (TASK_QUEUE.size() > queueSize) {
                discardPolicy.discard();
            }
            TASK_QUEUE.addLast(runnable);
            TASK_QUEUE.notifyAll();
        }
    }

    /**
     * 关闭线程池中的线程
     *
     * @throws InterruptedException
     */
    public void shutdown() throws InterruptedException {
        // 当任务队列还有任务，则稍微等待。
        while (!TASK_QUEUE.isEmpty()) {
            Thread.sleep(50);
        }
        // 判断线程中有没有正在运行的任务
        synchronized (THREAD_QUEUE) {
            int initVal = THREAD_QUEUE.size();
            while (initVal > 0) {
                for (WorkerTask task : THREAD_QUEUE) {
                    if (task.getTaskState() == TaskState.BLOCKED) {
                        task.interrupt();
                        task.close();
                        initVal--;
                    } else {
                        Thread.sleep(10);
                    }
                }
            }
        }

        System.out.println("Group active count is " + GROUP.activeCount());
        this.destroy = true;
        System.out.println("The thread pool disposed.");
    }

    public boolean isDestroy() {
        return destroy;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public int getActive() {
        return active;
    }

    public int getSize() {
        return size;
    }

    public int getQueueSize() {
        return queueSize;
    }

    /**
     * 封装任务类
     */
    private static class WorkerTask extends Thread {
        private volatile TaskState taskState = TaskState.FREE;

        public TaskState getTaskState() {
            return this.taskState;
        }

        public WorkerTask(ThreadGroup group, String name) {
            super(group, name);
        }

        @Override
        public void run() {
            OUTER:
            while (this.taskState != TaskState.DEAD) {
                Runnable runnable;
                synchronized (TASK_QUEUE) {
                    // 任务队列为空，则进入阻塞状态
                    while (TASK_QUEUE.isEmpty()) {
                        try {
                            this.taskState = TaskState.BLOCKED;
                            TASK_QUEUE.wait();
                        } catch (InterruptedException e) {
                            System.out.println("Closed.");
                            break OUTER;
                        }
                    }
                    // 任务队列不为空，取出任务
                    runnable = TASK_QUEUE.removeFirst();
                }
                // 任务不为空，则执行任务
                if (runnable != null) {
                    this.taskState = TaskState.RUNNING;
                    runnable.run();
                    this.taskState = TaskState.FREE;
                }
            }
        }

        public void close() {
            this.taskState = TaskState.DEAD;
        }
    }

    /**
     * 线程状态
     */
    private enum TaskState {
        FREE, RUNNING, BLOCKED, DEAD
    }

    /**
     * 拒绝策略
     */
    public interface DiscardPolicy {
        void discard() throws DiscardException;
    }

    /**
     * 拒绝策略异常
     */
    public static class DiscardException extends RuntimeException {
        public DiscardException(String message) {
            super(message);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SimpleThreadPool threadPool = new SimpleThreadPool();
        /*SimpleThreadPool threadPool = new SimpleThreadPool(6, 10, SimpleThreadPool.DEFAULT_DISCARD_POLICY);*/
        IntStream.rangeClosed(1, 40).forEach(i -> {
            threadPool.submit(() -> {
                System.out.printf("The runnable %d be serviced by %s start.\n", i, Thread.currentThread().getName());
                try {
                    Thread.sleep(3_000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("The runnable %d be serviced by %s finished.\n", i, Thread.currentThread().getName());
            });
        });

        Thread.sleep(30_000L);
        threadPool.shutdown();// 关闭线程池中的线程
        /*threadPool.submit(() -> System.out.println("线程池被销毁，不能添加任务到任务队列"));*/
    }
}
