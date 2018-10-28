package code.dashboard;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.fittreat.android.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import code.common.SimpleHTTPConnection;
import code.database.AppSettings;
import code.general.RegisterActivity;
import code.utils.AppUrls;
import code.utils.AppUtils;
import code.view.BaseActivity;

public class MyProfileActivity extends BaseActivity implements View.OnClickListener {

    //RelativeLayout
    RelativeLayout rlBack;

    //TextView
    TextView tvHeader;

    //ImageView
    ImageView ivMiddle;

    //Spinner
    Spinner spinnerWeight,spinnerHeight,spinnerFood,spinnerMedical;

    //LinearLayout
    LinearLayout llBottom,llTop;

    //ArrayList
    private  static ArrayList<String> WeightList = new ArrayList<String>();
    private  static ArrayList<String> HeightList = new ArrayList<String>();
    private  static ArrayList<String> FoodList = new ArrayList<String>();
    private  static ArrayList<String> MedicalList = new ArrayList<String>();

    //EditText
    EditText etFirstName,etLastName,etEmail,etDob,etHeight,etWeight,etGender;

    //TextView
    TextView tvSubmit;

    //RelativeLayout
    RelativeLayout rlEdit;

    //ImageView
    ImageView ivCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        findViewById();
    }

    private void findViewById() {

        //RelativeLayout
        rlBack = findViewById(R.id.rlBack);

        //TextView for Header Text
        tvHeader = findViewById(R.id.tvHeader);
        tvSubmit = findViewById(R.id.tvSubmit);

        //RelativeLayout
        rlEdit = findViewById(R.id.rlEdit);

        //Spinner
        spinnerWeight= findViewById(R.id.spinnerWeight);
        spinnerHeight= findViewById(R.id.spinnerHeight);
        spinnerFood= findViewById(R.id.spinnerFood);
        spinnerMedical= findViewById(R.id.spinnerMedical);

        //LinearLayout
        llBottom = findViewById(R.id.llBottom);
        llTop = findViewById(R.id.llTop);

        //ImageView
        ivCamera = findViewById(R.id.ivCamera);

        //All Edittext
        etFirstName= findViewById(R.id.etFirstName);
        etLastName= findViewById(R.id.etLastName);
        etEmail= findViewById(R.id.etEmail);
        etGender= findViewById(R.id.etGender);
        etDob= findViewById(R.id.etDob);
        etHeight= findViewById(R.id.etHeight);
        etWeight= findViewById(R.id.etWeight);

        ivMiddle = findViewById(R.id.ivMiddle);

        tvHeader.setText(getString(R.string.my_profile));
        ivMiddle.setImageResource(R.drawable.ic_user_white);

        settingValues();

        AppUtils.enableDisable(llTop,false);
        AppUtils.enableDisable(llBottom,false);
        rlEdit.setVisibility(View.VISIBLE);
        llTop.setVisibility(View.VISIBLE);
        tvSubmit.setVisibility(View.GONE);
        ivCamera.setVisibility(View.VISIBLE);

        rlBack.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
        rlEdit.setOnClickListener(this);
        ivCamera.setOnClickListener(this);

        AppUtils.hideSoftKeyboard(mActivity);
    }

    private void settingValues() {

        etFirstName.setText(AppSettings.getString(AppSettings.firstName));
        etLastName.setText(AppSettings.getString(AppSettings.lastName));
        etEmail.setText(AppSettings.getString(AppSettings.email));
        etDob.setText(AppSettings.getString(AppSettings.dateOfBirth));
        etHeight.setText(AppSettings.getString(AppSettings.height));
        etWeight.setText(AppSettings.getString(AppSettings.weight));
        etGender.setText(AppSettings.getString(AppSettings.gender));

        if(AppSettings.getString(AppSettings.userPhoto).isEmpty())
        {
            Resources res = getResources();
            Drawable background = res.getDrawable(R.drawable.ic_camera);
            int primaryColor = res.getColor(R.color.blueButton);
            background.setColorFilter(primaryColor, PorterDuff.Mode.SRC_IN);
            ivCamera.setImageDrawable(background);
        }
        else
        {
            Resources res = getResources();
            Drawable background = res.getDrawable(R.drawable.ic_camera);
            int primaryColor = res.getColor(R.color.white);
            background.setColorFilter(primaryColor, PorterDuff.Mode.SRC_IN);
            ivCamera.setImageDrawable(background);
        }

        WeightList.clear();
        WeightList.add(getString(R.string.kg));
        WeightList.add(getString(R.string.lb));

        HeightList.clear();
        HeightList.add(getString(R.string.cm));
        HeightList.add(getString(R.string.m));
        HeightList.add(getString(R.string.ft));

        FoodList.clear();
        FoodList.add(getString(R.string.food_preferences));
        FoodList.add(getString(R.string.vegan));
        FoodList.add(getString(R.string.vegetarian));
        FoodList.add(getString(R.string.nonVegetarian));

        MedicalList.clear();
        MedicalList.add(getString(R.string.medical_condition));
        MedicalList.add(getString(R.string.none));
        MedicalList.add(getString(R.string.diabetes));
        MedicalList.add(getString(R.string.thyroid));

        int pos=0;
        for(int i=0;i<WeightList.size();i++)
        {
            if(AppSettings.getString(AppSettings.weightUnit).equalsIgnoreCase(WeightList.get(i)))
            {
                pos=i;
                break;
            }
        }

        spinnerWeight.setAdapter(new adapterSpinner(mActivity, R.layout.spinner_adapter, WeightList));
        spinnerWeight.setSelection(pos);

        pos=0;
        for(int i=0;i<HeightList.size();i++)
        {
            if(AppSettings.getString(AppSettings.heightUnit).equalsIgnoreCase(HeightList.get(i)))
            {
                pos=i;
                break;
            }
        }

        spinnerHeight.setAdapter(new adapterSpinner(mActivity, R.layout.spinner_adapter, HeightList));
        spinnerHeight.setSelection(pos);

        pos=0;
        for(int i=0;i<FoodList.size();i++)
        {
            if(AppSettings.getString(AppSettings.foodPreference).equalsIgnoreCase(FoodList.get(i)))
            {
                pos=i;
                break;
            }
        }

        spinnerFood.setAdapter(new adapterSpinner(mActivity, R.layout.spinner_adapter, FoodList));
        spinnerFood.setSelection(pos);

        pos=0;
        for(int i=0;i<MedicalList.size();i++)
        {
            if(AppSettings.getString(AppSettings.medicalCondition).equalsIgnoreCase(MedicalList.get(i)))
            {
                pos=i;
                break;
            }
        }

        spinnerMedical.setAdapter(new adapterSpinner(mActivity, R.layout.spinner_adapter, MedicalList));
        spinnerMedical.setSelection(pos);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.rlBack:

                onBackPressed();

                return;

            case R.id.ivCamera:



                return;

            case R.id.rlEdit:

                rlEdit.setVisibility(View.GONE);
                llTop.setVisibility(View.GONE);
                tvSubmit.setVisibility(View.VISIBLE);
                AppUtils.enableDisable(llBottom,true);

                return;

            case R.id.tvSubmit:

                if(etFirstName.getText().toString().trim().isEmpty())
                {
                    AppUtils.showToastSort(mActivity, getString(R.string.errorFName));
                }
                else  if(!AppUtils.isValidEmail(etEmail.getText().toString()))
                {
                    AppUtils.showToastSort(mActivity, getString(R.string.errorEmail));
                }
                else if(etDob.getText().toString().isEmpty())
                {
                    AppUtils.showToastSort(mActivity, getString(R.string.errorDob));
                }
                else if(etHeight.getText().toString().isEmpty())
                {
                    AppUtils.showToastSort(mActivity, getString(R.string.errorHeight));
                }
                else if(etWeight.getText().toString().isEmpty())
                {
                    AppUtils.showToastSort(mActivity, getString(R.string.errorWeight));
                }
                else if(spinnerFood.getSelectedItemPosition()==0)
                {
                    AppUtils.showToastSort(mActivity, getString(R.string.errorFood));
                }
                else if(spinnerMedical.getSelectedItemPosition()==0)
                {
                    AppUtils.showToastSort(mActivity, getString(R.string.errorMedical));
                }
                else if (SimpleHTTPConnection.isNetworkAvailable(mActivity)) {
                    updateProfileApi();
                } else {
                    AppUtils.showToastSort(mActivity, getString(R.string.errorInternet));
                }

                return;
        }
    }

    //Spinner DemoAdapter
    public class adapterSpinner extends ArrayAdapter<String> {

        ArrayList<String> data;

        public adapterSpinner(Context context, int textViewResourceId, ArrayList <String> arraySpinner_time) {

            super(context, textViewResourceId, arraySpinner_time);

            this.data = arraySpinner_time;

        }

        @Override
        public View getDropDownView(int position, View convertView,ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View row=inflater.inflate(R.layout.spinner_adapter, parent, false);
            TextView label=(TextView)row.findViewById(R.id.tv_spinner_name);

            label.setText(data.get(position).toString());

            return row;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    private void updateProfileApi() {

        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);

        String url = AppUrls.updateProfile;
        Log.v("updateProfileApi-URL", url);

        JSONObject json_data = new JSONObject();

        try {

            json_data.put("id", AppSettings.getString(AppSettings.userId));
            json_data.put("firstName", etFirstName.getText().toString().trim());
            json_data.put("lastName", etLastName.getText().toString().trim());
            json_data.put("weight",  etWeight.getText().toString().trim());
            json_data.put("height",  etHeight.getText().toString().trim());
            json_data.put("foodPreference",  spinnerFood.getSelectedItem());
            json_data.put("medicalCondition",  spinnerMedical.getSelectedItem());
            json_data.put("weightUnit",  spinnerWeight.getSelectedItem());
            json_data.put("heightUnit",  spinnerHeight.getSelectedItem());

            Log.v("updateProfileApi", json_data.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.put(url)
                .addJSONObjectBody(json_data)
                .addHeaders("Content-Type","application/json")
                //.setContentType("application/json; charset=utf-8")
                .setPriority(Priority.HIGH)
                .setTag("updateProfileApi")
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

                            if( error.getErrorCode()==422)
                            {
                                try {
                                    JSONObject jsonObject = new JSONObject(error.getErrorBody());

                                    AppUtils.showToastSort(mActivity, jsonObject.getString("error"));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

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

            if(jsonObject.has("error"))
            {
                AppUtils.showToastSort(mActivity, jsonObject.getString("error"));
            }
            else
            {
                rlEdit.setVisibility(View.VISIBLE);
                llTop.setVisibility(View.VISIBLE);
                tvSubmit.setVisibility(View.GONE);
                AppUtils.enableDisable(llBottom,false);

                AppSettings.putString(AppSettings.firstName,jsonObject.getString("firstName"));
                AppSettings.putString(AppSettings.lastName,jsonObject.getString("lastName"));
                AppSettings.putString(AppSettings.email,jsonObject.getString("email"));
                AppSettings.putString(AppSettings.dateOfBirth,jsonObject.getString("dateOfBirth"));
                AppSettings.putString(AppSettings.weight,jsonObject.getString("weight"));
                AppSettings.putString(AppSettings.height,jsonObject.getString("height"));
                AppSettings.putString(AppSettings.medicalCondition,jsonObject.getString("medicalCondition"));
                AppSettings.putString(AppSettings.foodPreference,jsonObject.getString("foodPreference"));
                AppSettings.putString(AppSettings.targetWeight,jsonObject.getString("targetWeight"));
                AppSettings.putString(AppSettings.targetDate,jsonObject.getString("targetDate"));
                AppSettings.putString(AppSettings.targetCalories,jsonObject.getString("targetCalories"));
                AppSettings.putString(AppSettings.role,jsonObject.getString("role"));
                AppSettings.putString(AppSettings.userId,jsonObject.getString("_id"));
                AppSettings.putString(AppSettings.age,jsonObject.getString("age"));
                AppSettings.putString(AppSettings.gender,jsonObject.getString("gender"));
                AppSettings.putString(AppSettings.heightUnit,jsonObject.getString("heightUnit"));
                AppSettings.putString(AppSettings.weightUnit,jsonObject.getString("weightUnit"));

                settingValues();

            }

        } catch (JSONException e) {
            e.printStackTrace();
            AppUtils.showToastSort(mActivity, response);
        }
    }
}
