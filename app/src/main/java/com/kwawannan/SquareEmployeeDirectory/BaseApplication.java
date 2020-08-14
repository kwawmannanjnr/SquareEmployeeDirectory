package com.kwawannan.SquareEmployeeDirectory;

import android.app.Activity;
import android.app.Application;

import com.kwawannan.SquareEmployeeDirectory.di.AppComponent;
import com.kwawannan.SquareEmployeeDirectory.di.AppModule;
import com.kwawannan.SquareEmployeeDirectory.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class BaseApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> mDispatchingAndroidInjector;

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent.builder()
                .application(this)
                .appModule(new AppModule(this))
                .build();

        mAppComponent.inject(this);
    }

    public AppComponent getmAppComponent() {
        return mAppComponent;
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return mDispatchingAndroidInjector;
    }
}
