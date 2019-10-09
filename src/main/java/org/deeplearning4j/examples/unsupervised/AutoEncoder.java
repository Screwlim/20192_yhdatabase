package org.deeplearning4j.examples.unsupervised;


import org.apache.commons.lang3.tuple.*;
import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.arbiter.util.ClassPathResource;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.WorkspaceMode;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerStandardize;
import org.nd4j.linalg.learning.config.RmsProp;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import java.util.*;


public class AutoEncoder {

    private static int batchSize = 100000;
    private static int nEpochs = 5;

    public static void main(String[] args) throws Exception {

        int labelNum = 41;
        MultiLayerNetwork net = createModel(labelNum, labelNum);
        net.setListeners(new ScoreIterationListener(1));

        trainAutoEncoder(net);
        testAutoEncoder(net);
    }


    private static void trainAutoEncoder(MultiLayerNetwork net) throws Exception {

        String fileName = "data02_train_nolabel.txt";
        int numLinesToSkip = 0;
        char delimiter = ',';

        // Read
        RecordReader trainReader = new CSVRecordReader(numLinesToSkip, delimiter);
        trainReader.initialize(new FileSplit(new ClassPathResource(fileName).getFile()));
        DataSetIterator trainIter = new RecordReaderDataSetIterator(trainReader, batchSize);

        // Normalization
        DataNormalization normalizer = new NormalizerStandardize();
        normalizer.fit(trainIter);
        trainIter.reset();
        trainIter.setPreProcessor(normalizer);

        // Train
        net.fit(trainIter, nEpochs);
    }


    private static void testAutoEncoder(MultiLayerNetwork net) throws Exception {

        String fileName = "data02_test_nolabel.txt";
        int numLinesToSkip = 0;
        char delimiter = ',';

        // Read
        RecordReader testReader = new CSVRecordReader(numLinesToSkip, delimiter);
        testReader.initialize(new FileSplit(new ClassPathResource(fileName).getFile()));
        DataSetIterator testIter = new RecordReaderDataSetIterator(testReader, batchSize);

        // Normalization
        DataNormalization normalizer = new NormalizerStandardize();
        normalizer.fit(testIter);
        testIter.reset();
        testIter.setPreProcessor(normalizer);

        // Test [Not working]
        List<Pair<Double, Integer>> evalList = new ArrayList<>();

        int count = 0;
        double totalScore = 0;
        while (testIter.hasNext()) {
            DataSet ds = testIter.next();
            double score = net.score(ds);
            totalScore += score;
            evalList.add(new ImmutablePair<>(score, count));
            count++;
        }

        evalList.sort(Comparator.comparing(Pair::getLeft));
        Stack<Integer> anomalyData = new Stack<>();
        double threshold = totalScore / evalList.size();
        for (Pair<Double, Integer> pair: evalList) {
            double s = pair.getLeft();
            if (s >  threshold) {
                anomalyData.push(pair.getRight());
            }
        }

        //output anomaly data
        System.out.println("based on the score, all anomaly data is following with descending order:\n");
        for (int i = anomalyData.size(); i > 0; i--) {
            System.out.println(anomalyData.pop());
        }
    }


    private static MultiLayerNetwork createModel(int inputNum, int outputNum) {

        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
            .trainingWorkspaceMode(WorkspaceMode.ENABLED).inferenceWorkspaceMode(WorkspaceMode.ENABLED)
            .seed(123456)
            .optimizationAlgo( OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
            .updater(new RmsProp.Builder().learningRate(0.05).rmsDecay(0.002).build())
            .l2(0.0005)
            .weightInit(WeightInit.XAVIER)
            .activation(Activation.TANH)
            .list()
            .layer(0, new DenseLayer.Builder().nIn(inputNum).nOut(20).build())
            .layer(1, new DenseLayer.Builder().nOut(10).build())
            .layer(2, new DenseLayer.Builder().nOut(20).build())
            .layer(3, new OutputLayer.Builder().nOut(outputNum)
                .activation(Activation.IDENTITY).lossFunction(LossFunctions.LossFunction.MSE).build())
            .build();

        MultiLayerNetwork net = new MultiLayerNetwork(conf);
        net.init();
        return net;
    }
}
