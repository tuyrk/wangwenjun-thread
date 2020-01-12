package com.tuyrk.chapter04.lifecycle;

import lombok.Getter;

/**
 * 线程事件
 *
 * @author tuyrk
 */
@Getter
public class RunnableEvent {
    private final RunnableState state;
    private final Thread thread;
    private final Throwable cause;

    public RunnableEvent(RunnableState state, Thread thread, Throwable cause) {
        this.state = state;
        this.thread = thread;
        this.cause = cause;
    }
}
