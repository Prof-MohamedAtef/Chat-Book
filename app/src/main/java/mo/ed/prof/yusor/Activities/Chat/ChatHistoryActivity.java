package mo.ed.prof.yusor.Activities.Chat;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import mo.ed.prof.yusor.Dev.Entity.FirebaseChat;
import mo.ed.prof.yusor.Dev.Entity.FirebaseUsers;
import mo.ed.prof.yusor.Fragments.ChatsFragment;
import mo.ed.prof.yusor.Fragments.UsersFragment;
import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.helpers.Room.StudentsEntity;

public class ChatHistoryActivity extends AppCompatActivity {

    public static final int permission_checker = 1;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private StudentsEntity studentsEntity;
    private FirebaseUser firebaseUser;
    private DatabaseReference reference;
    private int unread;
    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_history);
        setTheme(R.style.ArishTheme);
        this.setTitle(getResources().getString(R.string.chat_));
        studentsEntity = new StudentsEntity();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar_layout);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.chat_container);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        reference=FirebaseDatabase.getInstance().getReference("yusor-chat").child("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                unread=0;
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    FirebaseChat chat=snapshot.getValue(FirebaseChat.class);
                    if (chat.getReceiver().equals(firebaseUser.getUid()) && !chat.isIsseen()){
                        unread++;
                    }
                }

                mSectionsPagerAdapter.addFragment(new UsersFragment(), getResources().getString(R.string.students));
                if (unread==0){
                    mSectionsPagerAdapter.addFragment(new ChatsFragment(), getResources().getString(R.string.chats));
                }else {
                    mSectionsPagerAdapter.addFragment(new ChatsFragment(), "("+unread+")"+getResources().getString(R.string.chats));
                }


                mViewPager.setAdapter(mSectionsPagerAdapter);
                tabLayout.setupWithViewPager(mViewPager);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        reference= FirebaseDatabase.getInstance().getReference("yusor-chat").child("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                FirebaseUsers users= dataSnapshot.getValue(FirebaseUsers.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case permission_checker: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_chat_history, container, false);
            return rootView;
        }
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;



        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fragments=new ArrayList<>();
            this.titles=new ArrayList<>();
        }

        @Override
        public Fragment getItem(int position) {

            return fragments.get(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

        public void addFragment(Fragment fragment,String title){
            fragments.add(fragment);
            titles.add(title);
        }



    }

    private void Status(String status){
        reference=FirebaseDatabase.getInstance().getReference("yusor-chat").child("Users").child(firebaseUser.getUid());
        HashMap<String, Object> hashMap=new HashMap<>();
        hashMap.put("status", status);

        reference.updateChildren(hashMap);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Status("online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Status("offline");
    }
}
