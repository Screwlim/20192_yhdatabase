package org.deeplearning4j.examples.deeplearning;

import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.arbiter.util.ClassPathResource;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.examples.dataexamples.CSVExample;
import org.deeplearning4j.examples.download.DownloaderUtility;
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

import java.io.File;

public class Training {

    private static Logger log = LoggerFactory.getLogger(Training.class);

    public static void main(String[] args) throws  Exception {

        int numLinesToSkip = 0;
        char delimiter = ',';

        RecordReader recordReader = new CSVRecordReader(numLinesToSkip,delimiter);
        recordReader.initialize(new FileSplit(new ClassPathResource("train0203.txt").getFile()));

        int labelIndex = 41;
        int numClasses = 2;
        int batchSize = 2000000;

        DataSetIterator iterator = new RecordReaderDataSetIterator(recordReader,batchSize,labelIndex,numClasses);
        DataSet allData = iterator.next();
        allData.shuffle();

        //We need to normalize our data. We'll use NormalizeStandardize (which gives us mean 0, unit variance):
        DataNormalization normalizer = new NormalizerStandardize();
        normalizer.fit(allData);           //Collect the statistics (mean/stdev) from the training data. This does not modify the input data
        normalizer.transform(allData);     //Apply normalization to the training data

        final int numInputs = 41;
        int outputNum = 2;
        long seed = 6;

        log.info("Build model.....");
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .seed(seed)
                .activation(Activation.TANH)
                .weightInit(WeightInit.XAVIER)
                .updater(new Sgd(0.1))
                .l2(1e-4)
                .list()
                .layer(new DenseLayer.Builder().nIn(numInputs).nOut(3)
                        .build())
                .layer(new DenseLayer.Builder().nIn(3).nOut(3)
                        .build())
                .layer( new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                        .activation(Activation.SOFTMAX)
                        .nIn(3).nOut(outputNum).build())
                .build();

        MultiLayerNetwork model = new MultiLayerNetwork(conf);
        model.init();
        model.setListeners(new ScoreIterationListener(1));

        log.info("tarin model.....");
        for(int i=0; i<100; i++ ) {
            model.fit(allData);
        }

        log.info("save model.....");
        ModelSerializer.writeModel(model, "C:\\20192_yhdatabase\\src\\main\\resources\\trainedModel\\model.zip", true);

        log.info("complete.");
    }
}