## 딥러닝을 활용한 네트워크 이상패킷 감지 및 분류 개발
![java_badge](https://img.shields.io/badge/java-%3E%3D1.7(x64)-blue)
![maven badge](https://img.shields.io/badge/maven-%3E%3D3.5.3-red)
> 본 프로젝트는 [경북대학교](http://knu.ac.kr/wbbs/)와 [(주)YH데이터베이스](http://www.yhdatabase.com/)와의 산학 협력 프로젝트로 진행 되었습니다.

> 본 프로젝트의 모듈은 Apache2.0 라이선스를 따르는 DL4J를 기반으로 작성 되었습니다.

본 프로젝트는 기술의 발달로 다양해지고 변화하는 네트워크 상의 공격에 대응하기 위해 딥러닝 기법을 이용한 새로운 대처 방법을 찾고자 진행 되었습니다. 이를 통해 네트워크 장치의 분석 데이터를 기반으로 한 효율적인 이상 패킷 차단 기술을 확보하고, 그에 따른 적정 자원 할당 및 처리의 방향을 찾아보고자 합니다.


## 설치
우분투 리눅스 환경을 기반으로 진행합니다.

1. 실행 환경인 [intelliJ](https://www.jetbrains.com/idea/download/#section=windows)와
7 이상 버전의 [java(jdk)](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html),
3.5.2 이상 버전의 [maven](https://maven.apache.org/download.cgi) 설치.

2. 실시간 패킷 처리 모듈을 실행하기 위해 pcap 라이브러리 [tcpdump](https://github.com/the-tcpdump-group/tcpdump)와
[python3](https://www.python.org/downloads/)을 설치.

3. git 복사. (경로 설정을 위해 ~$에 설치 하는 것을 권장)
<pre><code>~$ git clone https://github.com/Screwlim/20192_yhdatabase.git</code></pre>

4. pcap(raw packet) to nsl-kdd 변환 모듈 빌드.
<pre><code>~$ git clone https://github.com/AI-IDS/kdd99_feature_extractor.git
~$ cd kdd99_feature_extractor
~/kdd99_feature_extractor$ mkdir build-files
~/kdd99_feature_extractor$ cd build-files
~/kdd99_feature_extractor/build-files$ cmake -DCMAKE_BUILD_TYPE=Debug -G "CodeBlocks - Unix Makefiles" ..
~/kdd99_feature_extractor/build-files$ cd ..
~/kdd99_feature_extractor$ cmake --build ./build-files --target kdd99extractor -- -j 4
</code></pre>


## 실행
1. intelliJ에서 ~/20192_yhdatabase/ML module/pom.xml 불러오기.

2. ~/20192_yhdatabase/ML module/src/main/java/org/deeplearning4j/examples/deeplearning/Training3.java로 학습.
학습 완료 된 모델은 ~/20192_yhdatabase/ML module/src/main/resources/trainedModel 경로에 저장.

3. Service.java의 IPaddress 변수에 자신의 IP주소 입력.

4. Classification.java의 path 변수에 원하는 분류 모델(또는 학습 한 모델)설정.

5. Service.java 실행.


## 파일 정보
### data_set [(상세정보)](https://github.com/Screwlim/20192_yhdatabase/blob/master/data%20set/README.md)
<pre>
신경망을 학습시키고 테스트할 때 사용된 모든 데이터 셋

</pre>
### ML module
<pre>
DL4J라이브러리를 사용한 신경망 설계 프로젝트 파일
</pre>


## 참고
* DL4J (http://deeplearning4j.org/)
* kdd99 형식 추출 모듈 (https://github.com/AI-IDS/kdd99_feature_extractor)


## 깃 프로젝트 참여
#### 2015114262 임석렬 [Screwlim](https://github.com/Screwlim)
#### 2014105009 김기윤 [ko6154](https://github.com/ko6154)
#### 2014105106 황석영 [tjrdud123](https://github.com/tjrdud123)
#### 2017113020 박효빈 [Bachbold](https://github.com/Bachbold)
