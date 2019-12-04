package org.deeplearning4j.examples.deeplearning;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Runtime;
import java.lang.Process;

public class Shell {
    public static void shell(String args) throws IOException {
	// linux환경에서 쉘 코드를 실행하기 위한 클래스 입니다. 실행할 command를 매개변수로 받아서 쉘코드를 실행합니다.
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
