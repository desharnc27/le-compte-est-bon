/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package central;

import java.io.File;
import java.nio.file.Files;

/**
 *
 * @author desharnc27
 *
 * Class for static data
 */
public class Statix {
    private static Operation[] opers;
    private static String root;
    public static void initialize() {
        opers = new Operation[4];
        for (byte i = 0; i < 4; i++) {
            opers[i] = new Operation(i);
        }
        setRoot();
    }
    private static void setRoot(){
        String rawRoot = System.getProperty("user.dir");
        String tc = File.separator + "target" + File.separator + "classes";
        int detectTarget = rawRoot.indexOf(tc);
        if (detectTarget < 0)
            root = rawRoot;
        else
            root = rawRoot.substring(0,detectTarget);
    }
    public static String getRoot(){
        return root;
    }

    public static Operation getOp(int i) {
        return opers[i];
    }

}
