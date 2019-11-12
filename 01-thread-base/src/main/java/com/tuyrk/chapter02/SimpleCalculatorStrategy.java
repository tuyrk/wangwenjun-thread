package com.tuyrk.chapter02;

/**
 * 计算税率：工资*0.1 + 奖金*0.15
 */
public class SimpleCalculatorStrategy implements CalculatorStrategy {
    private final static double SALARY_RATE = 0.1;
    private final static double BONUS_RATE = 0.15;

    @Override
    public double calculate(double salary, double bonus) {
        return salary * SALARY_RATE + bonus * BONUS_RATE;
    }
}
