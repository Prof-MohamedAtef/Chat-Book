package mo.ed.prof.yusor.Activities.Book;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import butterknife.ButterKnife;
import mo.ed.prof.yusor.Fragments.FragmentNewBookDetails;
import mo.ed.prof.yusor.Fragments.SelectBookFragmentIFExist;
import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.helpers.Config;

public class AddBookActivity extends AppCompatActivity implements SelectBookFragmentIFExist.OnNewBookAddition,
        FragmentNewBookDetails.OnNextDetailsRequired,
        SelectBookFragmentIFExist.OnExistingBookDetailsRequired{


    SelectBookFragmentIFExist selectBookFragmentIFExist;
    private String SelectBookFragIfExist_KEY ="SelectBookFragIfExist_KEY";
    private FragmentNewBookDetails newBookDetailsFragment;
    private String NewBookDetialsFrag_KEY="NewBookDetialsFrag_KEY";
    public static String BookID_KEY="BookID_KEY";
    public static String BookTitle_KEY="BookTitle_KEY";
    public static String AUTHOR_NAME_KEY="AUTHOR_NAME_KEY";
    public static String ISBN_KEY="ISBN_KEY";
    public static String FacultyName_KEY="FacultyName_KEY";
    public static String PublishYear_KEY="PublishYear_KEY";
    public static String ImageUri_KEY="ImageUri_KEY";
    public static String NextNewBook="NextNewBook";
    public static String ExistingBook="ExistingBook";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        ButterKnife.bind(this);


        selectBookFragmentIFExist=new SelectBookFragmentIFExist();
        newBookDetailsFragment=new FragmentNewBookDetails();

        if (savedInstanceState==null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_frame_existence, selectBookFragmentIFExist, SelectBookFragIfExist_KEY)
                    .commit();
        }else {
            SelectBookFragmentIFExist selectBookFragmentIFExist= (SelectBookFragmentIFExist)getSupportFragmentManager().findFragmentByTag(SelectBookFragIfExist_KEY);
            FragmentNewBookDetails fragmentNewBookDetails= (FragmentNewBookDetails)getSupportFragmentManager().findFragmentByTag(NewBookDetialsFrag_KEY);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onNewBookAdditionNeeded(String bookID, String bookTitle) {
        Bundle bundle=new Bundle();
        bundle.putString(BookID_KEY,bookID);
        bundle.putString(BookTitle_KEY,bookTitle);
        newBookDetailsFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_frame_existence2, newBookDetailsFragment, NewBookDetialsFrag_KEY)
                .commit();
    }

    @Override
    public void onNextNewBookNameDetailsNeeded(String BookName, String Book_ID) {
        Intent intent = new Intent(this, CompleteAddBookActivity.class)
                .putExtra(BookTitle_KEY, BookName)
                .putExtra(BookID_KEY, Book_ID);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
        Config.BookExistence=NextNewBook;
    }

    @Override
    public void onExistingBookDetailsRequired(String bookID, String bookTitle) {
        Intent intent = new Intent(this, CompleteAddBookActivity.class)
                .putExtra(BookTitle_KEY, bookTitle)
                .putExtra(BookID_KEY, bookID);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
        Config.BookExistence= ExistingBook;
    }
}