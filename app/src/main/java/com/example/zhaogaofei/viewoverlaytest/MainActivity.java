package com.example.zhaogaofei.viewoverlaytest;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.ViewOverlay;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FrameLayout redLayout;
    private FrameLayout orangeLayout;
    private FrameLayout greenLayout;
    private Button btOne;
    private Button btTwo;
    private Button btThree;
    private TextView tvReset;

    private boolean redDefault = true;
    private boolean orangeDefault = true;
    private boolean greenDefault = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        redLayout = findViewById(R.id.red_container);
        orangeLayout = findViewById(R.id.orange_container);
        greenLayout = findViewById(R.id.green_container);
        btOne = findViewById(R.id.button);
        btTwo = findViewById(R.id.button2);
        btThree = findViewById(R.id.button3);
        btOne.setOnClickListener(this);
        btTwo.setOnClickListener(this);
        btThree.setOnClickListener(this);

        tvReset = findViewById(R.id.tv_reset);
        tvReset.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                redDefault = false;
                setRedAnimation();
                break;
            case R.id.button2:
                orangeDefault = false;
                setOrangeAnimation();
                break;
            case R.id.button3:
                greenDefault = false;
                setGreenAnimation();
                break;
            case R.id.tv_reset:
                reset();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void reset() {
        if (!redDefault) {
            redDefault = true;
            resetRedAnimation();
        }
        if (!orangeDefault) {
            orangeDefault = true;
            resetOrangeAnimation();
        }
        if (!greenDefault) {
            greenDefault = true;
            resetGreenAnimation();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @SuppressLint("WrongConstant")
    private void setRedAnimation() {
        final ViewGroup container = (ViewGroup) redLayout.getParent();
        container.getOverlay().add(btOne);

        ObjectAnimator anim = ObjectAnimator.ofFloat(btOne, "translationY", container.getHeight());
        anim.setDuration(2000);

        ObjectAnimator rotate = ObjectAnimator.ofFloat(btOne, "rotation", 0, 360);
        rotate.setRepeatCount(Animation.INFINITE);
        rotate.setRepeatMode(Animation.REVERSE);
        rotate.setDuration(350);

        final AnimatorSet set = new AnimatorSet();
        set.playTogether(anim, rotate);
        set.start();

        ValueAnimator ofInt = ObjectAnimator.ofInt(0, 1);
        ofInt.setDuration(2000);
        ofInt.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator arg0) {
                set.end();
                container.getOverlay().remove(btOne);
            }

            @Override
            public void onAnimationCancel(Animator arg0) {
                set.end();
                container.getOverlay().remove(btOne);
            }
        });
        ofInt.start();
    }

    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void resetRedAnimation() {
        final ViewGroup container = (ViewGroup) redLayout.getParent();
        container.getOverlay().add(btOne);

        ObjectAnimator anim = ObjectAnimator.ofFloat(btOne, "translationY", container.getHeight(), 0);
        anim.setDuration(2000);

        final ObjectAnimator rotate = ObjectAnimator.ofFloat(btOne, "rotation", 360, 0);
        rotate.setRepeatCount(Animation.INFINITE);
        rotate.setRepeatMode(Animation.REVERSE);
        rotate.setDuration(350);

        final AnimatorSet set = new AnimatorSet();
        set.playTogether(anim, rotate);
        set.start();

        ValueAnimator ofInt = ObjectAnimator.ofInt(0, 1);
        ofInt.setDuration(2000);
        ofInt.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator arg0) {
                set.end();
                container.getOverlay().remove(btOne);
                redLayout.addView(btOne);
            }

            @Override
            public void onAnimationCancel(Animator arg0) {
                set.end();
                container.getOverlay().remove(btOne);
                redLayout.addView(btOne);
            }
        });
        ofInt.start();
    }

    private void setOrangeAnimation() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(btTwo, "translationY", -orangeLayout.getHeight());
        anim.setDuration(1000);
        anim.start();
    }

    private void resetOrangeAnimation() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(btTwo, "translationY", -orangeLayout.getHeight(), 0);
        anim.setDuration(1000);
        anim.start();
    }

    private void setGreenAnimation() {
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(btThree, "alpha", 1f, 0f);
        fadeOut.setDuration(500);

        final ObjectAnimator anim = ObjectAnimator.ofFloat(btThree, "translationY", -orangeLayout.getHeight() * 2);
        anim.setDuration(2000);

        anim.addListener(new AnimatorListenerAdapter() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onAnimationEnd(Animator animation) {
                orangeLayout.getOverlay().remove(btThree);
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onAnimationCancel(Animator animation) {
                orangeLayout.getOverlay().remove(btThree);
            }
        });

        fadeOut.addListener(new AnimatorListenerAdapter() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onAnimationEnd(Animator arg0) {
                orangeLayout.getOverlay().add(btThree);
                btThree.setAlpha(1f);
                anim.start();
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onAnimationCancel(Animator arg0) {
                orangeLayout.getOverlay().add(btThree);
                btThree.setAlpha(1f);
                anim.start();
            }
        });

        fadeOut.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void resetGreenAnimation() {
        orangeLayout.getOverlay().add(btThree);

        final ObjectAnimator fadeOut = ObjectAnimator.ofFloat(btThree, "alpha", 0f, 1f);
        fadeOut.setDuration(500);

        final ObjectAnimator anim = ObjectAnimator.ofFloat(btThree, "translationY", -orangeLayout.getHeight() * 2, 0);
        anim.setDuration(2000);

        anim.addListener(new AnimatorListenerAdapter() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onAnimationEnd(Animator animation) {
                orangeLayout.getOverlay().remove(btThree);
                greenLayout.addView(btThree);
                btThree.setAlpha(0f);
                fadeOut.start();
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onAnimationCancel(Animator animation) {
                orangeLayout.getOverlay().remove(btThree);
                greenLayout.addView(btThree);
                btThree.setAlpha(0f);
                fadeOut.start();
            }
        });

        anim.start();
    }
}
