package com.tuyrk.chapter08;

/**
 * 同步调用
 *
 * @author tuyrk
 */
public class SyncInvoker {
    public static void main(String[] args) throws InterruptedException {
        // 陷入阻塞
        // main()等待get()执行完成，也即get()的阻塞导致了main()的阻塞
        String result = get();
        System.out.println(result);
    }

    /**
     * 从数据库、网络、磁盘等获取数据（耗时操作）
     *
     * @return 返回结果
     */
    private static String get() throws InterruptedException {
        Thread.sleep(10_000);
        return "FINISH";
    }
}
