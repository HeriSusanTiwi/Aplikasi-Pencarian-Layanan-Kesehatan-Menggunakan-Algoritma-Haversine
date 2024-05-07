package com.herisusan.pencarianlayanankesehatan.utils

object AlgortimaHaversine {
    // Rumus Jarak Haversine
    fun getDistance(
        currentLatitude: Double,
        currentLongitude: Double,
        destinationLatitude: Double,
        destinationLongitude: Double
    ): Double {
        val earthRadius = 3958.75
        val dLatitude = Math.toRadians(destinationLatitude - currentLatitude)
        val dLongitude = Math.toRadians(destinationLongitude - currentLongitude)
        val a = Math.sin(dLatitude / 2) * Math.sin(dLatitude / 2) +
                Math.cos(Math.toRadians(currentLatitude)) *
                Math.cos(Math.toRadians(destinationLatitude)) *
                Math.sin(dLongitude / 2) * Math.sin(dLongitude / 2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        val dDistance = earthRadius * c
        val meterConversion = 1609

        // Jika jarak kurang dari 1 km, kembalikan dalam meter
        val distanceInMeter = dDistance * meterConversion
        return if (distanceInMeter < 1000) {
            distanceInMeter
        } else {
            Math.floor(distanceInMeter / 1000)
        }
    }

}