package com.daffamuhtar.fmkotlin.services

import com.daffamuhtar.fmkotlin.data.remote.response.RefreshTokenResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AccountServices {

    @FormUrlEncoded
    @POST("api/auth/refresh_token")
    fun postRefreshToken(
        @Field("userId") userId: String,
        @Field("currentAppVersion") currentAppVersion: String,
        @Field("appType") appType: String
    ): Call<com.daffamuhtar.fmkotlin.data.remote.response.RefreshTokenResponse>


}