package mo.ed.prof.yusor.Activities.Book;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import mo.ed.prof.yusor.Activities.MainActivity;
import mo.ed.prof.yusor.Adapter.AuthorsSpinnerAdapter;
import mo.ed.prof.yusor.Adapter.CustomSpinnerAdapter;
import mo.ed.prof.yusor.Adapter.FacultiesSpinnerAdapter;
import mo.ed.prof.yusor.Fragments.FragmentNewBookDetails;
import mo.ed.prof.yusor.GenericAsyncTasks.FacultiesAsyncTask;
import mo.ed.prof.yusor.GenericAsyncTasks.RetrieveAuthorsAsyncTask;
import mo.ed.prof.yusor.Network.VerifyConnection;
import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.Volley.MakeVolleyRequests;
import mo.ed.prof.yusor.helpers.Config;
import mo.ed.prof.yusor.helpers.Room.StudentsEntity;
import mo.ed.prof.yusor.helpers.SessionManagement;

import static mo.ed.prof.yusor.helpers.Config.AuthURL;
import static mo.ed.prof.yusor.helpers.Config.FacultiesURL;

public class EditBookActivity extends AppCompatActivity implements RetrieveAuthorsAsyncTask.OnAuthorsRetrievalTaskCompleted,
        FacultiesAsyncTask.OnFacultiesRetrievalTaskCompleted,
        MakeVolleyRequests.OnCompleteListener{

@BindView(R.id.Edit_addBook)
    EditText Edit_addBook;

    @BindView(R.id.Auth_spinner)
    Spinner Auth_spinner;

    @BindView(R.id.Edit_isbnNum)
    EditText Edit_isbnNum;

    @BindView(R.id.Faculty_spinner)
    Spinner Faculty_spinner;

    @BindView(R.id.Purpose_spinner)
    Spinner Purpose_spinner;

    @BindView(R.id.BookStatus_spinner)
    Spinner BookStatus_spinner;

    @BindView(R.id.Edit_PublishYear)
    EditText Edit_PublishYear;

    @BindView(R.id.Edit_enterDescription)
    EditText Edit_enterDescription;

    @BindView(R.id.Edit_BookPrice)
    EditText Edit_BookPrice;

    @BindView(R.id.Edit_BTN)
    GenerateProcessButton Edit_BTN;


    private Intent intent;
    private StudentsEntity studentEntity;
    private RadioButton checkedBookStatus;
    private RadioButton checkedTransaction;
    private String BookStatus;
    private String TransactionType;
    private VerifyConnection verifyConnection;
    private MakeVolleyRequests makeVolleyRequests;
    private String BookName;
    private String ISBN_Num;
    private String PublishYear;
    private Uri ImageUri;
    private SessionManagement sessionManagement;
    private HashMap<String, String> user;
    private String ApiToken;
    private String sentTransactionType;
    private String BookPrice;
    private String sentBookStatus;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);
        ButterKnife.bind(this);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_arrow_red));
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        intent=getIntent();

        sessionManagement=new SessionManagement(getApplicationContext());
        user =sessionManagement.getUserDetails();
        if (user!=null) {
            ApiToken = user.get(SessionManagement.KEY_idToken);
        }

        verifyConnection=new VerifyConnection(getApplicationContext());

        if (verifyConnection.isConnected()) {
            RetrieveAuthorsAsyncTask retrieveAuthorsAsyncTask = new RetrieveAuthorsAsyncTask((RetrieveAuthorsAsyncTask.OnAuthorsRetrievalTaskCompleted) EditBookActivity.this, getApplicationContext());
            retrieveAuthorsAsyncTask.execute(AuthURL);
            FacultiesAsyncTask facultiesAsyncTask= new FacultiesAsyncTask((FacultiesAsyncTask.OnFacultiesRetrievalTaskCompleted) EditBookActivity.this, getApplicationContext());
            facultiesAsyncTask.execute(FacultiesURL);
        }else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_internet),Toast.LENGTH_SHORT).show();
        }

        if (intent!=null) {
            studentEntity = (StudentsEntity) intent.getSerializableExtra("feedItem");
            if (studentEntity != null) {
                Edit_addBook.setText(studentEntity.getBookTitle());
                Edit_isbnNum.setText(studentEntity.getISBN_NUM());
                Edit_PublishYear.setText(studentEntity.getPublishYear());
                Edit_enterDescription.setText(studentEntity.getBookDescription());
                Edit_BookPrice.setText(studentEntity.getBookPrice());
                Config.BookID=studentEntity.getBookID();
                Config.Availability=studentEntity.getAvailability();
            }
        }

        final ArrayList<String> Status = new ArrayList<String>();
        Status.add(getResources().getString(R.string._new));
        Status.add(getResources().getString(R.string.Intermediate));
        Status.add(getResources().getString(R.string.not_bad));

        final ArrayList<String> Purpose= new ArrayList<String>();
        Purpose.add(getResources().getString(R.string.sale));
        Purpose.add(getResources().getString(R.string.exchange));
        Purpose.add(getResources().getString(R.string.gift));
        //Tags Spinner
        CustomSpinnerAdapter customStatusSpinnerAdapter= new CustomSpinnerAdapter(getApplicationContext(), Status);
        BookStatus_spinner.setAdapter(customStatusSpinnerAdapter);

        CustomSpinnerAdapter customPurposeSpinnerAdapter= new CustomSpinnerAdapter(getApplicationContext(), Purpose);
        Purpose_spinner.setAdapter(customPurposeSpinnerAdapter);

        BookStatus_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                BookStatus=Status.get(position).toString();
                Config.BookStatus=BookStatus;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Purpose_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TransactionType=Purpose.get(position).toString();
                Config.TransactionType=TransactionType;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Auth_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Config.AuthorTitle=Config.AuthList.get(position).getAuthorTitle();
                Config.AuthorID= Config.AuthList.get(position).getAuthorID();
                Config.AuthPosition= position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        Faculty_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Config.FacultyName = Config.FacultiesList.get(position).getDepartmentName();
                Config.FacultyID= Config.FacultiesList.get(position).getDepartmentID();
                Config.FacultyPosition=position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Edit_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeVolleyRequests = new MakeVolleyRequests(getApplicationContext(), EditBookActivity.this);
                BookName = Edit_addBook.getText().toString();
                Config.BookName = BookName;
                ISBN_Num = Edit_isbnNum.getText().toString();
                Config.ISBN_Number = ISBN_Num;
                PublishYear = Edit_PublishYear.getText().toString();
                Config.BookDescription = Edit_enterDescription.getText().toString();
                Config.PublishYear = PublishYear;
                ImageUri = Config.ImageFileUri;
                if (TransactionType != null) {
                    if (TransactionType.equals(getResources().getString(R.string.sale))) {
                        sentTransactionType = getResources().getString(R.string.sale_book);
                    } else if (TransactionType.equals(getResources().getString(R.string.exchange))) {
                        sentTransactionType = getResources().getString(R.string.exchange_book);
                    } else if (TransactionType.equals(getResources().getString(R.string.gift))) {
                        sentTransactionType = getResources().getString(R.string.gift_book);
                    }
                }
                if (Config.BookStatus != null) {
                    if (Config.BookStatus.equals(getResources().getString(R.string._new))) {
                        sentBookStatus = getResources().getString(R.string.new_book);
                    } else if (Config.BookStatus.equals(getResources().getString(R.string.Intermediate))) {
                        sentBookStatus = getResources().getString(R.string.intermediate_book);
                    } else if (Config.BookStatus.equals(getResources().getString(R.string.not_bad))) {
                        sentBookStatus = getResources().getString(R.string.not_bad_book);
                    }
                }
                BookPrice = Edit_BookPrice.getText().toString();
                if (Config.BookName != null && Config.BookName.length() > 0) {
                    if (Config.BookDescription != null && Config.BookDescription.length() > 0) {
                        if (Config.PublishYear != null && Config.PublishYear.length() > 0) {
                            if (Config.FacultyID != null) {
                                if (user != null) {
                                    ApiToken = user.get(SessionManagement.KEY_idToken);
                                    if (ApiToken != null) {
                                        if (Config.ISBN_Number != null && Config.ISBN_Number.length() > 0) {
                                            if (Config.AuthorID != null && Config.AuthorID.length() > 0) {
                                                if (Config.AuthorTitle != null && Config.AuthorTitle.length() > 0) {
                                                    if (Config.BookID != null) {
                                                        if (sentBookStatus!= null) {
                                                            if (Config.Availability != null) {
                                                                if (sentTransactionType != null) {
                                                                    if (BookPrice != null) {
//                                        if (Config.ImageFileUri!=null&&Config.ImageFileUri.toString().length()>0){
//                                        }else{
//                                            Toast.makeText(getActivity(), getString(R.string.enter_image), Toast.LENGTH_SHORT).show();
//                                        }
                                                                        makeVolleyRequests.updateBookDetails(Config.BookName, Config.BookDescription, Config.AuthorID, Config.PublishYear,
                                                                                Config.FacultyID, Config.ISBN_Number, ApiToken, Config.BookID, sentBookStatus, Config.Availability,
                                                                                sentTransactionType, BookPrice, Config.AuthorTitle);
                                                                    } else {
                                                                        Toast.makeText(getApplicationContext(), getString(R.string.enter_price), Toast.LENGTH_SHORT).show();
                                                                    }
                                                                } else {
                                                                    Toast.makeText(getApplicationContext(), getString(R.string.transaction_type_null), Toast.LENGTH_SHORT).show();
                                                                }
                                                            } else {
                                                                Toast.makeText(getApplicationContext(), getString(R.string.book_availability_null), Toast.LENGTH_SHORT).show();
                                                            }
                                                        } else {
                                                            Toast.makeText(getApplicationContext(), getString(R.string.book_status_null), Toast.LENGTH_SHORT).show();
                                                        }
                                                    } else {
                                                        Toast.makeText(getApplicationContext(), getString(R.string.null_book_id), Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    Toast.makeText(getApplicationContext(), getString(R.string.hint_author_name), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        } else {
                                            Toast.makeText(getApplicationContext(), getString(R.string.enter_isbn_num), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), getString(R.string.enter_faculty_id), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), getString(R.string.enter_publish_year), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.enter_desc), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.enter_book_name), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void PopulateExistingAuthorsList(ArrayList<StudentsEntity> AuthorssList, int auth_Position) {
        AuthorsSpinnerAdapter authorsSpinnerAdapter= new AuthorsSpinnerAdapter(getApplicationContext(), AuthorssList);
        Auth_spinner.setAdapter(authorsSpinnerAdapter);
        Auth_spinner.setSelection(auth_Position);
    }

    private void PopulateExistingFacultiesList(ArrayList<StudentsEntity> result, int position) {
        FacultiesSpinnerAdapter customSpinnerAdapterFaculties = new FacultiesSpinnerAdapter(getApplicationContext(), result);
        Faculty_spinner.setAdapter(customSpinnerAdapterFaculties);
        Faculty_spinner.setSelection(position);
    }

    @Override
    public void onAuthorsRetrievalApiTaskCompleted(ArrayList<StudentsEntity> result) {
        if (result.size() > 0) {
            Config.AuthList=result;
            PopulateExistingAuthorsList(result, 0);
        }
    }

    @Override
    public void onFacultiesRetrievalApiTaskCompleted(ArrayList<StudentsEntity> result) {
        if (result.size() > 0) {
            Config.FacultiesList=result;
            PopulateExistingFacultiesList(result, 0);
        }
    }

    @Override
    public void onComplete(ArrayList<StudentsEntity> studentsEntities) {
        if (studentsEntities!=null){
            if (studentsEntities.size()>0){
                for (StudentsEntity studentsEntity:studentsEntities){
                    if (studentsEntity.getException()!=null){
                        Toast.makeText(getApplicationContext(), studentsEntity.getException().toString(),Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(), studentsEntity.getServerMessage().toString(),Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(intent);
                    }
                }
            }
        }
    }
}