package com.tuyrk.chapter02;

/**
 * 计算税率：工资*0.1 + 奖金*0.15
 */
public class TaxCalculatorMain {
    public static void main(String[] args) {
        // 1. 模版方法
        /*TaxCalculator calculator = new TaxCalculator(10000d, 2000d) {
            @Override
            protected double calcTax() {
                return getSalary() * 0.1 + getBonus() * 0.15;
            }
        };
        System.out.println(calculator.calculate());*/

        // 2.1 策略模式
        /*TaxCalculator calculator = new TaxCalculator(10000d, 2000d);
        CalculatorStrategy strategy = new SimpleCalculatorStrategy();
        calculator.setCalculatorStrategy(strategy);
        System.out.println(calculator.calculate());*/

        // 2.2 策略模式-Java8Lambda
        /*TaxCalculator calculator = new TaxCalculator(10000d, 2000d);
        calculator.setCalculatorStrategy((s, b) -> s * 0.1 + b * 0.15);
        System.out.println(calculator.calculate());*/
        TaxCalculator calculator = new TaxCalculator(10000d, 2000d, (s, b) -> s * 0.1 + b * 0.15);
        System.out.println(calculator.calculate());
    }
}
