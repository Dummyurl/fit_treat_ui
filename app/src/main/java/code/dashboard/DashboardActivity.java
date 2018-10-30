package code.dashboard;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import code.utils.AppUrls;
import code.utils.AppUtils;
import code.view.BaseActivity;

public class DashboardActivity extends BaseActivity implements View.OnClickListener {

    private static final int STORAGE_PERMISSION_CODE = 50;

    //RelativeLayout
    RelativeLayout rlMenu,rlSync,rlProfile,rlInbox,rlNearByDoctor,rlNearByPharmacy,rlWrite,rlAboutUs,rlReferences,rlLogout;
    RelativeLayout rlCalories,rlDietPlan,rlUtilities;

    //TextView
    TextView tvCalculatedBMI,tvTargetWeight,tvInboxCount,tvTarget;

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

        if(!AppSettings.getString(AppSettings.targetWeight).isEmpty())
        {
            tvTarget.setText(getString(R.string.goal_weight)+": "+AppSettings.getString(AppSettings.targetWeight)
                    +" "+AppSettings.getString(AppSettings.weightUnit)
                    +"\nTarget Calories: "+AppSettings.getString(AppSettings.targetCalories)
                    +"\nGoal Date: "+AppSettings.getString(AppSettings.targetDate));
        }

        tvCalculatedBMI.setText(AppUtils.calculateBMI(mActivity,
                Double.valueOf(AppSettings.getString(AppSettings.height)),
                AppSettings.getString(AppSettings.heightUnit),
                Double.valueOf(AppSettings.getString(AppSettings.weight)),
                AppSettings.getString(AppSettings.weightUnit)));

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

                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    //TO do here if permission is granted by user

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=near+by+doctors"));
                    intent.setPackage("com.google.android.apps.maps");
                    startActivity(intent);

                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION}, STORAGE_PERMISSION_CODE);
                    }
                }

                return;

            case R.id.rlNearByPharmacy:

                closeDrawer();

                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    //TO do here if permission is granted by user

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=near+by+pharmacies"));
                    intent.setPackage("com.google.android.apps.maps");
                    startActivity(intent);

                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION}, STORAGE_PERMISSION_CODE);
                    }
                }

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


}
