package code.dashboard;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.fittreat.android.R;

import org.json.JSONException;
import org.json.JSONObject;

import code.common.RoundedImageView;
import code.database.AppSettings;
import code.general.LoginActivity;
import code.utils.AppUrls;
import code.utils.AppUtils;
import code.view.BaseActivity;

public class DashboardActivity extends BaseActivity implements View.OnClickListener {

    private static final int STORAGE_PERMISSION_CODE = 50;

    //RelativeLayout
    RelativeLayout rlMenu,rlSync,rlProfile,rlInbox,rlNearByDoctor,rlNearByPharmacy,rlWrite,rlAboutUs,rlReferences,rlLogout;
    RelativeLayout rlCalories,rlDietPlan,rlUtilities;

    //TextView
    TextView tvCalculatedBMI,tvTargetWeight,tvInboxCount,tvTarget,tvResult;

    //DrawerLayout
    static DrawerLayout mDrawerLayout;

    //Handler
    static Handler mHandler;

    //ScrollView
    static ScrollView scrollSideMenu;

    boolean doubleBackToExitPressedOnce;

    //RoundedImageView
    ImageView ivPic;

    //TextView
    TextView tvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        
        findViewById();
    }

    private void findViewById() {

        //Handler
        mHandler = new Handler();

        //TextView
        tvCalculatedBMI= findViewById(R.id.tvCalculatedBMI);
        tvTargetWeight= findViewById(R.id.tvTargetWeight);
        tvInboxCount= findViewById(R.id.tvInboxCount);
        tvTarget= findViewById(R.id.tvTarget);
        tvName= findViewById(R.id.tvName);
        tvResult= findViewById(R.id.tvH2);

        ivPic= findViewById(R.id.ivPic);

        //RelativeLayout
        rlMenu= findViewById(R.id.rlMenu);
        rlSync= findViewById(R.id.rlInfo);
        rlProfile= findViewById(R.id.rlProfile);
        rlInbox= findViewById(R.id.rlInbox);
        rlNearByDoctor= findViewById(R.id.rlNearByDoctor);
        rlNearByPharmacy= findViewById(R.id.rlNearByPharmacy);
        rlWrite= findViewById(R.id.rlWrite);
        rlAboutUs= findViewById(R.id.rlAboutUs);
        rlReferences= findViewById(R.id.rlReferences);
        rlLogout= findViewById(R.id.rlLogout);
        rlCalories= findViewById(R.id.rlCalories);
        rlDietPlan= findViewById(R.id.rlDietPlan);
        rlUtilities= findViewById(R.id.rlUtilities);

        //DrawerLayout
        mDrawerLayout       =  findViewById(R.id.drawer_layout);

        //ScrollView
        scrollSideMenu = findViewById(R.id.scroll_side_menu);

        rlMenu.setOnClickListener(this);
        rlSync.setOnClickListener(this);
        rlProfile.setOnClickListener(this);
        rlInbox.setOnClickListener(this);
        rlNearByDoctor.setOnClickListener(this);
        rlNearByPharmacy.setOnClickListener(this);
        rlWrite.setOnClickListener(this);
        rlAboutUs.setOnClickListener(this);
        rlReferences.setOnClickListener(this);
        rlLogout.setOnClickListener(this);
        rlDietPlan.setOnClickListener(this);
        rlUtilities.setOnClickListener(this);
        tvTargetWeight.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(AppSettings.getString(AppSettings.unreadCount).isEmpty()
                ||AppSettings.getString(AppSettings.unreadCount).equals("0"))
        {
            tvInboxCount.setVisibility(View.GONE);
        }
        else
        {
            tvInboxCount.setVisibility(View.VISIBLE);
            tvInboxCount.setText(AppSettings.getString(AppSettings.unreadCount));
        }

        tvCalculatedBMI.setText(AppUtils.calculateBMI(mActivity,
                Double.valueOf(AppSettings.getString(AppSettings.height)),
                AppSettings.getString(AppSettings.heightUnit),
                Double.valueOf(AppSettings.getString(AppSettings.weight)),
                AppSettings.getString(AppSettings.weightUnit)));

        double bmi = Double.parseDouble(AppUtils.calculateBMI(mActivity,
                Double.valueOf(AppSettings.getString(AppSettings.height)),
                AppSettings.getString(AppSettings.heightUnit),
                Double.valueOf(AppSettings.getString(AppSettings.weight)),
                AppSettings.getString(AppSettings.weightUnit)));

        Log.d("bmi", String.valueOf(bmi));

        if(bmi<18.5)
        {
            tvResult.setText("Underweight");
            tvCalculatedBMI.setTextColor(getResources().getColor(R.color.underweight));
        }
        else  if(bmi>=18.5 && bmi<=24.9)
        {
            tvResult.setText("Normal Weight");
            tvCalculatedBMI.setTextColor(getResources().getColor(R.color.normal));
        }
        else  if(bmi>=25  && bmi<=29.9)
        {
            tvResult.setText("Overweight");
            tvCalculatedBMI.setTextColor(getResources().getColor(R.color.overweight));
        }
        else  if(bmi>=30  && bmi<=34.9)
        {
            tvResult.setText("Obese");
            tvCalculatedBMI.setTextColor(getResources().getColor(R.color.obese));
        }

        if(!AppSettings.getString(AppSettings.targetWeight).isEmpty())
        {
            Typeface font = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

            String str1 = getString(R.string.goal_weight)+": "+AppSettings.getString(AppSettings.targetWeight)
                    +" "+AppSettings.getString(AppSettings.weightUnit);

            String str2 = str1+"\nTarget Calories: "+AppSettings.getString(AppSettings.targetCalories);

            String str = getString(R.string.goal_weight)+": "+AppSettings.getString(AppSettings.targetWeight)
                    +" "+AppSettings.getString(AppSettings.weightUnit)
                    +"\nTarget Calories: "+AppSettings.getString(AppSettings.targetCalories)
                    +"\nGoal Date: "+AppUtils.convertDate(AppSettings.getString(AppSettings.targetDate));

            if(AppSettings.getString(AppSettings.targetCalories).contains("-"))
            {
                SpannableString spannableString = new SpannableString(str);
                ForegroundColorSpan foregroundSpan = new ForegroundColorSpan(Color.RED);
                spannableString.setSpan(foregroundSpan,  str1.length(), str2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvTarget.setText(spannableString);
            }
            else
            {
                SpannableString spannableString = new SpannableString(str);
                ForegroundColorSpan foregroundSpan = new ForegroundColorSpan(getResources().getColor(R.color.green));
                spannableString.setSpan(foregroundSpan,  str1.length(), str2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvTarget.setText(spannableString);
            }

            tvTarget.setTypeface(font);
        }

        tvName.setText(AppSettings.getString(AppSettings.firstName)
                + " "+ AppSettings.getString(AppSettings.lastName));

        if(!AppSettings.getString(AppSettings.profile).isEmpty())
        {
            ivPic.setImageBitmap(AppUtils.convertBase64ToBitmap(AppSettings.getString(AppSettings.profile)));
        }

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.rlMenu:

                openDrawer();

                return;

            case R.id.rlInfo:

                return;

            case R.id.rlProfile:

                closeDrawer();
                startActivity(new Intent(mActivity, MyProfileActivity.class));

                return;

            case R.id.rlInbox:

                closeDrawer();
                startActivity(new Intent(mActivity, InboxActivity.class));

                return;

            case R.id.rlNearByDoctor:

                closeDrawer();

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=near+by+doctors"));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);

                return;

            case R.id.rlNearByPharmacy:

                closeDrawer();

                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=near+by+pharmacies"));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);

                return;

            case R.id.rlWrite:

                closeDrawer();
                startActivity(new Intent(mActivity, WriteUsActivity.class));

                return;

            case R.id.rlAboutUs:

                closeDrawer();
                startActivity(new Intent(mActivity, AboutUsActivity.class));

                return;

            case R.id.rlReferences:

                closeDrawer();
                startActivity(new Intent(mActivity, ReferenceActivity.class));

                return;

            case R.id.rlLogout:

                closeDrawer();
                Alert();

                return;

            case R.id.tvTargetWeight:

                closeDrawer();
                startActivity(new Intent(mActivity, TargetActivity.class));

                return;

            case R.id.rlDietPlan:

                closeDrawer();
                startActivity(new Intent(mActivity, DietPlanActivity.class));

                return;

            case R.id.rlUtilities:

                closeDrawer();
                startActivity(new Intent(mActivity, UtilitiesActivity.class));

                return;

        }
    }

    //openDrawer
    public void openDrawer() {

        mDrawerLayout.openDrawer(scrollSideMenu);

    }

    //closeDrawer
    public static void closeDrawer() {

        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {

                mDrawerLayout.closeDrawer(scrollSideMenu);
            }
        }, 100);
    }

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
        }
        this.doubleBackToExitPressedOnce = true;
        AppUtils.showToastSort(mActivity, getString(R.string.exit));
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        } ,2000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        } else {
            //AppUtils.showToastSort(mActivity,getString(R.string.error));
        }
    }


    public void Alert() {
        final Dialog dialog = new Dialog(this,android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alert_yes_no);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        TextView tv_text        = (TextView)dialog.findViewById(R.id.tv_alert_msg);
        final TextView tvAlert       = (TextView)dialog.findViewById(R.id.tv_alert);
        TextView tvyes          = (TextView)dialog.findViewById(R.id.tvOk);
        TextView tvCancel          = (TextView)dialog.findViewById(R.id.tvCancel);

        tvAlert.setText(getString(R.string.alert));
        tv_text.setText(getString(R.string.logoutMSG));
        tvyes.setText(getString(R.string.logout));
        tvCancel.setText(getString(R.string.cancel));

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        tvyes.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                dialog.dismiss();

                AppSettings.clearSharedPreference();
                startActivity(new Intent(mActivity, LoginActivity.class));
                finishAffinity();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();

            }
        });
    }

}
