package io.asma;

/**
 * Simple 2 input Block
 */
class SimpleBlock extends Block {
    final String name;
    final Connection up;
    final Connection down;

    SimpleBlock(String name, Connection upCon, Connection downCon/*, Connection upRight, Connection downRight*/) {
        this.name = name;
        this.up = upCon;
        this.down = downCon;
    }

    @Override
    public String toString() {
        return null;
    }
}
