package code.dashboard;

import android.annotation.SuppressLint;
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

import java.util.ArrayList;
import java.util.HashMap;

import code.view.BaseActivity;

public class InboxActivity extends BaseActivity implements View.OnClickListener {

    //GridLayoutManager
    GridLayoutManager mGridLayoutManager;

    //RelativeLayout
    RelativeLayout rlBack;

    //RecyclerView
    RecyclerView recyclerView;

    Adapter adapter;

    ArrayList<HashMap<String, String>> InboxList = new ArrayList<HashMap<String, String>>();

    //TextView
    TextView tvHeader;

    //View
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        findViewById();
    }

    private void findViewById() {

        //RelativeLayout
        rlBack = findViewById(R.id.rlBack);

        //recyclerView
        recyclerView = findViewById(R.id.recyclerView);

        //TextView for Header Text
        tvHeader = findViewById(R.id.tvHeader);

        tvHeader.setText(getString(R.string.inbox));

        mGridLayoutManager = new GridLayoutManager(mActivity, 1);
        recyclerView.setLayoutManager(mGridLayoutManager);
        recyclerView.setNestedScrollingEnabled(true);

        for(int i=0;i<5;i++)
        {
            HashMap<String, String> hashMap = new HashMap();

            hashMap.put("id","1");
            hashMap.put("text","Dinner");
            hashMap.put("name","Smoky Maple-Mustard Salmon");
            hashMap.put("time","2 hrs");

            InboxList.add(hashMap);
        }

        adapter = new Adapter(InboxList);
        recyclerView.setAdapter(adapter);

        rlBack.setOnClickListener(this);
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
            return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_inbox,parent, false));
        }

        @SuppressLint("SetTextI18n")
        public void onBindViewHolder(Holder holder, final int position) {

            holder.rlMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            holder.tvName.setText(data.get(position).get("name"));
            holder.tvDetails.setText(data.get(position).get("text"));
            holder.tvTime.setText(data.get(position).get("time"));

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

        TextView tvName,tvDetails,tvTime;
        ImageView ivDietPhoto;
        RelativeLayout rlMain;

        public Holder(View itemView) {
            super(itemView);
            tvName =  itemView.findViewById(R.id.tvName);
            tvDetails =  itemView.findViewById(R.id.tvDetails);
            tvTime =  itemView.findViewById(R.id.tvTime);
            ivDietPhoto =  itemView.findViewById(R.id.ivDietPhoto);
            rlMain =  itemView.findViewById(R.id.rlMain);
        }
    }
}
