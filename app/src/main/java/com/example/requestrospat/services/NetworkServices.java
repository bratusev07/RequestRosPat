package com.example.requestrospat.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/** Класс для отправки запросов на сервер */
public class NetworkServices {
    private static NetworkServices mInstance;
    private static final String BASE_URL =
            "https://searchplatform.rospatent.gov.ru/patsearch/v0.2/"; //адрес сервера
    private Retrofit mRetrofit; // Объект для отправки запросов

    /** Конструктор класса */
    private NetworkServices() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    /** Метод для получения instance */
    public static NetworkServices getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkServices();
        }
        return mInstance;
    }

    /** Метод для обращения к вспомогательному интерфейсу */
    public JSONPlaceHolderApi getJSONApi() {
        return mRetrofit.create(JSONPlaceHolderApi.class);
    }
}