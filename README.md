<img src="/image/logo.png" width="240" height="47.5"></img>
# Tutorial for Android

본 저장소는 다비오맵스 SDK를 보다 쉽게 적용하기 위한 튜토리얼 프로젝트를 제공합니다.


## Index

1. [Init MapView](/IndoorTutorialProject/app/src/main/java/com/dabeeo/indoor/sample/view/basic/)
2. [Add DrawObjects](/IndoorTutorialProject/app/src/main/java/com/dabeeo/indoor/sample/view/drawobjects/)
3. [Add UIComponent](/IndoorTutorialProject/app/src/main/java/com/dabeeo/indoor/sample/view/uicomponent/)
4. [Add Event (Map Poi)](/IndoorTutorialProject/app/src/main/java/com/dabeeo/indoor/sample/view/event/)
5. [Add Event (Custom DrawObjects)](/IndoorTutorialProject/app/src/main/java/com/dabeeo/indoor/sample/view/event/)
6. [Map Animation](/IndoorTutorialProject/app/src/main/java/com/dabeeo/indoor/sample/view/animation/)
7. [Preview](/IndoorTutorialProject/app/src/main/java/com/dabeeo/indoor/sample/view/navigation/)
8. [VPS](/IndoorTutorialProject/app/src/main/java/com/dabeeo/indoor/sample/view/vps/)

## API 문서
- 상세한 API는 [Android_국문_1.2.2.pdf](./Android_국문_1.2.2.pdf) 파일을 확인하세요.
-[SDK ChangeLog](./ChangLog.md)


## Download
- [다비오맵스 홈페이지](https://www.dabeeomaps.com/docs/android) 에서 다운로드


## Setup Android Studio
- import *com.dabeeo.maps.indoormap-[latest_version].aar*

### AndroidManifest
- permission

	```xml
    <uses-permission android:name="android.permission.INTERNET"/>  
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.CAMERA" />
	```

### build.gradle (project)
- dependency

	```
	classpath 'com.google.ar.sceneform:plugin:1.15.0'
	```


### build.gradle(app)

- plugin

	```
	apply plugin: 'com.google.ar.sceneform.plugin'
	```


- minSdkVersion

	```kotlin
    minSdkVersion 24
	```
- dependencies

	```kotlin
    // RecyclerView - if you use androidx library 
    implementation 'androidx.recyclerview:recyclerview:[your_androidx_library_version]'  
    
    // RecyclerView - if you use android support library  
    // implementation 'com.android.support:recyclerview-v7:[your_support_library_version]'  
      
    // Google AR
    implementation 'com.google.ar.sceneform.ux:sceneform-ux:1.15.0'
    implementation 'com.google.ar.sceneform:core:1.15.0'
    implementation 'com.google.ar.sceneform:assets:1.15.0'
    implementation "com.google.ar.sceneform:animation:1.15.0"

    
    // Provides ARCore Session and related resources.
    implementation 'com.google.ar:core:1.15.0'
      
    // Picasso  
    implementation 'com.squareup.picasso:picasso:2.71828'
    
    //Gson
    implementation group: 'com.google.code.gson', name: 'gson', version: '2.8.5'
	```
- compileOptions

    ```
    // AR CORE
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
    ```

## Debug Log
- Enable **(Default)**
	
	```kotlin
    IndoorMapConfig.BUILD_DEBUG = true
	```
- Disable
	
	```kotlin
    IndoorMapConfig.BUILD_DEBUG = false
	```

