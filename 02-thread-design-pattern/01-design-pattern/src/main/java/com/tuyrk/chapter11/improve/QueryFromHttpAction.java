package com.tuyrk.chapter11.improve;

import com.tuyrk.chapter11.Context;

/**
 * 线程运行上下文设计模式
 * 调用接口获取数据
 *
 * @author tuyrk
 */
public class QueryFromHttpAction {
    public void execute() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ActionContext actionContext = ActionContext.getInstance();
        Context context = actionContext.getContext();
        String name = context.getName();
        String cardId = getCardId(name);
        context.setCardId(cardId);
    }

    private String getCardId(String name) {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "510823" + Thread.currentThread().getId();
    }
}
