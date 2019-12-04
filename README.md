## 딥러닝을 활용한 네트워크 이상패킷 감지 및 분류 개발
![java_badge](https://img.shields.io/badge/java-%3E%3D1.7(x64)-blue)
![maven badge](https://img.shields.io/badge/maven-%3E%3D3.5.3-red)
> 본 프로젝트는 [경북대학교](http://knu.ac.kr/wbbs/)와 [(주)YH데이터베이스](http://www.yhdatabase.com/)와의 산학 협력 프로젝트로 진행 되었습니다.

본 프로젝트는 기술의 발달로 다양해지고 변화하는 네트워크 상의 공격에 대응하기 위해 딥러닝 기법을 이용한 새로운 대처 방법을 찾고자 진행 되었습니다. 이를 통해 네트워크 장치의 분석 데이터를 기반으로 한 효율적인 이상 패킷 차단 기술을 확보하고, 그에 따른 적정 자원 할당 및 처리의 방향을 찾아보고자 합니다.


## 설치
실행환경 : 우분투 리눅스

1. 실행 환경인 [intelliJ](https://www.jetbrains.com/idea/download/#section=windows)와
7 이상 버전의 [java(jdk)](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html),
3.5.2 이상 버전의 [maven](https://maven.apache.org/download.cgi) 설치.

2. ~$ git clone https://github.com/Screwlim/20192_yhdatabase.git
의 파일들을 리눅스의 ~/ 디렉토리에 clone 합니다. (경로설정 때문에 꼭 ~/ 디렉토리에 해주세요.)

3. (밑의 설치 전 리눅스에 pcap 라이브러리가 깔려있어야 합니다.)
~$ git clone https://github.com/AI-IDS/kdd99_feature_extractor.git
~$ cd kdd99_feature_extractor
~/kdd99_feature_extractor$ mkdir build-files
~/kdd99_feature_extractor$ cd build-files
~/kdd99_feature_extractor/build-files$ cmake -DCMAKE_BUILD_TYPE=Debug -G "CodeBlocks - Unix Makefiles" ..
~/kdd99_feature_extractor/build-files$ cd ..
~/kdd99_feature_extractor$ cmake --build ./build-files --target kdd99extractor -- -j 4

.pcap 파일(raw packet)에서 nsl-kdd dataset의 특징들을 추출하는 코드입니다.

5. intellij에서 ~/20192_yhdatabase 의 pom.xml을 불러옵니다.
(import project 클릭 -> ~/20192_yhdatabase/pom.xml 불러오기)

6. ~/20192_yhdatabase/src/main/java/org/deeplearning4j/examples/deeplearning 경로에 신경망 코드파일들이 있습니다.
최종 실시간 패킷을 분류하는 코드파일은 Service.java, Classification.java, Shell.java입니다.

저희가 학습한 모델은 
~/20192_yhdatabase/src/main/resources/trainedModel 경로에 있습니다. 

7. Service.java를 열어 IPaddress변수에 자신의 IP주소를 입력합니다.

8. 분류 모델을 바꾸고 싶으면 Classification.java파일의 path변수에서 바꿔주면 됩니다.

9. Service.java를 실행시키면 실시간 패킷 분류 모듈이 실행됩니다.

[모듈 상세정보](https://github.com/Screwlim/20192_yhdatabase/blob/master/data%20set/README.md)


## 파일 정보
### data_set
<pre>
신경망을 학습시키고 테스트할 때 사용된 모든 데이터 셋
</pre>
### ML module
<pre>
DL4J라이브러리를 사용한 신경망 설계 프로젝트 파일
</pre>


## 크레딧
### 2015114262 임석렬
### 2014105009 김기윤
### 2014105106 황석영
### 2017113020 박효빈
