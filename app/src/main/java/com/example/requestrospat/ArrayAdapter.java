package com.example.requestrospat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.requestrospat.models.Hit;

import java.util.List;
import java.util.Locale;

public class ArrayAdapter extends BaseAdapter {

    private List<Hit> responses;
    private Context context;

    public ArrayAdapter(List<Hit> responses, Context context) {
        this.responses = responses;
        this.context = context;
    }

    @Override
    public int getCount() {
        return responses.size();
    }

    @Override
    public Hit getItem(int position) {
        return responses.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint({"SetTextI18n", "InflateParams"})
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = LayoutInflater.from(context)
                    .inflate(R.layout.custome_list_item, null);
        }

        Hit hit = getItem(position);
        String title = hit.getBiblio().getRu().getTitle();
        String author = hit.getBiblio().getRu().getInventor().get(0).getName();
        title = title.trim();
        author = author.trim();
        title = properCase(title);

        ((TextView) view.findViewById(R.id.title)).setText(title);
        ((TextView) view.findViewById(R.id.author)).setText("Автор " +
                properCase(author.split(" ")[0]) + " " + properCase(author.split(" ")[1]) +
                " " + properCase(author.split(" ")[2]));
        ((TextView) view.findViewById(R.id.mpk)).setText(hit.getCommon().getClassification().getIpc().get(0).getFullname());
        ((TextView) view.findViewById(R.id.doc)).setText(hit.getCommon().getPublishingOffice() + " " +
                hit.getCommon().getDocumentNumber() + " " + hit.getCommon().getKind() + " " +
                hit.getCommon().getPublicationDate());

        return view;
    }

    private String properCase(String inputVal) {
        if (inputVal.length() == 0) return "";
        if (inputVal.length() == 1) return inputVal.toUpperCase();
        return inputVal.substring(0, 1).toUpperCase()
                + inputVal.substring(1).toLowerCase();
    }
}
