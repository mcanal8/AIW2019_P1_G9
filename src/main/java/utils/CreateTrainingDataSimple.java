/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Horacio
 */
public class CreateTrainingDataSimple {
    
    public static void main(String[] args) {
      /* diectory to process */
        File inDir;
        File[] flist;
        BufferedReader reader;
        String floc;
        String line;
        String text;
        /* airplane accidents spanish */
        inDir=new File(".\\resources\\Concisus-v3\\text_files\\aviation\\spanish");
        flist=inDir.listFiles();

        for(int f=0;f<flist.length;f++) {
            floc=flist[f].getAbsolutePath();
            try {
                reader=new BufferedReader(new FileReader(floc));
                text="";
                while((line=reader.readLine())!=null) {
                    line=line.replaceAll("'", " ");
                    text=text+line+" ";
                }
                System.out.println("'"+text+"',"+"aviation");
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    
}
}
