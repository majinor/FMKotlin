package com.daffamuhtar.fmkotlin.app

import android.content.Context
import com.daffamuhtar.fmkotlin.constants.Constanta
import com.daffamuhtar.fmkotlin.constants.ConstantaApp
import com.daffamuhtar.fmkotlin.constants.ConstantaApp.BASE_URL_V2_0


class Server {

    companion object {

        private val contexta: Context? = null
        var userId: String? = null
        var token: String? = null
        var companyType: String? = null

        var URL: String? = null
        var URL_V20: String? = null

//    ========================PRODUCTION===============
//    public static final String URL = "https://api-v10.fleetify.id/";

//    public static String URL1 = "https://api-v10.fleetify.id/";
//    public static String URL2 = "https://api-app-v10.fleetify.id/";
//    public static String URL1_V20 = "https://api-v20.fleetify.id/";
//    public static String URL1_V20_REP = "http://192.168.0.48:3000/";

//    ==========================STAGING=================
//    public static final String URL = "https://api-staging-v10.fleetify.id/";
//    ---------------------------------------------

        //    ========================PRODUCTION===============
        //    public static final String URL = "https://api-v10.fleetify.id/";
        //    public static String URL1 = "https://api-v10.fleetify.id/";
        //    public static String URL2 = "https://api-app-v10.fleetify.id/";
        //    public static String URL1_V20 = "https://api-v20.fleetify.id/";
        //    public static String URL1_V20_REP = "http://192.168.0.48:3000/";
        //    ==========================STAGING=================
        //    public static final String URL = "https://api-staging-v10.fleetify.id/";
        //    ---------------------------------------------
        var URL1 = "https://api-staging-v10.fleetify.id/"
        var URL2 = "https://api-app-staging-v10.fleetify.id/"
        var URL1_V20 = "https://api-staging-v20-dot-fair-catcher-256606.el.r.appspot.com/"
        var URL1_V20_REP = "https://api-staging-v20-dot-fair-catcher-256606.el.r.appspot.com/"


//    =========================LOCAL==============

//    public static final String URL = "http://192.168.0.168/fleetify_api_temporary/";

//    public static String URL1 = "https://ef15-180-254-64-203.ngrok.io/fleetify_api/";
//    public static String URL2 = "https://ef15-180-254-64-203.ngrok.io/fleetify_api_internal_vendor/";

//    ----------------------------------------------------

        //    public static String URL1 = "http://192.168.0.168/fleetify_api/";
//    public static String URL2 = "http://192.168.0.168/fleetify_api_internal_vendor/";
//    public static String URL1_V20 = "http://192.168.0.168:3000/";
//    public static String URL1_V20 = "https://api-v10.fleetify.id/";

//    public static String URL1 = "http://192.168.0.14/fleetify_api/";
//    public static String URL2 = "http://192.168.0.14/fleetify_api_internal_vendor/";

//    public static String URL1 = "http://192.168.0.48/fleetify_api/";
//    public static String URL2 = "http://192.168.0.48/fleetify_api_internal_vendor/";
//    public static String URL1_V20 = "http://192.168.0.48:3000/";

//    public static String URL1 = "http://192.168.43.180/fleetify_api/";
//    public static String URL2 = "http://192.168.43.180/fleetify_api_internal_vendor/";

//    =================================================================


        //    =========================LOCAL==============
        //    public static final String URL = "http://192.168.0.168/fleetify_api_temporary/";
        //    public static String URL1 = "https://ef15-180-254-64-203.ngrok.io/fleetify_api/";
        //    public static String URL2 = "https://ef15-180-254-64-203.ngrok.io/fleetify_api_internal_vendor/";
        //    ----------------------------------------------------
        //    public static String URL1 = "http://192.168.0.168/fleetify_api/";
        //    public static String URL2 = "http://192.168.0.168/fleetify_api_internal_vendor/";
        //    public static String URL1_V20 = "http://192.168.0.168:3000/";
        //    public static String URL1_V20 = "https://api-v10.fleetify.id/";
        //    public static String URL1 = "http://192.168.0.14/fleetify_api/";
        //    public static String URL2 = "http://192.168.0.14/fleetify_api_internal_vendor/";
        //    public static String URL1 = "http://192.168.0.48/fleetify_api/";
        //    public static String URL2 = "http://192.168.0.48/fleetify_api_internal_vendor/";
        //    public static String URL1_V20 = "http://192.168.0.48:3000/";
        //    public static String URL1 = "http://192.168.43.180/fleetify_api/";
        //    public static String URL2 = "http://192.168.43.180/fleetify_api_internal_vendor/";
        //    =================================================================
        var URLTEST1 = "http://192.168.0.168/fleetify_api/"
        var URLTEST2 = "http://192.168.0.168/fleetify_api_internal_vendor/"

        var URLTEST3 = "http://192.168.0.168:3000/"

        //    public static String URLTEST3 = "https://api-staging-v20-dot-fair-catcher-256606.el.r.appspot.com/";
        //    public static String URLTEST3 = "https://api-v20-dot-fair-catcher-256606.el.r.appspot.com/";
        public fun urlTesting(context: Context): String {
            checkId(context)
            val URL1 = URLTEST1
            val URL2 = URLTEST2
            var URL: String? = null
            URL = if (companyType != null) {
                if (companyType == "1") {
                    URL1
                } else {
                    URL2
                }
            } else {
                URL1
            }
            return URL
        }

        fun urlSample(context: Context?, apiVersion: String?): String {
            if (context != null) {
                checkId(context)
            }
            var URL = URL1
            URL = if (companyType != null) {
                if (companyType == "1") {
                    when (apiVersion) {
                        ConstantaApp.BASE_URL_V2_0 -> URL1_V20
                        ConstantaApp.BASE_URL_V2_0_REP -> URL1_V20_REP
                        else -> URL1 //ConstantaApp.BASE_URL_V1_0
                    }
                } else {
                    URL2
                }
            } else {
                URL1
            }
            return URL
        }

        fun checkId(context: Context) {
            val sharedpreferences =
                context.getSharedPreferences(Constanta.my_shared_preferences, Context.MODE_PRIVATE)
            userId = sharedpreferences.getString(Constanta.EXTRA_USERID, null)
            token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjIiLCJ1c2VybmFtZSI6InVtLXRlc3RpbmctOTk5OSIsImlhdCI6MTY4OTgyNjA4MCwiZXhwIjoxNjg5OTEyNDgwfQ.q__EBWCuTn54Rc_OHtpkh2qFzLFGCkkbq6Yx3Fl7CM4"
            companyType = sharedpreferences.getString(Constanta.EXTRA_COMPANYTYPE, null)
        }

    }

//==============================


}