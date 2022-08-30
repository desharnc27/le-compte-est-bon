/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package central;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author desharnc27
 */
/**
 * One instance of this class represent all int values that you can get by
 * manipulating a specific subset of the initial numbers. With every value, the
 * way to get it that is stored (see ValPack). If there are may ways to get the
 * same value, only one is remembered.
 *
 */
class State {

    //int id;
    //boolean [] used;
    ArrayList<ValPack> valList;

    public State(int number) {
        this();
        ValPack vp = new ValPack(number);
        valList.add(vp);
    }

    public State() {
        valList = new ArrayList<>();
    }

    /**
     * Feeds this state with all values you can obtain by combining values in
     * two states state0 and state1
     *
     * @param s0 index of some state0 in bank
     * @param s1 index of some state1 in bank
     * @param bank array of all states
     */
    public void include(int s0, int s1, State[] bank) {
        for (int i = 0; i < bank[s0].valList.size(); i++) {
            ValPack vp0 = bank[s0].valList.get(i);
            //int v0 = vp0.val;
            for (int j = 0; j < bank[s1].valList.size(); j++) {
                ValPack vp1 = bank[s1].valList.get(j);
                //int v1 = vp1.val;
                for (int k = 0; k < 4; k++) {
                    ValPack vpNew = new ValPack(vp0, vp1, Statix.getOp(k));
                    if (vpNew.val < 1 || vpNew.val >= (1 << 16)) {
                        // former : division impossible, latter: too big
                        continue;
                    }
                    maybeAdd(vpNew);
                }

            }
        }
    }

    /**
     * Adds a valpack to this state, unless the value of the valpack already
     * exists in this state. In the latter case, it means that there is
     * different ways to reach the same values with the same numbers, so the
     * attribute storing the number of possibilities is increased
     *
     * @param vp instance of Valpack
     */
    public void maybeAdd(ValPack vp) {
        int idx = Collections.binarySearch(valList, vp);
        if (idx >= 0) {
            valList.get(idx).increasePossibilityCount(vp);
            return;
        }
        idx = -idx - 1;
        valList.add(idx, vp);
    }

    /**
     * Prints a combo that outputs val
     * @param val the value to reach
     */
    public void printOneCombo(int val) {
        int idx = Collections.binarySearch(valList, new ValPack(val));
        if (idx < 0) {
            System.out.println("There is no way to get val " + val);
            return;
        }
        System.out.println(valList.get(idx).getDetailedStrPossibility());
    }
    /**
     * Prints a combo that outputs val, with the step by steps operation
     * @param val the value to reach
     */
    public void printOneComboWithSteps(int val) {
        int idx = Collections.binarySearch(valList, new ValPack(val));
        if (idx < 0) {
            System.out.println("There is no way to get val " + val);
            return;
        }
        ValPack root = valList.get(idx);
        int maxD = root.getMaxDepth();

        String[] lines = new String[maxD + 1];

        for (int i = 0; i < maxD + 1; i++) {
            lines[i] = root.getAllDescAt(maxD - i);
        }
        String eqFlow = String.join("\n=", lines);
        System.out.println(eqFlow);
        System.out.println("Number of possibilities: " + root.getNbPoss());

    }
    /**
     * Prints how much possible combos lead to every value
     */
    public void printPossibilityCountOfAllVals() {
        for (int i = 0; i < valList.size(); i++) {
            valList.get(i).printNbposs();
        }
    }
}
