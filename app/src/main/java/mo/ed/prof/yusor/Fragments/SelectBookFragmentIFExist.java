package mo.ed.prof.yusor.Fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import mo.ed.prof.yusor.Adapter.CustomSpinnerAdapter;
import mo.ed.prof.yusor.GenericAsyncTasks.BooksAsyncTask;
import mo.ed.prof.yusor.GenericAsyncTasks.RetrieveBooksAsyncTask;
import mo.ed.prof.yusor.GenericAsyncTasks.RetrieveDepartmentsAsyncTask;
import mo.ed.prof.yusor.Network.SnackBarClassLauncher;
import mo.ed.prof.yusor.Network.VerifyConnection;
import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.Volley.MakeVolleyRequests;
import mo.ed.prof.yusor.helpers.Config;
import mo.ed.prof.yusor.helpers.Room.StudentsEntity;

/**
 * Created by Prof-Mohamed Atef on 3/15/2019.
 */

public class SelectBookFragmentIFExist extends Fragment implements RetrieveBooksAsyncTask.OnBooksRetrievalTaskCompleted{

    @BindView(R.id.Books_spinner)
    Spinner Books_spinner;

    @BindView(R.id.AddBook_BTN)
    Button AddBook_BTN;

    @BindView(R.id.Next_BTN)
    Button Next_BTN;

    private SnackBarClassLauncher snackBarLauncher;
    private String URL="http://fla4news.com/Yusor/api/v1/books";
    private VerifyConnection verifyConnection;
    private String KEY_BooksLIST="KEY_BooksLIST";
    private String KEY_POSITION="KEY_POSITION";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verifyConnection=new VerifyConnection(getActivity());
        AddBook_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Next_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.select_book_if_exist_fragment, container, false);
        ButterKnife.bind(this,rootView);
        snackBarLauncher=new SnackBarClassLauncher();
        return rootView;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_BooksLIST, Config.BooksList);
        outState.putInt(KEY_POSITION,Config.BookPosition);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState!=null){
            if (savedInstanceState!=null) {
                Config.BooksList = (ArrayList<StudentsEntity>) savedInstanceState.getSerializable(KEY_BooksLIST);
                Config.BookPosition= savedInstanceState.getInt(KEY_POSITION);
                if (Config.BooksList != null) {
                    PopulateExistingBooksList(Config.BooksList, Config.BookPosition);
                }
            }
        }else {
            if (verifyConnection.isConnected()){
                RetrieveBooksAsyncTask retrieveBooksAsyncTask=new RetrieveBooksAsyncTask((RetrieveBooksAsyncTask.OnBooksRetrievalTaskCompleted) SelectBookFragmentIFExist.this, getActivity());
                retrieveBooksAsyncTask.execute(URL);
            }
        }
        Books_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Config.BookTitle = Config.BooksList.get(position).getBookTitle();
                Config.BookID = Config.BooksList.get(position).getBookID();
                Config.BookPosition=position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }


    @Override
    public void onBooksRetrievalApiTaskCompleted(ArrayList<StudentsEntity> result) {
        if (result.size() > 0) {
            Config.BooksList=result;
            PopulateExistingBooksList(result, 0);
        }
    }

    private void PopulateExistingBooksList(ArrayList<StudentsEntity> result, int position) {
        CustomSpinnerAdapter customSpinnerAdapterFaculties = new CustomSpinnerAdapter(getActivity(), result);
        Books_spinner.setAdapter(customSpinnerAdapterFaculties);
        Books_spinner.setSelection(position);
    }


    public interface OnNewBookAddition{
        void onNewBookAdditionNeeded();
    }
}