package com.tuyrk.chapter02;

@FunctionalInterface
public interface CalculatorStrategy {
    /**
     *
     * @param salary 薪水
     * @param bonus 奖金
     * @return 税收
     */
    double calculate(double salary, double bonus);
}
