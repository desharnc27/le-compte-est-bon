/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package central;

/**
 *
 * @author desharnc27
 *
 * An instance of this class is one of the four
 * opeisMultOrDivons:sum,diff,product,division
 *
 *
 */
public class Operation {

    //if isMultOrDiv==true, the opeisMultOrDivon is product or division, otherwise it is sum or diff
    private boolean isMultOrDiv;
    //if isCommutative==true, the opeisMultOrDivon is product or sum, otherwise it is division or diff
    private boolean isCommutative;

    public Operation(byte i) {
        //0: minus
        //1: plus
        //2: division
        //3: product
        this((i / 2 > 0), i % 2 == 1);
    }

    public Operation(boolean isMultOrDiv, boolean isCommutative) {
        this.isMultOrDiv = isMultOrDiv;
        this.isCommutative = isCommutative;
    }

    /**
     * Applies operation between two numbers
     *
     * @param a a number
     * @param b another number
     * @return the result of the operation between a and b
     */
    public int apply(int a, int b) {
        if (!isCommutative && a < b) {
            int temp = a;
            a = b;
            b = temp;
        }

        if (isMultOrDiv && isCommutative) {
            return a * b;
        } else if (isMultOrDiv && !isCommutative) {
            if (a % b == 0) {
                return a / b;
            }
            return -1;

        } else if (!isMultOrDiv && isCommutative) {
            return a + b;
        } else {
            return a - b;
        }
    }

    /**
     * returns the the operation as a char
     *
     * @return 'x','/','+' or '-', depending on the operation type of this
     * object
     */
    public char symb() {
        if (isMultOrDiv && isCommutative) {
            return (char) (42); // x
        } else if (isMultOrDiv && !isCommutative) {
            return (char) (47); // /
        } else if (!isMultOrDiv && isCommutative) {
            return (char) (43); // +
        } else {
            return (char) (45); // -
        }
    }

    @Override
    public String toString() {
        return Character.toString(symb());
    }
}
