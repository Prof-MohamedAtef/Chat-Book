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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;
import mo.ed.prof.yusor.Adapter.BooksGalleryAdapter;
import mo.ed.prof.yusor.Network.SnackBarClassLauncher;
import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.Volley.MakeVolleyRequests;
import mo.ed.prof.yusor.helpers.Room.StudentsEntity;
import mo.ed.prof.yusor.helpers.SessionManagement;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Prof-Mohamed Atef on 2/6/2019.
 */

public class BooksGalleryFragment extends Fragment implements MakeVolleyRequests.OnCompleteListener{
//        RetrieveBooksAsyncTask.OnBooksRetrievalTaskCompleted {

    private RecyclerView recyclerView;
    private SnackBarClassLauncher snackBarLauncher;
    private boolean TwoPane;
    private MakeVolleyRequests makeVolleyRequest;
    private SessionManagement sessionManagement;
    private HashMap<String, String> user;
    private String TokenID;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        RetrieveBooksAsyncTask retrieveBooksAsyncTask=new RetrieveBooksAsyncTask(this, getActivity());
        //get api link
//        retrieveBooksAsyncTask.execute(BooksURL);
        sessionManagement=new SessionManagement(getApplicationContext());
        user=sessionManagement.getUserDetails();
        if (user!=null){
            TokenID=user.get(SessionManagement.KEY_idToken);
            if (TokenID!=null){
                makeVolleyRequest=new MakeVolleyRequests(getActivity(),BooksGalleryFragment.this);
                makeVolleyRequest.getAllBooksForSale(TokenID);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.books_gallery_fragment,container,false);
        ButterKnife.bind(this, rootView);
        recyclerView=(RecyclerView)rootView.findViewById(R.id.recycler_view);
        snackBarLauncher=new SnackBarClassLauncher();
        return  rootView;
    }

    private void PopulateBooksGallery(ArrayList<StudentsEntity> BooksList) {
        BooksGalleryAdapter mAdapter=new BooksGalleryAdapter(getActivity(),BooksList, TwoPane);
        mAdapter.notifyDataSetChanged();
        RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

//    @Override
//    public void onBooksRetrievalApiTaskCompleted(ArrayList<StudentsEntity> result) {
//
//    }

    @Override
    public void onComplete(ArrayList<StudentsEntity> studentsEntities) {
        if (studentsEntities!=null){
            if (studentsEntities.size()>0){
                for (StudentsEntity studentsEntity: studentsEntities){
                    if (studentsEntity.getException()!=null){
                        Toast.makeText(getApplicationContext(), studentsEntity.getException().toString(), Toast.LENGTH_LONG).show();
                    }else {
                        PopulateBooksGallery(studentsEntities);
                    }
                }
            }
        }
    }
}