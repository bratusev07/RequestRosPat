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
        String title;
        try {
            title = hit.getBiblio().getRu().getTitle();
        } catch (Exception e) {
            title = hit.getBiblio().getEn().getTitle();
        }
        String author;
        try{author = hit.getBiblio().getRu().getInventor().get(0).getName();}catch (Exception e){
            author = hit.getBiblio().getEn().getInventor().get(0).getName();
        }
        String ipc;
        try{ipc = hit.getCommon().getClassification().getIpc().get(0).getFullname();}catch (Exception e){
            ipc = "untitled";
        }
        title = title.trim();
        author = author.trim();

        ((TextView) view.findViewById(R.id.title)).setText(title);
        ((TextView) view.findViewById(R.id.author)).setText("Автор " + author);
        ((TextView) view.findViewById(R.id.mpk)).setText("МПК " + ipc);
        ((TextView) view.findViewById(R.id.doc)).setText("Документ " + hit.getCommon().getPublishingOffice() + " " +
                hit.getCommon().getDocumentNumber() + " " + hit.getCommon().getKind() + " " +
                hit.getCommon().getPublicationDate());

        return view;
    }
}
