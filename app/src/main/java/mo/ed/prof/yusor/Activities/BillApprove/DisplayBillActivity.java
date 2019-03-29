package mo.ed.prof.yusor.Activities.BillApprove;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.Serializable;

import mo.ed.prof.yusor.Activities.MainActivity;
import mo.ed.prof.yusor.Fragments.BillsFragment;
import mo.ed.prof.yusor.Network.VerifyConnection;
import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.helpers.Room.StudentsEntity;

public class DisplayBillActivity extends AppCompatActivity {

    // http://fla4news.com/Yusor/api/v1/Bills_sold   (pending)   for seller
    // http://fla4news.com/Yusor/api/v1/Bills_sold   (completed) for both
    private VerifyConnection verifyConn;
    private Intent intent;
    private StudentsEntity studentEntity;
    BillsFragment billsFragment;
    private String Frags_KEY="Frags_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_bill);
        billsFragment = new BillsFragment();
//        intent = getIntent();

//        studentEntity = (StudentsEntity) intent.getSerializableExtra("feedItem");
        // get data from intent
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("feedItem", studentEntity);
//        billsFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.master_list_fragment, billsFragment, Frags_KEY)
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(intent);
        finish();
    }
}