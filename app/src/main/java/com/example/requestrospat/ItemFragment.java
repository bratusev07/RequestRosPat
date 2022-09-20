package com.example.requestrospat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.requestrospat.models.Biblio;
import com.example.requestrospat.models.Hit;
import com.example.requestrospat.models.MyPriority;

public class ItemFragment extends Fragment {

    private static final String HIT_KEY = "hit_key";
    private Hit hit;
    private String color = "#ABD1FF";
    private View root;

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
        return root;
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
        setData();
    }

    private void setData() {
        String des = hit.getSnippet().getDescription();
        String titleString = hit.getBiblio().getRu().getTitle();
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
        String priorityString = "";
        String authorString = "";

        poster.setText(hit.getSnippet().getApplicant());
        try {
            for (MyPriority myPriority : hit.getCommon().getPriority()) {
                String tmp = myPriority.toString().trim();
                priorityString += tmp + "\n\n";
            }
        } catch (Exception e) {
            priorityString = "undefined";
        }
        priority.setText(priorityString);
        try {
            for (Biblio.Ru.Inventor inventor : hit.getBiblio().getRu().getInventor()) {
                String tmp = inventor.getName().trim();
                authorString += tmp + "\n\n";
            }
        } catch (Exception e) {
            authorString = "undefined";
        }

        authors.setText(authorString);
        owner.setText(hit.getSnippet().getPatentee());
        docs.setText("docs");
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
