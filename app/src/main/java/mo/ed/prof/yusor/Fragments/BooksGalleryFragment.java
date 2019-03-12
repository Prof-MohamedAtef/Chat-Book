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

import butterknife.ButterKnife;
import mo.ed.prof.yusor.Adapter.SimilarBooksAdapter;
import mo.ed.prof.yusor.GenericAsyncTasks.RetrieveBooksAsyncTask;
import mo.ed.prof.yusor.Network.SnackBarClassLauncher;
import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.helpers.Config;
import mo.ed.prof.yusor.helpers.OptionsEntity;

import static mo.ed.prof.yusor.helpers.Config.TwoPane;

/**
 * Created by Prof-Mohamed Atef on 2/6/2019.
 */

public class BooksGalleryFragment extends Fragment implements RetrieveBooksAsyncTask.OnBooksRetrievalTaskCompleted {

    private RecyclerView recyclerView;
    private SnackBarClassLauncher snackBarLauncher;
    private boolean TwoPane;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RetrieveBooksAsyncTask retrieveBooksAsyncTask=new RetrieveBooksAsyncTask(this, getActivity());
        //get api link
        retrieveBooksAsyncTask.execute();

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

    private void PopulateBooksGallery(ArrayList<OptionsEntity> BooksList) {
        SimilarBooksAdapter mAdapter=new SimilarBooksAdapter(getActivity(),BooksList, TwoPane);
        mAdapter.notifyDataSetChanged();
        RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onBooksRetrievalApiTaskCompleted(ArrayList<OptionsEntity> result) {

    }
}