package code.general;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fittreat.android.R;

import code.utils.AppUtils;
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

        AppUtils.hideSoftKeyboard(mActivity);
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}
