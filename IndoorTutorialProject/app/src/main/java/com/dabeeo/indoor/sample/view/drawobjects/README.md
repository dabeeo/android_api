
## Add DrawObjects  
  
MapView에 DrawObjects를 추가하는 액티비티 입니다.  

<img src="sample.gif" width="236.5" height="500" />

## Sample Code  
  
- AddDrawObjectsActivity.kt

	- Add Polygon  
		```kotlin
		/**
		 * Polygon 생성자에 사용하는 포인트는 시계 방향 순서 
		 */
		private fun addPolygon() {  
			val polygonOptions = PolygonOptions().apply {  
			    fillColor = getRandomColorHexCode()
			    strokeColor = getRandomColorHexCode()  
			}  
			  
			Polygon(  
			    listOf(  
			        Point(  
			            mRandom.nextDouble(0.0, mMapInfo.size.width / 2),  
			            mRandom.nextDouble(0.0, mMapInfo.size.height / 2),  
			            0.0  
			        ),  
			        Point(  
			            mRandom.nextDouble(mMapInfo.size.width / 2, mMapInfo.size.width),  
			            mRandom.nextDouble(0.0, mMapInfo.size.height / 2),  
			            0.0  
			        ),  
			        Point(  
			            mRandom.nextDouble(mMapInfo.size.width / 2, mMapInfo.size.width),  
			            mRandom.nextDouble(mMapInfo.size.height / 2, mMapInfo.size.height),  
			            0.0  
			        ),  
			        Point(  
			            mRandom.nextDouble(0.0, mMapInfo.size.width / 2),  
			            mRandom.nextDouble(mMapInfo.size.height / 2, mMapInfo.size.height),  
			            0.0  
			        )),  
			    polygonOptions,  
			    mMapView  
			).addTo()
		}
		```

	- Add Polyline
		```kotlin
		/**
         * Polyline 생성자에 사용하는 포인트는 라인의 포인트 순서
         */
        private fun addPolyline() {
            val polylineOptions = PolylineOptions().apply {
                color = getRandomColorHexCode()
            }

            Polyline(
                listOf(
                    Point(
                        mRandom.nextDouble(0.0, mMapInfo.size.width),
                        mRandom.nextDouble(0.0, mMapInfo.size.height),
                        0.0
                    ),
                    Point(
                        mRandom.nextDouble(0.0, mMapInfo.size.width),
                        mRandom.nextDouble(0.0, mMapInfo.size.height),
                        0.0
                    ),
                    Point(
                        mRandom.nextDouble(0.0, mMapInfo.size.width),
                        mRandom.nextDouble(0.0, mMapInfo.size.height),
                        0.0
                    ),
                    Point(
                        mRandom.nextDouble(0.0, mMapInfo.size.width),
                        mRandom.nextDouble(0.0, mMapInfo.size.height),
                        0.0
                    ),
                    Point(
                        mRandom.nextDouble(0.0, mMapInfo.size.width),
                        mRandom.nextDouble(0.0, mMapInfo.size.height),
                        0.0
                    )
                ),
                polylineOptions,
                mMapView
            ).addTo()
        }
        ```

    - Add Circle
        ```kotlin
        private fun addCircle() {
            val circleOptions = CircleOptions().apply {
                radius = mRandom.nextDouble(50.0, 100.0)
                fillColor = getRandomColorHexCode()
                strokeColor = getRandomColorHexCode()
                fillOpacity = mRandom.nextDouble(0.0, 1.1)
                strokeOpacity = mRandom.nextDouble(0.0, 1.1)
            }

            Circle(
                Point(
                    mRandom.nextDouble(0.0, mMapInfo.size.width),
                    mRandom.nextDouble(0.0, mMapInfo.size.height),
                    0.0
                ),
                circleOptions,
                mMapView
            ).addTo()
        }
        ```

    - Add Marker
        ```kotlin
        /**
         * URL을 이용한 마커 추가
         */
        private fun addMarker() {
            val markerOptions = MarkerOptions().apply {
                icon = Image(
                    Size(mRandom.nextDouble(50.0, 100.0), mRandom.nextDouble(50.0, 100.0)),
                    URL("https://indoor.dabeeomaps.com/image/assets/logo_maps-3x-54848d261e3cc1fbd0465a25c50dcc7a.png")
                )
                title = SimpleDateFormat("hh:mm:ss").format(System.currentTimeMillis())
                fontColor = getRandomColorHexCode()
                fontSize = mRandom.nextInt(20, 40)
                isAutoRotate = mRandom.nextInt(2) == 0
                isAutoScale = mRandom.nextInt(4) == 2
            }

            Marker(
                this,
                Point(
                    mRandom.nextDouble(0.0, mMapInfo.size.width),
                    mRandom.nextDouble(0.0, mMapInfo.size.height),
                    0.0
                ),
                markerOptions,
                mMapView
            ).addTo()
        }
        ```