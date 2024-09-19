# S005 Fridge는 Free지  
  

## 기획 배경
- 'AI를 활용한 냉장고 Manager' 과제에서 생성형 AI를 도입한 냉장고 서비스 개발
- CES 2024의 비스포크 냉장고 패밀리허브 플러스의 기능을 바탕으로 패밀리허브 기반 냉장고 서비스 고안  
&nbsp;
&nbsp;
  
  
## 서비스 소개  
![Freezia 소개 이미지](assets/소개.png)
- 비스포크 냉장고 패밀리허브 플러스에 나 중심의 패러다임을 더한 개인화 서비스
- 모바일 기기를 사용하여 자유롭게 이동하면서 서비스를 사용하고, 개인 데이터를 기반으로 맞춤 서비스를 제공  
  - 기존에는 냉장고 스크린 앞에 사용자가 있어야 했다면 갤럭시워치를 도입하여 워치안에서 서비스를 사용할 수 있음  
  - 기존에는 냉장고 식재료 기반으로 레시피를 제공했다면 여기에 개인데이터를 더해 맞춤 레시피 제공  
&nbsp;
&nbsp;


## 기능 소개

### 메인 화면
<img src="https://github.com/user-attachments/assets/dec7c6d5-f5bd-40e9-862f-c96df9d336f4" width="300"/>

### 사용자 온보딩 화면
<p>
  <img src="https://github.com/user-attachments/assets/f67059e3-a30d-456b-844a-9a686ffae663" width="300" />
  <img src="https://github.com/user-attachments/assets/14c0ccbf-962a-4e3d-bfd5-d64a696a259a" width="300" />
  <img src="https://github.com/user-attachments/assets/f5247285-518c-49ab-b7e0-c305f4bf813f" width="300" />
</p>

### 사용자 정보 화면
<img src="https://github.com/user-attachments/assets/dca5d1e1-8c5b-4448-93cb-4543299348a4" width="300"/>

### 레시피 둘러보기 화면
<img src="https://github.com/user-attachments/assets/4f1f1cf0-5355-4d27-aece-fadee260f163" width="300"/>

### 레시피 생성하기 화면
<img src="https://github.com/user-attachments/assets/ba619012-6fcc-4dfa-a1e8-15824e292d7e" width="300"/>

### 레시피 상세조회 화면
<p>
  <img src="https://github.com/user-attachments/assets/d1f19806-1a9e-4080-968f-d8c866ccb4a2" width="300" />
  <img src="https://github.com/user-attachments/assets/6ff5ca7a-d546-4f08-95a0-304271eb99c8" width="300" />
</p>

### 레시피 요리 진행 화면
<p>
  <img src="https://github.com/user-attachments/assets/1caf910d-64eb-4f9c-b1e5-ed88f910cc33" width="300" />
  <img src="https://github.com/user-attachments/assets/e7e74b3b-23a1-41d0-851f-25f3566f83af" width="300" />
  <img src="https://github.com/user-attachments/assets/3b30d062-382b-4fa2-a96f-3da614d1d68f" width="300" />
</p>

### 워치 레시피 요리 진행 화면
<p>
  <img src="https://github.com/user-attachments/assets/fcc97dad-212a-499e-9330-19a7dd28570a" width="300" />
  <img src="https://github.com/user-attachments/assets/a0311f87-28d6-46bc-a9b0-7b0a64d069cc" width="300" />
  <img src="https://github.com/user-attachments/assets/2a99f3f6-2b37-48ec-bdd0-e60d5df1f94c" width="300" />
</p>

### 워치 타이머
<p>
  <img src="https://github.com/user-attachments/assets/64025029-4f6c-415e-b0c4-4de3d6298f19" width="300" />
  <img src="https://github.com/user-attachments/assets/ef3ef491-9e42-404c-9e9f-aabe2cd5b44c" width="300" />
  <img src="https://github.com/user-attachments/assets/48841890-a433-4d86-9134-b1a8ba65ff5b" width="300" />
</p>

### 워치 위험 식재료 알림
<img src="https://github.com/user-attachments/assets/c68bc743-23a6-4362-8910-606d1fd07b30" width="300"/>

### 워치 냉장고 재고 확인
<img src="https://github.com/user-attachments/assets/d454ba56-c623-470e-966d-fbee206f5163" width="300"/>
  
&nbsp;
&nbsp;

  
## 시스템 아키텍처
![시스템아키텍처](/assets/시스템_아키텍처.png)  
&nbsp;
&nbsp; 
  
  
## 기대효과
- 스마트싱스와 빅스비로 이루어진 스마트홈에서 패밀리허브를 중심으로 갤럭시 워치, 스마트폰을 도입하여 삼성의 생태계 확장
- 생성형 AI를 활용하여 기존의 레시피 확인까지 필요했던 3단계를 1단계로 줄여 사용자 경험 강화
- 유통기한 임박 식재료를 기반으로 레시피를 추천하여 사용자가 식재료를 잘 소비할 수 있게하여 음식물 쓰레기 감소, 환경오염 감소로 친환경 이룸
&nbsp;
&nbsp;
  
  
## 확장가능성
- 기존의 냉장고 앞 사용자 인식 과정을 단순화할 수 있음
  - 삼성의 화자인식(VOICE ID) 기술 사용
  - 모바일기기에 uwb 기술을 적용해 패밀리허브 가까이에 있는 사용자 인식
- 다양한 개인화 서비스로 발전할 수 있음
  - 누가 어떤 식재료를 꺼냈는지 안다면 삼성 헬스의 식단기록과 연동하여 자동으로 식단 기록  
  - 기록된 식단으로 바탕으로 영양을 분석하고 영양 관리
  - 영양 상태를 바탕으로 생성형 AI를 통해 식단 추천

