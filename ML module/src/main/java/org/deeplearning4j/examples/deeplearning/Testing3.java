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

import java.util.Scanner;


public class Testing3 {

    private static Logger log = LoggerFactory.getLogger(Testing3.class);

    public static void main(String[] args) throws Exception {

        Scanner scan = new Scanner(System.in);

        int numLinesToSkip = 0;
        char delimiter = ',';

        String fileName = "./data/testing.txt"; // 테스트 할 테스트 데이터셋의 경로를 입력합니다. src/resources/ 부터 입력하면 됩니다.

        RecordReader recordReader = new CSVRecordReader(numLinesToSkip, delimiter);
        recordReader.initialize(new FileSplit(new ClassPathResource(fileName).getFile()));
	// 테스트 데이터를 csv형식으로 읽기 위한 선언들 입니다.	

        int labelIndex = 28; // label이 몇 열에 있는지에 대한 변수입니다.
        int numClasses = 2; // label이 몇 개 있는지에 대한 변수입니다.
        int batchSize = 500000; // testing에는 batch size가 딱히 의미가 없어 그냥 모든데이터를 한번에 테스트 할수있게 큰 값으로 저장했습니다.

        DataSetIterator iterator = new RecordReaderDataSetIterator(recordReader, batchSize, labelIndex, numClasses);
        DataSet allData = iterator.next();
	// 데이터 iterator입니다.        

        MultiLayerNetwork model;

        System.out.print("model name : ");

        String modelName = scan.nextLine();
        modelName = modelName + ".zip";
        String path = "C:\\20192_yhdatabase\\src\\main\\resources\\trainedModel\\";
        path = path + modelName;

        model = ModelSerializer.restoreMultiLayerNetwork(path);
	// 저장된 모델을 불러오는 과정입니다.

        Evaluation eval = new Evaluation(2);
        INDArray output = model.output(allData.getFeatures());
        eval.eval(allData.getLabels(), output);
        log.info(eval.stats());
        // 테스트 결과를 출력하는 과정입니다.
    }
}