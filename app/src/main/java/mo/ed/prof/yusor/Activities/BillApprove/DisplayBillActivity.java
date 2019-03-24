package mo.ed.prof.yusor.Activities.BillApprove;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.Serializable;

import mo.ed.prof.yusor.Network.VerifyConnection;
import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.helpers.Room.StudentsEntity;

public class DisplayBillActivity extends AppCompatActivity {

    private VerifyConnection verifyConn;
    private Intent intent;
    private StudentsEntity studentEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_bill);

        verifyConn=new VerifyConnection(getApplicationContext());
        if (verifyConn.isConnected()){
            // connect to Api
        }else{
            intent=getIntent();
            studentEntity= (StudentsEntity)intent.getSerializableExtra("feedItem");
            // get data from intent
        }
    }
}
