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
import android.widget.ImageView;

import mo.ed.prof.yusor.Activities.Book.AddNewBookActivity;
import mo.ed.prof.yusor.R;

/**
 * Created by Prof-Mohamed Atef on 3/29/2019.
 */

public class NoBooksInGalleryFragment extends Fragment{
    private Button btn_addBook;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.no_book_in_gallery, container, false);
        btn_addBook=(Button)mainView.findViewById(R.id.add_book);
        btn_addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),AddNewBookActivity.class);
                startActivity(intent);
            }
        });
        return mainView;
    }
}
