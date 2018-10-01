package code.general;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fittreat.android.R;

import code.view.BaseActivity;

public class ForgotActivity extends BaseActivity implements View.OnClickListener {

    //RelativeLayout
    RelativeLayout rlLogo,rlLoginBottom;

    //TextView
    TextView tvSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        findViewById();
    }

    private void findViewById() {

        //RelativeLayout
        rlLogo = findViewById(R.id.rlLogo);
        rlLoginBottom = findViewById(R.id.rlLoginBottom);

        tvSubmit= findViewById(R.id.tvSubmit);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                upViewAnimation();
            }
        },600);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rlLoginBottom.setVisibility(View.VISIBLE);
            }
        },1600);

        tvSubmit.setOnClickListener(this);
    }

    private void upViewAnimation() {
        TranslateAnimation mTranslateAnimation = new TranslateAnimation(0, 0, -(getResources().getDimension(R.dimen._220sdp)), 0);
        mTranslateAnimation.setDuration(1000);
        mTranslateAnimation.setFillAfter(true);
        rlLogo.setVisibility(View.VISIBLE);
        rlLogo.startAnimation(mTranslateAnimation);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tvSubmit:

                return;


        }
    }
}
