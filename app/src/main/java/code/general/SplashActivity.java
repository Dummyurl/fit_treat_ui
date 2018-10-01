package code.general;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fittreat.android.R;

import org.json.JSONException;
import org.json.JSONObject;

import code.common.SimpleHTTPConnection;
import code.dashboard.AboutUsActivity;
import code.dashboard.DashboardActivity;
import code.dashboard.DietPlanActivity;
import code.dashboard.OtcListingActivity;
import code.dashboard.ReferenceActivity;
import code.dashboard.UtilitiesActivity;
import code.database.AppSettings;
import code.utils.AppConstants;
import code.utils.AppUrls;
import code.utils.AppUtils;
import code.view.BaseActivity;

public class SplashActivity extends BaseActivity {

    ImageView ivLogo;

    RelativeLayout rlLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        ivLogo = findViewById(R.id.imageView);
        rlLogo = findViewById(R.id.rlLogo);

        logoAnimation();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(AppSettings.getString(AppSettings.userId).isEmpty())
                {
                    startActivity(new Intent(mActivity, SelectionActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(mActivity, DashboardActivity.class));
                    finish();
                }
            }
        },1000);

    }

    private void logoAnimation() {
        AlphaAnimation mAlphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        mAlphaAnimation.setDuration(800);
        mAlphaAnimation.setFillAfter(true);
        rlLogo.setVisibility(View.VISIBLE);
        rlLogo.startAnimation(mAlphaAnimation);
    }
}
