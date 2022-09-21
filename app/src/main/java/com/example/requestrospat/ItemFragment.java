package com.example.requestrospat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.requestrospat.models.Hit;

import java.util.Objects;

public class ItemFragment extends Fragment {

    private static final String HIT_KEY = "hit_key";
    private Hit hit;
    private View root;

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
