# VPS
Dabeeo VPS(Vision Positioning System) 기능을 사용할 수 있는 AR, 지도뷰를 나타낸 뷰 컨트롤러 입니다.

## 측위
VPSFragment를 사용하기 위해서는 **수집 프로세스**가 필요합니다.
아래의 Sample Code 및 영상은 **수집 프로세스**를 진행 한 후에 정상 작동이 되는 점 참고하시기 바랍니다.

* 자세한 사항은 [Dabeeo](https://www.dabeeo.com/ko/contact/)에 문의 부탁드립니다.

## 권한 설정
* VPSFragment를 사용하기 위해서는 아래와 같이 필수 권한이 필요합니다.

```android
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.CAMERA" />
```

## AR Core
Dabeeo VPS는 Google AR Core 기술을 이용합니다.
[AR Core를 지원하는 기기](https://developers.google.com/ar/discover/supported-devices)를 참고해주시기 바랍니다.

'Google Play AR 서비스' 앱이 설치되지 않으면 다음과 같은 Error Code를 전달 합니다.

* Code : 8000
* Message : NEED_UPDATE_OR_INSTALL_ARCORE	

## Sample Code
[VPSActivity](./VPSActivity.kt) 에서는 아래와 같은 기능이 있습니다.

1. 내 위치 측위
2. 2DContent 추가

<video controls autoplay loop> 
   <source src="sample.mp4" type="video/mp4"> 
</video>

