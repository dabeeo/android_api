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

### build.gradle (project)
#### dependency
```
classpath 'com.google.ar.sceneform:plugin:1.15.0'
```

### build.gradle (app)

#### plugin
```
apply plugin: 'com.google.ar.sceneform.plugin'
```

#### compileOptions
```
// AR CORE
compileOptions {
    sourceCompatibility JavaVersion.VERSION_1_8
    targetCompatibility JavaVersion.VERSION_1_8
}
```

#### dependency
```
// Google AR
implementation 'com.google.ar.sceneform.ux:sceneform-ux:1.15.0'
implementation 'com.google.ar.sceneform:core:1.15.0'
implementation 'com.google.ar.sceneform:assets:1.15.0'
implementation "com.google.ar.sceneform:animation:1.15.0"


// Provides ARCore Session and related resources.
implementation 'com.google.ar:core:1.15.0'

//Gson
implementation group: 'com.google.code.gson', name: 'gson', version: '2.8.5'

```

#### Content3D Asset

Android SDK에서 3D Content를 로드하기 위해서 ARCore의 Sceneform을 사용하고 있습니다.

obj, fbx, gltf 확장자들에 대해 지원이 가능한 상태 이지만 ARCore Sceneform에서 obj, fbx를 사용하기 위해서는
Sceneform 플러그인을 통해서 app을 빌드 할 때 아래와 같이 [sfb 파일로 변환하는 작업](https://developers.google.com/sceneform/develop/import-assets)이 필요합니다.

(glft는 현재 Sceneform 1.15 버전에서는 애니메이션에 대해 지원하고 있지 않습니다.)

```
sceneform.asset('sampledata/andy/andy_dance.fbx',
            'default',
            'sampledata/andy/andy_dance.sfa',
            'src/main/assets/andy',
            ['sampledata/andy/andy_wave_r.fbx', 'sampledata/andy/andy_wave_l.fbx'])

sceneform.asset('sampledata/helicopter/helicopter.fbx',
        'default',
        'sampledata/helicopter/helicopter.sfa',
        'src/main/assets/helicopter')
```


## Sample Code
[VPSActivity](./VPSActivity.kt) 에서는 아래와 같은 기능이 있습니다.

1. 내 위치 측위
2. 2DContent 추가,삭제
3. 3DContent 추가,삭제
4. 3DContent 애니메이션 실행
5. VPSFragment MapView 드래그,리사이즈

<img src="sample.gif" width="236.5" height="500" />
