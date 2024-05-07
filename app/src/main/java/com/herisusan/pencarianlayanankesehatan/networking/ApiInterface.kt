package com.herisusan.pencarianlayanankesehatan.networking

import com.herisusan.pencarianlayanankesehatan.data.model.ModelResult
import com.herisusan.pencarianlayanankesehatan.data.response.ModelResultDetail
import com.herisusan.pencarianlayanankesehatan.data.response.ModelResultNearby
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("nearbysearch/json")
    fun getDataResult(
               @Query("key")
                key: String,
                @Query("keyword")
                keyword: String,
                @Query("location")
                location: String,
                @Query("rankby")
                rankby: String?) : Call<ModelResultNearby>

    @GET("details/json")
    fun getDetailResult
                (@Query("key")
                 key: String,
                 @Query("placeid")
                 placeid: String): Call<ModelResultDetail>
}