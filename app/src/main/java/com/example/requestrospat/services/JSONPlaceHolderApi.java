package com.example.requestrospat.services;

import com.example.requestrospat.models.MyBaseModel;
import com.example.requestrospat.models.RosResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/** Вспомогательный интерфейс для отправки запросов на сервер */
public interface JSONPlaceHolderApi {

    @POST("search")
    Call<RosResponse> getRequest(@Header("Authorization") String token, @Body MyBaseModel model);
}
