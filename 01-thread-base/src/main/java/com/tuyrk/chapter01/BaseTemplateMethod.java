package com.tuyrk.chapter01;

public abstract class BaseTemplateMethod {
    public static void main(String[] args) {
        BaseTemplateMethod t1 = new BaseTemplateMethod() {

            @Override
            protected void wrapPrint(String message) {
                System.out.println("*" + message + "*");
            }
        };
        t1.print("Hello Thread");

        BaseTemplateMethod t2 = new BaseTemplateMethod() {
            @Override
            protected void wrapPrint(String message) {
                System.out.println("+" + message + "+");
            }
        };
        t2.print("Hello Thread");
    }

    public final void print(String message) {
        System.out.println("#####################");
        wrapPrint(message);
        System.out.println("#####################");
    }

    protected abstract void wrapPrint(String message);
}
