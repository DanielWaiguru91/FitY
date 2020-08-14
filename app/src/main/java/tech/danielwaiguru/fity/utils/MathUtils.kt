package tech.danielwaiguru.fity.utils

import android.location.Location
import tech.danielwaiguru.fity.service.route
import kotlin.math.round

object MathUtils {
    fun calculateTotalDistance(route: route): Float{
        var distance = 0f
         for (i in 0..route.size - 2){
            val startPoint = route[i]
            val endPoint = route[i + 1]
            val resultArray = FloatArray(1)
            Location.distanceBetween(
                startPoint.latitude,
                startPoint.longitude,
                endPoint.latitude,
                endPoint.longitude,
                resultArray
            )
             distance += resultArray[0]
        }
        return distance
    }

    /**
     * speed = totalSpeed / totalTime
     */
    fun calculateAverageSpeed(distance: Float, time: Long): Float {
        return round((distance / 1000f ) / (time / 1000f / 60 / 60) * 10) /10f
    }
}