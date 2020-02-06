package com.tuyrk.chapter11;

/**
 * 线程运行上下文设计模式
 * 线程执行任务
 *
 * @author tuyrk
 */
public class ExecutionTask implements Runnable {
    private QueryFromDbAction queryAction = new QueryFromDbAction();
    private QueryFromHttpAction httpAction = new QueryFromHttpAction();

    @Override
    public void run() {
        Context context = new Context();
        queryAction.execute(context);
        System.out.println("The name query successful.");
        httpAction.execute(context);
        System.out.println("The card id query successful.");
        System.out.println("The Name is " + context.getName() + " and CardId is " + context.getCardId());
        System.out.println(context);
    }
}
