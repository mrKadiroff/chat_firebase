package com.example.shoxgram.Retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @Headers(
        "Content-type:application/json",
        "Authorization:key=AAAA0d_J0Gw:APA91bHdDrBffQUrDM62Vcr8jzk0t5Xao2qWJOf-OM5dafM4yb0-RdV5v_LEjIFrtndQY4pVIMyMnb7bRrn6b5iC06fe-6Pxw7UyGZog1-s1nQCVmmuPq0Yvwscz0y4e5UVVMb2DdmP2"
    )
    @POST("fcm/send")
    fun sendNotification(@Body sender: Sender): Call<MyResponce>

}