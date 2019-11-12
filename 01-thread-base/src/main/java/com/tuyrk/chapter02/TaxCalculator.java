package com.tuyrk.chapter02;

import lombok.Data;

/**
 * 税务计算器。Runnable与多线程设计模式
 */
@Data
public class TaxCalculator {
    // 薪水
    private final double salary;
    // 奖金
    private final double bonus;

    private CalculatorStrategy calculatorStrategy;

    public TaxCalculator(double salary, double bonus) {
        this.salary = salary;
        this.bonus = bonus;
    }

    public TaxCalculator(double salary, double bonus, CalculatorStrategy calculatorStrategy) {
        this.salary = salary;
        this.bonus = bonus;
        this.calculatorStrategy = calculatorStrategy;
    }

    /**
     * 计算税率
     *
     * @return
     */
    protected double calcTax() {
        return calculatorStrategy.calculate(salary, bonus);
    }

    /**
     * 计算
     *
     * @return
     */
    public double calculate() {
        return this.calcTax();
    }
}
