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
 * Created by Prof-Mohamed Atef on 3/15/2019.
 */

public class RetrieveAuthorsAsyncTask extends AsyncTask<String, Void, ArrayList<StudentsEntity>> {

    private final String LOG_TAG = RetrieveBooksAsyncTask.class.getSimpleName();
    public JSONArray AuthorssDataArray;
    public JSONObject oneAuthorData;
    private ProgressDialog dialog;
    public RetrieveBooksAsyncTask retrieveBooksAsyncTask;
    private ArrayList<StudentsEntity> list = new ArrayList<StudentsEntity>();
    OnAuthorsRetrievalTaskCompleted onAuthorsRetrievalTaskCompleted;
    Context mContext;
    private JSONObject AuthorsJson;
    private String ID_KEY="id";
    private String AuthorName_KEY ="name";
    private String ID_STR;
    private String AuthorNAME_STR;
    private StudentsEntity studentsEntity;

    public RetrieveAuthorsAsyncTask(OnAuthorsRetrievalTaskCompleted onAuthorsRetrievalTaskCompleted, Context context){
        this.onAuthorsRetrievalTaskCompleted=onAuthorsRetrievalTaskCompleted;
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

            urlConnection.setRequestMethod("GET");
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
            return getAuthorsJson(Articles_JsonSTR );
        } catch (JSONException e) {
            Log.e(LOG_TAG, "didn't got Articles Data from getJsonData method", e);
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<StudentsEntity> getAuthorsJson(String usersDesires_jsonSTR) throws JSONException {
        AuthorsJson = new JSONObject(usersDesires_jsonSTR);
        AuthorssDataArray = AuthorsJson.getJSONArray("data");
        list.clear();
        for (int i = 0; i < AuthorssDataArray.length(); i++) {
            try {
                oneAuthorData = AuthorssDataArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ID_STR= oneAuthorData.getString(ID_KEY);
            AuthorNAME_STR = oneAuthorData.getString(AuthorName_KEY);


            if (ID_STR==null){
                ID_STR="";
            }
            if (AuthorNAME_STR ==null){
                AuthorNAME_STR ="";
            }

            studentsEntity = new StudentsEntity(ID_STR, AuthorNAME_STR,"","");
            list.add(studentsEntity);
        }
        return list;
    }

    @Override
    protected void onPostExecute(ArrayList<StudentsEntity> result) {
        super.onPostExecute(result);
        if (result != null) {
            if (onAuthorsRetrievalTaskCompleted!=null){
                onAuthorsRetrievalTaskCompleted.onAuthorsRetrievalApiTaskCompleted(result);
            }
            if (dialog.isShowing()){
                dialog.dismiss();
            }
        }

    }



    public interface OnAuthorsRetrievalTaskCompleted{
        void onAuthorsRetrievalApiTaskCompleted(ArrayList<StudentsEntity> result);
    }
}