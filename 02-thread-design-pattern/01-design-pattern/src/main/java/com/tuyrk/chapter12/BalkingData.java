package com.tuyrk.chapter12;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Balking设计模式
 * 数据文件
 *
 * @author tuyrk
 */
public class BalkingData {
    private final String filename;
    private String context;
    private boolean changed;

    public BalkingData(String filename, String context) {
        this.filename = filename;
        this.context = context;
        this.changed = true;
    }

    /**
     * 用户变化
     */
    public synchronized void change(String newContext) {
        this.context = newContext;
        this.changed = true;
    }

    /**
     * 服务保存
     */
    public synchronized void save() throws IOException {
        if (!changed) {
            return;
        }
        doSave();
        this.changed = false;
    }

    private void doSave() throws IOException {
        System.out.println(Thread.currentThread().getName() + " calls do save, content=" + context);
        String newLine = System.getProperty("line.separator");
        try (Writer writer = new FileWriter(filename, true)) {
            writer.write(context);
            writer.write(newLine);
            writer.flush();
        }
    }
}
