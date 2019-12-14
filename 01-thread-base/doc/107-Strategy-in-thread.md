# 策略模式在Thread和Runnable中的应用分析

Runnable接口与多线程中的哪种设计模式比较接近？

- 策略模式

举例：税收计算器。需多看代码示例，深入理解策略模式。

```java
@FunctionalInterface
public interface CalculatorStrategy {
    /**
     * @param salary 薪水
     * @param bonus 奖金
     * @return 税收
     */
    double calculate(double salary, double bonus);
}
```

```java
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
```

```java
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

    // 计算税率
    protected double calcTax() {
        return calculatorStrategy.calculate(salary, bonus);
    }

    // 计算
    public double calculate() {
        return this.calcTax();
    }
}
```

```java
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
```



通过策略模式的思想改造`BankRunnable.java`

```java
/**
 * 银行大厅
 */
public class BankRunnable {
    private final static int MAX = 50;
    private int index = 1;

    public static void main(String[] args) {
        // 1. 一个runnable实例被多个线程共享
        /*TicketWindowRunnable ticketWindow = new TicketWindowRunnable();*/
        // 2. 策略模式思想改造runnable实例线程
        Runnable ticketWindow = new BankRunnable().getTicketWindow();
      
        Thread windowThread1 = new Thread(ticketWindow, "一号窗口");
        Thread windowThread2 = new Thread(ticketWindow, "二号窗口");
        Thread windowThread3 = new Thread(ticketWindow, "三号窗口");
        windowThread1.start();
        windowThread2.start();
        windowThread3.start();
    }

    private Runnable getTicketWindow() {
        return () -> {
            while (index <= MAX) {
                System.out.println(Thread.currentThread() + " 的号码是:" + (index++));
                Thread.sleep(100);
            }
        };
    }
}
```



