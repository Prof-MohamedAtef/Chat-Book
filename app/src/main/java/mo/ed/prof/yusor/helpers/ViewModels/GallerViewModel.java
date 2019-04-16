package mo.ed.prof.yusor.helpers.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import mo.ed.prof.yusor.helpers.Config;
import mo.ed.prof.yusor.helpers.BasicApp;
import mo.ed.prof.yusor.helpers.Room.StudentsEntity;

/**
 * Created by Prof-Mohamed Atef on 4/15/2019.
 */

public class GallerViewModel extends AndroidViewModel {

    String Category;

    private final MediatorLiveData<List<StudentsEntity>> mObserverMediatorLiveDataGalleryList;

    public GallerViewModel(@NonNull Application application) {
        super(application);
        Config.application = application;
        this.mObserverMediatorLiveDataGalleryList = new MediatorLiveData<>();
        this.mObserverMediatorLiveDataGalleryList.setValue(null);
        LiveData<List<StudentsEntity>> BooksGallery = ((BasicApp) application).getRepository().LoadGalleryBooks();
        mObserverMediatorLiveDataGalleryList.addSource(BooksGallery, mObserverMediatorLiveDataGalleryList::setValue);
    }

    public MediatorLiveData<List<StudentsEntity>> getmObserverMediatorLiveDataGalleryList() {
        return mObserverMediatorLiveDataGalleryList;
    }
}