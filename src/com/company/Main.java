package com.company;


import net.sourceforge.jFuzzyLogic.FIS;

public class Main {

    public static void main(String[] args) {

        // Load from 'FCL' file
        String fileName = "C:\\Users\\jan_s\\Study\\Semester 2\\Expert System\\Project\\IntelligentCart\\src\\com\\company\\rules.fcl";
        FIS fis = FIS.load(fileName,true);

        // Error while loading?
        if( fis == null ) {
            System.err.println("Can't load file: '" + fileName + "'");
            return;
        }

        



    }
}
