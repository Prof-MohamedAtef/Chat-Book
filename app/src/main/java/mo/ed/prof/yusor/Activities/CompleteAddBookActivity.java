package mo.ed.prof.yusor.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import mo.ed.prof.yusor.Fragments.FragmentPriecsSuggestions;
import mo.ed.prof.yusor.Fragments.SelectBookFragmentIFExist;
import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.helpers.Config;

import static mo.ed.prof.yusor.Activities.AddBookActivity.AUTHOR_NAME_KEY;
import static mo.ed.prof.yusor.Activities.AddBookActivity.BookID_KEY;
import static mo.ed.prof.yusor.Activities.AddBookActivity.BookTitle_KEY;
import static mo.ed.prof.yusor.Activities.AddBookActivity.ExistingBook;
import static mo.ed.prof.yusor.Activities.AddBookActivity.FacultyName_KEY;
import static mo.ed.prof.yusor.Activities.AddBookActivity.ISBN_KEY;
import static mo.ed.prof.yusor.Activities.AddBookActivity.ImageUri_KEY;
import static mo.ed.prof.yusor.Activities.AddBookActivity.NextNewBook;
import static mo.ed.prof.yusor.Activities.AddBookActivity.PublishYear_KEY;

public class CompleteAddBookActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_add_book);
        bundle=new Bundle();
        fragmentPriecsSuggestions=new FragmentPriecsSuggestions();
        if (Config.BookExistence.equals(NextNewBook)){
            PublishYear= getIntent().getExtras().getString(PublishYear_KEY);
            ImageUri= getIntent().getExtras().getString(ImageUri_KEY);
            FacultyName= getIntent().getExtras().getString(FacultyName_KEY);
            ISBN= getIntent().getExtras().getString(ISBN_KEY);
            AuthorName= getIntent().getExtras().getString(AUTHOR_NAME_KEY);
            BookTitle= getIntent().getExtras().getString(BookTitle_KEY);
            bundle.putString(PublishYear_KEY, PublishYear);
            bundle.putString(ImageUri_KEY, ImageUri);
            bundle.putString(FacultyName_KEY, FacultyName);
            bundle.putString(ISBN_KEY, ISBN);
            bundle.putString(AUTHOR_NAME_KEY, AuthorName);
            bundle.putString(BookTitle_KEY, BookTitle);
            NavigatToFragments(bundle);
        }else if (Config.BookExistence.equals(ExistingBook)){
            BookTitle= getIntent().getExtras().getString(BookTitle_KEY);
            BookID= getIntent().getExtras().getString(BookID_KEY);
            bundle.putString(BookTitle_KEY,BookTitle);
            bundle.putString(BookID_KEY,BookID);
            NavigatToFragments(bundle);
        }
        NavigatToFragments(BookTitle);
//        frame_prices_suggestions
    }

    private void NavigatToFragments(String bookTitle) {
        bundle.putString(BOOK_NAME,bookTitle);
        fragmentPriecsSuggestions.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_prices_suggestions, fragmentPriecsSuggestions, FragsSuggest_KEY)
                .commit();
    }

    private void NavigatToFragments(Bundle bundle) {
//        fragmentSoundPlayer.setArguments(bundle);
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.Audio_container, fragmentSoundPlayer, Frags_KEY)
//                .commit();
    }
}