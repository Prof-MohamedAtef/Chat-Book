package mo.ed.prof.yusor.helpers.Room.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import mo.ed.prof.yusor.helpers.Room.StudentsEntity;

/**
 * Created by Prof-Mohamed Atef on 4/15/2019.
 */
@Dao
public interface BooksDao {
    @Insert
    long InsertBooksGallery(StudentsEntity studentsEntity) ;

    @Query("DELETE FROM Books WHERE BookID LIKE :BookID")
    abstract int deleteByBookID(String BookID);

    @Query("SELECT * From Books")
    LiveData<List<StudentsEntity>> getAllBooksData();

    @Query("DELETE FROM Books")
    abstract int deleteAllBooksInGallery();
}