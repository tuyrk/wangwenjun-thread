# 自定义个简单的线程池并且测试

```java
public class SimpleThreadPool {
    // 传参线程池大小
    private final int size;
    // 默认线程池大小
    private static final int DEFAULT_SIZE = 10;
    // 线程名自增序号
    private static AtomicInteger sequence = new AtomicInteger();
    // 线程名前缀
    private static final String THREAD_PREFIX = "SIMPLE_THREAD_POOL-";
    // 线程组
    private static final ThreadGroup GROUP = new ThreadGroup("Pool_Group");
    // 任务队列
    private static final LinkedList<Runnable> TASK_QUEUE = new LinkedList<>();
    // 线程队列
    private static final List<WorkerTask> THREAD_QUEUE = new ArrayList<>();

    public SimpleThreadPool() {
        this(DEFAULT_SIZE);
    }
    public SimpleThreadPool(int size) {
        this.size = size;
        init();
    }

    private void init() {
        for (int i = 0; i < size; i++) {
            createWorkTask();
        }
    }
    private void createWorkTask() {
        WorkerTask task = new WorkerTask(GROUP, THREAD_PREFIX + (sequence.getAndIncrement()));
        task.start();
        THREAD_QUEUE.add(task);
    }

    // 提交任务
    public void submit(Runnable runnable) {
        synchronized (TASK_QUEUE) {
            TASK_QUEUE.addLast(runnable);
            TASK_QUEUE.notifyAll();
        }
    }

    // 封装任务类
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
                            break OUTER;
                        }
                    }
                    // 任务队列不为空，取出任务。很快
                    runnable = TASK_QUEUE.removeFirst();
                }
                // 任务不为空，则执行任务。很慢
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

    // 线程状态
    private enum TaskState {
        FREE, RUNNING, BLOCKED, DEAD
    }
}
```

```java
public static void main(String[] args) {
    SimpleThreadPool threadPool = new SimpleThreadPool();
    IntStream.rangeClosed(1, 40).forEach(i -> {
        threadPool.submit(() -> {
            System.out.printf("The runnable %d be serviced by %s start.\n", i, Thread.currentThread().getName());
            Thread.sleep(10_000);
            System.out.printf("The runnable %d be serviced by %s finished.\n", i, Thread.currentThread().getName());
        });
    });
}
```

实现了任务队列，还未支持拒绝策略。

目前只有init，还需实现active，max

还需实现停止线程池（shutdown）