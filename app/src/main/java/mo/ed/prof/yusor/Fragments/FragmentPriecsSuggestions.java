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

import mo.ed.prof.yusor.Adapter.SimilarBooksAdapter;
import mo.ed.prof.yusor.GenericAsyncTasks.RetrieveBooksAsyncTask;
import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.helpers.OptionsEntity;

import static mo.ed.prof.yusor.Activities.AddBookActivity.BOOK_NAME;
import static mo.ed.prof.yusor.helpers.Config.TwoPane;

/**
 * Created by Prof-Mohamed Atef on 2/6/2019.
 */

public class FragmentPriecsSuggestions extends Fragment implements RetrieveBooksAsyncTask.OnBooksRetrievalTaskCompleted {

    private RecyclerView recyclerView_Horizontal;
    private GridLayoutManager layoutManager;
    String BOOK_NAME_STR;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.fragment_prices_suggestions,container,false);
        Bundle bundle=getArguments();
        if (bundle!=null){
            BOOK_NAME_STR=(String)bundle.get(BOOK_NAME);
            // send to api and get all similar books with their prices and display on this fragment,
            // on this fragment enter the price, once entered,redirect to add book activity with the entered price here to submit book
        }
        rootview.findViewById(R.id.recycler_view_horizontal);
        return rootview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RetrieveBooksAsyncTask retrieveBooksAsyncTask=new RetrieveBooksAsyncTask(this, getActivity());
        //get api link
        retrieveBooksAsyncTask.execute();
    }

    private void PopulateSimilarBooks(ArrayList<OptionsEntity> urgentArticlesList) {
        SimilarBooksAdapter mAdapter=new SimilarBooksAdapter(getActivity(),urgentArticlesList, TwoPane);
        mAdapter.notifyDataSetChanged();
        layoutManager=(GridLayoutManager)recyclerView_Horizontal.getLayoutManager();
        RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView_Horizontal.setLayoutManager(mLayoutManager);
        recyclerView_Horizontal.setItemAnimator(new DefaultItemAnimator());
        recyclerView_Horizontal.setAdapter(mAdapter);
    }

    @Override
    public void onBooksRetrievalApiTaskCompleted(ArrayList<OptionsEntity> result) {

    }
}