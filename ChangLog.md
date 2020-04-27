# SDK Change Log
## v1.2.2
### 새로운 기능
* content3D를 이동시킬수 있는 Animator API를 추가 합니다. ```VPSFragment.getContent3DMoveAnimator```를 참고하세요.
* content3D의 AnimationData를 접근 할 수 있는 API를 추가 합니다. ```Content3D.getAnimationData()```를 참고하세요.
### 버그 수정
* Content2D,Content3D 제스쳐로 스케일, 회전 되는 버그를 수정하였습니다.
* VPS State의 상태가 Pause -> Tracking으로 변경될때 노출되었던 Content2D, Content3D가 나오지 않는 버그를 수정하였습니다.

## v1.2.3
### 새로운 기능
#### VPSFragment.class
* Content2D,Content3D를 추가, 삭제 할 수 있는 API를 추가합니다.
	* ```VPSFragment.addContent2D```
	* ```VPSFragment.removeContent2D```
	* ```VPSFragment.addContent3D```
	* ```VPSFragment.removeContent3D```

#### Content2D.class
* scale, z(높이 값), 노출 거리를 설정 할 수 있도록 API를 추가 합니다.
	* ```Content2D.setScale```
	* ```Content2D.setPosition```
	* ```Content2D.setVisibleDistance```

### API 변경
#### VPSFragment.class
* ```VPSFragment.initContents2D``` API가 ```VPSFragment.addContent2DList``` 로 이름을 변경하였습니다.
* 기존 ```VPSFragment.initContents3D``` API가 ```VPSFragment.addContent3DList``` 로 이름을 변경하였습니다.

#### Content2D.class
* ```Content2D.setLocation``` API가 ```Content2D.setPosition```로 변경하였습니다.
