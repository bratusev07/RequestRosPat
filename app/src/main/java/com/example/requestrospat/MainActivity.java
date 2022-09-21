package com.example.requestrospat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.requestrospat.models.Hit;
import com.example.requestrospat.models.MyBaseModel;
import com.example.requestrospat.models.RosResponse;
import com.example.requestrospat.models.TmpObject;
import com.example.requestrospat.services.NetworkServices;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String token = "26a213594e7f4f6e8cd89064d885ea93";

    private String[] sortType = {"relevance", "publication date:asc", "publication date:desc",
                "filing date:asc", "filing date:desc"};

    private String[] groupType = {"family:docdb", "family:dwpi"};

    private Button btnSearch;
    private Button btnFilter;
    private EditText etSearch;

    private EditText etAuthor;
    private EditText etCountry;
    private EditText etKind;
    private EditText etPatentHolder;

    private BottomSheetBehavior bottomSheetBehavior;
    private ConstraintLayout bottomSheet;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);
        bottomSheet = findViewById(R.id.bottom_sheet);

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);


        btnFilter = findViewById(R.id.btnFilter);
        btnSearch = findViewById(R.id.btnSearch);
        etSearch = findViewById(R.id.etSearch);

        etAuthor = findViewById(R.id.etAuthor);
        etCountry = findViewById(R.id.etCountry);
        etKind = findViewById(R.id.etKind);
        etPatentHolder = findViewById(R.id.etPatentHolder);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("myLog", "Click on " + i);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mainScreenActivity_Container, ItemFragment.newInstance((Hit) adapterView.getItemAtPosition(i)))
                        .addToBackStack("").commit();
            }
        });

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmpty(etSearch)) {
                    Toast.makeText(getApplicationContext(), "Поле поиска пусто", Toast.LENGTH_LONG).show();
                } else {
                    MyBaseModel model = new MyBaseModel(etSearch.getText().toString());
                    MyBaseModel.MyFilter filter = new MyBaseModel.MyFilter();

                    if (!isEmpty(etAuthor)) {
                        ArrayList<String> authors = new ArrayList<>();
                        authors.add(etAuthor.getText().toString());
                        filter.setAuthors(new TmpObject(authors));
                    }
                    if (!isEmpty(etCountry)) {
                        ArrayList<String> country = new ArrayList<>();
                        country.add(etCountry.getText().toString().toUpperCase(Locale.ROOT));
                        filter.setCountry(new TmpObject(country));
                    }
                    if (!isEmpty(etKind)) {
                        ArrayList<String> kind = new ArrayList<>();
                        kind.add(etKind.getText().toString());
                        filter.setKind(new TmpObject(kind));
                    }
                    if (!isEmpty(etPatentHolder)) {
                        ArrayList<String> patent_holders = new ArrayList<>();
                        patent_holders.add(etPatentHolder.getText().toString());
                        filter.setPatent_holders(new TmpObject(patent_holders));
                    }

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

                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

    }

    private boolean isEmpty(EditText et) {
        return et.getText().length() == 0;
    }

}