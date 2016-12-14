package edu.mit.compilers.cfg;

/**
 * Created by devinmorgan on 12/13/16.
 */
public class BlockLabelPair {
    private final BasicBlock basicBlock;
    private final String label;

    public BlockLabelPair(BasicBlock basicBlock, String label) {
        this.basicBlock = basicBlock;
        this.label = label;
    }

    public BasicBlock getBasicBlock() {
        return this.basicBlock;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BlockLabelPair) {
            BlockLabelPair that = (BlockLabelPair) obj;
            return this.basicBlock.equals(that.basicBlock) && this.label.equals(that.label);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.basicBlock.hashCode() * this.label.hashCode();
    }
}
