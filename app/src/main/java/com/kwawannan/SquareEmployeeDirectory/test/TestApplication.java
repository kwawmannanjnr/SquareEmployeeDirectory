package com.kwawannan.SquareEmployeeDirectory.test;

import com.kwawannan.SquareEmployeeDirectory.BaseApplication;
import com.kwawannan.SquareEmployeeDirectory.di.DaggerAppComponent;

public class TestApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerAppComponent.builder()
                .application(this)
                .appModule(new TestAppModule(this))
                .build().inject(this);
    }
}
