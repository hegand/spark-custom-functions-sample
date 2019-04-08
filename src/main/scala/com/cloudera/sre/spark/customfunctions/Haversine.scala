package com.cloudera.sre.spark.customfunctions

import org.apache.spark.sql.api.java.UDF4


class Haversine extends UDF4[Double, Double, Double, Double, Double] {
  override def call(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Double = Haversine(lat1,lng1,lat2,lng2)

  def Haversine(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Double = {
    val earthRadius = 6378137.0
    val dLat = Math.toRadians(lat2) - Math.toRadians(lat1)
    val dLng = Math.toRadians(lng2) - Math.toRadians(lng1)
    val a = Math.sin(dLat / 2.0) * Math.sin(dLat / 2.0) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2.0) * Math.sin(dLng / 2.0)
    val c = 2.0 * Math.atan2(Math.sqrt(a), Math.sqrt(1.0 - a))
    val dist = (earthRadius * c).toFloat
    dist
  }
}
