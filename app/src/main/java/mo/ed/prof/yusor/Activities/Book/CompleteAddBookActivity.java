package mo.ed.prof.yusor.Activities.Book;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.dd.processbutton.iml.GenerateProcessButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import mo.ed.prof.yusor.Activities.MainActivity;
import mo.ed.prof.yusor.Adapter.BooksSpinnerAdapter;
import mo.ed.prof.yusor.Adapter.SimilarBooksAdapter;
import mo.ed.prof.yusor.Dev.Entity.FirebaseUsers;
import mo.ed.prof.yusor.Fragments.FragmentPriecsSuggestions;
import mo.ed.prof.yusor.Network.VerifyConnection;
import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.Volley.MakeVolleyRequests;
import mo.ed.prof.yusor.helpers.Config;
import mo.ed.prof.yusor.helpers.Designsers.ProgressGenerator;
import mo.ed.prof.yusor.helpers.Room.StudentsEntity;
import mo.ed.prof.yusor.helpers.SessionManagement;

import static mo.ed.prof.yusor.helpers.Config.TwoPane;

public class CompleteAddBookActivity extends AppCompatActivity implements ProgressGenerator.OnProgressCompleteListener,
        MakeVolleyRequests.OnMyBookCompleteListener,
        MakeVolleyRequests.OnSearchSuggestedBooksCompListener{

    @BindView(R.id.Book_spinner)
    Spinner Book_spinner;

    @BindView(R.id.radioBookStatusGroup)
    RadioGroup radioBookStatusGroup;

    @BindView(R.id.radioTransactionGroup)
    RadioGroup radioTransactionGroup;

    @BindView(R.id.Edit_BookPrice)
    EditText Edit_BookPrice;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.Publish_BTN)
    GenerateProcessButton Publish_BTN;

    private String PublishYear;
    private String ImageUri;
    private String FacultyName;
    private String ISBN;
    private String AuthorName;
    private String BookTitle;
    Bundle bundle;
    private String BookID;
    FragmentPriecsSuggestions fragmentPriecsSuggestions;
    private String FragsSuggest_KEY="FragsSuggest_KEY";
    public static String BOOK_NAME="BOOK_NAME";
    private RadioButton checkedBookStatus;
    private MakeVolleyRequests makeVolleyRequest;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private String BookStatus;
    private RadioButton checkedTransaction;
    private String TransactionType;
    private VerifyConnection verifyConnection;
    private ProgressGenerator progressGenerator;
    private String BookPrice;
    private SessionManagement sessionManagement;
    private HashMap<String, String> user;
    private String TokenID;
    private String sentBookStatus;
    private String sentTransactionType;
    private String sentAvailability="1";
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_add_book);
        ButterKnife.bind(this);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (Locale.getDefault().getLanguage().contentEquals("en")){
            mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.arrow_back_en));
        }else if (Locale.getDefault().getLanguage().contentEquals("ar")){
            mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.arrow_back_ar));
        }
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        bundle=new Bundle();
        fragmentPriecsSuggestions=new FragmentPriecsSuggestions();
        verifyConnection=new VerifyConnection(getApplicationContext());
        sessionManagement=new SessionManagement(getApplicationContext());
        user=sessionManagement.getUserDetails();
        if (user != null) {
            TokenID = user.get(SessionManagement.KEY_idToken);
        }


        if (findViewById(R.id.mTwoPane)!=null){
            // two pane Ui
            TwoPane=true;
        }else {
            // one pane Ui
            TwoPane=false;
        }
        if (Config.BookExistence!=null){
            if (Config.BookExistence.equals(AddBookActivity.ExistingBook)){
                BookTitle= getIntent().getExtras().getString(AddBookActivity.BookTitle_KEY);
                BookID= getIntent().getExtras().getString(AddBookActivity.BookID_KEY);
                Config.BookID=BookID;
                if (BookTitle!=null||BookID!=null){
                    makeVolleyRequest=new MakeVolleyRequests(getApplicationContext(), (MakeVolleyRequests.OnSearchSuggestedBooksCompListener) CompleteAddBookActivity.this);
                    makeVolleyRequest.searchSuggestedBooks(BookID, BookTitle, TokenID);
                }
            }
        } else if (verifyConnection.isConnected()){
            makeVolleyRequest=new MakeVolleyRequests(getApplicationContext(), (MakeVolleyRequests.OnMyBookCompleteListener) CompleteAddBookActivity.this);
            makeVolleyRequest.getAllBooksForUser_Similar(TokenID);
            Book_spinner.setVisibility(View.VISIBLE);
            Book_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Config.BookName = Config.BooksListCArr.get(position).getBookTitle();
                    Config.BookID = Config.BooksListCArr.get(position).getBookID();
                    Config.BookPosition=position;
                    if (Config.BookName!=null||Config.BookID!=null){
                        makeVolleyRequest=new MakeVolleyRequests(getApplicationContext(), (MakeVolleyRequests.OnSearchSuggestedBooksCompListener) CompleteAddBookActivity.this);
                        makeVolleyRequest.searchSuggestedBooks(Config.BookID, Config.BookName, TokenID);
                    }else {
                        Toast.makeText(getApplicationContext(), getString(R.string.select_spinner), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    private void PopulateExistingBooksList(List<StudentsEntity> result, int position) {
        BooksSpinnerAdapter booksSpinnerAdapter= new BooksSpinnerAdapter(getApplicationContext(), result);
        Book_spinner.setAdapter(booksSpinnerAdapter);
        Book_spinner.setSelection(position);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Publish_BTN.setEnabled(true);
        Publish_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedBookStatus = (RadioButton) radioBookStatusGroup.findViewById(radioBookStatusGroup.getCheckedRadioButtonId());
                BookStatus = checkedBookStatus.getText().toString();
                checkedTransaction = (RadioButton) radioTransactionGroup.findViewById(radioTransactionGroup.getCheckedRadioButtonId());
                TransactionType = checkedTransaction.getText().toString();
                BookPrice = Edit_BookPrice.getText().toString();
                if (Config.BookID != null) {
                    if (user != null) {
                        TokenID = user.get(SessionManagement.KEY_idToken);
                        if (BookStatus != null) {
                            if (BookStatus.equals(getResources().getString(R.string._new))) {
                                sentBookStatus = getResources().getString(R.string.new_book);
                            } else if (BookStatus.equals(getResources().getString(R.string.Intermediate))) {
                                sentBookStatus = getResources().getString(R.string.intermediate_book);
                            } else if (BookStatus.equals(getResources().getString(R.string.not_bad))) {
                                sentBookStatus = getResources().getString(R.string.not_bad_book);
                            }
                        }
                        if (TransactionType != null) {
                            if (TransactionType.equals(getResources().getString(R.string.sale))) {
                                sentTransactionType = getResources().getString(R.string.sale_book);
                            } else if (TransactionType.equals(getResources().getString(R.string.exchange))) {
                                sentTransactionType = getResources().getString(R.string.exchange_book);
                            } else if (TransactionType.equals(getResources().getString(R.string.gift))) {
                                sentTransactionType = getResources().getString(R.string.gift_book);
                            }
                        }
                        if (BookPrice!=null&&BookPrice.length()>0){
                            if (verifyConnection.isConnected()) {
                                progressGenerator = new ProgressGenerator((ProgressGenerator.OnProgressCompleteListener) CompleteAddBookActivity.this, getApplicationContext());
                                Publish_BTN.setEnabled(false);
                                progressGenerator.PublishBook(Publish_BTN, Config.BookID, BookPrice, sentBookStatus, sentAvailability, sentTransactionType, TokenID);
                            }
                        }else {
                            Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.enter_price),Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }



    @Override
    public void onProgressComplete(ArrayList<StudentsEntity> studentsEntities) {
        if (studentsEntities!=null){
            if (studentsEntities.size()>0){
                for (StudentsEntity studentsEntity:studentsEntities){
                    if (studentsEntity.getException()!=null){
                        Toast.makeText(getApplicationContext(), studentsEntity.getException().toString(), Toast.LENGTH_LONG).show();
                        Publish_BTN.setEnabled(true);
                    }else {
                        HomeRedirect(studentsEntity);
                    }
                }
            }
        }
    }

    private void HomeRedirect(StudentsEntity studentsEntity) {
        if (studentsEntity!=null){
            // redirect to Gallery
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void OnMyBookCompleted(CopyOnWriteArrayList<StudentsEntity> studentsEntities) {
        if (studentsEntities.size() > 0) {
            Config.BooksListCArr=studentsEntities;
            PopulateExistingBooksList(removeDublicates(studentsEntities), 0);

        }
    }

    private List<StudentsEntity> removeDublicates(CopyOnWriteArrayList<StudentsEntity> studentsEntities) {
        HashSet hashSet=new HashSet();
        hashSet.addAll(studentsEntities);
        studentsEntities.clear();
        studentsEntities.addAll(hashSet);
        return  studentsEntities;
    }

    @Override
    public void OnSearchSuggestedBooksCompleted(ArrayList<StudentsEntity> studentsEntities) {
        if (studentsEntities!=null){
            PopulateSimilarBooks(studentsEntities);
        }
    }



    private void PopulateSimilarBooks(ArrayList<StudentsEntity> urgentArticlesList) {
        SimilarBooksAdapter mAdapter=new SimilarBooksAdapter(getApplicationContext(),urgentArticlesList, TwoPane);
        mAdapter.notifyDataSetChanged();
        if (TwoPane){
            RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(getApplicationContext(), 2);
            recyclerView.setLayoutManager(layoutManager1);
        }else {
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }
}