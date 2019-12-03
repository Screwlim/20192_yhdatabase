package org.deeplearning4j.examples.deeplearning;

import org.apache.commons.io.FileUtils;
import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.arbiter.util.ClassPathResource;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.jfree.data.general.Dataset;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerStandardize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class Classification {

    private static Logger log = LoggerFactory.getLogger(Classification.class);

    public static void classification(String fileName) throws Exception {

        int numLinesToSkip = 0;
        char delimiter = ',';

        RecordReader recordReader = new CSVRecordReader(numLinesToSkip, delimiter);

        String temp = fileName + "IP.txt";
        fileName = fileName + "data.txt";

        File file = new File("./src/main/resources/" + fileName);
        File file2 = new File("./src/main/resources/" + temp);
        File ip = new File("./src/main/resources/data/attackIP.txt");

        FileWriter fw = new FileWriter(ip, true);

        String line = new String();
        if(file.exists())
        {
            if(file.length() == 0)
            {
                System.out.println("패킷이 0개 캡쳐됨.");
                return;
            }
            else
            {
                recordReader.initialize(new FileSplit(file));
            }
        }
        else
        {
            System.out.println("xxxxxxx");
            return;
        }





        int labelIndex = 28;
        int numClasses = 2;
        int batchSize = 1000000;
        int nPacket;

        DataSetIterator iterator = new RecordReaderDataSetIterator(recordReader, batchSize);
        DataSet allData = iterator.next();
        nPacket = allData.numExamples();

        DataNormalization normalizer = new NormalizerStandardize();
        //normalizer.fit(allData);
        //normalizer.transform(allData);

        MultiLayerNetwork model;

        String path = "./src/main/resources/trainedModel/1128.zip";
        model = ModelSerializer.restoreMultiLayerNetwork(path);

        INDArray output = model.output(allData.getFeatures());
        //allData.getFeatures();
        //System.out.println(allData.getFeatures().getDouble(25443,1));
        int i = 0;
        int nNormal=0, nAbnormal=0;
        while(i < nPacket)
        {
            if(output.getDouble(i, 0) > output.getDouble(i, 1)) // 정상 패킷
            {
                System.out.println(i + " : 정상패킷 " + output.getDouble(i, 0) + " " + output.getDouble(i, 1));
                nNormal++;
            }
            else // 이상 패킷
            {
                System.out.println(i + " : 이상패킷 " + output.getDouble(i, 0) + " " + output.getDouble(i, 1) + " " + allData.getFeatures().getRow(i));
                nAbnormal++;
                line = FileUtils.readLines(file2).get(i);
                //System.out.println("qqqqqqqqqqqqqqqqqqqqqqqqqqqqq" + line);

                // data/attackIP.txt 에서 search 후 해당 IP가 있다면 count++해주고 없다면 맨밑에 line 1 쓴다.
                fw.write(line + "\n");
                fw.flush();
            }

            i++;
        }
        System.out.println("정상패킷 수 : " + nNormal + " 이상패킷 수 : " + nAbnormal);
    }
}