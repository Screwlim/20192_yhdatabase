package org.deeplearning4j.examples.deeplearning;

import java.io.File;
import java.io.IOException;



public class Service {
    public static void main(String[] args) throws Exception {

        Classification cl = new Classification(); // 파일에 주석참고
        Shell sh = new Shell(); // 파일에 주석참고

        long time;
        String fileName;
        String IPaddress = "192.168.219.116"; // 이 아이피로 들어오는 패킷을 다 캡쳐합니다. 자기 아이피를 입력.
        String temp;
        String temp2;



        while(true){
            time = System.currentTimeMillis(); // 파일이름을 매번 다르게 하기 위해 현재시간으로 합니다.
            fileName = String.valueOf(time);
            temp = fileName;
            temp2 = temp;
            fileName = "~/20192_yhdatabase/src/main/resources/" + fileName + ".pcap";
            temp = "~/data/" + temp;

            File ip = new File("./src/main/resources/" + temp2 + "IP.txt");

            String command = "timeout 10 tcpdump dst " + IPaddress + " -w " + fileName;

            System.out.println("패킷 캡쳐 시작 -----------------------------------------------------------------------");
            sh.shell(command);
            Thread.sleep(10000); // 10초간 패킷을 캡쳐합니다.

            System.out.println("패킷 특징 추출 시작");
            command = "~/kdd99_feature_extractor/build-files/src/kdd99extractor -e " + fileName + " > ~/20192_yhdatabase/src/main/resources/" + temp2 + ".txt";
            sh.shell(command);

            System.out.println("데이터 2열3열 맵핑 + IP주소 파일 생성");
            command = "python3 ~/20192_yhdatabase/mod.py ~/20192_yhdatabase/src/main/resources/" + temp2 + ".txt ~/20192_yhdatabase/src/main/resources/" + temp2 + "data.txt ~/20192_yhdatabase/src/main/resources/" + temp2 + "IP.txt";
            sh.shell(command);

            Thread.sleep(1000); // 파일이 wirte되기전에 읽는것을 방지하기 위해 1초 딜레이를 넣었습니다.
            command = temp2;
            cl.classification(command);


            System.out.println();
            System.out.println();
            System.out.println();
        }
    }
}
