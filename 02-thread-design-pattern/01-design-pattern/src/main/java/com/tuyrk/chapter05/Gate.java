package com.tuyrk.chapter05;

/**
 * 门。共享资源
 *
 * @author tuyrk
 */
public class Gate {
    private int counter = 0;
    private String name = "Nobody";
    private String address = "Nowhere";

    public void pass(String name, String address) {
        this.counter++;
        this.name = name;
        this.address = address;
        verify();
    }

    private void verify() {
        if (this.name.charAt(0) != this.address.charAt(0)) {
            System.out.println("***********BROKEN***********" + toString());
        }
    }

    @Override
    public String toString() {
        return "No." + counter + ":" + name + "," + address;
    }
}
