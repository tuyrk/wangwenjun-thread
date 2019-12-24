package com.tuyrk.chapter02;

import org.junit.Test;

/**
 * 计算税率：工资*0.1 + 奖金*0.15
 */
public class TaxCalculatorMain {
    /**
     * 1. 模版方法
     */
    @Test
    public void test1() {
        TaxCalculator calculator = new TaxCalculator(10000d, 2000d) {
            @Override
            protected double calcTax() {
                return getSalary() * 0.1 + getBonus() * 0.15;
            }
        };
        System.out.println(calculator.calculate());
    }

    /**
     * 2.1 策略模式
     */
    @Test
    public void test2() {
        TaxCalculator calculator = new TaxCalculator(10000d, 2000d);
        CalculatorStrategy strategy = new SimpleCalculatorStrategy();
        calculator.setCalculatorStrategy(strategy);
        System.out.println(calculator.calculate());
    }

    /**
     * 2.2 策略模式-Java8Lambda-1
     */
    @Test
    public void test3() {
        TaxCalculator calculator = new TaxCalculator(10000d, 2000d);
        calculator.setCalculatorStrategy((s, b) -> s * 0.1 + b * 0.15);
        System.out.println(calculator.calculate());
    }

    /**
     * 2.2 策略模式-Java8Lambda-2
     */
    @Test
    public void test4() {
        TaxCalculator calculator = new TaxCalculator(10000d, 2000d, (s, b) -> s * 0.1 + b * 0.15);
        System.out.println(calculator.calculate());
    }
}
