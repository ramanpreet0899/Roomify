package com.example.findroommate;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
public interface FcmApi {
    @Headers({"Authorization: key=AAAAhkA5kEM:APA91bGCVeRzCBNb8Wm7_Ktn8KyCA6zRFtSE0ezkDNOA6uwDcesqIdQLL7fc2iHb6teW0uoBPOXZnhSsySyXs3s4q_ZGtAT9ftHh4KUgNpg9EnPYV8I8K7GsPG5j1b00Kzv0XhmGGDGL",
            "Content-Type:application/json"})
    @POST("fcm/send")
    Call<ResponseBody> sendChatNotification(@Body RequestNotificaton requestNotificaton);
}
