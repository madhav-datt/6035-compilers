package edu.mit.compilers.ir;

import edu.mit.compilers.LlBuilder;
import edu.mit.compilers.ll.LlComponent;
import edu.mit.compilers.ll.LlLocationVar;

public abstract  class IrExpr extends Ir {

    public IrExpr(int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
    }
    public abstract IrType getExpressionType();
    public String toString(){
        return "IrExpr";
    }

    // returns an expression that is in the standard
    // canonical form according to the 20 rules found
    // the whale book
    public static IrExpr canonicalizeExpr(IrExpr expr) {

        if (expr instanceof IrOperBinaryArith) {
            IrOperBinaryArith binArth = (IrOperBinaryArith) expr;
            IrExpr op1 = binArth.getLeftOperand();
            String operation = binArth.getOperation();
            IrExpr op2 = binArth.getRightOperand();

            // Rule 1
            if (op1 instanceof IrLiteralInt && operation.equals("+") && op2 instanceof IrLiteralInt) {
                long c1 = ((IrLiteralInt) op1).getValue();
                long c2 = ((IrLiteralInt) op2).getValue();
                return new IrLiteralInt(c1 + c2, expr.getLineNumber(), expr.getColNumber());
            }

            // Rule 3
            if (op2 instanceof IrLiteralInt && operation.equals("*") && op2 instanceof IrLiteralInt) {
                long c1 = ((IrLiteralInt) op1).getValue();
                long c2 = ((IrLiteralInt) op2).getValue();
                return new IrLiteralInt(c1 * c2, expr.getLineNumber(), expr.getColNumber());
            }

            // Rule 5
            if (op2 instanceof IrLiteralInt && operation.equals("-") && op2 instanceof IrLiteralInt) {
                long c1 = ((IrLiteralInt) op1).getValue();
                long c2 = ((IrLiteralInt) op2).getValue();
                return new IrLiteralInt(c1 - c2, expr.getLineNumber(), expr.getColNumber());
            }

            // Rule 2
            if (operation.equals("+") && op2 instanceof IrLiteralInt) {
                IrExpr canonicalizeOp1 = canonicalizeExpr(op1);
                return new IrOperBinaryArith("+", op2, canonicalizeOp1);
            }

            // Rule 4
            if (operation.equals("*") && op2 instanceof IrLiteralInt) {
                IrExpr canonicalizeOp1 = canonicalizeExpr(op1);
                return new IrOperBinaryArith("*", op2, canonicalizeOp1);
            }

            // Rule 6
            if (operation.equals("-") && op2 instanceof IrLiteralInt) {
                IrExpr canonicalizeOp1 = canonicalizeExpr(op1);
                IrLiteralInt c2 = (IrLiteralInt) op2;
                IrLiteralInt negatedOp2 = new IrLiteralInt(-1*c2.getValue(), c2.getLineNumber(), c2.getColNumber());
                return new IrOperBinaryArith(operation, negatedOp2, canonicalizeOp1);
            }

            // Rule 7
            if (operation.equals("+") && op2 instanceof IrOperBinaryArith) {
                // left sub-tree
                IrExpr t1 = canonicalizeExpr(op1);

                // right sub-tree
                IrOperBinaryArith rightSubTree = (IrOperBinaryArith) op2;
                IrExpr t2 = canonicalizeExpr(rightSubTree.getLeftOperand());
                IrExpr t3 = canonicalizeExpr(rightSubTree.getRightOperand());

                // re-structure tree
                IrOperBinaryArith newLeftSubTree = new IrOperBinaryArith("+", t1, t2);
                return new IrOperBinaryArith("+", newLeftSubTree, t3);
            }

            // Rule 8
            if (operation.equals("*") && op2 instanceof IrOperBinaryArith) {
                // left sub-tree
                IrExpr t1 = canonicalizeExpr(op1);

                // right sub-tree
                IrOperBinaryArith rightSubTree = (IrOperBinaryArith) op2;
                IrExpr t2 = canonicalizeExpr(rightSubTree.getLeftOperand());
                IrExpr t3 = canonicalizeExpr(rightSubTree.getRightOperand());

                // re-structure tree
                IrOperBinaryArith newLeftSubTree = new IrOperBinaryArith("*", t1, t2);
                return new IrOperBinaryArith("*", newLeftSubTree, t3);
            }

            // Rule 9
            if (op1 instanceof IrOperBinaryArith && operation.equals("+") && op2 instanceof IrLiteralInt) {
                IrOperBinaryArith leftSubTree = (IrOperBinaryArith) op1;

                if (leftSubTree.getLeftOperand() instanceof IrLiteralInt && leftSubTree.getOperation().equals("+")) {
                    IrLiteralInt c1 = (IrLiteralInt) leftSubTree.getLeftOperand();
                    IrExpr t = canonicalizeExpr(leftSubTree.getRightOperand());
                    IrLiteralInt c2 = (IrLiteralInt) op2;
                    IrLiteralInt c3 = new IrLiteralInt(c1.getValue() + c2.getValue(), c1.getLineNumber(), c2.getColNumber());
                    return new IrOperBinaryArith("+", c3, t);
                }
            }

            // Rule 10
            if (op1 instanceof IrOperBinaryArith && operation.equals("*") && op2 instanceof IrLiteralInt) {
                IrOperBinaryArith leftSubTree = (IrOperBinaryArith) op1;

                if (leftSubTree.getLeftOperand() instanceof IrLiteralInt && leftSubTree.getOperation().equals("*")) {
                    IrLiteralInt c1 = (IrLiteralInt) leftSubTree.getLeftOperand();
                    IrExpr t = canonicalizeExpr(leftSubTree.getRightOperand());
                    IrLiteralInt c2 = (IrLiteralInt) op2;
                    IrLiteralInt c3 = new IrLiteralInt(c1.getValue() * c2.getValue(), c1.getLineNumber(), c2.getColNumber());
                    return new IrOperBinaryArith("*", c3, t);
                }
            }

            // Rule 11
            if (op1 instanceof IrOperBinaryArith && operation.equals("*") && op2 instanceof IrLiteralInt) {
                IrOperBinaryArith leftSubTree = (IrOperBinaryArith) op1;

                if (leftSubTree.getLeftOperand() instanceof IrLiteralInt && leftSubTree.getOperation().equals("+")) {
                    IrLiteralInt c1 = (IrLiteralInt) leftSubTree.getLeftOperand();
                    IrExpr t = canonicalizeExpr(leftSubTree.getRightOperand());
                    IrLiteralInt c2 = (IrLiteralInt) op2;

                    // create a re-structured tree
                    IrLiteralInt newLeftSubTree = new IrLiteralInt(c1.getValue() * c2.getValue(), c1.getLineNumber(), c2.getColNumber());
                    IrOperBinaryArith newRightSubTree = new IrOperBinaryArith("*", c2, t);
                    return new IrOperBinaryArith("+", newLeftSubTree, newRightSubTree);
                }
            }

            // Rule 12
            if (op1 instanceof IrLiteralInt && operation.equals("*") && op2 instanceof IrOperBinaryArith) {
                IrOperBinaryArith rightSubTree = (IrOperBinaryArith) op2;

                if (rightSubTree.getLeftOperand() instanceof IrLiteralInt && rightSubTree.getOperation().equals("+")) {
                    IrLiteralInt c1 = (IrLiteralInt) op1;
                    IrLiteralInt c2 = (IrLiteralInt) rightSubTree.getLeftOperand();
                    IrExpr t = canonicalizeExpr(rightSubTree.getRightOperand());

                    // create a re-structured tree
                    IrLiteralInt newLeftSubTree = new IrLiteralInt(c1.getValue() * c2.getValue(), c1.getLineNumber(), c2.getColNumber());
                    IrOperBinaryArith newRightSubTree = new IrOperBinaryArith("*", c1, t);
                    return new IrOperBinaryArith("+", newLeftSubTree, newRightSubTree);
                }
            }

            // Rule 13
            if (op1 instanceof IrOperBinaryArith && operation.equals("*") && op2 instanceof IrLiteralInt) {
                IrOperBinaryArith leftSubTree = (IrOperBinaryArith) op1;

                if (leftSubTree.getOperation().equals("+")) {
                    IrExpr t1 = canonicalizeExpr(leftSubTree.getLeftOperand());
                    IrExpr t2 = canonicalizeExpr(leftSubTree.getRightOperand());
                    IrLiteralInt c = (IrLiteralInt) op2;

                    // create a re-structured tree
                    IrOperBinaryArith newLeftSubTree = new IrOperBinaryArith("*", c, t1);
                    IrOperBinaryArith newRightSubTree = new IrOperBinaryArith("*", c, t2);
                    return new IrOperBinaryArith("+", newLeftSubTree, newRightSubTree);
                }
            }

            // Rule 14
            if (op1 instanceof IrLiteral && operation.equals("*") && op2 instanceof IrOperBinaryArith) {
                IrOperBinaryArith rightSubTree = (IrOperBinaryArith) op2;

                if (rightSubTree.getOperation().equals("+")) {
                    IrLiteralInt c = (IrLiteralInt) op1;
                    IrExpr t1 = canonicalizeExpr(rightSubTree.getLeftOperand());
                    IrExpr t2 = canonicalizeExpr(rightSubTree.getRightOperand());

                    // create a re-structured tree
                    IrOperBinaryArith newLeftSubTree = new IrOperBinaryArith("*", c, t1);
                    IrOperBinaryArith newRightSubTree = new IrOperBinaryArith("*", c, t2);
                    return new IrOperBinaryArith("+", newLeftSubTree, newRightSubTree);
                }
            }

            // Rule 15
            if (op1 instanceof IrOperBinaryArith && operation.equals("*") && op2 instanceof IrLiteralInt) {
                IrOperBinaryArith leftSubTree = (IrOperBinaryArith) op1;

                if (leftSubTree.getOperation().equals("-")) {
                    IrExpr t1 = canonicalizeExpr(leftSubTree.getLeftOperand());
                    IrExpr t2 = canonicalizeExpr(leftSubTree.getRightOperand());
                    IrLiteralInt c = (IrLiteralInt) op2;

                    // create a re-structured tree
                    IrOperBinaryArith newLeftSubTree = new IrOperBinaryArith("*", c, t1);
                    IrOperBinaryArith newRightSubTree = new IrOperBinaryArith("*", c, t2);
                    return new IrOperBinaryArith("-", newLeftSubTree, newRightSubTree);
                }
            }

            // Rule 16
            if (op1 instanceof IrLiteral && operation.equals("*") && op2 instanceof IrOperBinaryArith) {
                IrOperBinaryArith rightSubTree = (IrOperBinaryArith) op2;

                if (rightSubTree.getOperation().equals("-")) {
                    IrLiteralInt c = (IrLiteralInt) op1;
                    IrExpr t1 = canonicalizeExpr(rightSubTree.getLeftOperand());
                    IrExpr t2 = canonicalizeExpr(rightSubTree.getRightOperand());

                    // create a re-structured tree
                    IrOperBinaryArith newLeftSubTree = new IrOperBinaryArith("*", c, t1);
                    IrOperBinaryArith newRightSubTree = new IrOperBinaryArith("*", c, t2);
                    return new IrOperBinaryArith("-", newLeftSubTree, newRightSubTree);
                }
            }

            // Rule 17
            if (op1 instanceof IrOperBinaryArith && operation.equals("*")) {
                IrOperBinaryArith leftSubTree = (IrOperBinaryArith) op1;

                if (leftSubTree.getOperation().equals("+")) {
                    IrExpr t1 = canonicalizeExpr(leftSubTree.getLeftOperand());
                    IrExpr t2 = canonicalizeExpr(leftSubTree.getRightOperand());
                    IrExpr t3 = canonicalizeExpr(op2);

                    // create a re-structured tree
                    IrOperBinaryArith newLeftSubTree = new IrOperBinaryArith("*", t1, t3);
                    IrOperBinaryArith newRightSubTree = new IrOperBinaryArith("*", t2, t3);
                    return new IrOperBinaryArith("+", newLeftSubTree, newRightSubTree);
                }
            }

            // Rule 18
            if (operation.equals("*") && op2 instanceof IrOperBinaryArith) {
                IrOperBinaryArith rightSubTree = (IrOperBinaryArith) op2;

                if (rightSubTree.getOperation().equals("+")) {
                    IrExpr t1 = canonicalizeExpr(op1);
                    IrExpr t2 = canonicalizeExpr(rightSubTree.getLeftOperand());
                    IrExpr t3 = canonicalizeExpr(rightSubTree.getRightOperand());

                    // create a re-structured tree
                    IrOperBinaryArith newLeftSubTree = new IrOperBinaryArith("*", t1, t2);
                    IrOperBinaryArith newRightSubTree = new IrOperBinaryArith("*", t1, t3);
                    return new IrOperBinaryArith("+", newLeftSubTree, newRightSubTree);
                }
            }

            // Rule 19
            if (op1 instanceof IrOperBinaryArith && operation.equals("*")) {
                IrOperBinaryArith leftSubTree = (IrOperBinaryArith) op1;

                if (leftSubTree.getOperation().equals("-")) {
                    IrExpr t1 = canonicalizeExpr(leftSubTree.getLeftOperand());
                    IrExpr t2 = canonicalizeExpr(leftSubTree.getRightOperand());
                    IrExpr t3 = canonicalizeExpr(op2);

                    // create a re-structured tree
                    IrOperBinaryArith newLeftSubTree = new IrOperBinaryArith("*", t1, t3);
                    IrOperBinaryArith newRightSubTree = new IrOperBinaryArith("*", t2, t3);
                    return new IrOperBinaryArith("-", newLeftSubTree, newRightSubTree);
                }
            }

            // Rule 20
            if (operation.equals("*") && op2 instanceof IrOperBinaryArith) {
                IrOperBinaryArith rightSubTree = (IrOperBinaryArith) op2;

                if (rightSubTree.getOperation().equals("-")) {
                    IrExpr t1 = canonicalizeExpr(op1);
                    IrExpr t2 = canonicalizeExpr(rightSubTree.getLeftOperand());
                    IrExpr t3 = canonicalizeExpr(rightSubTree.getRightOperand());

                    // create a re-structured tree
                    IrOperBinaryArith newLeftSubTree = new IrOperBinaryArith("*", t1, t2);
                    IrOperBinaryArith newRightSubTree = new IrOperBinaryArith("*", t1, t3);
                    return new IrOperBinaryArith("-", newLeftSubTree, newRightSubTree);
                }
            }
        }

        // base case: if it doesn't match any of the above conditions just return the expression itself.
        return expr;
    }

}