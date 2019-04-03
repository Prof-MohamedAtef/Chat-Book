package mo.ed.prof.yusor.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import mo.ed.prof.yusor.Activities.Book.AddNewBookActivity;
import mo.ed.prof.yusor.Activities.MainActivity;
import mo.ed.prof.yusor.R;

/**
 * Created by Prof-Mohamed Atef on 4/2/2019.
 */

public class NoBillsForU extends Fragment {
    private Button go_gallery;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.no_bills_for_u, container, false);
        go_gallery=(Button)mainView.findViewById(R.id.go_gallery);
        go_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return  mainView;
    }
}
