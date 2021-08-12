package io.asma;

/**
 *  Blocks with more than 2 inputs e.g. 4x4 or 8x8
 */
class ComplexBlock extends Block {
    final Column<SimpleBlock> left;
    final Column<Block> mid;
    final Column<SimpleBlock> right;


    ComplexBlock(Column<SimpleBlock> left, Column<Block> mid, Column<SimpleBlock> right) {
        this.left = left;
        this.mid = mid;
        this.right = right;
    }

    @Override
    public String toString() {
        return null;
    }

    public String doCycle() {
        return "";
    }

}
