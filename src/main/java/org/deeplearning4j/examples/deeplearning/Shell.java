package org.deeplearning4j.examples.deeplearning;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Runtime;
import java.lang.Process;

public class Shell {
    public static void shell(String args) throws IOException {

        //String temp = "~/kdd99_feature_extractor/build-files/src/kdd99extractor ~/data2.pcap > ~/xx.txt";
        String[] command = {"/bin/sh","-c", args};

        try{
            Process ps = Runtime.getRuntime().exec(command);
            //ps.waitFor();
            //System.out.println(ps.exitValue());
            //ps.destroy();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
