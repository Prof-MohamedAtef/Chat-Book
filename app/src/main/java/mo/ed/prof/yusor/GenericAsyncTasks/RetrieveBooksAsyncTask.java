package mo.ed.prof.yusor.GenericAsyncTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.helpers.Room.StudentsEntity;

/**
 * Created by Prof-Mohamed Atef on 2/6/2019.
 */

public class RetrieveBooksAsyncTask extends AsyncTask<String, Void, ArrayList<StudentsEntity>> {

    private final String LOG_TAG = RetrieveBooksAsyncTask.class.getSimpleName();
    public JSONObject BooksJson;
    public JSONArray BooksDataArray;
    public JSONObject oneBookData;
    private ProgressDialog dialog;
    public RetrieveBooksAsyncTask retrieveBooksAsyncTask;
    private ArrayList<StudentsEntity> list = new ArrayList<StudentsEntity>();
    OnBooksRetrievalTaskCompleted onBooksRetrievalTaskCompleted;
    Context mContext;

    public RetrieveBooksAsyncTask(OnBooksRetrievalTaskCompleted onBooksRetrievalTaskCompleted,Context context){
        this.onBooksRetrievalTaskCompleted=onBooksRetrievalTaskCompleted;
        dialog = new ProgressDialog(context);
        this.mContext=context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        try{
            if (dialog!=null&&dialog.isShowing()){
                this.dialog.dismiss();
            }else {
                this.dialog.setMessage(mContext.getResources().getString(R.string.loading));
                this.dialog.show();
            }
        }catch (Exception e){
            Log.v(LOG_TAG, "Problem in ProgressDialogue" );
        }
    }

    @Override
    protected void onPostExecute(ArrayList<StudentsEntity> result) {
        super.onPostExecute(result);
        if (result != null) {
            if (onBooksRetrievalTaskCompleted!=null){
                onBooksRetrievalTaskCompleted.onBooksRetrievalApiTaskCompleted(result);
            }else if (onBooksRetrievalTaskCompleted!=null){
                onBooksRetrievalTaskCompleted.onBooksRetrievalApiTaskCompleted(result);
            }
            if (dialog.isShowing()){
                dialog.dismiss();
            }
        }

    }

    @Override
    protected ArrayList<StudentsEntity> doInBackground(String... strings) {
        return null;
    }

    public interface OnBooksRetrievalTaskCompleted{
        void onBooksRetrievalApiTaskCompleted(ArrayList<StudentsEntity> result);
    }
}