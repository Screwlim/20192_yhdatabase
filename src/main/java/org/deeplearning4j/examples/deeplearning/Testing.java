package org.deeplearning4j.examples.deeplearning;

import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.arbiter.util.ClassPathResource;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.examples.dataexamples.CSVExample;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.SplitTestAndTrain;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerStandardize;
import org.nd4j.linalg.learning.config.Sgd;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;


public class Testing {

    private static Logger log = LoggerFactory.getLogger(Testing.class);

    public static void main(String[] args) throws  Exception {

        Scanner scan = new Scanner(System.in);

        int numLinesToSkip = 0;
        char delimiter = ',';

        System.out.print("test file name : ");
        String fileName = scan.nextLine();
        fileName = fileName + ".txt";

        RecordReader recordReader = new CSVRecordReader(numLinesToSkip,delimiter);
        recordReader.initialize(new FileSplit(new ClassPathResource(fileName).getFile()));

        int labelIndex = 41;
        int numClasses = 2;
        int batchSize = 2000000;

        DataSetIterator iterator = new RecordReaderDataSetIterator(recordReader,batchSize,labelIndex,numClasses);
        DataSet allData = iterator.next();

        DataNormalization normalizer = new NormalizerStandardize();
        normalizer.fit(allData);
        normalizer.transform(allData);


        System.out.print("model name : ");
        String modelName = scan.nextLine();
        modelName = modelName + ".zip";
        String path = "C:\\20192_yhdatabase\\src\\main\\resources\\trainedModel\\";
        path = path + modelName;

        MultiLayerNetwork model = ModelSerializer.restoreMultiLayerNetwork(path);

        Evaluation eval = new Evaluation(2);
        INDArray output = model.output(allData.getFeatures());
        eval.eval(allData.getLabels(), output);
        log.info(eval.stats());
    }
}