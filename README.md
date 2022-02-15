# PhotoFrame Step1
## 프로젝트 실행하기
### 안드로이드 애뮬레이터에서 실행
![image](https://user-images.githubusercontent.com/58967292/153792063-08ed489b-7af4-4534-9085-0c638edf1d46.png)
### 하드웨어 기계에서 앱 실행
![image](https://user-images.githubusercontent.com/58967292/153981226-1d3bfe62-9b53-4607-9cfa-6bd704b71320.png)

### LogCat 활용하기
![step1_logcat](https://user-images.githubusercontent.com/58967292/153793055-f41b0568-eff4-4440-8f55-2e75d6ac078c.PNG)

## Activity
* 액티비티는 사용자에게 사용자 인터페이스를 제공하는 앱 구성요소이다
* 액티비티는 화면의 기본 구성단위이다.

## Activity 작동
* UI는 XML 파일로, 동작은 .kt파일로 구성, 둘이 한 쌍을 이룸

## Activity 생명주기
![image](https://user-images.githubusercontent.com/58967292/153981987-86468b48-206d-4d63-b347-28dbcf729cc1.png)

* 사람이 태어나서 죽을떄까지 유아기,청소년기,노년기를 거치듯 액티비티도 생성부터 소멸까지 여러단계를 거친다
* 액티비티는 각 상태마다 할 수 있는 행동, 해야하는 행동이 다르다.
* 액티비티는 사용자의 활동에 따라 새로운 상태에 들어가고, 그 상태에 들어가면 시스템은 미리 정의한 콜백 함수를 실행한다.

* onCreate()
  * 액티비티를 '생성된 상태'로 만들어준다
  * 액티비티에 필요한 초기 설정을 여기서 해주면된다 ( EX setContentView() 함수로 사용자에게 보여줄 레이아웃을 선택, 레이아웃을 정의한 파일(리소스)의 ID를 파라미터로 준다)
  * '생성된 상태'가 되려면 화면이 있어야 하고 화면이 있기 위해서는 사용할 레이아웃이 무엇인지 알아야 한다.
  * 한줄 요역: 시스템이 액티비티를 처음시작할때 실행되며, 레이아웃 지정, 클래스 범위 변수 초기화등 기본적인 앱 시작로직 구현해주는 단계이다. 

* onStart()
  * 액티비티가 시작된 상태에 들어가기 직전에 실행되는 콜백함수
  * 액티비티가 사용자에게 보이지만, 사용자와의 상호작용은 아직 준비하는 단계 => 포커스가 아직 없음
  * UI 관련 로직 초기화 처리 단계

* onResume()
  * 액티비티가 재개된 상태로 들어가기 직전에 실행되는 콜백함수. 
  * onResume()가 호출되는 Case 
    * 액티비타가 처음 실행되고 create-start를 거쳐 resume이 되는 경우
    * 다른 액티비티로 넘어갔다가, 다시 돌아오는 과정에서 restart->start 를 거쳐 resume이 되는 경우 
    * 이벤트로 인해 액티비티가 중단되거나 , 멀티 윈도우의 경우 다른 앱에 포커스가 갔다 다시 해당 앱으로 포커스가 생기는 경우  onPause() 상태에서 onResume()이 호출된다. 
  * 액티비티와 사용자와의 상호작용이 가능해진다.
  * 전화가 오거나, 다른 액티비티로 이동하는 등 포커스를 잃는 경우가 아닌 이상 액티비티는 재개된 상태로 존재

* 포커스
  *  특정 뷰가 사용자와 상호작용하기 시작하면 해당 뷰에 '포커스가 있다'라고 한다
  *  포커스가 없다 != 보이지 않는다 , 멀티 윈도우 환경에서 보일수 있다

* onPause()
  * 사용자가 액티비티를 떠나는 경우 처음 실행되는 콜백함수 
  * 더는 해당 액티비티에 포커스가 없다  but 액티비티가 소멸된 것이 아니다 
  * 액티비티가 완전히 보이지않을때나 다시 실행될때까지 pause 상태에 머무른다
  * onPause()가 호출되는 Case
    * 일부 이벤트가 앱실행을 방해함
    * 멀티 윈도우의 경우, 여러개의 앱을 실행하는데 포커스를 가질수있는 앱은 하나뿐이다. 포커스를 가진 앱 이외의 앱들은 일시중지 되어야 한다.
    * 새로운 반투명 액티비티가 실행 => 부분적으로 액티비티가 여전히 보일 수 있지만, 포커스가 없기 때문에 일시정지된 상태 
  * 액티비티가 보이지 않을 때 or 사용자와 상호작용할 필요가 없을 떄,  더 이상 실행할 필요없는 부분들을 비활성화 해주면 된다 
  * 대부분은 경우 onPause()이후 
  * onPause()가 지속되는 시간이 굉장히 짧음 => 사용자 정보를 DB에 저장하거나 네트워크 호출을 하는등 중요하거나 시간이 오래걸리는 작업을 수행하면 안된다
  * 부하가 큰 작업은 다음 콜백인 onStop()에서 수행해 줘야 한다

* onStop()
  * 액티비티가 사용자에게 더이상 표시 안되는 완전히 중단된 상태에 들어가기 직전에 실행되는 콜백함수
  * onPause()에서 언급한 부하가 큰 작업들을 실행할 수 있다

* onDestory()
  * 액티비티가 완전히 솔명되기 직전에 호출되는 콜백함수

* onRestart()
  * 중단된 상태에 있던 액티비티가 사용자에 의해 재실행되는 경우 먼저 호출되는 콜백함수
  * onstart() 함수가 이어서 호출된다

