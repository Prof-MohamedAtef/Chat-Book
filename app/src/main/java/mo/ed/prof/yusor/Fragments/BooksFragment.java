package mo.ed.prof.yusor.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by Prof-Mohamed Atef on 3/21/2019.
 */

public class BooksFragment extends Fragment{
    private static final String ARG_SECTION_NUMBER = "section_number";

    public static BooksFragment newInstance(int sectionNum) {
        BooksFragment _booksFragment =new BooksFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNum);
        _booksFragment.setArguments(args);
        return _booksFragment;
    }
}
