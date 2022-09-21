package com.example.requestrospat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.requestrospat.models.Hit;

import java.util.List;

public class RecyclerCustomAdapter extends RecyclerView.Adapter<RecyclerCustomAdapter.MyViewHolder> {

    private Context context;
    private List<Hit> hits;

    public RecyclerCustomAdapter(Context context, List<Hit> hits) {
        this.context = context;
        this.hits = hits;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custome_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Hit hit = hits.get(position);

        String title = "untitled";
        try {
            title = hit.getBiblio().getRu().getTitle();
            title = title.trim();
        } catch (Exception e) {
            try {
                title = hit.getBiblio().getEn().getTitle();
                title = title.trim();
            } catch (Exception exception) {
            }
        }
        String author;
        try {
            author = hit.getBiblio().getRu().getInventor().get(0).getName();
            author = author.trim();
        } catch (Exception e) {
            try {
                author = hit.getBiblio().getEn().getInventor().get(0).getName();
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

        holder.getTitle().setText(title);
        holder.getAuthor().setText("Автор " + author);
        holder.getMpk().setText("МПК " + ipc);
        holder.getDoc().setText("Документ " + hit.getCommon().getPublishingOffice() + " " +
                hit.getCommon().getDocumentNumber() + " " + hit.getCommon().getKind() + " " +
                hit.getCommon().getPublicationDate());
    }

    @Override
    public int getItemCount() {
        return hits.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView author;
        private TextView title;
        private TextView doc;
        private TextView mpk;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.author);
            title = itemView.findViewById(R.id.title);
            doc = itemView.findViewById(R.id.doc);
            mpk = itemView.findViewById(R.id.mpk);
        }

        public TextView getAuthor() {
            return author;
        }

        public TextView getTitle() {
            return title;
        }

        public TextView getDoc() {
            return doc;
        }

        public TextView getMpk() {
            return mpk;
        }
    }
}
