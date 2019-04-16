package mo.ed.prof.yusor.helpers.Room.Helper.AsyncTasks;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;
import mo.ed.prof.yusor.Volley.MakeVolleyRequests;
import mo.ed.prof.yusor.helpers.Config;
import mo.ed.prof.yusor.helpers.Room.AppDatabase;
import mo.ed.prof.yusor.helpers.Room.StudentsEntity;

/**
 * Created by Prof-Mohamed Atef on 4/15/2019.
 */

public class InsertBooksServiceAsyncTask extends AsyncTask<Void, Void, Boolean> {
    private final AppDatabase mDatabase;
    private final ArrayList<StudentsEntity> list;
    private final MakeVolleyRequests.OnCompleteListener mListener;
    private LiveData<List<StudentsEntity>> booksRoomList;
    private String NULL_KEY="null";
    private ArrayList<StudentsEntity> ArticlesEntityList_x;

    public InsertBooksServiceAsyncTask(AppDatabase mDatabase, ArrayList<StudentsEntity> studentsEntities, MakeVolleyRequests.OnCompleteListener mListener) {
        this.mDatabase=mDatabase;
        this.list= studentsEntities;
        this.mListener=mListener;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        final boolean[] Inserted = {false};
        booksRoomList = mDatabase.booksDao().getAllBooksData();
        booksRoomList.observe((LifecycleOwner) mListener, existingArticles -> {
            DeleteInsertOperations(Inserted, existingArticles,mListener);
        });
        return Inserted[0];
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if (aBoolean != null) {
            if (mListener!=null) {
                mListener.onComplete(ArticlesEntityList_x);
            }
        }
    }

    private void DeleteInsertOperations(boolean[] inserted, List<StudentsEntity> existingArticles, MakeVolleyRequests.OnCompleteListener listener) {
        if (existingArticles!=null&&existingArticles.size()>0&&!existingArticles.isEmpty()){
            int deleted=mDatabase.booksDao().deleteAllBooksInGallery();
            if (deleted>0){
                inserted[0] =IsArrayInserted();
                if (inserted[0]){
                    inserted[0] =true;
                    booksRoomList.removeObservers((LifecycleOwner) listener);
                }else {
                    inserted[0] =false;
                }
            }else {
                inserted[0] =IsArrayInserted();
                if (inserted[0]){
                    inserted[0] =true;
                    booksRoomList.removeObservers((LifecycleOwner) listener);
                }else {
                    inserted[0] =false;
                }
            }
        }else {
            inserted[0] =IsArrayInserted();
            if (inserted[0]){
                inserted[0] =true;
                booksRoomList.removeObservers((LifecycleOwner) listener);
            }else {
                inserted[0] =false;
            }
        }
    }

