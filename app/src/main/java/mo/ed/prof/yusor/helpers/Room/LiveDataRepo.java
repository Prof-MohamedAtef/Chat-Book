package mo.ed.prof.yusor.helpers.Room;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import java.util.List;

/**
 * Created by Prof-Mohamed Atef on 4/15/2019.
 */

public class LiveDataRepo {
    private static LiveDataRepo liveDataRepoInstance;
    private final AppDatabase mDatabase;

    private MediatorLiveData<List<StudentsEntity>> mObservableBooks;

    public LiveDataRepo(final AppDatabase database) {
        mDatabase = database;
        mObservableBooks = new MediatorLiveData<>();

        mObservableBooks.addSource(mDatabase.booksDao().getAllBooksData(),
                ArticlesEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableBooks.postValue(ArticlesEntities);
                    }
                });
    }

    public static LiveDataRepo getLiveDataRepoInstance(final AppDatabase database){
        if(liveDataRepoInstance==null){
            synchronized (LiveDataRepo.class){
                if (liveDataRepoInstance==null){
                    liveDataRepoInstance=new LiveDataRepo(database);
                }
            }
        }
        return liveDataRepoInstance;
    }

    public LiveData<List<StudentsEntity>> LoadGalleryBooks() {
        return mDatabase.booksDao().getAllBooksData();
    }

}
