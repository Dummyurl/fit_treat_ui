package code.dashboard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fittreat.android.R;

import java.util.ArrayList;
import java.util.HashMap;

import code.database.AppSettings;
import code.utils.AppUtils;
import code.view.BaseActivity;

public class OtcListingActivity extends BaseActivity implements View.OnClickListener {

    //GridLayoutManager
    GridLayoutManager mGridLayoutManager;

    //RelativeLayout
    RelativeLayout rlBack,rlSearch;

    //EditText
    EditText etSearch;

    //RecyclerView
    RecyclerView recyclerView;

    Adapter adapter;

    ArrayList<HashMap<String, String>> OtcListingList = new ArrayList<HashMap<String, String>>();

    //TextView
    TextView tvHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otc);

        findViewById();
    }

    private void findViewById() {

        //RelativeLayout
        rlBack = findViewById(R.id.rlBack);
        rlSearch = findViewById(R.id.rlSearch);

        //recyclerView
        recyclerView = findViewById(R.id.recyclerView);

        //TextView for Header Text
        tvHeader = findViewById(R.id.tvHeader);

        //Edittext for search
        etSearch = findViewById(R.id.etSearch);

        tvHeader.setText(getString(R.string.otcMedicines));

        mGridLayoutManager = new GridLayoutManager(mActivity, 1);
        recyclerView.setLayoutManager(mGridLayoutManager);
        recyclerView.setNestedScrollingEnabled(true);

        for(int i=0;i<5;i++)
        {
            HashMap<String, String> hashMap = new HashMap();

            hashMap.put("id","1");
            hashMap.put("name","Smoky Maple-Mustard Salmon");

            OtcListingList.add(hashMap);
        }

        adapter = new Adapter(OtcListingList);
        recyclerView.setAdapter(adapter);

        rlBack.setOnClickListener(this);

        AppUtils.hideSoftKeyboard(mActivity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.rlBack:

                onBackPressed();

                return;
        }
    }

    private class Adapter extends RecyclerView.Adapter<Holder> {
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

        public Adapter(ArrayList<HashMap<String, String>> favList) {
            data = favList;
        }

        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_otc_listing, parent, false));
        }

        @SuppressLint("SetTextI18n")
        public void onBindViewHolder(Holder holder, final int position) {

            holder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AppSettings.putString(AppSettings.otcName,data.get(position).get("name"));
                    startActivity(new Intent(mActivity, OtcDetailActivity.class));

                }
            });

            holder.tvName.setText(data.get(position).get("name"));

            /*try {
                Picasso.get().load(data.get(position).get("profile")).into(holder.ivDietPhoto);
            } catch (Exception e) {
                e.printStackTrace();
                holder.ivDietPhoto.setImageResource(R.mipmap.user);
            }*/

        }

        public int getItemCount() {
            return data.size();
        }
    }

    private class Holder extends RecyclerView.ViewHolder {

        TextView tvName;

        public Holder(View itemView) {
            super(itemView);
            tvName =  itemView.findViewById(R.id.tvName);
        }
    }
}
