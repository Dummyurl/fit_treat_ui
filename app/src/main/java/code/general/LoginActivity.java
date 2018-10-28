package code.general;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.fittreat.android.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import code.common.SimpleHTTPConnection;
import code.dashboard.DashboardActivity;
import code.database.AppSettings;
import code.database.DatabaseController;
import code.utils.AppConstants;
import code.utils.AppUrls;
import code.utils.AppUtils;
import code.view.BaseActivity;
import okhttp3.OkHttpClient;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    //RelativeLayout
    RelativeLayout rlLogo,rlLoginBottom;

    //TextView
    TextView tvRegister,tvForgot,tvLogin;

    //EditText
    EditText etEmail,etPassword;

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

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

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

        etEmail.setText("test@gmail.com");
        etPassword.setText("Asdf1234");

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

            case R.id.tvRegister:

                startActivity(new Intent(mActivity, RegisterActivity.class));

                return;

            case R.id.tvForgot:

                startActivity(new Intent(mActivity, ForgotActivity.class));

                return;

            case R.id.tvLogin:

                if(!AppUtils.isValidEmail(etEmail.getText().toString()))
                {
                    AppUtils.showToastSort(mActivity, getString(R.string.errorEmail));
                }
                else  if(etPassword.getText().toString().isEmpty())
                {
                    AppUtils.showToastSort(mActivity, getString(R.string.errorPassword));
                }
                else  if (SimpleHTTPConnection.isNetworkAvailable(mActivity)) {
                    userLoginApi();
                } else {
                    AppUtils.showToastSort(mActivity, getString(R.string.errorInternet));
                }

                return;
        }
    }


    private void userLoginApi() {

        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);

        String url = AppUrls.login;
        Log.v("userLoginApi-URL", url);

        JSONObject json_data = new JSONObject();

        try {

            json_data.put("email", etEmail.getText().toString().trim());
            json_data.put("password",  etPassword.getText().toString().trim());

            Log.v("userLoginApi", json_data.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }


        AndroidNetworking.post(url)
                .addJSONObjectBody(json_data)
                .addHeaders("Content-Type","application/json")
                //.setContentType("application/json; charset=utf-8")
                .setPriority(Priority.HIGH)
                .setTag("userLoginApi")
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        parseJSON(response);
                    }

                    @Override
                    public void onError(ANError error) {
                        AppUtils.hideDialog();
                        // handle error
                        if (error.getErrorCode() != 0) {
                            AppUtils.showToastSort(mActivity,String.valueOf(error.getErrorCode()));
                            Log.d("onError errorCode ", "onError errorCode : " + error.getErrorCode());
                            Log.d("onError errorBody", "onError errorBody : " + error.getErrorBody());
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());

                            if( error.getErrorCode()==401&&error.getErrorBody().equals("Unauthorized"))
                            {
                                AppUtils.showToastSort(mActivity, "Kindly check your login credentials");
                            }

                        } else {
                            AppUtils.showToastSort(mActivity, String.valueOf(error.getErrorDetail()));
                        }
                    }
                });
    }

    private void parseJSON(String response){

        AppUtils.hideDialog();

        Log.d("response ", response.toString());

        try {
            JSONObject jsonObject = new JSONObject(response);

           AppSettings.putString(AppSettings.firstName,jsonObject.getString("firstName"));
           AppSettings.putString(AppSettings.lastName,jsonObject.getString("lastName"));
           AppSettings.putString(AppSettings.email,jsonObject.getString("email"));
           AppSettings.putString(AppSettings.dateOfBirth,jsonObject.getString("dateOfBirth"));
           AppSettings.putString(AppSettings.weight,jsonObject.getString("weight"));
           AppSettings.putString(AppSettings.height,jsonObject.getString("height"));
           AppSettings.putString(AppSettings.bmi,jsonObject.getString("bmi"));
            AppSettings.putString(AppSettings.userPhoto,jsonObject.getString("userPhoto"));
           AppSettings.putString(AppSettings.medicalCondition,jsonObject.getString("medicalCondition"));
           AppSettings.putString(AppSettings.foodPreference,jsonObject.getString("foodPreference"));
           AppSettings.putString(AppSettings.targetWeight,jsonObject.getString("targetWeight"));
           AppSettings.putString(AppSettings.targetDate,jsonObject.getString("targetDate"));
           AppSettings.putString(AppSettings.targetCalories,jsonObject.getString("targetCalories"));
           AppSettings.putString(AppSettings.userPhoto,jsonObject.getString("userPhoto"));
           AppSettings.putString(AppSettings.role,jsonObject.getString("role"));
           AppSettings.putString(AppSettings.userId,jsonObject.getString("_id"));
           AppSettings.putString(AppSettings.age,jsonObject.getString("age"));
           AppSettings.putString(AppSettings.gender,jsonObject.getString("gender"));
           AppSettings.putString(AppSettings.heightUnit,jsonObject.getString("heightUnit"));
           AppSettings.putString(AppSettings.weightUnit,jsonObject.getString("weightUnit"));

            startActivity(new Intent(mActivity, DashboardActivity.class));
            finishAffinity();

        } catch (JSONException e) {
            e.printStackTrace();
            AppUtils.showToastSort(mActivity, response);
        }
    }

}