    @NonNull
    private boolean IsArrayInserted() {
        long x = 0;
        StudentsEntity studentsEntity=new StudentsEntity();
        for (StudentsEntity OneStudentEntity: this.list){
            if (OneStudentEntity.getPivotID()!=null){
                studentsEntity.setPivotID(OneStudentEntity.getPivotID());
            }else {
                studentsEntity.setPivotID(NULL_KEY);
            }
            if (OneStudentEntity.getBookOwnerID()!=null){
                studentsEntity.setBookOwnerID(OneStudentEntity.getBookOwnerID());
            }else {
                studentsEntity.setBookOwnerID(NULL_KEY);
            }
            if (OneStudentEntity.getSellerID()!=null){
                studentsEntity.setSellerID(OneStudentEntity.getSellerID());
            }else {
                studentsEntity.setSellerID(NULL_KEY);
            }
            if (OneStudentEntity.getBookID()!=null){
                studentsEntity.setBookID(OneStudentEntity.getBookID());
            }else {
                studentsEntity.setBookID(NULL_KEY);
            }
            if (OneStudentEntity.getBookPrice()!=null){
                studentsEntity.setBookPrice(OneStudentEntity.getBookPrice());
            }else {
                studentsEntity.setBookPrice(NULL_KEY);
            }
            if (OneStudentEntity.getAvailability()!=null){
                studentsEntity.setAvailability(OneStudentEntity.getAvailability());
            }else {
                studentsEntity.setAvailability(NULL_KEY);
            }
            if (OneStudentEntity.getTransactionType()!=null){
                studentsEntity.setTransactionType(OneStudentEntity.getTransactionType());
            }else {
                studentsEntity.setTransactionType(NULL_KEY);
            }
            if (OneStudentEntity.getBillStatus()!=null){
                studentsEntity.setBillStatus(OneStudentEntity.getBillStatus());
            }else {
                studentsEntity.setBillStatus(NULL_KEY);
            }
            if (OneStudentEntity.getBookTitle()!=null){
                studentsEntity.setBookTitle(OneStudentEntity.getBookTitle());
            }else {
                studentsEntity.setBookTitle(NULL_KEY);
            }
            if (OneStudentEntity.getBookDescription()!=null){
                studentsEntity.setBookDescription(OneStudentEntity.getBookDescription());
            }else {
                studentsEntity.setBookDescription(NULL_KEY);
            }
            if (OneStudentEntity.getPublishYear()!=null){
                studentsEntity.setPublishYear(OneStudentEntity.getPublishYear());
            }else {
                studentsEntity.setPublishYear(NULL_KEY);
            }
            if (OneStudentEntity.getAuthorID()!=null){
                studentsEntity.setAuthorID(OneStudentEntity.getAuthorID());
            }else {
                studentsEntity.setAuthorID(NULL_KEY);
            }
            if (OneStudentEntity.getDepartmentID()!=null){
                studentsEntity.setDepartmentID(OneStudentEntity.getDepartmentID());
            }else {
                studentsEntity.setDepartmentID(NULL_KEY);
            }
            if (OneStudentEntity.getISBN_NUM()!=null){
                studentsEntity.setISBN_NUM(OneStudentEntity.getISBN_NUM());
            }else {
                studentsEntity.setISBN_NUM(NULL_KEY);
            }
            if (OneStudentEntity.getBookImage()!=null){
                studentsEntity.setBookImage(OneStudentEntity.getBookImage());
            }else {
                studentsEntity.setBookImage(NULL_KEY);
            }
            if (OneStudentEntity.getPersonName()!=null){
                studentsEntity.setPersonName(OneStudentEntity.getPersonName());
            }else {
                studentsEntity.setPersonName(NULL_KEY);
            }
            if (OneStudentEntity.getSellerUserName()!=null){
                studentsEntity.setSellerUserName(OneStudentEntity.getSellerUserName());
            }else {
                studentsEntity.setSellerUserName(NULL_KEY);
            }
            if (OneStudentEntity.getSellerGender()!=null){
                studentsEntity.setSellerGender(OneStudentEntity.getSellerGender());
            }else {
                studentsEntity.setSellerGender(NULL_KEY);
            }
            if (OneStudentEntity.getSellerEmail()!=null){
                studentsEntity.setSellerEmail(OneStudentEntity.getSellerEmail());
            }else {
                studentsEntity.setSellerEmail(NULL_KEY);
            }
            if (OneStudentEntity.getSellerDepartmentID()!=null){
                studentsEntity.setSellerDepartmentID(OneStudentEntity.getSellerDepartmentID());
            }else {
                studentsEntity.setSellerDepartmentID(NULL_KEY);
            }
            if (OneStudentEntity.getAuthorTitle()!=null){
                studentsEntity.setAuthorTitle(OneStudentEntity.getAuthorTitle());
            }else {
                studentsEntity.setAuthorTitle(NULL_KEY);
            }
            if (OneStudentEntity.getDepartmentName()!=null){
                studentsEntity.setDepartmentName(OneStudentEntity.getDepartmentName());
            }else {
                studentsEntity.setDepartmentName(NULL_KEY);
            }
            if (OneStudentEntity.getSellerFirebaseUid()!=null){
                studentsEntity.setSellerFirebaseUid(OneStudentEntity.getSellerFirebaseUid());
            }else {
                studentsEntity.setSellerFirebaseUid(NULL_KEY);
            }
            if (OneStudentEntity.getBookStatus()!=null){
                studentsEntity.setBookStatus(OneStudentEntity.getBookStatus());
            }else {
                studentsEntity.setBookStatus(NULL_KEY);
            }
            if (OneStudentEntity.getBillID()!=null){
                studentsEntity.setBillID(OneStudentEntity.getBillID());
            }else {
                studentsEntity.setBillID(NULL_KEY);
            }
            if (OneStudentEntity.getOwnerStatus()!=null){
                studentsEntity.setOwnerStatus(OneStudentEntity.getOwnerStatus());
            }else {
                studentsEntity.setOwnerStatus(NULL_KEY);
            }
            if (OneStudentEntity.getBuyerStatus()!=null){
                studentsEntity.setBuyerStatus(OneStudentEntity.getBuyerStatus());
            }else {
                studentsEntity.setBuyerStatus(NULL_KEY);
            }
            if (OneStudentEntity.getBuyerFirebaseUiD()!=null){
                studentsEntity.setBuyerFirebaseUiD(OneStudentEntity.getBuyerFirebaseUiD());
            }else {
                studentsEntity.setBuyerFirebaseUiD(NULL_KEY);
            }
            if (this.list!=null&&mDatabase!=null) {
                x = mDatabase.booksDao().InsertBooksGallery(studentsEntity);
            }
        }
        if (x>0){
            ArticlesEntityList_x=new ArrayList<>();
            ArticlesEntityList_x=this.list;
            return true;
        }else{
            return false;
        }
    }
}