package com.example.requestrospat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.requestrospat.models.MyBaseModel;
import com.example.requestrospat.models.RosResponse;
import com.example.requestrospat.models.TmpObject;
import com.example.requestrospat.services.NetworkServices;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String token = "26a213594e7f4f6e8cd89064d885ea93";

    private String[] sortType = {"relevance", "publication date:asc", "publication date:desc",
                "filing date:asc", "filing date:desc"};

    private String[] groupType = {"family:docdb", "family:dwpi"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);

        MyBaseModel.MyFilter filter = new MyBaseModel.MyFilter();
        MyBaseModel model = new MyBaseModel("Ракета");

        ArrayList<String> authors = null;
        ArrayList<String> country = null;
        ArrayList<String> kind = null;
        ArrayList<String> patent_holders = null;

        /*authors = new ArrayList<>();
        authors.add("Такеда Кенго (JP)");*/

        filter.setAuthors(new TmpObject(authors));
        filter.setCountry(new TmpObject(country));
        filter.setKind(new TmpObject(kind));
        filter.setPatent_holders(new TmpObject(patent_holders));

        model.setLimit(100);
        model.setFilter(filter);

        NetworkServices.getInstance().getJSONApi().getRequest(token, model).enqueue(new Callback<RosResponse>() {
            @Override
            public void onResponse(Call<RosResponse> call, Response<RosResponse> response) {
                listView.setAdapter(new ArrayAdapter(response.body().getHits(), getApplicationContext()));
            }

            @Override
            public void onFailure(Call<RosResponse> call, Throwable t) {
                Log.d("resultCode", t.getMessage());
            }
        });
    }
}