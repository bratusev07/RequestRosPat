package com.example.requestrospat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
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
    private ListView listView;
    private int offset = 0;
    private int step = 30;
    private ArrayList<Hit> responses;
    private int listSize = 0;

    private String[] sortType = {"relevance", "publication date:asc", "publication date:desc",
            "filing date:asc", "filing date:desc"};

    private String[] groupType = {"family:docdb", "family:dwpi"};

    private MyBaseModel model;

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
        responses = new ArrayList<>();
        listView = findViewById(R.id.listView);

        MyBaseModel.MyFilter filter = new MyBaseModel.MyFilter();
        model = new MyBaseModel("Ракета");

        ArrayList<String> authors = null;
        ArrayList<String> country = null;
        ArrayList<String> kind = null;
        ArrayList<String> patent_holders = null;

        ListView listView = findViewById(R.id.listView);
        bottomSheet = findViewById(R.id.bottom_sheet);

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        btnFilter = findViewById(R.id.btnFilter);
        btnSearch = findViewById(R.id.btnSearch);
        etSearch = findViewById(R.id.etSearch);

        model.setLimit(step);
        model.setFilter(filter);

        if (isNetworkConnected()) {
            findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
            getList();
        } else {
            new AlertDialog.Builder(MainActivity.this)
                    .setMessage("Проверьте интернет соединение")
                    .create().show();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("myLog", "Click on " + i);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mainScreenActivity_Container, ItemFragment.newInstance((Hit) adapterView.getItemAtPosition(i)))
                        .addToBackStack("").commit();
            }
        });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void getList() {
        listSize = responses.size();
        if(offset > 0) listSize-=2;
        NetworkServices.getInstance().getJSONApi().getRequest(token, model).enqueue(new Callback<RosResponse>() {
            @Override
            public void onResponse(Call<RosResponse> call, Response<RosResponse> response) {
                findViewById(R.id.loadingPanel).setVisibility(View.INVISIBLE);
                responses.addAll(response.body().getHits());
                listView.setAdapter(new ArrayAdapter(responses, getApplicationContext()));
                listView.setSelection(listSize);
                if (response.body().getHits().size() > 0) setOnScroll();
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
    }

    private void setOnScroll() {
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (listView.getLastVisiblePosition() == listView.getAdapter().getCount() - 1
                        && listView.getChildAt(listView.getChildCount() - 1).getBottom() <= listView.getHeight()) {
                    Log.d("scroll", "bottom");
                    offset += step;
                    model.setOffset(offset);
                    findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
                    getList();
                    
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