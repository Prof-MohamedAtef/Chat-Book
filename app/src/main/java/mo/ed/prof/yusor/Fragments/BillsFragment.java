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
import java.util.ArrayList;
import java.util.HashMap;
import butterknife.ButterKnife;
import mo.ed.prof.yusor.Adapter.Bills.BillsAdapter;
import mo.ed.prof.yusor.Network.VerifyConnection;
import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.Volley.MakeVolleyRequests;
import mo.ed.prof.yusor.helpers.Room.StudentsEntity;
import mo.ed.prof.yusor.helpers.SessionManagement;

/**
 * Created by Prof-Mohamed Atef on 3/25/2019.
 */

public class BillsFragment extends Fragment implements MakeVolleyRequests.OnCompleteListener{

    private BillsAdapter mBillsAdapter;
    private VerifyConnection verifyConn;
    private MakeVolleyRequests makeVolleyRequests;
    private SessionManagement sessionManagement;
    private HashMap<String, String> user;
    private String ApiToken;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        verifyConn=new VerifyConnection(getActivity());
        makeVolleyRequests=new MakeVolleyRequests(getActivity(),BillsFragment.this);
        sessionManagement=new SessionManagement(getActivity());
        user =sessionManagement.getUserDetails();
        if (user!=null) {
            ApiToken = user.get(SessionManagement.KEY_idToken);
            if (verifyConn.isConnected()){
                if (ApiToken!=null){
                    makeVolleyRequests.getSoldBills(ApiToken);
                }
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.bills_fragment,container,false);
        ButterKnife.bind(this, rootView);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        return rootView;
    }

    @Override
    public void onComplete(ArrayList<StudentsEntity> studentsEntities) {
        if (studentsEntities!=null){
            if (studentsEntities.size()>0){
                PopulaterSoldBills(studentsEntities);
            }
        }
    }

    private void PopulaterSoldBills(ArrayList<StudentsEntity> studentsEntities){
        BillsAdapter mAdapter=new BillsAdapter(getActivity(),studentsEntities);
        mAdapter.notifyDataSetChanged();
        mLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.computeVerticalScrollOffset();
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }
}
