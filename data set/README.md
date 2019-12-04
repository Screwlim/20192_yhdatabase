# Dataset 1

## 파일 정보

### Code - Non-integer data -> integer data
#### 비정수형 데이터를 정수형 데이터로 변환
<pre>
2label_full_feature_data
KDD-NSL 형태의 데이터의 모든 특징에 대하여 normal / abnormal 로 구분
</pre>
<pre>
2label_some_feature_data_for_linux
KDD-NSL 형태의 데이터의 10 ~ 22번 특징을 제외 , normal / abnormal 로 구분 , linux에서 인자로 input 및 output 설정 가능
</pre>
<pre>
5label_full_feature_data
KDD-NSL 형태의 데이터의 모든 특징에 대하여 5개의 label로 구분 (normal , dos , probe , R2L , U2R)
</pre>
### Data
<pre>
raw - KDD-NSL(Train , Test) , CSE (dos1 , dos2) 
</pre>
<pre>
modify - 2label_full_xxxx(KDD-NSL)
5label_full_xxxx(KDD-NSL)
5label_full_67 5개의 라벨에 대하여 67개씩만 따로 추출 라벨간의 표본 갯수가 차이나서 동일하게 학습시킨 결과 확인
2label_some_final(KDD-NSL + cse + packet capture) 
기존에 가지고 있던 데이터 + wireshark의 패킷 캡쳐 기능을 이용하여 수집한 데이터의 총 합에 대하여 10~22번 특징 제외
</pre>



## 코드
* (Python)

## 데이터 정보
* (KDD-NSL)
* (CSE-CIC - dos)

