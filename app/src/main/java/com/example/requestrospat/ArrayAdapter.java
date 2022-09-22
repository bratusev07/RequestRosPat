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
            title = title.trim();
        } catch (Exception e) {
            try {
                title = hit.getBiblio().getEn().getTitle();
                title = title.trim();
            } catch (Exception exception) {
                title = "untitled";
            }
        }
        String author;
        try {
            author = parseAuthor(hit.getBiblio().getRu().getInventor().get(0).getName());
            author = author.trim();
        } catch (Exception e) {
            try {
                author = parseAuthor(hit.getBiblio().getEn().getInventor().get(0).getName());
                author = author.trim();
            } catch (Exception exception) {
                author = "unnamed";
            }
        }
        String ipc;
        try {
            ipc = hit.getCommon().getClassification().getIpc().get(0).getFullname();
        } catch (Exception e) {
            ipc = "untitled";
        }

        ((TextView) view.findViewById(R.id.title)).setText(title);
        ((TextView) view.findViewById(R.id.author)).setText(author);
        ((TextView) view.findViewById(R.id.mpk)).setText(ipc);
        ((TextView) view.findViewById(R.id.doc)).setText(hit.getCommon().getDocumentNumber());

        return view;
    }

    private String parseAuthor(String author){
        String[] tmp = author.split(" ");
        String result = tmp[0];
        try {
            result += " " + tmp[1].charAt(0) + ".";
        }catch (Exception e){}

        try {
            if(tmp[2].charAt(0) != '(') result += " " + tmp[2].charAt(0) + ".";
        }catch (Exception e){}

        return result;
    }
}
