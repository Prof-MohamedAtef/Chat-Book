package mo.ed.prof.yusor.GenericAsyncTasks;

import android.app.Activity;
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
 * Created by Prof-Mohamed Atef on 3/12/2019.
 */

public class RetrieveDepartmentsAsyncTask extends AsyncTask<String, Void, ArrayList<StudentsEntity>> {

    private final String LOG_TAG = RetrieveDepartmentsAsyncTask.class.getSimpleName();
    public JSONObject DepartmentsJson;
    public JSONArray DepartmentsDataArray;
    public JSONObject oneDepartmentData;
    private ProgressDialog dialog;
    public RetrieveDepartmentsAsyncTask retrieveDepartmentsAsyncTask;
    private ArrayList<StudentsEntity> list = new ArrayList<StudentsEntity>();
    RetrieveDepartmentsAsyncTask.OnDepartmentsRetrievalTaskCompleted onDepartmentsRetrievalTaskCompleted;
    Context mContext;
    private String ID_STR;
    private String NAME_STR;
    private StudentsEntity studentsEntity;
    private String ID_KEY="id";
    private String Name_KEY="name";
    private Activity activity;

    public RetrieveDepartmentsAsyncTask(RetrieveDepartmentsAsyncTask.OnDepartmentsRetrievalTaskCompleted onDepartmentsRetrievalTaskCompleted, Context context) {
        this.onDepartmentsRetrievalTaskCompleted = onDepartmentsRetrievalTaskCompleted;
        dialog = new ProgressDialog(context);
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        try {
            dialog.setOwnerActivity((Activity) mContext);
            activity = dialog.getOwnerActivity();
            if (dialog != null && dialog.isShowing()) {
                this.dialog.dismiss();
            } else {
                if (dialog != null) {
                    dialog.dismiss();
                    this.dialog.setMessage(mContext.getResources().getString(R.string.loading));
                    if (!activity.isFinishing()) {
                        this.dialog.show();
                    }
                } else {
                    this.dialog.setMessage(mContext.getResources().getString(R.string.loading));
                    if (!activity.isFinishing()) {
                        this.dialog.show();
                    }
                }
            }
        } catch (Exception e) {
            Log.v(LOG_TAG, "Problem in ProgressDialogue");
        }
    }

    @Override
    protected void onPostExecute(ArrayList<StudentsEntity> result) {
        super.onPostExecute(result);
        if (result != null) {
            if (onDepartmentsRetrievalTaskCompleted != null) {
                onDepartmentsRetrievalTaskCompleted.onDepartmentsRetrievalApiTaskCompleted(result);
            }
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
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
            return getDepartmentsJson(Articles_JsonSTR );
        } catch (JSONException e) {
            Log.e(LOG_TAG, "didn't got Articles Data from getJsonData method", e);
            e.printStackTrace();
        }
        return null;
/*
        CertificateFactory cf = null;

        try {
            cf = CertificateFactory.getInstance("X.509");
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        InputStream caInput=null;
        InputStream is = null;
        InputStream inputStream = null;
//            is=getClass().getResourceAsStream("/raw/certificate.crt");

        InputStream path=null;
        try {
            path=mContext.getResources().openRawResource(R.raw.fla4newscom);
        }catch (Exception e){
            path=null;
        }


        if (path==null){
            path=null;
        }else {
            caInput = new BufferedInputStream(path);
        }
        Certificate ca = null;
        try {
            try {
                ca = cf.generateCertificate(caInput);
            } catch (CertificateException e) {
                e.printStackTrace();
            }
            Log.v(LOG_TAG, "my Certificate Authority= " + ((X509Certificate) ca).getSubjectDN());
        } finally {
            try {
                caInput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

// Create a KeyStore containing our trusted CAs
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = null;
        try {
            keyStore = KeyStore.getInstance(keyStoreType);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        try {
            keyStore.load(null, null);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        try {
            keyStore.setCertificateEntry("ca", ca);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }


// Create a TrustManager that trusts the CAs in our KeyStore
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = null;
        try {
            tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            tmf.init(keyStore);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

// Create an SSLContext that uses our TrustManager
        SSLContext context = null;
        try {
            context = SSLContext.getInstance("TLS");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            context.init(null, tmf.getTrustManagers(), null);
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

// Tell the URLConnection to use a SocketFactory from our SSLContext

        URL url = null;
        BufferedReader reader = null;
        try {
            url = new URL(strings[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();

        }
        if (strings.length == 0) {
            return null;
        }
        HttpsURLConnection urlConnection = null;
//        try {
//            urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection.setSSLSocketFactory(context.getSocketFactory());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try {
//            urlConnection=(HttpsURLConnection)url.openConnection();
            urlConnection=new HttpsURLConnection(url) {
                @Override
                public String getCipherSuite() {
                    return null;
                }

                @Override
                public Certificate[] getLocalCertificates() {
                    return new Certificate[0];
                }

                @Override
                public Certificate[] getServerCertificates() throws SSLPeerUnverifiedException {
                    return new Certificate[0];
                }

                @Override
                public void disconnect() {

                }

                @Override
                public boolean usingProxy() {
                    return false;
                }

                @Override
                public void connect() throws IOException {

                }
            };
            urlConnection.connect();
            urlConnection.setSSLSocketFactory(context.getSocketFactory());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
//            copyInputStreamToOutputStream(in, System.out);
            /*
            End of SSL
             */


//        String UsersDesires_JsonSTR = null;

//            HttpURLConnection urlConnection = null;

        /*try {
//                URL url = new URL(params[0]);
//                urlConnection = (HttpURLConnection) url.openConnection();


            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                UsersDesires_JsonSTR = null;
            }else {
                reader = new BufferedReader(new InputStreamReader(inputStream));
            }
            String line;
            if (reader!=null){
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
            }
            if (buffer.length() == 0) {
                return null;
            }
            UsersDesires_JsonSTR = buffer.toString();
            Log.v(LOG_TAG, "Articles String: " + UsersDesires_JsonSTR);
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
            return getDepartmentsJson(UsersDesires_JsonSTR);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "didn't got Users Desires from getJsonData method", e);
            e.printStackTrace();
        }
        return null;*/
    }

    private ArrayList<StudentsEntity> getDepartmentsJson(String usersDesires_jsonSTR) throws JSONException {
        DepartmentsJson = new JSONObject(usersDesires_jsonSTR);
        DepartmentsDataArray= DepartmentsJson.getJSONArray("data");
        list.clear();
        for (int i = 0; i < DepartmentsDataArray.length(); i++) {
            try {
                oneDepartmentData = DepartmentsDataArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ID_STR= oneDepartmentData.getString(ID_KEY);
            NAME_STR= oneDepartmentData.getString(Name_KEY);


            if (ID_STR==null){
                ID_STR="";
            }
            if (NAME_STR==null){
                NAME_STR="";
            }

            studentsEntity = new StudentsEntity(ID_STR, NAME_STR);
            list.add(studentsEntity);
        }
        return list;
    }

    public interface OnDepartmentsRetrievalTaskCompleted {
        void onDepartmentsRetrievalApiTaskCompleted(ArrayList<StudentsEntity> result);
    }
}