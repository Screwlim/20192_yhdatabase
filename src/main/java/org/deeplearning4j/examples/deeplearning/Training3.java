package org.deeplearning4j.examples.deeplearning;

import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.api.storage.StatsStorage;
import org.deeplearning4j.api.storage.StatsStorageRouter;
import org.deeplearning4j.api.storage.impl.RemoteUIStatsStorageRouter;
import org.deeplearning4j.arbiter.util.ClassPathResource;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.deeplearning4j.ui.api.UIServer;
import org.deeplearning4j.ui.stats.StatsListener;
import org.deeplearning4j.ui.storage.InMemoryStatsStorage;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerStandardize;
import org.nd4j.linalg.learning.config.Sgd;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.deeplearning4j.api.storage.StatsStorageRouter;
import org.deeplearning4j.api.storage.impl.RemoteUIStatsStorageRouter;
import org.deeplearning4j.examples.userInterface.util.UIExampleUtils;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.ui.api.UIServer;
import org.deeplearning4j.ui.stats.StatsListener;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

import java.util.Scanner;

public class Training3 {

    private static Logger log = LoggerFactory.getLogger(Training3.class);

    public static void main(String[] args) throws Exception {

        Scanner scan = new Scanner(System.in);

        int numLinesToSkip = 0;
        char delimiter = ',';

        String fileName = "new.txt";

        RecordReader recordReader = new CSVRecordReader(numLinesToSkip, delimiter);
        recordReader.initialize(new FileSplit(new ClassPathResource(fileName).getFile()));

        int labelIndex = 28;
        int numClasses = 2;
        int batchSize = 314908;


        DataSetIterator iterator = new RecordReaderDataSetIterator(recordReader, batchSize, labelIndex, numClasses);
        DataSet allData = iterator.next();
        //allData.shuffle();

        DataNormalization normalizer = new NormalizerStandardize();
        //normalizer.fit(allData);
        //normalizer.transform(allData);

        final int numInputs = 28;
        int outputNum = 2;
        long seed = 6;
        double learningRate = 0.09;

        log.info("Build model.....");
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .seed(seed)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .activation(Activation.TANH)
                .weightInit(WeightInit.XAVIER)
                .updater(new Sgd(learningRate))
                .l2(1e-4)
                .list()
                .layer(new DenseLayer.Builder().nIn(numInputs).nOut(25).weightInit(WeightInit.XAVIER).activation(Activation.TANH)
                        .build())
                .layer(new DenseLayer.Builder().nIn(25).nOut(20).weightInit(WeightInit.XAVIER).activation(Activation.TANH)
                        .build())
                .layer(new DenseLayer.Builder().nIn(20).nOut(10).weightInit(WeightInit.XAVIER).activation(Activation.TANH)
                        .build())
                .layer(new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                        .activation(Activation.SOFTMAX)
                        .nIn(10).nOut(outputNum).build())
                .build();

        MultiLayerNetwork model = new MultiLayerNetwork(conf);

        model.init();

        UIServer uiServer = UIServer.getInstance();


        uiServer.enableRemoteListener();

        StatsStorageRouter remoteUIRouter = new RemoteUIStatsStorageRouter("http://localhost:9000");
        model.setListeners(new StatsListener(remoteUIRouter), new ScoreIterationListener(1));

        log.info("Train model.....");

        for (int i = 0; i < 30; i++)
            model.fit(allData);




        System.out.print("model name : ");
        String modelName = scan.nextLine();
        modelName = modelName + ".zip";
        String path = "C:\\20192_yhdatabase\\src\\main\\resources\\trainedModel\\";
        path = path + modelName;
        ModelSerializer.writeModel(model, path, true);


        log.info("Complete.");
    }
}