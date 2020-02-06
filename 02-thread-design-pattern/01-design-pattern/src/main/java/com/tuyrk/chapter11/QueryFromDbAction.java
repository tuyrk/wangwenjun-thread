package com.tuyrk.chapter11;

/**
 * 线程运行上下文设计模式
 * 从数据库查询数据
 *
 * @author tuyrk
 */
public class QueryFromDbAction {
    public void execute(Context context) {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String name = "Alex-" + Thread.currentThread().getName();
        context.setName(name);
    }
}
