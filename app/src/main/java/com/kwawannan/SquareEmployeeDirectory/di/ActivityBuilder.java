package com.kwawannan.SquareEmployeeDirectory.di;

import com.kwawannan.SquareEmployeeDirectory.views.EmployeeListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = FragmentBuilder.class)
    abstract EmployeeListActivity bindEmployeeListActivity();

}

