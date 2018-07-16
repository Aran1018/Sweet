package com.lucky.sweet.utility

import com.tencent.mapsdk.raster.model.LatLng

object GaoDeMapUtil {

    val GAODE_MAP = "m.amap.com/navi/?start=116.403124,39.940693" +
            "&dest=116.481488,39.990464" +
            "&destName=阜通西" +
            "naviBy=car&key=31d07ef08c787f75c25bf496fa7affb5"

    fun getGaoDeMapUri(start: LatLng, dest: LatLng): String {
        return "m.amap.com/navi/?start=" + start.latitude + "," + dest.longitude + "&dest=" + dest.latitude + "," + start.longitude +
                "&destName=目的地&naviBy=car&key=31d07ef08c787f75c25bf496fa7affb5"
    }
}
