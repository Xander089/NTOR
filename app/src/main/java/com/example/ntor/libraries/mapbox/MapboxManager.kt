package com.example.ntor.libraries.mapbox

import android.content.Context
import androidx.appcompat.content.res.AppCompatResources
import com.example.ntor.R
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location

class MapboxManager(
    private val handlePositionReading: (Double, Double) -> Unit = { _, _ -> }
) {

    private lateinit var mapView: MapView

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

}