package code.dashboard;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fittreat.android.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import code.database.AppSettings;
import code.utils.AppConstants;
import code.view.BaseActivity;

public class OtcDetailActivity extends BaseActivity implements View.OnClickListener {

    private static final int STORAGE_PERMISSION_CODE = 50;

    //RelativeLayout
    RelativeLayout rlBack,rlNearByDoctor,rlNearByPharmacy;

    //TextView
    TextView tvHeader, tvSymptoms,tvOtc;

    ArrayList<HashMap<String, String>> OtcMedicineList = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otc_detail);

        findViewById();
    }

    private void findViewById() {

        //RelativeLayout
        rlBack = findViewById(R.id.rlBack);
        rlNearByDoctor = findViewById(R.id.rlNearByDoctor);
        rlNearByPharmacy = findViewById(R.id.rlNearByPharmacy);

        //TextView for Header Text
        tvHeader = findViewById(R.id.tvHeader);
        tvSymptoms = findViewById(R.id.tvSymptoms);
        tvOtc = findViewById(R.id.tvOtc);

        tvHeader.setText(AppSettings.getString(AppSettings.otcName));

        try {
            JSONObject jsonObject = new JSONObject(AppSettings.getString(AppSettings.json));

            JSONArray jsonArray = jsonObject.getJSONArray("medicines");

            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                HashMap<String, String> hashMap = new HashMap();

                String str = jsonObject1.getString("Ingredients").replaceAll("\\[", "").replaceAll("\\]", "");
                //AppConstants.separated = str.split(",");

                if(str.contains(","))
                {
                    str = str.replaceAll(",","\n\t").replaceAll(" ","");
                }

                hashMap.put("medicine",str.trim());
                hashMap.put("name",jsonObject1.getString("Name"));
                hashMap.put("id",jsonObject1.getString("_id"));

                OtcMedicineList.add(hashMap);
            }

            String string = "";

            for(int i=0;i<OtcMedicineList.size();i++)
            {
                string = string + OtcMedicineList.get(i).get("name")
                        +"\nIngredients: "
                        +"\n\t"+OtcMedicineList.get(i).get("medicine")+ "\n\n" ;
            }

            tvOtc.setText(string);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        rlBack.setOnClickListener(this);
        rlNearByDoctor.setOnClickListener(this);
        rlNearByPharmacy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.rlBack:

                onBackPressed();

                return;

            case R.id.rlNearByDoctor:

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

        }
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
