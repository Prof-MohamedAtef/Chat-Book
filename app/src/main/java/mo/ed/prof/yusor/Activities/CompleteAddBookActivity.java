package mo.ed.prof.yusor.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.dd.processbutton.iml.GenerateProcessButton;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import mo.ed.prof.yusor.Activities.Authentication.TaibahRegistrationActivity;
import mo.ed.prof.yusor.Fragments.FragmentPriecsSuggestions;
import mo.ed.prof.yusor.Fragments.SelectBookFragmentIFExist;
import mo.ed.prof.yusor.Network.VerifyConnection;
import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.helpers.Config;
import mo.ed.prof.yusor.helpers.Designsers.ProgressGenerator;
import mo.ed.prof.yusor.helpers.Room.StudentsEntity;
import mo.ed.prof.yusor.helpers.SessionManagement;

import static mo.ed.prof.yusor.Activities.AddBookActivity.AUTHOR_NAME_KEY;
import static mo.ed.prof.yusor.Activities.AddBookActivity.BookID_KEY;
import static mo.ed.prof.yusor.Activities.AddBookActivity.BookTitle_KEY;
import static mo.ed.prof.yusor.Activities.AddBookActivity.ExistingBook;
import static mo.ed.prof.yusor.Activities.AddBookActivity.FacultyName_KEY;
import static mo.ed.prof.yusor.Activities.AddBookActivity.ISBN_KEY;
import static mo.ed.prof.yusor.Activities.AddBookActivity.ImageUri_KEY;
import static mo.ed.prof.yusor.Activities.AddBookActivity.NextNewBook;
import static mo.ed.prof.yusor.Activities.AddBookActivity.PublishYear_KEY;

public class CompleteAddBookActivity extends AppCompatActivity implements ProgressGenerator.OnCompleteListener{

    @BindView(R.id.radioBookStatusGroup)
    RadioGroup radioBookStatusGroup;

    @BindView(R.id.radioTransactionGroup)
    RadioGroup radioTransactionGroup;

    @BindView(R.id.Edit_BookPrice)
    EditText Edit_BookPrice;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_add_book);
        ButterKnife.bind(this);
        bundle=new Bundle();
        fragmentPriecsSuggestions=new FragmentPriecsSuggestions();
        verifyConnection=new VerifyConnection(getApplicationContext());
        sessionManagement=new SessionManagement(getApplicationContext());
        user=sessionManagement.getUserDetails();
        if (Config.BookExistence.equals(ExistingBook)){
            BookTitle= getIntent().getExtras().getString(BookTitle_KEY);
            BookID= getIntent().getExtras().getString(BookID_KEY);
            Config.BookID=BookID;
            bundle.putString(BookTitle_KEY,BookTitle);
            bundle.putString(BookID_KEY,BookID);
        }
        NavigatToFragments(BookID);
    }

    @Override
    protected void onResume() {
        super.onResume();

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
                                progressGenerator = new ProgressGenerator((ProgressGenerator.OnCompleteListener) CompleteAddBookActivity.this, getApplicationContext());
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

    private void NavigatToFragments(String bookID) {
        bundle.putString(BookID_KEY,bookID);
        fragmentPriecsSuggestions.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_prices_suggestions, fragmentPriecsSuggestions, FragsSuggest_KEY)
                .commit();
    }

    @Override
    public void onComplete(ArrayList<StudentsEntity> studentsEntities) {
        if (studentsEntities!=null){
            if (studentsEntities.size()>0){
                for (StudentsEntity studentsEntity:studentsEntities){
                    if (studentsEntity.getException()!=null){
                        Toast.makeText(getApplicationContext(), studentsEntity.getException().toString(), Toast.LENGTH_LONG).show();
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
}