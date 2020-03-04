## Add UIComponent

Custom UIComponent를 추가/제거,
UIComponent 객체 간의 충돌시 UIComponent show/hide 기능을 확인할 수 있는 액티비티 입니다.

<img src="sample.gif" width="236.5" height="500" />

## Sample Code

- ./samplecomponent/SampleLogoUIComponent.kt
	```kotlin
	class SampleLogoUIComponent constructor(context: Context) : UIComponent(context) {

        init {
            addView(UicomponentSampleLogoBinding.inflate(LayoutInflater.from(context)).root)

            /**
             * UIComponent 영역 설정
             *
             * 미설정시 UIComponent의 충돌검사가 정상적으로 동작하지 않음
             */
            setWidthDp(240)
            setHeightDp(140)
        }
    }
	```

- AddUIComponentActivity.kt
    ```kotlin
    // add custom uicomponent
    mMapView.addUIComponent(mSampleLogoUIComponent, 0, UIPositionType.TOP_LEFT)

    // remove custom uicomponent
    mMapView.removeUIComponent(mSampleLogoUIComponent)
    ```