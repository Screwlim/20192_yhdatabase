Training.java
신경망을 구축하고 학습시키기 위한 코드입니다. 학습 후 모델을 resources/trainedModel 폴더에 저장합니다.

Testing.java
저장된 모델을 읽어 입력한 데이터셋에 대해 테스트 하는 코드입니다.

Service.java
실시간 패킷 분류 모듈 코드입니다. Classification.java, Shell.java를 사용합니다.

Classification.java
실제로 패킷 데이터셋을 정상/공격으로 분류하는 코드입니다.

Shell.java
Linux환경에서 자바코드로 쉘코드를 실행하기 위한 클래스 입니다.