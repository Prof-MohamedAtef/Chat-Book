package mo.ed.prof.yusor.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import butterknife.ButterKnife;
import mo.ed.prof.yusor.Fragments.SelectBookFragmentIFExist;
import mo.ed.prof.yusor.R;

public class AddBookActivity extends AppCompatActivity implements SelectBookFragmentIFExist.OnNewBookAddition {


    SelectBookFragmentIFExist selectBookFragmentIFExist;
    private String SelectBookFragIfExist_KEY ="SelectBookFragIfExist_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        ButterKnife.bind(this);


        selectBookFragmentIFExist=new SelectBookFragmentIFExist();

        if (savedInstanceState==null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_frame_existence, selectBookFragmentIFExist, SelectBookFragIfExist_KEY)
                    .commit();
        }else {
            SelectBookFragmentIFExist selectBookFragmentIFExist= (SelectBookFragmentIFExist)getSupportFragmentManager().findFragmentByTag(SelectBookFragIfExist_KEY);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    public void onNewBookAdditionNeeded() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_frame_existence2, selectBookFragmentIFExist, SelectBookFragIfExist_KEY)
                .commit();
    }
}