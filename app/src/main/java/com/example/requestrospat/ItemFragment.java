package com.example.requestrospat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.requestrospat.models.Biblio;
import com.example.requestrospat.models.Common;

import com.bumptech.glide.Glide;
import com.example.requestrospat.models.Hit;
import com.example.requestrospat.models.MyPriority;
import com.example.requestrospat.models.RosResponse;
import com.example.requestrospat.models.SameModel;
import com.example.requestrospat.services.NetworkServices;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemFragment extends Fragment implements View.OnClickListener {

    private static final String HIT_KEY = "hit_key";
    private Hit hit;
    private String color = "#ABD1FF";
    private View root;

    public static final String token = "26a213594e7f4f6e8cd89064d885ea93";

    private int authorCount = 0;
    private int docsCount = 0;
    private int ownerCount = 0;
    private int priorityCount = 0;
    private int posterCount = 0;


    private String priorityString = "";
    private String authorString = "";

    private TextView description;
    private TextView title;
    private TextView status;
    private TextView number;
    private TextView pushDate;
    private TextView postDate;
    private TextView priority;
    private TextView authors;
    private TextView poster;
    private TextView owner;
    private TextView docs;

    private RecyclerView sameList;
    private ImageView imageView;

    private int i;
    
    public static ItemFragment newInstance(Hit hit) {
        Bundle bundle = new Bundle();
        ItemFragment fragment = new ItemFragment();
        bundle.putSerializable(HIT_KEY, hit);
        fragment.setArguments(bundle);
        return fragment;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        hit = (Hit) getArguments().getSerializable(HIT_KEY);
        root = inflater.inflate(R.layout.info_screen, container, false);
        ViewFlipper flipper = root.findViewById(R.id.flipper);
        flipper.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            public void onSwipeRight() {
                flipper.showPrevious();
            }

            public void onSwipeLeft() {
                flipper.showNext();
            }
        });

        findViews();
        imageView = root.findViewById(R.id.imageView);



        imageView.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            public void onSwipeLeft() {
                String url = "https://searchplatform.rospatent.gov.ru/" + hit.getDrawings().get(i++).getUrl();
                Glide.with(requireContext()).load(url).into(imageView);

                if (i >= hit.getDrawings().size()) i = hit.getDrawings().size() - 1;
                if (i < 0) i = 0;
            }

            public void onSwipeRight() {
                String url = "https://searchplatform.rospatent.gov.ru/" + hit.getDrawings().get(i--).getUrl();
                Glide.with(requireContext()).load(url).into(imageView);

                if (i >= hit.getDrawings().size()) i = hit.getDrawings().size() - 1;
                if (i < 0) i = 0;
            }
        });

        return root;
    }

    private void setRecyclerView(){
        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        sameList.setLayoutManager(layoutManager);

        Common common = hit.getCommon();
        findTheSame(new SameModel(common.getPublishingOffice() + common.getDocumentNumber() +
                common.getKind() + "_" + common.getPublicationDate().replace(".", ""), 10));
    }

    private void findViews() {
        description = root.findViewById(R.id.info_des);
        title = root.findViewById(R.id.info_title);
        status = root.findViewById(R.id.info_status);
        number = root.findViewById(R.id.info_number);
        pushDate = root.findViewById(R.id.info_pushDate);
        postDate = root.findViewById(R.id.info_postDate);
        priority = root.findViewById(R.id.info_priority);
        authors = root.findViewById(R.id.info_authors);
        poster = root.findViewById(R.id.info_poster);
        owner = root.findViewById(R.id.info_owner);
        docs = root.findViewById(R.id.info_docs);
        sameList = root.findViewById(R.id.recycler_view);

        setRecyclerView();
        authors.setOnClickListener(this);
        owner.setOnClickListener(this);
        docs.setOnClickListener(this);
        poster.setOnClickListener(this);
        priority.setOnClickListener(this);
        setData();
    }

    private void setData() {
        String des = hit.getSnippet().getDescription();
        String titleString;
        try {
            titleString = hit.getBiblio().getRu().getTitle();
        } catch (Exception e) {
            titleString = hit.getBiblio().getEn().getTitle();
        }
        titleString = titleString.trim();
        des = "<p>" + des + "</p>";
        des = des.replace("<em>", "<span style=\"background-color: " + color + ";\">");
        titleString = titleString.replace("<em>", "<span style=\"background-color: " + color + ";\">");
        des = des.replace("</em>", "</span>");
        titleString = titleString.replace("</em>", "</span>");
        description.setText(Html.fromHtml(des));
        title.setText(Html.fromHtml(titleString));
        number.setText(hit.getCommon().getDocumentNumber());
        postDate.setText(hit.getCommon().getPublicationDate());
        status.setText("status");
        pushDate.setText("push date");

        poster.setText(hit.getSnippet().getApplicant());
        try {
            for (MyPriority myPriority : hit.getCommon().getPriority()) {
                String tmp = myPriority.toString().trim();
                priorityCount++;
                priorityString += tmp + "\n\n";
            }
        } catch (Exception e) {
            priorityString = "undefined";
        }
        priority.setText(priorityString);
        try {
            for (Biblio.Ru.Inventor inventor : hit.getBiblio().getRu().getInventor()) {
                String tmp = inventor.getName().trim();
                authorCount++;
                authorString += tmp + "\n\n";
            }
        } catch (Exception e) {
            for (Biblio.En.Inventor_1 inventor : hit.getBiblio().getEn().getInventor()) {
                String tmp = inventor.getName().trim();
                authorCount++;
                authorString += tmp + "\n\n";
            }
        }

        authors.setText(authorString);
        owner.setText(hit.getSnippet().getPatentee());
        docs.setText("docs");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.info_authors: {
                switchTextState(authors, authorCount, authorString);
            }
            break;
            case R.id.info_docs: {
                //switchTextState(docs, docsCount);
            }
            break;
            case R.id.info_owner: {
                //switchTextState(owner, ownerCount);
            }
            break;
            case R.id.info_priority: {
                switchTextState(priority, priorityCount, priorityString);
            }
            break;
            case R.id.info_poster: {
                //switchTextState(poster, posterCount);
            }
            break;
        }
    }

    private void switchTextState(TextView textView, int maxSize, String fulStr) {
        if (textView.getMaxLines() == 1 && maxSize > 1) {
            textView.setMaxLines(maxSize);
            try {
                textView.setText(fulStr);
            } catch (Exception e) {
            }
        } else {
            textView.setMaxLines(1);
        }
    }

    private void findTheSame(SameModel sameModel) {
        NetworkServices.getInstance().getJSONApi().findSame(token, sameModel).enqueue(new Callback<RosResponse>() {
            @Override
            public void onResponse(Call<RosResponse> call, Response<RosResponse> response) {
                sameList.setAdapter(new RecyclerCustomAdapter(getContext(), response.body().getHits()));
            }

            @Override
            public void onFailure(Call<RosResponse> call, Throwable t) {
                Log.d("same", t.getMessage());
            }
        });
    }

    public class OnSwipeTouchListener implements View.OnTouchListener {

        private final GestureDetector gestureDetector;

        public OnSwipeTouchListener(Context ctx) {
            gestureDetector = new GestureDetector(ctx, new GestureListener());
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }

        private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeRight();
                            } else {
                                onSwipeLeft();
                            }
                            result = true;
                        }
                    } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeBottom();
                        } else {
                            onSwipeTop();
                        }
                        result = true;
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }
        }

        public void onSwipeRight() {
        }

        public void onSwipeLeft() {
        }

        public void onSwipeTop() {
        }

        public void onSwipeBottom() {
        }
    }
}
