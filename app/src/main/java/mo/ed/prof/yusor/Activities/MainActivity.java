package mo.ed.prof.yusor.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import mo.ed.prof.yusor.Activities.Book.AddNewBookActivity;
import mo.ed.prof.yusor.Activities.BillApprove.DisplayBillActivity;
import mo.ed.prof.yusor.Activities.Book.MyBooksActivity;
import mo.ed.prof.yusor.Activities.Chat.ChatHistoryActivity;
import mo.ed.prof.yusor.Activities.Profile.ProfileActivity;
import mo.ed.prof.yusor.Fragments.BooksGalleryFragment;
import mo.ed.prof.yusor.Fragments.NoBooksInGalleryFragment;
import mo.ed.prof.yusor.Fragments.NoInternetFragment;
import mo.ed.prof.yusor.Network.SnackBarClassLauncher;
import mo.ed.prof.yusor.Network.VerifyConnection;
import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.Volley.MakeVolleyRequests;
import mo.ed.prof.yusor.helpers.Room.StudentsEntity;
import mo.ed.prof.yusor.helpers.SessionManagement;

public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener,
        NoInternetFragment.onReloadInternetServiceListener, BooksGalleryFragment.NoBooksFragment, MakeVolleyRequests.OnCompleteListener{

    Snackbar snackbar;
    SnackBarClassLauncher snackBarLauncher;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private TextView EmailText;
    private TextView UserNameText;
    private ImageView ProfilePicView;
    private SessionManagement sessionManagement;
    private HashMap<String, String> user;
    private String LoggedEmail;
    private String LoggedUserName;
    private String LoggedProfilePic;
    private String TokenID;
    private DrawerLayout drawerLayout;
    NoInternetFragment noInternetFragment;
    private BooksGalleryFragment booksGalleryFragment;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private String LoggedType;
    private String firebaseUserID;
    private NoBooksInGalleryFragment noBooksInGalleryFragment;
    private MakeVolleyRequests makeVolleyRequest;
    private VerifyConnection verifyConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTheme(R.style.ArishTheme);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        snackBarLauncher=new SnackBarClassLauncher();
        View header=navigationView.getHeaderView(0);
        verifyConnection=new VerifyConnection(getApplicationContext());
        EmailText=(TextView)header.findViewById(R.id.Email);
        UserNameText=(TextView)header.findViewById(R.id.UserName);
        ProfilePicView=(ImageView)header.findViewById(R.id.profile_image);
        final Bundle bundle=new Bundle();
        noInternetFragment=new NoInternetFragment();
        booksGalleryFragment=new BooksGalleryFragment();
        noBooksInGalleryFragment=new NoBooksInGalleryFragment();
        sessionManagement=new SessionManagement(getApplicationContext());
        user=sessionManagement.getUserDetails();
        if (user!=null){
            LoggedEmail = user.get(SessionManagement.KEY_EMAIL);
            LoggedUserName=user.get(SessionManagement.KEY_NAME);
            LoggedProfilePic=user.get(SessionManagement.KEY_Profile_Pic);
            TokenID=user.get(SessionManagement.KEY_idToken);
            firebaseUserID=user.get(SessionManagement.firebase_UID_KEY);
            if (LoggedEmail!=null){
                EmailText.setText(LoggedEmail);
            }
            displayGallery();
            if (LoggedUserName!=null){
                UserNameText.setText(LoggedUserName);
            }
            if (LoggedProfilePic!=null){
                Picasso.with(getApplicationContext()).load(LoggedProfilePic)
                        .error(R.drawable.web_hi_res_512)
                        .into(ProfilePicView);
            }
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                switch (menuItem.getItemId()){
                    case R.id.add_book:
                        Intent intent=new Intent(getApplicationContext(),AddNewBookActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.browse_book:
                        displayGallery();
                        return true;
                    case R.id.chat_history:
                        //retrive chat history
                        // if chatarray.size>0 -- send with intent
                        //else show dialogue no chat history exist
                        Intent intent2=new Intent(getApplicationContext(),ChatHistoryActivity.class);
                        startActivity(intent2);
                        return true;
                    case R.id.bills:
                        //retrive bills
                        // if bills_array.size>0 -- send with intent
                        //else show dialogue
//                        bundle.putString(ArticleType,ARTS);
                        Intent intent3=new Intent(getApplicationContext(),DisplayBillActivity.class);
                        startActivity(intent3);
                        return true;
                    case R.id.books:
                        //retrive bills
                        // if bills_array.size>0 -- send with intent
                        //else show dialogue
//                        bundle.putString(ArticleType,ARTS);
                        Intent intent3_=new Intent(getApplicationContext(),MyBooksActivity.class);
                        startActivity(intent3_);
                        return true;
                    case R.id.user_profile:
                        Intent intent4=new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(intent4);
                        return true;
                    case R.id.reports:
                        Intent intent_reports=new Intent(getApplicationContext(), ReportsActivity.class);
                        startActivity(intent_reports);
                        return true;
                    case R.id.logout:
                        SignOut();
                        return true;
                    default:
                        return true;
                }
            }
        });
        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar, R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);

            }
            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };
        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    private void displayGallery() {
        if (TokenID != null) {
            if (verifyConnection.isConnected()){
                makeVolleyRequest = new MakeVolleyRequests(getApplicationContext(), MainActivity.this);
                makeVolleyRequest.getAllBooksForSale(TokenID);
            }else {
                InternetDisabled();
            }
        }
    }

    private void SignOut() {
        if (verifyConnection.isConnected()){
            user =sessionManagement.getLoginType();
            if (user!=null){
                LoggedType = user.get(SessionManagement.KEY_LoginType);
                if (LoggedType!=null){
                    if (LoggedType.equals("EP")) {
                        sessionManagement.logoutUser();
                        FirebaseAuth.getInstance().signOut();
                    }
                }
            }
        }else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    private void InternetDisabled() {
        snackbar=NetCut();
        snackBarLauncher.SnackBarInitializer(snackbar);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_frame, noInternetFragment, "newsApi")
                .commit();
    }

    private Snackbar NetCut() {
        return snackbar= Snackbar
                .make(drawerLayout, getApplicationContext().getResources().getString(R.string.no_internet), Snackbar.LENGTH_LONG)
                .setAction(getApplicationContext().getResources().getString(R.string.retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        displayGallery();
                    }
                });
    }

    @Override
    public void ReloadInternetService() {
        displayGallery();
    }

    @Override
    public void noBooksFragment() {
        noBooksFrag();
    }

    public void noBooksFrag(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_frame, noBooksInGalleryFragment , "newsApi")
                .commitAllowingStateLoss();
    }

    @Override
    public void onComplete(ArrayList<StudentsEntity> studentsEntities) {
        if (studentsEntities != null) {
            if (studentsEntities.size() > 0) {
                for (StudentsEntity studentsEntity : studentsEntities) {
                    if (studentsEntity.getException() != null) {
                        Toast.makeText(getApplicationContext(), studentsEntity.getException().toString(), Toast.LENGTH_LONG).show();
                    } else if (studentsEntity.getServerMessage() != null) {
                        Toast.makeText(getApplicationContext(), studentsEntity.getServerMessage().toString(), Toast.LENGTH_LONG).show();
                        noBooksFrag();
                    } else {
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("galleryItems",studentsEntities);
                        booksGalleryFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container_frame, booksGalleryFragment, "newsApi")
                                .commitAllowingStateLoss();
                    }
                }
            }else if (studentsEntities.size()==0){
                noBooksFrag();
            }
        }
    }
}