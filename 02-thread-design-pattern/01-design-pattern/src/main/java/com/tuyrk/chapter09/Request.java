package com.tuyrk.chapter09;

import lombok.Getter;

/**
 * Guarded Suspension设计模式
 * 请求
 *
 * @author tuyrk
 */
@Getter
public class Request {
    private final String value;

    public Request(String value) {
        this.value = value;
    }
}
