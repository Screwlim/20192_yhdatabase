package org.deeplearning4j.examples.deeplearning;

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

    public static void main(String[] args) throws Exception {

        int numLinesToSkip = 0;
        char delimiter = ',';

        String fileName = "noLabel.txt";

        RecordReader recordReader = new CSVRecordReader(numLinesToSkip, delimiter);
        recordReader.initialize(new FileSplit(new ClassPathResource(fileName).getFile()));

        int labelIndex = 28;
        int numClasses = 2;
        int batchSize = 500000;
        int nPacket;

        DataSetIterator iterator = new RecordReaderDataSetIterator(recordReader, batchSize);
        DataSet allData = iterator.next();
        nPacket = allData.numExamples();


        DataNormalization normalizer = new NormalizerStandardize();
        //normalizer.fit(allData);
        //normalizer.transform(allData);

        MultiLayerNetwork model;

        String path = "C:\\20192_yhdatabase\\src\\main\\resources\\trainedModel\\1118.zip";
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
            }

            i++;
        }
        System.out.println("정상패킷 수 : " + nNormal + " 이상패킷 수 : " + nAbnormal);
    }
}