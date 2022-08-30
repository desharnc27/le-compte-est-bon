/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package central;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author desharnc27
 */
public class LeCompteEstBon {

    int numbers[];
    int nbTerms = -1;

    //bank contains all possible valPacks. There is one valPack per subset of numbers so bank.length = 2^numbers.length
    //The indexes in bank have a base-2 logic.
    //Ex: suppose numbers = {4,7,55,3,83,2}, then bank[13] is the state managing subset {4,55,3}
    // since 13 = 001101, contains "1" at indexes 0,2,3 and numbers at indexes 0,2,3 are 4,55,3 
    State[] bank;

    public void initialize() {
        nbTerms = numbers.length;
        bank = new State[1 << nbTerms];
        for (int i = 0; i < nbTerms; i++) {
            int id = 1 << i;
            bank[id] = new State(numbers[i]);
        }
        for (int i = 0; i < (1 << nbTerms); i++) {
            if (bank[i] == null) {
                bank[i] = new State();
            }
        }
    }

    /**
     * Recursively fully fills bank
     */
    public void expandAll() {
        int nbs = 1 << nbTerms;
        for (int level = 1; level <= nbTerms; level++) {
            for (int i = 1; i < nbs; i++) {
                for (int j = 1; j < i; j++) {
                    if ((i & j) > 0) {
                        //If i and j have a common bit, it means that the subsets they refer to have a
                        //common number, so they cannot combine since every number can only be used once
                        continue;
                    }
                    int mergeId = i | j;
                    if (Integer.bitCount(mergeId) != level) {
                        //The level is the size of the subest represented by mergeId (how many numbers does it combine)
                        //That condition combined to the outer for-loop ensures that we expand in ascening order of level
                        //Purpose: we have bank[i] and bank[j] must be fully developped before adding their combos to bank[i|j]
                        continue;
                    }
                    expand(i, j);

                }
            }
        }
    }

    /**
     *
     * @param s0 any index i bank
     * @param s1 any other index in bank Will expand valPack at index s1 | s0
     */
    private void expand(int s0, int s1) {
        int mergeId = s1 | s0;
        if (bank[mergeId] == null) {
            bank[mergeId] = new State();
        }
        bank[mergeId].include(s0, s1, bank);

    }

    public void printOneCombo(int val) {
        bank[(1 << nbTerms) - 1].printOneCombo(val);
    }

    public void printOneComboWithSteps(int val) {
        bank[(1 << nbTerms) - 1].printOneComboWithSteps(val);
    }

    public void doAll() {
        initialize();
        expandAll();
    }

    public LeCompteEstBon(int[] vals) {
        nbTerms = vals.length;
        numbers = Arrays.copyOf(vals, nbTerms);
    }

    public void printFullState(int id) {
        System.out.print("starters: ");
        for (int j = 0; j < nbTerms; j++) {
            if ((id & (1 << j)) != 0) {
                System.out.print(numbers[j] + " ");
            }
        }
        System.out.println();
        bank[id].printPossibilityCountOfAllVals();
    }

    public void megaPrint() {
        for (int id = 1; id < (1 << nbTerms); id++) {
            System.out.println("--------------");
            printFullState(id);
        }
    }

    public void printFullLast() {
        printFullState((1 << nbTerms) - 1);
    }

    public static void MyMainInCode() {
        int[] start = new int[]{91, 53, 27, 16, 23, 7, 19};
        //int [] start = new int []{3,33,5};
        //int target = 48;
        int target = 14;
        LeCompteEstBon urk = new LeCompteEstBon(start);
        urk.doAll();
        //urk.printResultsB(target);
        //urk.printFullState(15);
        //urk.megaPrint();
        //urk.printFullLast();
        urk.bank[63].printOneComboWithSteps(14);
    }
    
    //input management
    
    public static boolean subMain(String[] args) {
        Statix.initialize();
        
        if (args.length == 0){
            System.out.println("Fail: Arguments required. Aborted");
            return false;
        }

        if (args.length > 2){
            System.out.println("Fail: Too much arguments. Aborted");
            return false;
        }
        if (args[0].equals("mytest")){
            MyMainInCode();
            return true;
        }
        if (args[0].equals("help")){
            displayHelp();
            return true;
        }
        String [] numberStrs = args[0].split(",");
        int [] numbers = new int[numberStrs.length];
        try{
            int idx = 0;
            for (String str:numberStrs)
                numbers[idx++]= Integer.parseInt(str);
            
        }catch(NumberFormatException e){
            System.out.println("Fail: Invalid first argument: " + args[0]);
            System.out.println("First argument should be all the terms separated with commas (no space)");
            System.out.println("Every term must be a non-negative integer.");
            return false;
        }
        
        int target = -1;
        if (args.length == 2){
            try{
                target = Integer.parseInt(args[1]);
                if (target<0)
                    throw new NumberFormatException();
            }catch(NumberFormatException e){
                System.out.println("Fail: Invalid second argument: " + args[1]);
                System.out.println("It should be a non-negative number (or unexistent)");
                return false;
            }
        }
        //All validations completed
        
        LeCompteEstBon ob = new LeCompteEstBon(numbers);
        ob.doAll();
        if (target < 0){
            ob.printFullLast();
        }else{
            ob.printOneComboWithSteps(target);
        }
        return true;
            
    }
    public static void displayHelp(){
        String root = Statix.getRoot();
        String helpPath = String.join(File.separator,root,"help.txt");
        Path filePath = Path.of(helpPath);
        try {
            String content = Files.readString(filePath);
            System.out.println(content);
        } catch (IOException ex) {
            System.out.println("Oups! Program bug! No help file found: "+ helpPath);
        }
        
    }
    public static void main(String[] args){
        boolean ok = subMain(args);
        if (!ok){
            System.out.println("Note: you may enter \"help\" as argument for help on arguments.");
        }
    }
}
