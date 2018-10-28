package code.dashboard;

import android.content.Context;
import android.content.Intent;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.fittreat.android.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import code.common.SimpleHTTPConnection;
import code.database.AppSettings;
import code.utils.AppUrls;
import code.utils.AppUtils;
import code.view.BaseActivity;

public class TargetActivity extends BaseActivity implements View.OnClickListener {

    //RelativeLayout
    RelativeLayout rlBack;

    //TextView
    TextView tvHeader,tvSubmit;

    //ImageView
    ImageView ivMiddle;

    EditText etTarWeight,etTime;

    Spinner spinnerTarWeight,spinnerTime,spinnerActivity;

    private  static ArrayList<String> TargetWeightList = new ArrayList<String>();
    private  static ArrayList<String> TargetTimeList = new ArrayList<String>();
    private  static ArrayList<String> ActivityList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_weight);

        findViewById();
    }

    private void findViewById() {

        //RelativeLayout
        rlBack = findViewById(R.id.rlBack);

        //TextView for Header Text
        tvHeader = findViewById(R.id.tvHeader);
        tvSubmit = findViewById(R.id.tvSubmit);

        etTarWeight = findViewById(R.id.etTarWeight);
        etTime = findViewById(R.id.etTime);

        ivMiddle = findViewById(R.id.ivMiddle);

        spinnerTarWeight = findViewById(R.id.spinnerTarWeight);
        spinnerTime= findViewById(R.id.spinnerTime);
        spinnerActivity= findViewById(R.id.spinnerActivity);

        tvHeader.setText(getString(R.string.target_weight));
        ivMiddle.setImageResource(R.drawable.ic_target_weight);

        TargetWeightList.clear();
        TargetWeightList.add(getString(R.string.kg));
        TargetWeightList.add(getString(R.string.lb));

        TargetTimeList.clear();
        TargetTimeList.add(getString(R.string.days));
        TargetTimeList.add(getString(R.string.weeks));

        ActivityList.clear();
        ActivityList.add(getString(R.string.dailyActivities));
        ActivityList.add(getString(R.string.sedentart));
        ActivityList.add(getString(R.string.slightlyActive));
        ActivityList.add(getString(R.string.moderatelyActive));
        ActivityList.add(getString(R.string.veryActive));
        ActivityList.add(getString(R.string.extraActive));

        spinnerTarWeight.setAdapter(new adapterSpinner(mActivity, R.layout.spinner_adapter, TargetWeightList));
        spinnerTarWeight.setSelection(0);

        spinnerTime.setAdapter(new adapterSpinner(mActivity, R.layout.spinner_adapter, TargetTimeList));
        spinnerTime.setSelection(0);

        spinnerActivity.setAdapter(new adapterSpinner(mActivity, R.layout.spinner_adapter, ActivityList));
        spinnerActivity.setSelection(0);

        rlBack.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);

        AppUtils.hideSoftKeyboard(mActivity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.rlBack:

                onBackPressed();

                return;

            case R.id.tvSubmit:

                if(etTarWeight.getText().toString().isEmpty())
                {
                    AppUtils.showToastSort(mActivity, getString(R.string.errorTarWeight));
                }
                else if(etTime.getText().toString().isEmpty())
                {
                    AppUtils.showToastSort(mActivity, getString(R.string.errorTarTime));
                }
                else if(spinnerActivity.getSelectedItemPosition()==0)
                {
                    AppUtils.showToastSort(mActivity, getString(R.string.errorDailyActivity));
                }
                else
                {
                    double calCalorie = AppUtils.calculateCalories(mActivity,
                            Double.valueOf(AppSettings.getString(AppSettings.height)),
                            AppSettings.getString(AppSettings.heightUnit),
                            AppSettings.getString(AppSettings.gender),
                            Integer.parseInt(AppSettings.getString(AppSettings.age)),
                            Double.valueOf(AppSettings.getString(AppSettings.weight)),
                            AppSettings.getString(AppSettings.weightUnit),
                            Double.valueOf(etTarWeight.getText().toString().trim()),
                            spinnerTarWeight.getSelectedItem().toString(),
                            Double.valueOf(etTime.getText().toString().trim()),
                            spinnerTime.getSelectedItem().toString(),
                            spinnerActivity.getSelectedItem().toString());

                    calCalorie =  Math.round(calCalorie);

                    String cal = String.valueOf(calCalorie);

                    if(cal.contains("."))
                    {
                        String[] separated = cal.split("\\.");
                        cal = separated[0];
                    }

                    AppSettings.putString(AppSettings.targetCalories, cal);
                    AppSettings.putString(AppSettings.targetDate, String.valueOf(AppUtils.covertDays(mActivity,
                            Double.parseDouble(etTime.getText().toString().trim()),
                            spinnerTime.getSelectedItem().toString())));
                    AppSettings.putString(AppSettings.targetWeight, etTarWeight.getText().toString().trim());

                    //Toast.makeText(mActivity, String.valueOf(calCalorie), Toast.LENGTH_SHORT).show();

                    if (SimpleHTTPConnection.isNetworkAvailable(mActivity)) {
                        userTargetWeightApi();
                    } else {
                        AppUtils.showToastSort(mActivity, getString(R.string.errorInternet));
                    }
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


    private void userTargetWeightApi() {

        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);

        String url = AppUrls.targetWeight;
        Log.v("userTargetWeightApi-URL", url);

        JSONObject json_data = new JSONObject();

        try {

            json_data.put("id",AppSettings.getString(AppSettings.userId));
            json_data.put("targetWeight",AppSettings.getString(AppSettings.targetWeight));
            json_data.put("targetDate",  AppSettings.getString(AppSettings.targetDate));
            json_data.put("targetCalories", AppSettings.getString(AppSettings.targetCalories));

            Log.v("userTargetWeightApi", json_data.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }


        AndroidNetworking.put(url)
                .addJSONObjectBody(json_data)
                .addHeaders("Content-Type","application/json")
                //.setContentType("application/json; charset=utf-8")
                .setPriority(Priority.HIGH)
                .setTag("userTargetWeightApi")
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

            AppUtils.showToastSort(mActivity, jsonObject.getString("status"));

            onBackPressed();

        } catch (JSONException e) {
            e.printStackTrace();
            AppUtils.showToastSort(mActivity, response);
        }
    }

}
