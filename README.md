# MP2021-2-9
1.설치 방법 및 버전
SDK
android 10.0(Q)
Installation
avd기기..? 같은거,,,? 어떤거 다운받아서 썼는지?
폴더

3. 파일별 수행하는 내용
layout
- promotion_main_screen.xml : 부스 홍보 메인화면 layout
- shopping_main_screen.xml : 굿즈 메인 화면 layout
- activity_userpage.xml : 개인화면 페이지(다른 페이지로 넘어가기 위한 버튼들이 모여있음)
drawable
-
Java
- AddItemFrag.java / AddPromotionFrag.java : edittext에서 받은 정보를 string으로 바꿔 데이터 베이스에 저장하는 역할
- Detailpromotionfrag.java :  홍보 메인페이지의 정보를 받아 화면에 띄우는 역할 (사진, 이름,부스장소, 시간, 상세정보)
- AddPromotionData.java : 앱에서 작성한 데이터를 담아 파이어베이스에 보내기 위해 쓰임