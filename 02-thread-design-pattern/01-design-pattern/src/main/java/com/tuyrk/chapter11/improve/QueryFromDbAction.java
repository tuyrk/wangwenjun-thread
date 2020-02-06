package com.tuyrk.chapter11.improve;

import com.tuyrk.chapter11.Context;

/**
 * 线程运行上下文设计模式
 * 从数据库查询数据
 *
 * @author tuyrk
 */
public class QueryFromDbAction {
    public void execute() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String name = "Alex-" + Thread.currentThread().getName();
        ActionContext actionContext = ActionContext.getInstance();
        Context context = actionContext.getContext();
        context.setName(name);
    }
}
