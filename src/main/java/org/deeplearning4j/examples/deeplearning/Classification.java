package org.deeplearning4j.examples.deeplearning;

import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.arbiter.util.ClassPathResource;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
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
        BufferedWriter bw_normal = new BufferedWriter(new FileWriter("C:\\20192_yhdatabase\\src\\main\\resources\\noraml.txt", true));
        BufferedWriter bw_abnormal = new BufferedWriter(new FileWriter("C:\\20192_yhdatabase\\src\\main\\resources\\noraml.txt", true));

        PrintWriter pw_normal = new PrintWriter(bw_normal, true);
        PrintWriter pw_abnormal = new PrintWriter(bw_abnormal, true);

        pw_normal.write("hello\n");
        pw_normal.write("good\n");

        pw_normal.flush();
        pw_normal.close();
    }
}