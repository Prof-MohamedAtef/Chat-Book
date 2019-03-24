package mo.ed.prof.yusor.Activities.AddBook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import mo.ed.prof.yusor.Activities.AddBook.CompleteAddBookActivity;
import mo.ed.prof.yusor.Fragments.FragmentNewBookDetails;
import mo.ed.prof.yusor.Fragments.SelectBookFragmentIFExist;
import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.helpers.Config;

public class AddNewBookActivity extends AppCompatActivity implements SelectBookFragmentIFExist.OnNewBookAddition,
        FragmentNewBookDetails.OnNextDetailsRequired,
        SelectBookFragmentIFExist.OnExistingBookDetailsRequired,
        FragmentNewBookDetails.OnBackButtonPressed{
//
//},
//        SelectBookFragmentIFExist.OnBookChangedValue{


    SelectBookFragmentIFExist selectBookFragmentIFExist;
    private String SelectBookFragIfExist_KEY = "SelectBookFragIfExist_KEY";
    private FragmentNewBookDetails newBookDetailsFragment;
    private String NewBookDetialsFrag_KEY = "NewBookDetialsFrag_KEY";
    public static String BookID_KEY = "BookID_KEY";
    public static String BookTitle_KEY = "BookTitle_KEY";
    public static String AUTHOR_NAME_KEY = "AUTHOR_NAME_KEY";
    public static String ISBN_KEY = "ISBN_KEY";
    public static String FacultyName_KEY = "FacultyName_KEY";
    public static String PublishYear_KEY = "PublishYear_KEY";
    public static String ImageUri_KEY = "ImageUri_KEY";
    public static String NextNewBook = "NextNewBook";
    public static String ExistingBook = "ExistingBook";

    @BindView(R.id.container_frame_existence)
    FrameLayout container_frame_existence;

    @BindView(R.id.container_frame_existence2)
    FrameLayout container_frame_existence2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_book);
        ButterKnife.bind(this);


        selectBookFragmentIFExist = new SelectBookFragmentIFExist();
        newBookDetailsFragment = new FragmentNewBookDetails();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_frame_existence, selectBookFragmentIFExist, SelectBookFragIfExist_KEY)
                    .commit();
        } else {
            SelectBookFragmentIFExist selectBookFragmentIFExist = (SelectBookFragmentIFExist) getSupportFragmentManager().findFragmentByTag(SelectBookFragIfExist_KEY);
            FragmentNewBookDetails fragmentNewBookDetails = (FragmentNewBookDetails) getSupportFragmentManager().findFragmentByTag(NewBookDetialsFrag_KEY);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onNewBookAdditionNeeded(String bookID, String bookTitle) {
//        Bundle bundle = new Bundle();
//        bundle.putString(BookID_KEY, bookID);
//        bundle.putString(BookTitle_KEY, bookTitle);
//        newBookDetailsFragment.setArguments(bundle);
        container_frame_existence.setVisibility(View.GONE);
        container_frame_existence2.setVisibility(View.VISIBLE);
        Config.BookName=null;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_frame_existence2, newBookDetailsFragment, NewBookDetialsFrag_KEY)
                .commit();
    }

    @Override
    public void onNextNewBookNameDetailsNeeded(String BookName, String book_id) {
        Intent intent = new Intent(this, CompleteAddBookActivity.class)
                .putExtra(BookTitle_KEY, BookName)
                .putExtra(BookID_KEY, book_id);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
        Config.BookExistence = ExistingBook;
    }

    @Override
    public void onExistingBookDetailsRequired(String bookID, String bookTitle) {
        Intent intent = new Intent(this, CompleteAddBookActivity.class)
                .putExtra(BookTitle_KEY, bookTitle)
                .putExtra(BookID_KEY, bookID);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
        Config.BookExistence = ExistingBook;
    }

    @Override
    public void OnBackButtonPressed() {
        container_frame_existence.setVisibility(View.VISIBLE);
        container_frame_existence2.setVisibility(View.GONE);
        Config.BookName=null;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_frame_existence, selectBookFragmentIFExist, SelectBookFragIfExist_KEY)
                .commit();

    }
//
//    @Override
//    public void OnBookChangedValue(String BookName, String BookID, int Position) {
//        Config.BookName=BookName;
//        Config.BookID=BookID;
//        Config.BookPosition=Position;
//    }
}
