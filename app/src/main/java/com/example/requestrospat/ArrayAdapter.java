package com.example.requestrospat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.requestrospat.models.Hit;

import java.util.List;

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

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = LayoutInflater.from(context)
                    .inflate(R.layout.custome_list_item, null);
        }

        ((TextView)view.findViewById(R.id.name)).setText(getItem(position).getBiblio().getRu().getTitle());
        return view;
    }
}
