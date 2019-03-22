package mo.ed.prof.yusor.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import mo.ed.prof.yusor.Dev.Adapter.UserAdapter;
import mo.ed.prof.yusor.Dev.Entity.FirebaseUsers;
import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.helpers.Room.StudentsEntity;

/**
 * Created by Prof-Mohamed Atef on 3/21/2019.
 */

public class UsersFragment extends Fragment{

    private static final String ARG_SECTION_NUMBER = "section_number";
    private ListView Users_listView;
    private ViewGroup UserItem;

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    List<FirebaseUsers> mUsers;
    private UserAdapter userAdapter;

    public static UsersFragment newInstance(int sectionNum) {
        UsersFragment _usersFragment =new UsersFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNum);
        _usersFragment.setArguments(args);
        return _usersFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.user_list, container, false);
        recyclerView=(RecyclerView)mainView.findViewById(R.id.recycler_view);
        mUsers=new ArrayList<>();

        return mainView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        readUsers();
    }

    private void readUsers() {
        final FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("yusor-chat").child("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    FirebaseUsers users=snapshot.getValue(FirebaseUsers.class);
                    assert users!=null;
                    if (!users.getID().equals(firebaseUser.getUid())){
                        mUsers.add(users);
                    }
                }
                PopulateUsersList(mUsers);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void PopulateUsersList(List<FirebaseUsers> typesArticlesList) {
        UserAdapter mAdapter=new UserAdapter(getActivity(),typesArticlesList);
        mAdapter.notifyDataSetChanged();
        recyclerView.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter.setHasStableIds(true);
        recyclerView.setAdapter(mAdapter);
    }
}
