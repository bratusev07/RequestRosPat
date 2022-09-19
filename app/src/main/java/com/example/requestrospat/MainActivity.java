package com.example.requestrospat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.requestrospat.models.MyBaseModel;
import com.example.requestrospat.models.RosResponse;
import com.example.requestrospat.services.NetworkServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String token = "26a213594e7f4f6e8cd89064d885ea93";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NetworkServices.getInstance().getJSONApi().getRequest(token, new MyBaseModel("Ракета")).enqueue(new Callback<RosResponse>() {
            @Override
            public void onResponse(Call<RosResponse> call, Response<RosResponse> response) {
                Log.d("resultCode", response.body().getHits().get(0).getBiblio().getEn().getInventor().get(0).getName());
            }

            @Override
            public void onFailure(Call<RosResponse> call, Throwable t) {
                Log.d("resultCode", t.getMessage());
            }
        });
    }
}