package org.deeplearning4j.examples.deeplearning;

import java.io.File;
import java.io.IOException;



public class Service {
    public static void main(String[] args) throws Exception {

        Classification cl = new Classification();
        Shell sh = new Shell();
        long time;
        String fileName;
        String IPaddress = "192.168.219.116";
        String temp;
        String temp2;



        while(true){
            time = System.currentTimeMillis();
            fileName = String.valueOf(time);
            temp = fileName;
            temp2 = temp;
            fileName = "~/jp/src/main/resources/" + fileName + ".pcap";
            temp = "~/data/" + temp;

            File ip = new File("./src/main/resources/" + temp2 + "IP.txt");

            String command = "timeout 10 tcpdump dst " + IPaddress + " -w " + fileName;

            System.out.println("패킷 캡쳐 시작 -----------------------------------------------------------------------");
            sh.shell(command);
            Thread.sleep(10000);

            System.out.println("패킷 특징 추출 시작");
            command = "~/kdd99_feature_extractor/build-files/src/kdd99extractor -e " + fileName + " > ~/jp/src/main/resources/" + temp2 + ".txt";
            sh.shell(command);

            System.out.println("데이터 2열3열 맵핑 + IP주소 파일 생성");
            command = "python3 ~/mod.py ~/jp/src/main/resources/" + temp2 + ".txt ~/jp/src/main/resources/" + temp2 + "data.txt ~/jp/src/main/resources/" + temp2 + "IP.txt";
            sh.shell(command);

            Thread.sleep(1000);
            command = temp2;
            cl.classification(command);


            System.out.println();
            System.out.println();
            System.out.println();
        }
    }
}
