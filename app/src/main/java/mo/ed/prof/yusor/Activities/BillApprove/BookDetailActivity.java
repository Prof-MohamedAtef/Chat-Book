package mo.ed.prof.yusor.Activities.BillApprove;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dd.processbutton.iml.GenerateProcessButton;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import mo.ed.prof.yusor.Activities.Authentication.TaibahRegistrationActivity;
import mo.ed.prof.yusor.Dev.MessageActivity;
import mo.ed.prof.yusor.Network.VerifyConnection;
import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.helpers.Config;
import mo.ed.prof.yusor.helpers.Designsers.ProgressGenerator;
import mo.ed.prof.yusor.helpers.Room.StudentsEntity;
import mo.ed.prof.yusor.helpers.SessionManagement;

public class BookDetailActivity extends AppCompatActivity {

    Intent intent;
    private StudentsEntity studentsEntity;
    private String BookName;
    private String BookPrice;
    private String SellerName;
    private String BookDescription;
    private String PublishYear;
    private String ISBN;
    private String AuthorTitle;
    private String TransactionType;
    private String SellerEmail;
    private String SellerGender;
    private String SellerFaculty;
    private String BookStatusID;
    private String BookStatus;
    private String BookAvailability;
    private String BookAvailabilityID;
    private String TransactionTypeID;
    private String BookPhoto;
    private String FirebaseSellerAccount;

    @BindView(R.id.book_name)
    TextView txt_bookName;

    @BindView(R.id.author_name)
    TextView txt_authorName;

    @BindView(R.id.publish_year)
    TextView txt_publishYear;

    @BindView(R.id.num_isbn)
    TextView txt_Isbn_Num;

    @BindView(R.id.desc)
    TextView txt_desc;

    @BindView(R.id.available_in_store)
    TextView txt_Available_txt;

    @BindView(R.id.book_status)
    TextView txt_Book_Status;

    @BindView(R.id.transaction_type)
    TextView txt_TransactionType_txt;

    @BindView(R.id.seller_name)
    TextView txt_SellerName_txt;

    @BindView(R.id.seller_email)
    TextView txt_SellerEmail_txt;

    @BindView(R.id.seller_gender)
    TextView txt_SellerGender_txt;

    @BindView(R.id.seller_faculty)
    TextView txt_SellerFaculty_txt;

    @BindView(R.id.start_chat_btn)
    Button startChat_btn;

    @BindView(R.id.create_bill)
    GenerateProcessButton create_bill;

    @BindView(R.id.price)
    TextView txt_price;

    @BindView(R.id.book_photo)
    ImageView book_photo;
    private VerifyConnection verifyConn;
    private ProgressGenerator progressGenerator;
    private String BookSellerFBUi;
    private SessionManagement sessionManagement;
    private HashMap<String, String> user;
    private String LoggedEmail;
    private String LoggedUserName;
    private String LoggedProfilePic;
    private String loggedFirebaseUserID;
    private String ApiToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        setTheme(R.style.ArishTheme);
        ButterKnife.bind(this);

        intent=getIntent();

        sessionManagement=new SessionManagement(getApplicationContext());
        user=sessionManagement.getUserDetails();
        if (user!=null) {
            LoggedEmail = user.get(SessionManagement.KEY_EMAIL);
            LoggedUserName = user.get(SessionManagement.KEY_NAME);
            LoggedProfilePic = user.get(SessionManagement.KEY_Profile_Pic);
            loggedFirebaseUserID = user.get(SessionManagement.firebase_UID_KEY);
            ApiToken = user.get(SessionManagement.KEY_idToken);
        }
        if (intent!=null){
            studentsEntity= (StudentsEntity) intent.getExtras().getSerializable("feedItem");
            BookName=studentsEntity.getBookTitle();
            BookPrice=studentsEntity.getBookPrice();
            BookDescription=studentsEntity.getBookDescription();
            PublishYear=studentsEntity.getPublishYear();
            ISBN=studentsEntity.getISBN_NUM();
            AuthorTitle=studentsEntity.getAuthorTitle();
            TransactionTypeID=studentsEntity.getTransactionType();
            BookPhoto=studentsEntity.getBookImage();
            BookStatusID =studentsEntity.getBookStatus();
            BookAvailabilityID=studentsEntity.getAvailability();
            BookSellerFBUi=studentsEntity.getFirebaseUiD();
            if (BookStatusID != null) {
                if (BookStatusID.equals(getResources().getString(R.string.new_book))) {
                    BookStatus = getResources().getString(R.string._new);
                } else if (BookStatusID.equals(getResources().getString(R.string.intermediate_book))) {
                    BookStatus = getResources().getString(R.string.Intermediate);
                } else if (BookStatusID.equals(getResources().getString(R.string.not_bad_book))) {
                    BookStatus = getResources().getString(R.string.not_bad);
                }
            }
            if (TransactionTypeID != null) {
                if (TransactionTypeID.equals(getResources().getString(R.string.sale_book))) {
                    TransactionType = getResources().getString(R.string.sale);
                } else if (TransactionTypeID.equals(getResources().getString(R.string.exchange_book))) {
                    TransactionType = getResources().getString(R.string.exchange);
                } else if (TransactionTypeID.equals(getResources().getString(R.string.gift_book))) {
                    TransactionType = getResources().getString(R.string.gift);
                }
            }
            if (BookAvailabilityID.equals("1")){
                BookAvailability="Available";
            }

            // seller information

            SellerName=studentsEntity.getSellerUserName();
            SellerEmail=studentsEntity.getSellerEmail();
            SellerGender=studentsEntity.getSellerGender();
            SellerFaculty=studentsEntity.getDepartmentName();
            FirebaseSellerAccount=studentsEntity.getFirebaseUiD();


            if (BookSellerFBUi.equals(loggedFirebaseUserID)){
                // seller
                if (!BookAvailabilityID.equals("1")){
                    create_bill.setText("pending Approval");
                    create_bill.setEnabled(false);
                }else {
                    create_bill.setVisibility(View.VISIBLE);
                    create_bill.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            intent =new Intent(getApplicationContext(),BillsActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            Bundle bundle=new Bundle();
                            bundle.putSerializable("feedItem",studentsEntity);
                            intent.putExtras(bundle);
                            getApplicationContext().startActivity(intent);
                        }
                    });
                }
            }else {
                // buyer
                startChat_btn.setVisibility(View.VISIBLE);
                startChat_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent=new Intent(getApplicationContext(),MessageActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("userid",studentsEntity.getFirebaseUiD());
                        getApplicationContext().startActivity(intent);
                    }
                });
            }

            txt_bookName.setText(BookName);
            txt_authorName.setText(AuthorTitle);
            txt_publishYear.setText(PublishYear);
            txt_Isbn_Num.setText(ISBN);
            txt_Available_txt.setText(BookAvailability);
            txt_Book_Status.setText(BookStatus);
            txt_TransactionType_txt.setText(TransactionType);
            txt_SellerName_txt.setText(SellerName);
            txt_SellerEmail_txt.setText(SellerEmail);
            txt_SellerGender_txt.setText(SellerGender);
            txt_SellerFaculty_txt.setText(SellerFaculty);
            txt_desc.setText(BookDescription);
            txt_price.setText(BookPrice+ " ");

            Picasso.with(getApplicationContext()).load(BookPhoto)
                    .error(R.drawable.logo)
                    .into(book_photo);
        }
    }
}