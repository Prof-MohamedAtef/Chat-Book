package mo.ed.prof.yusor.GenericAsyncTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.helpers.Room.StudentsEntity;

/**
 * Created by Prof-Mohamed Atef on 2/6/2019.
 */

public class RetrieveBooksAsyncTask extends AsyncTask<String, Void, ArrayList<StudentsEntity>> {

    private final String LOG_TAG = RetrieveBooksAsyncTask.class.getSimpleName();
    public JSONArray BooksDataArray;
    public JSONObject oneBookData;
    private ProgressDialog dialog;
    public RetrieveBooksAsyncTask retrieveBooksAsyncTask;
    private ArrayList<StudentsEntity> list = new ArrayList<StudentsEntity>();
    OnBooksRetrievalTaskCompleted onBooksRetrievalTaskCompleted;
    Context mContext;
    private JSONObject BooksJson;
    private String ID_KEY="id";
    private String BookName_KEY="title";
    private String ID_STR;
    private String BookNAME_STR;
    private StudentsEntity studentsEntity;

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
    protected ArrayList<StudentsEntity> doInBackground(String... strings) {
        String Articles_JsonSTR = null;

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        if (strings.length == 0) {
            return null;
        }

        try {

            URL url = new URL(strings[0]);
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("POST");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                Articles_JsonSTR  = null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                return null;
            }

            Articles_JsonSTR = buffer.toString();
            Log.v(LOG_TAG, "Articles JSON String: " + Articles_JsonSTR );
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error here Exactly ", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        try {
            return getBookssJson(Articles_JsonSTR );
        } catch (JSONException e) {
            Log.e(LOG_TAG, "didn't got Articles Data from getJsonData method", e);
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<StudentsEntity> getBookssJson(String usersDesires_jsonSTR) throws JSONException {
        BooksJson = new JSONObject(usersDesires_jsonSTR);
        BooksDataArray= BooksJson.getJSONArray("data");
        list.clear();
        for (int i = 0; i < BooksDataArray.length(); i++) {
            try {
                oneBookData = BooksDataArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ID_STR= oneBookData.getString(ID_KEY);
            BookNAME_STR= oneBookData.getString(BookName_KEY);


            if (ID_STR==null){
                ID_STR="";
            }
            if (BookNAME_STR==null){
                BookNAME_STR="";
            }

            studentsEntity = new StudentsEntity(ID_STR, BookNAME_STR, "");
            list.add(studentsEntity);
        }
        return list;
    }

    @Override
    protected void onPostExecute(ArrayList<StudentsEntity> result) {
        super.onPostExecute(result);
        if (result != null) {
            if (onBooksRetrievalTaskCompleted!=null){
                onBooksRetrievalTaskCompleted.onBooksRetrievalApiTaskCompleted(result);
            }
            if (dialog.isShowing()){
                dialog.dismiss();
            }
        }

    }



    public interface OnBooksRetrievalTaskCompleted{
        void onBooksRetrievalApiTaskCompleted(ArrayList<StudentsEntity> result);
    }
}