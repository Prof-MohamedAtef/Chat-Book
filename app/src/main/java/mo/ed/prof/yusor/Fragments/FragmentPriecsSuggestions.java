package mo.ed.prof.yusor.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

import mo.ed.prof.yusor.Activities.AddBook.AddNewBookActivity;
import mo.ed.prof.yusor.Adapter.SimilarBooksAdapter;
import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.Volley.MakeVolleyRequests;
import mo.ed.prof.yusor.helpers.Config;
import mo.ed.prof.yusor.helpers.Room.StudentsEntity;
import mo.ed.prof.yusor.helpers.SessionManagement;

//import static mo.ed.prof.yusor.Activities.AddBook.AddBookActivity.BOOK_NAME;
import static com.facebook.FacebookSdk.getApplicationContext;
import static mo.ed.prof.yusor.Activities.AddBook.AddBookActivity.BookID_KEY;
import static mo.ed.prof.yusor.Activities.AddBook.AddBookActivity.BookTitle_KEY;
import static mo.ed.prof.yusor.helpers.Config.TwoPane;

/**
 * Created by Prof-Mohamed Atef on 2/6/2019.
 */

public class FragmentPriecsSuggestions extends Fragment implements MakeVolleyRequests.OnCompleteListener {

    private GridLayoutManager layoutManager;
    String BOOK_NAME_STR;
    Bundle bundle;
    private MakeVolleyRequests makeVolleyRequests;
    private RecyclerView recyclerView;
    private String BookID_STR;
    private SessionManagement sessionManagement;
    private HashMap<String, String> user;
    private String TokenID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManagement=new SessionManagement(getApplicationContext());
        user=sessionManagement.getUserDetails();
        if (user != null) {
            TokenID = user.get(SessionManagement.KEY_idToken);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.fragment_prices_suggestions,container,false);
        recyclerView=(RecyclerView)rootview.findViewById(R.id.recyclerView);
        bundle=getArguments();
        makeVolleyRequests=new MakeVolleyRequests(getActivity(),FragmentPriecsSuggestions.this);
        if (bundle!=null){
            BookID_STR=(String)bundle.get(BookID_KEY);
            BOOK_NAME_STR=(String)bundle.get(AddNewBookActivity.BookTitle_KEY);
            Config.BookID=BookID_STR;
            makeVolleyRequests.searchSuggestedBooks(BookID_STR, BOOK_NAME_STR, TokenID);
            // send to api and get all similar books with their prices and display on this fragment,
            // on this fragment enter the price, once entered,redirect to add book activity with the entered price here to submit book
        }
        return rootview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void PopulateSimilarBooks(ArrayList<StudentsEntity> urgentArticlesList) {
        SimilarBooksAdapter mAdapter=new SimilarBooksAdapter(getActivity(),urgentArticlesList, TwoPane);
        mAdapter.notifyDataSetChanged();
        RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onComplete(ArrayList<StudentsEntity> studentsEntities) {
        if (studentsEntities!=null){
            PopulateSimilarBooks(studentsEntities);
        }
    }
}