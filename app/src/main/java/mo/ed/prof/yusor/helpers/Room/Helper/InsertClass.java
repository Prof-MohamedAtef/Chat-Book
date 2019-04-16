package mo.ed.prof.yusor.helpers.Room.Helper;

import java.util.ArrayList;

import mo.ed.prof.yusor.Volley.MakeVolleyRequests;
import mo.ed.prof.yusor.helpers.Room.AppDatabase;
import mo.ed.prof.yusor.helpers.Room.Helper.AsyncTasks.InsertBooksServiceAsyncTask;
import mo.ed.prof.yusor.helpers.Room.StudentsEntity;

/**
 * Created by Prof-Mohamed Atef on 4/15/2019.
 */

public class InsertClass {

    private StudentsEntity articleEntity;

    public InsertClass(){
        articleEntity=new StudentsEntity();
    }

    public void TryInsertGalleryBooksData(AppDatabase mDatabase, ArrayList<StudentsEntity> studentsEntities, MakeVolleyRequests.OnCompleteListener mListener) {
        InsertBooksServiceAsyncTask insertBooksServiceAsyncTask =new InsertBooksServiceAsyncTask(mDatabase,studentsEntities,mListener);
        insertBooksServiceAsyncTask.execute();
    }


//    public void TryInsertNewsAPIData(AppDatabase mDatabase, ArrayList<StudentsEntity> list, NewsApiAsyncTask.OnNewsTaskCompleted onNewsTaskCompleted, String key) {
//        InsertBooksServiceAsyncTask insertWebServiceAsyncTask =new InsertBooksServiceAsyncTask(mDatabase,list,onNewsTaskCompleted,key);
//        insertWebServiceAsyncTask.execute();
//    }

//    public void TryInsertNewsAPIData(AppDatabase mDatabase, ArrayList<ArticlesEntity> list, UrgentAsyncTask.OnNewsUrgentTaskCompleted onNewsUrgentTaskCompleted, String key) {
//        InsertBooksServiceAsyncTask insertWebServiceAsyncTask =new InsertBooksServiceAsyncTask(mDatabase,list,onNewsUrgentTaskCompleted,key);
//        insertWebServiceAsyncTask.execute();
//    }
//
//    public void TryInsertFirebaseReportData(AppDatabase mDatabase, FirebaseDataHolder firebaseDataHolder, InsertLocallyFirebaseAsyncTask.OnFirebaseInsertedLocallyCompleted onFirebaseInsertedLocallyCompleted_, String key) {
//        InsertLocallyFirebaseAsyncTask insertLocallyFirebaseAsyncTask=new InsertLocallyFirebaseAsyncTask(mDatabase,firebaseDataHolder,onFirebaseInsertedLocallyCompleted_,key);
//        insertLocallyFirebaseAsyncTask.execute();
//    }
//
//    public void TryInsertFirebaseReportList(AppDatabase mDatabase, List<FirebaseDataHolder> firebaseDataHolderArrayList, InsertLocallyFirebaseAsyncTask.OnFirebaseInsertedLocallyCompleted onFirebaseInsertedLocallyCompleted_, String key) {
//        InsertLocallyFirebaseAsyncTask insertLocallyFirebaseAsyncTask=new InsertLocallyFirebaseAsyncTask(mDatabase,firebaseDataHolderArrayList,onFirebaseInsertedLocallyCompleted_,key);
//        insertLocallyFirebaseAsyncTask.execute();
//    }
}
