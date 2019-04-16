package mo.ed.prof.yusor.helpers;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import mo.ed.prof.yusor.helpers.Room.AppDatabase;
import mo.ed.prof.yusor.helpers.Room.AppExecutors;
import mo.ed.prof.yusor.helpers.Room.LiveDataRepo;

/**
 * Created by Prof-Mohamed Atef on 4/15/2019.
 */

public class BasicApp extends MultiDexApplication {
    private AppExecutors mAppExecutors;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAppExecutors = new AppExecutors();
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this, mAppExecutors);
    }

    public LiveDataRepo getRepository() {
        return LiveDataRepo.getLiveDataRepoInstance(getDatabase());
    }
}