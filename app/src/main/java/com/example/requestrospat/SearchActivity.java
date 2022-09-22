package com.example.requestrospat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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
    private int step = 30;

    private EditText etSearch;
    private ImageButton btnSearch;
    private ImageButton btnFilter;

    private EditText etAuthor;
    private EditText etCountry;
    private EditText etKind;
    private EditText etPatentHolder;
    private EditText etDateLeft;
    private EditText etDateRight;

    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.search_layout);

        etSearch = findViewById(R.id.etSearch);
        btnSearch = findViewById(R.id.btnSearch);
        btnFilter = findViewById(R.id.btnFilter);

        View v = findViewById(R.id.filterFields);
        v.setVisibility(View.GONE);

        etAuthor = v.findViewById(R.id.etAuthor);
        etCountry = v.findViewById(R.id.etCountry);
        etKind = v.findViewById(R.id.etKind);
        etPatentHolder = v.findViewById(R.id.etPatentHolder);/*
        etDateLeft = v.findViewById(R.id.etDateLeft);
        etDateRight = v.findViewById(R.id.etDateRight);*/

        btnFilter.setOnClickListener(this::showPopupMenu);


        btnSearch.setOnClickListener(view -> {
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
                if (!isEmpty(etDateLeft)) {
                    ArrayList<String> date_left = new ArrayList<>();
                    date_left.add(etDateLeft.getText().toString());
                    filter.setPatent_holders(new TmpObject(date_left));
                }
                if (!isEmpty(etDateRight)) {
                    ArrayList<String> date_right = new ArrayList<>();
                    date_right.add(etDateRight.getText().toString());
                    filter.setPatent_holders(new TmpObject(date_right));
                }

                model.setLimit(step);
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
        });
    }

    private void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(R.menu.pop_up_menu);

        popupMenu
                .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("NonConstantResourceId")
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu1:

                                return true;
                            case R.id.menu2:

                                return true;
                            case R.id.menu3:

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
