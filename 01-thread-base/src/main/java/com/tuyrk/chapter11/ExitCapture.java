package com.tuyrk.chapter11;

/**
 * javac ExitCapture.java
 * java -cp . ExitCapture
 * <p>
 * nohup java -cp . ExitCapture &
 * tail -f nohup.out
 * <p>
 * <p>
 * cd 01-thread-base/src/main/java/
 * javac com/tuyrk/chapter11/ExitCapture.java
 * java com.tuyrk.chapter11.ExitCapture
 * nohup java com.tuyrk.chapter11.ExitCapture &
 * tail -f nohup.out
 *
 * @author tuyrk
 */
public class ExitCapture {
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("The application will be exit.");
            notifyAndRelease();
        }));
        // int i = 0;
        while (true) {
            try {
                Thread.sleep(1_000L);
                System.out.println("I am working...");
            } catch (InterruptedException e) {
            }
            // i++;
            // if (i > 20) {
            //     throw new RuntimeException("Error");
            // }
        }
    }

    private static void notifyAndRelease() {
        System.out.println("notify to the admin.");
        try {
            Thread.sleep(1_000L);
        } catch (InterruptedException e) {
        }
        System.out.println("Will release resource(socket, file, connection.)");
        try {
            Thread.sleep(1_000L);
        } catch (InterruptedException e) {
        }
        System.out.println("Release and Notify Done!");
    }
}
