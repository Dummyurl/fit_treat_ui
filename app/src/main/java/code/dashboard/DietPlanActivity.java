package code.dashboard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fittreat.android.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import code.common.RoundedImageView;
import code.general.LoginActivity;
import code.view.BaseActivity;

public class DietPlanActivity extends BaseActivity implements View.OnClickListener {

    //GridLayoutManager
    GridLayoutManager mGridLayoutManager;

    //RelativeLayout
    RelativeLayout rlBack,rlFilter,rlFilterMain,rlBreakfast,rlLunch,rlDinner,rlOnlyLiquids;

    //RecyclerView
    RecyclerView recyclerView;

    Adapter adapter;

    ArrayList<HashMap<String, String>> DietPlanList = new ArrayList<HashMap<String, String>>();

    //TextView
    TextView tvHeader;

    //View
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_plan);

        findViewById();
    }

    private void findViewById() {

        //RelativeLayout
        rlBack = findViewById(R.id.rlBack);
        rlFilter = findViewById(R.id.rlFilter);
        rlFilterMain = findViewById(R.id.rlFilterMain);
        rlBreakfast= findViewById(R.id.rlBreakfast);
        rlLunch= findViewById(R.id.rlLunch);
        rlDinner= findViewById(R.id.rlDinner);
        rlOnlyLiquids= findViewById(R.id.rlOnlyLiquids);

        //View when filter is open
        view= findViewById(R.id.view);

        //recyclerView
        recyclerView = findViewById(R.id.recyclerView);

        //TextView for Header Text
        tvHeader = findViewById(R.id.tvHeader);

        tvHeader.setText(getString(R.string.dietPlan));
        rlFilter.setVisibility(View.VISIBLE);
        rlFilterMain.setVisibility(View.GONE);

        mGridLayoutManager = new GridLayoutManager(mActivity, 1);
        recyclerView.setLayoutManager(mGridLayoutManager);
        recyclerView.setNestedScrollingEnabled(true);

        for(int i=0;i<5;i++)
        {
            HashMap<String, String> hashMap = new HashMap();

            hashMap.put("id","1");
            hashMap.put("type","Dinner");
            hashMap.put("name","Smoky Maple-Mustard Salmon");
            hashMap.put("calories","400 Calories");
            hashMap.put("serving","1 Serving");

            DietPlanList.add(hashMap);
        }

        adapter = new Adapter(DietPlanList);
        recyclerView.setAdapter(adapter);

        rlBack.setOnClickListener(this);
        rlFilter.setOnClickListener(this);
        rlBreakfast.setOnClickListener(this);
        rlLunch.setOnClickListener(this);
        rlDinner.setOnClickListener(this);
        rlOnlyLiquids.setOnClickListener(this);
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.rlBack:

                onBackPressed();

                return;

            case R.id.rlFilter:

                if(rlFilterMain.getVisibility()==View.VISIBLE)
                {
                    view.setVisibility(View.GONE);
                    rlFilterMain.setVisibility(View.GONE);
                }
                else
                {
                    view.setVisibility(View.VISIBLE);
                    rlFilterMain.setVisibility(View.VISIBLE);
                }

                return;

            case R.id.rlBreakfast:

                view.setVisibility(View.GONE);
                rlFilterMain.setVisibility(View.GONE);

                return;

            case R.id.view:

                view.setVisibility(View.GONE);
                rlFilterMain.setVisibility(View.GONE);

                return;

            case R.id.rlLunch:

                view.setVisibility(View.GONE);
                rlFilterMain.setVisibility(View.GONE);

                return;

            case R.id.rlDinner:

                view.setVisibility(View.GONE);
                rlFilterMain.setVisibility(View.GONE);

                return;

            case R.id.rlOnlyLiquids:

                view.setVisibility(View.GONE);
                rlFilterMain.setVisibility(View.GONE);

                return;
        }
    }

    private class Adapter extends RecyclerView.Adapter<Holder> {
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

        public Adapter(ArrayList<HashMap<String, String>> favList) {
            data = favList;
        }

        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_diet_plan, parent, false));
        }

        @SuppressLint("SetTextI18n")
        public void onBindViewHolder(Holder holder, final int position) {

            holder.rlMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            holder.tvName.setText(data.get(position).get("name"));
            holder.tvDetails.setText(data.get(position).get("type")
            +"\n"+data.get(position).get("serving")
            +"\n"+data.get(position).get("calories"));

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

        TextView tvName,tvDetails;
        ImageView ivDietPhoto;
        RelativeLayout rlMain;

        public Holder(View itemView) {
            super(itemView);
            tvName =  itemView.findViewById(R.id.tvName);
            tvDetails =  itemView.findViewById(R.id.tvDetails);
            ivDietPhoto =  itemView.findViewById(R.id.ivDietPhoto);
            rlMain =  itemView.findViewById(R.id.rlMain);
        }
    }
}
