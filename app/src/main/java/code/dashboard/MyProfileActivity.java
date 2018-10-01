package code.dashboard;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fittreat.android.R;

import code.view.BaseActivity;

public class MyProfileActivity extends BaseActivity implements View.OnClickListener {

    //RelativeLayout
    RelativeLayout rlBack;

    //TextView
    TextView tvHeader;

    //ImageView
    ImageView ivMiddle;

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

        ivMiddle = findViewById(R.id.ivMiddle);

        tvHeader.setText(getString(R.string.my_profile));
        ivMiddle.setImageResource(R.drawable.ic_my_profile);

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
}
