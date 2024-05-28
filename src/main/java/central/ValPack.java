/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package central;

/**
 * One instance of this class contains a value and the way to get it
 * (represented recursively by its two last ancestors and the operation between
 * these two. If many ways to reach the value are possible with the same
 * starting numbers, only one is remembered. However, the number of ways to get
 * this value with the same numbers is counted (even if it's not necessary for
 * the goal of the project).
 *
 * @author desharnc27
 */
class ValPack implements Comparable<ValPack> {

    int val;
    ValPack ancestor0;
    ValPack ancestor1;
    Operation op;
    int nbPoss = 0;

    public ValPack(int val) {
        this.val = val;
        nbPoss = 1;
    }

    /**
     * Creates a new valPack by combining two valpacks with a specific operation
     *
     * @param vp0 a valpack
     * @param vp1 another valpack
     * @param op an operation
     */
    public ValPack(ValPack vp0, ValPack vp1, Operation op) {
        ancestor0 = vp0;
        ancestor1 = vp1;
        this.op = op;
        val = op.apply(ancestor0.val, ancestor1.val);
        this.nbPoss = vp0.nbPoss * vp1.nbPoss;
    }

    /**
     *
     * @param vp0
     */
    public void increasePossibilityCount(ValPack vp0) {
        nbPoss += vp0.nbPoss;
    }

    public String getDetailedStrPossibility() {
        if (ancestor1 == null) {
            return val + "";
        }
        ValPack left = ancestor0;
        ValPack right = ancestor1;
        if (left.val < right.val) {
            left = ancestor1;
            right = ancestor0;
        }
        String ls = left.getDetailedStrPossibility();
        String rs = right.getDetailedStrPossibility();
        if (left.ancestor1 != null) {
            ls = MiscUtils.enparen(ls);
        }
        if (right.ancestor1 != null) {
            rs = MiscUtils.enparen(rs);
        }

        return ls + op + rs;
    }

    public String getAllDescAt(int maxDepth) {
        if (ancestor1 == null || maxDepth == 0) {
            return val + "";
        }
        ValPack left = ancestor0;
        ValPack right = ancestor1;
        if (left.val < right.val) {
            left = ancestor1;
            right = ancestor0;
        }
        String ls = left.getAllDescAt(maxDepth - 1);
        String rs = right.getAllDescAt(maxDepth - 1);
        if (left.ancestor1 != null && maxDepth != 1) {
            ls = MiscUtils.enparen(ls);
        }
        if (right.ancestor1 != null && maxDepth != 1) {
            rs = MiscUtils.enparen(rs);
        }

        return ls + op + rs;
    }

    public int getMaxDepth() {
        if (ancestor1 == null) {
            return 0;
        }
        int v0 = ancestor0.getMaxDepth();
        int v1 = ancestor1.getMaxDepth();
        if (v1 > v0) {
            v0 = v1;
        }
        return v0 + 1;
    }

    @Override
    public int compareTo(ValPack t) {
        return val - t.val;
    }

    public int getNbPoss() {
        return nbPoss;
    }

    public void printNbposs() {
        System.out.println(val + "(" + nbPoss + ")" + " : " + this.getDetailedStrPossibility());

    }
}
