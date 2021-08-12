package io.asma;

import java.util.ArrayList;

class Column<T extends Block> {
    private final ArrayList<T> blocks;

    Column() {
        this.blocks = new ArrayList<T>();
    }

    public void add(T block) {
        blocks.add(block);
    }

    public ArrayList<T> getAll() {
        return blocks;
    }

    public Block getBlockByValue(int value) {
        return blocks.get((int) Math.ceil((value / 2.0)) - 1);
    }

    public Block getBlockByIndex(int index) {
        return blocks.get(index);
    }
}
