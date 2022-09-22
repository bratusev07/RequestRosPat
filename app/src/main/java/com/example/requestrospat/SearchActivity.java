package com.example.requestrospat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;

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

public class SearchActivity extends AppCompatActivity {

    public static final String token = "26a213594e7f4f6e8cd89064d885ea93";
    private ListView listView;
    private int offset = 0;
    private int step = 30;
    private ArrayList<Hit> responses;
    private int listSize = 0;
    private MyBaseModel model;

    private EditText etSearch;
    private ImageButton btnSearch;
    private ImageButton btnFilter;

    private EditText etAuthor;
    private EditText etCountry;
    private EditText etKind;
    private EditText etPatentHolder;
    private EditText etDateLeft;
    private EditText etDateRight;

    View v;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.search_layout);

        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        etSearch = findViewById(R.id.etSearch);
        btnSearch = findViewById(R.id.btnSearch);

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    if (etSearch.getText().toString().length() == 0) {
                        Toast.makeText(getApplicationContext(), "Введите запрос", Toast.LENGTH_SHORT).show();
                    } else {
                        v.setVisibility(View.GONE);
                        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);

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
                             /*if (!isEmpty(etDateLeft)) {
                                 ArrayList<String> date_left = new ArrayList<>();
                                 date_left.add(etDateLeft.getText().toString());
                                 filter.setPatent_holders(new TmpObject(date_left));
                             }
                             if (!isEmpty(etDateRight)) {
                                 ArrayList<String> date_right = new ArrayList<>();
                                 date_right.add(etDateRight.getText().toString());
                                 filter.setPatent_holders(new TmpObject(date_right));
                             }*/

                            model.setLimit(step);
                            model.setFilter(filter);

                            NetworkServices.getInstance().getJSONApi().getRequest(token, model).enqueue(new Callback<RosResponse>() {
                                @Override
                                public void onResponse(Call<RosResponse> call, Response<RosResponse> response) {
                                    listView.setAdapter(new ArrayAdapter(response.body().getHits(), getApplicationContext()));
                                    findViewById(R.id.loadingPanel).setVisibility(View.INVISIBLE);
                                }

                                @Override
                                public void onFailure(Call<RosResponse> call, Throwable t) {
                                    Log.d("resultCode", t.getMessage());
                                }
                            });

                        }
                    }
                    return true;
                }
                return false;
            }
        });
        btnSearch.setOnClickListener(view -> {
            v.setVisibility(View.GONE);
            findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);

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
                             /*if (!isEmpty(etDateLeft)) {
                                 ArrayList<String> date_left = new ArrayList<>();
                                 date_left.add(etDateLeft.getText().toString());
                                 filter.setPatent_holders(new TmpObject(date_left));
                             }
                             if (!isEmpty(etDateRight)) {
                                 ArrayList<String> date_right = new ArrayList<>();
                                 date_right.add(etDateRight.getText().toString());
                                 filter.setPatent_holders(new TmpObject(date_right));
                             }*/

                model.setLimit(step);
                model.setFilter(filter);

                NetworkServices.getInstance().getJSONApi().getRequest(token, model).enqueue(new Callback<RosResponse>() {
                    @Override
                    public void onResponse(Call<RosResponse> call, Response<RosResponse> response) {
                        listView.setAdapter(new ArrayAdapter(response.body().getHits(), getApplicationContext()));
                        findViewById(R.id.loadingPanel).setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onFailure(Call<RosResponse> call, Throwable t) {
                        Log.d("resultCode", t.getMessage());
                    }
                });

            }
        });
        btnFilter = findViewById(R.id.btnFilter);

        String input = getIntent().getStringExtra("input");
        etSearch.setText(input);
        responses = new ArrayList<>();
        listView = findViewById(R.id.listView);
        model = new MyBaseModel(input);

        btnFilter.setOnClickListener(this::showPopupMenu);

        if (isNetworkConnected()) {
            findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
            getList();
        } else {
            new AlertDialog.Builder(SearchActivity.this)
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

    private void getList() {
        listSize = responses.size();
        if (offset > 0) listSize -= 2;
        NetworkServices.getInstance().getJSONApi().getRequest(token, model).enqueue(new Callback<RosResponse>() {
            @Override
            public void onResponse(Call<RosResponse> call, Response<RosResponse> response) {
                findViewById(R.id.loadingPanel).setVisibility(View.INVISIBLE);
                findViewById(R.id.loadingPanel).findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
                responses.addAll(response.body().getHits());
                listView.setAdapter(new ArrayAdapter(responses, getApplicationContext()));
                listView.setSelection(listSize);
                if (response.body().getHits().size() > 0) setOnScroll();
                v = findViewById(R.id.filterFields);
                v.setVisibility(View.GONE);

                etAuthor = v.findViewById(R.id.etAuthor);
                etCountry = v.findViewById(R.id.etCountry);
                etKind = v.findViewById(R.id.etKind);
                etPatentHolder = v.findViewById(R.id.etPatentHolder);
                etDateLeft = v.findViewById(R.id.etDateLeft);
                etDateRight = v.findViewById(R.id.etDateRight);

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

            @Override
            public void onFailure(Call<RosResponse> call, Throwable t) {

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
                }
            }
        });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(R.menu.pop_up_menu);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("NonConstantResourceId")
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu1:
                                model = new MyBaseModel(etSearch.getText().toString());
                                model.setSort("relevance");
                                getList();
                                return true;
                            case R.id.menu2:
                                model = new MyBaseModel(etSearch.getText().toString());
                                model.setOffset(5);
                                getList();
                                return true;
                            case R.id.menu3:
                                model = new MyBaseModel(etSearch.getText().toString());
                                model.setOffset(24);
                                getList();
                                return true;
                            case R.id.menu4:
                                model = new MyBaseModel(etSearch.getText().toString());
                                model.setOffset(37);
                                getList();
                                return true;
                            case R.id.menu5:
                                model = new MyBaseModel(etSearch.getText().toString());
                                model.setOffset(11);
                                getList();
                                return true;
                            default:
                                return false;
                        }
                    }
                });

        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {

            }
        });
        popupMenu.show();
    }

    private boolean isEmpty(EditText et) {
        return et.getText().length() == 0;
    }
}
