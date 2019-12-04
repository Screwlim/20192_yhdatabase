# Dataset 1

## 파일 정보

### /Code
비정수형 데이터를 정수형 데이터로 변환시켜주는 파이썬 모듈이다.
모든 코드는 KDD-NSL 데이터의 형태로 변환하며 각 파일의 이름은 다음을 의미한다. 
2label은 데이터를 normal / abnormal 2가지로 구분하고 5label은 normal / dos / prove / U2R / R2L 로 구분한다.
full은 42개의 모든 특징을 이용하고 some은 10 ~ 22번 특징을 제외한 데이터를 사용한다.
<pre>
2label_full_feature_data.py
2label_some_feature_data_for_linux.py
리눅스에서 input과 output 인자를 받아서 사용할 수 있게 만든 코드이다.
5label_full_feature_data.py
</pre>

### /resource_set
<pre>
/raw : 
출처 KDD-NSL
Train01/Test01
KDD - NSL 의 원데이터이다.
Train02/Test02
kDD - NSL 의 일부를 추출한 데이터이다.
출처 CSE
dos1.txt , dos2.txt
DOS파일에 대해서 KDD-NSL 형태로 변형시킨 데이터 10 ~ 22번 특징과 라벨이 없다.
</pre>
<pre>
/modify : 
2label_full_xxxx (Train / Test)
KDD-NSL의 데이터의 모든 특징을 유지하고 2개의 라벨로 구분한뒤 정수형으로 변환한 데이터이다.
5label_full_xxxx (Train / Test)
KDD-NSL의 데이터의 모든 특징을 유지하고 5개의 라벨로 구분한뒤 정수형으로 변환한 데이터이다. 
5label_full_67
라벨간의 표본 갯수가 차이나서 동일하게 학습시킨 결과 확인을 위해 각 라벨별로 67개씩 추출한 데이터이다.
2label_some_final(KDD-NSL + cse + packet capture) 
기존에 가지고 있던 데이터 + wireshark의 패킷 캡쳐 기능을 이용하여 수집한 데이터의 총 합에 대하여 KDD-NSL형태의 10~22번 특징 제외한 뒤
2개의 라벨로 구분한 데이터이다.
</pre>



## 코드
* (Python)

## 데이터 출처
* NSL-KDD
* CIC DoS(2017)
* CSE-CIC-IDS2018 on AWS (https://registry.opendata.aws/cse-cic-ids2018/)
