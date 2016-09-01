package com.gizwits.framework.widget;

public class ArrayWheelAdapter<T> implements WheelAdapter {
    public static final int DEFAULT_LENGTH = -1;
    private T[] items;
    private int length;

    public ArrayWheelAdapter(T[] tArr) {
        this(tArr, -1);
    }

    public ArrayWheelAdapter(T[] tArr, int i) {
        this.items = tArr;
        this.length = i;
    }

    public String getItem(int i) {
        return (i < 0 || i >= this.items.length) ? null : this.items[i].toString();
    }

    public int getItemsCount() {
        return this.items.length;
    }

    public int getMaximumLength() {
        return this.length;
    }
}
