package mo.ed.prof.yusor.Activities.Book;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import mo.ed.prof.yusor.Activities.MainActivity;
import mo.ed.prof.yusor.Adapter.MyBooksAdapter;
import mo.ed.prof.yusor.Fragments.NoBooksInGalleryFragment;
import mo.ed.prof.yusor.Network.VerifyConnection;
import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.Volley.MakeVolleyRequests;
import mo.ed.prof.yusor.helpers.Room.StudentsEntity;
import mo.ed.prof.yusor.helpers.SessionManagement;

public class MyBooksActivity extends AppCompatActivity implements MakeVolleyRequests.OnCompleteListener{

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private VerifyConnection verifyConn;
    private MakeVolleyRequests makeVolleyRequest;
    private LinearLayoutManager mLayoutManager;
    private SessionManagement sessionManagement;
    private HashMap<String, String> user;
    private String ApiToken;
    private Toolbar mToolbar;
    private NoBooksInGalleryFragment noBooksInGalleryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_books);
        ButterKnife.bind(this);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (Locale.getDefault().getLanguage().contentEquals("en")){
            mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.arrow_back_en));
        }else if (Locale.getDefault().getLanguage().contentEquals("ar")){
            mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.arrow_back_ar));
        }
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        sessionManagement=new SessionManagement(getApplicationContext());
        user=sessionManagement.getUserDetails();
        if (user!=null) {
            ApiToken = user.get(SessionManagement.KEY_idToken);
        }
        verifyConn=new VerifyConnection(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (verifyConn.isConnected()){
            // get books
            makeVolleyRequest=new MakeVolleyRequests(getApplicationContext(), this);
            makeVolleyRequest.getAllBooksForUser(ApiToken);
        }
    }

    @Override
    public void onComplete(ArrayList<StudentsEntity> studentsEntities) {
        if (studentsEntities!=null){
            if (studentsEntities.size()>0){
                populateBookList(studentsEntities);
            }else if (studentsEntities.size()==0){
                FrameLayout frameLayout=(FrameLayout)findViewById(R.id.container_frame);
                frameLayout.setVisibility(View.VISIBLE);
                LinearLayout linearLayout=(LinearLayout)findViewById(R.id.RecyclerLinear);
                linearLayout.setVisibility(View.GONE);
                noBooksInGalleryFragment=new NoBooksInGalleryFragment();

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_frame, noBooksInGalleryFragment , "newsApi")
                        .commit();
//                Toast.makeText(getApplicationContext(), getResources().getString(R.string.you_no_books_exist), Toast.LENGTH_SHORT).show();
//                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                getApplicationContext().startActivity(intent);
            }
        }
    }

    private void populateBookList(ArrayList<StudentsEntity> studentsEntities) {
        MyBooksAdapter myBooksAdapter=new MyBooksAdapter(getApplicationContext(),studentsEntities);
        myBooksAdapter.notifyDataSetChanged();
        mLayoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.computeVerticalScrollOffset();
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myBooksAdapter);
    }
}