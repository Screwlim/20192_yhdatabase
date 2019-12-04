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

    private static Logger log = LoggerFactory.getLogger(Training3.class); // 콘솔창에 로그를 찍기 위한 변수 선언 입니다.

    public static void main(String[] args) throws Exception {

        Scanner scan = new Scanner(System.in);
        int numLinesToSkip = 0; // 데이터파일에서 데이터가 파일의 시작부터 얼마나 떨어져있는지에 대한 변수입니다.
        char delimiter = ','; // 데이터의 구분자가 뭔지 입니다.

        String fileName = "./data/training.txt"; // 학습 시킬 데이터의 경로 입니다.

        RecordReader recordReader = new CSVRecordReader(numLinesToSkip, delimiter); // 데이터를 csv형식으로 읽기위해 선언했습니다.
        recordReader.initialize(new FileSplit(new ClassPathResource(fileName).getFile())); // ''

        int labelIndex = 28; // 데이터에서 label이 몇 열에 있는지 저장하는 변수입니다.
        int numClasses = 2; // 아웃풋의 label이 몇개 있는지 저장하는 변수 입니다.
        int batchSize = 125973; // 학습의 batch size입니다.


        DataSetIterator iterator = new RecordReaderDataSetIterator(recordReader, batchSize, labelIndex, numClasses); // 데이터 iterator입니다.
        DataSet allData = iterator.next(); // 학습 시키기 위해 DataSet 변수로 바꿔줍니다.
        //allData.shuffle(); shuffle이 의미가 없을것같아 주석처리 했습니다.

        final int numInputs = 28; // 신경망의 입력노드의 개수 입니다.
        int outputNum = 2; //신경망의 출력층의 노드개수입니다.
        long seed = 6; 
        double learningRate = 0.09; // 신경망의 학습에 사용되는 learning rate 입니다.

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

		// 이까지가 신경망 구축 입니다. 활성화 함수는 하이퍼탄젠트 함수 히든레이어는 2개 러닝레이트는 0.09로 설정했습니다.

        MultiLayerNetwork model = new MultiLayerNetwork(conf);

        model.init();

        UIServer uiServer = UIServer.getInstance();
        uiServer.enableRemoteListener();
        StatsStorageRouter remoteUIRouter = new RemoteUIStatsStorageRouter("http://localhost:9000");
		// 학습과정을 ui로 보기위한 과정인데 학습에는 필요없습니다.

        model.setListeners(new StatsListener(remoteUIRouter), new ScoreIterationListener(1));

        log.info("Train model.....");

        for (int i = 0; i < 30; i++)
            model.fit(allData); // batchsize를 30번 학습시킵니다.




        System.out.print("model name : ");
        String modelName = scan.nextLine();
        modelName = modelName + ".zip";
        String path = "./src/main/resources/trainedModel/" + modelName;
        ModelSerializer.writeModel(model, path, true);
		// 모델을 파일로 저장하는 과정입니다. 콘솔창에 직접 모델이름을 입력하면 됩니다.
		// 모델의 파일형식은 .zip파일입니다.
		// .zip은 붙이지 않고 모델의 이름만 입력합니다.



        log.info("Complete.");
    }
}