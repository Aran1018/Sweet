package com.lucky.sweet.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebViewClient
import com.lucky.sweet.R
import com.lucky.sweet.utility.GaoDeMapUtil
import com.tencent.mapsdk.raster.model.LatLng
import kotlinx.android.synthetic.main.activity_map_web_acivity.*

class MapWebAcivity : AppCompatActivity() {

     val GAODE_MAP_KEY="65db589bc83a741541ebdb30810e7205"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_web_acivity)
        initView()

    }


    fun initView() {
        val urlPath = getUrlPath(intent.getStringExtra(START_LAT),intent.getStringExtra(START_LON),intent.getStringExtra(END_LAT), intent.getStringExtra(END_LON))
        println(urlPath)
        wb_map.settings.javaScriptEnabled=true
        wb_map.settings.javaScriptCanOpenWindowsAutomatically=true
        wb_map.loadUrl(urlPath)
    }

    fun getUrlPath(startLat: String,startLon: String,lat: String, lon: String): String {

        return "http://m.amap.com/navi/?start=$startLat,$startLon&dest=$lat,$lon&destName=您的目的地&naviBy=car&key=$GAODE_MAP_KEY"
    }


    companion object {

        val START_LAT = "START_LAT"
        val  START_LON = "START_LON"
        val END_LAT = "END_LAT"
        val END_LON = "END_LON"

        fun InStance(activity: Activity,startLat :String,startLon: String, endlong: String, endLat: String) {
            var intent = Intent(activity, MapWebAcivity::class.java)
            intent.putExtra(START_LAT, startLat)
            intent.putExtra(START_LON, startLon)
            intent.putExtra(END_LAT, endLat)
            intent.putExtra(END_LON, endlong)
            activity.startActivity(intent)

        }
    }
}
