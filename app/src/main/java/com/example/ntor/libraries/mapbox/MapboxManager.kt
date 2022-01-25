package com.example.ntor.libraries.mapbox

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import com.example.ntor.R
import com.example.ntor.core.entities.Point
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.createPolylineAnnotationManager
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location

class MapboxManager(
) {

    private var handlePositionReading: (Double, Double) -> Unit = { _, _ -> }
    private lateinit var mapView: MapView

    fun setMapBoxHandlePosition(lambda : (Double, Double) -> Unit){
       handlePositionReading = lambda
    }

    fun setMapView(_mapView: MapView) {
        this.mapView = _mapView
    }

    fun onMapReady(context: Context) {
        mapView.getMapboxMap().setCamera(
            CameraOptions.Builder()
                .zoom(14.0)
                .build()
        )
        mapView.getMapboxMap().loadStyleUri(
            Style.LIGHT
        ) {
            initLocationComponent(context)
            mapView.gestures.addOnMoveListener(onMoveListener)
        }
    }

    fun onRouteReady(context: Context, points: List<Point>) {
        if(points.isEmpty()){
            return
        }

        val route = points.map { toMabBoxPoint(it) }
        val center = getMapCenter(route)
        mapView.getMapboxMap().setCamera(
            CameraOptions.Builder()
                .zoom(14.0)
                .center(center)
                .build()
        )
        mapView.getMapboxMap().loadStyleUri(
            Style.SATELLITE
        ) {
            addAnnotationToMap(context, route.first())
            addAnnotationToMap(context, route.last())
        }
        addPolylineToMap(route)
    }

    private fun initLocationComponent(context: Context) {
        val locationComponentPlugin = mapView.location
        locationComponentPlugin.updateSettings {

            this.enabled = true
            this.locationPuck = LocationPuck2D(
                bearingImage = AppCompatResources.getDrawable(
                    context,
                    R.drawable.mapbox_user_puck_icon,
                ),
                shadowImage = AppCompatResources.getDrawable(
                    context,
                    R.drawable.mapbox_user_icon_shadow,
                ),
                scaleExpression = interpolate {
                    linear()
                    zoom()
                    stop {
                        literal(0.0)
                        literal(0.6)
                    }
                    stop {
                        literal(20.0)
                        literal(1.0)
                    }
                }.toJson()
            )
        }
        locationComponentPlugin.addOnIndicatorPositionChangedListener(
            onIndicatorPositionChangedListener
        )
        locationComponentPlugin.addOnIndicatorBearingChangedListener(
            onIndicatorBearingChangedListener
        )
    }

    private val onMoveListener = object : OnMoveListener {
        override fun onMoveBegin(detector: MoveGestureDetector) = onCameraTrackingDismissed()
        override fun onMove(detector: MoveGestureDetector) = false
        override fun onMoveEnd(detector: MoveGestureDetector) {}
    }

    private val onIndicatorBearingChangedListener = OnIndicatorBearingChangedListener {
        mapView
            .getMapboxMap()
            .setCamera(
                CameraOptions
                    .Builder()
                    .bearing(it)
                    .build()
            )
    }

    private val onIndicatorPositionChangedListener = OnIndicatorPositionChangedListener { point ->

        handlePositionReading(point.latitude(), point.longitude())
        mapView.getMapboxMap().setCamera(CameraOptions.Builder().center(point).build())
        mapView.gestures.focalPoint = mapView.getMapboxMap().pixelForCoordinate(point)
    }


    fun onCameraTrackingDismissed() {
        mapView.location
            .removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        mapView.location
            .removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        mapView.gestures.removeOnMoveListener(onMoveListener)
    }

    private fun toMabBoxPoint(point: Point) =
        com.mapbox.geojson.Point.fromLngLat(point.longitude, point.latitude)

    private fun addPolylineToMap(route: List<com.mapbox.geojson.Point>) {
        val annotationApi = mapView.annotations
        val polylineAnnotationManager = annotationApi.createPolylineAnnotationManager(mapView)
        val polylineAnnotationOptions: PolylineAnnotationOptions = PolylineAnnotationOptions()
            .withPoints(route)
            .withLineColor("#0369a1")
            .withLineWidth(5.0)
        polylineAnnotationManager.create(polylineAnnotationOptions)
    }

    private fun addAnnotationToMap(context: Context, point: com.mapbox.geojson.Point) {
        bitmapFromDrawableRes(
            context,
            R.drawable.ic_marker
        )?.let {
            val annotationApi = mapView.annotations
            val pointAnnotationManager = annotationApi.createPointAnnotationManager(mapView)
            val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
                .withPoint(point)
                .withIconImage(it)
            pointAnnotationManager.create(pointAnnotationOptions)
        }
    }

    private fun bitmapFromDrawableRes(context: Context, @DrawableRes resourceId: Int) =
        convertDrawableToBitmap(AppCompatResources.getDrawable(context, resourceId))

    private fun convertDrawableToBitmap(sourceDrawable: Drawable?): Bitmap? {
        if (sourceDrawable == null) {
            return null
        }
        return if (sourceDrawable is BitmapDrawable) {
            sourceDrawable.bitmap
        } else {
            val constantState = sourceDrawable.constantState ?: return null
            val drawable = constantState.newDrawable().mutate()
            val bitmap: Bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth, drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        }
    }

    private fun getMapCenter(route: List<com.mapbox.geojson.Point>): com.mapbox.geojson.Point {

        val longitudeList = route.map { point -> point.longitude() }
        val latitudeList = route.map { point -> point.latitude() }

        val maxLon = longitudeList.maxOrNull() ?: 0.0
        val minLon = longitudeList.minOrNull() ?: 0.0

        val maxLat = latitudeList.maxOrNull() ?: 0.0
        val minLat = latitudeList.minOrNull() ?: 0.0

        val mediumLon = (minLon + maxLon) / 2.0
        val mediumLat = (minLat + maxLat) / 2.0

        return com.mapbox.geojson.Point.fromLngLat(mediumLon, mediumLat)
    }

}