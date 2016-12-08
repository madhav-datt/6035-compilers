package edu.mit.compilers.cfg;

import edu.mit.compilers.ll.LlComponent;

/**
 * Created by devinmorgan on 12/3/16.
 */
public abstract class Computation {

    public abstract boolean contains(LlComponent comp);

    public static Computation createUnaryComputation(String operation, LlComponent op) {
        return new UnaryComputation(operation, op);
    }

    public static Computation createBinaryComputation(LlComponent op1, String operation, LlComponent op2) {
        return new BinaryComputation(op1, operation, op2);
    }

    public static class BinaryComputation extends Computation{
        private final LlComponent op1;
        private final String operation;
        private final LlComponent op2;

        public BinaryComputation(LlComponent op1, String operation, LlComponent op2) {
            this.op1 = op1;
            this.operation = operation;
            this.op2 = op2;
        }

        @Override
        public boolean contains(LlComponent comp) {
            return this.op1.equals(comp) || this.op2.equals(comp);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof BinaryComputation) {
                BinaryComputation that = (BinaryComputation) obj;
                return this.op1.equals(that.op1)
                        && this.op2.equals(that.op2)
                        && this.operation.equals(that.operation);
            }
            return false;
        }
    }

    public static class UnaryComputation extends Computation {
        private final String operation;
        private final LlComponent op;

        public UnaryComputation(String operation, LlComponent op) {
            this.operation = operation;
            this.op = op;
        }

        @Override
        public boolean contains(LlComponent comp) {
            return this.op.equals(comp);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof UnaryComputation) {
                UnaryComputation that = (UnaryComputation) obj;
                return this.op.equals(that.op)
                        && this.operation.equals(that.operation);
            }
            return false;
        }
    }


}
