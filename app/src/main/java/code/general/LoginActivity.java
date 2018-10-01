package code.general;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fittreat.android.R;

import code.dashboard.DashboardActivity;
import code.view.BaseActivity;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    //RelativeLayout
    RelativeLayout rlLogo,rlLoginBottom;

    //TextView
    TextView tvRegister,tvForgot,tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById();
    }

    private void findViewById() {

        //RelativeLayout
        rlLogo = findViewById(R.id.rlLogo);
        rlLoginBottom = findViewById(R.id.rlLoginBottom);

        tvRegister= findViewById(R.id.tvRegister);
        tvForgot= findViewById(R.id.tvForgot);
        tvLogin= findViewById(R.id.tvLogin);

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

        tvRegister.setOnClickListener(this);
        tvForgot.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
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

            case R.id.tvRegister:

                startActivity(new Intent(mActivity, RegisterActivity.class));

                return;

            case R.id.tvForgot:

                startActivity(new Intent(mActivity, ForgotActivity.class));

                return;

            case R.id.tvLogin:

                startActivity(new Intent(mActivity, DashboardActivity.class));

                return;
        }
    }
}
