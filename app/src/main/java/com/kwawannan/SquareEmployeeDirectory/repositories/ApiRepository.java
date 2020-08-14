package com.kwawannan.SquareEmployeeDirectory.repositories;
import com.kwawannan.SquareEmployeeDirectory.api.ApiService;
import com.google.gson.JsonObject;

import javax.inject.Inject;

import io.reactivex.Observable;

public class ApiRepository {

    private ApiService mApiService;

    @Inject
    public ApiRepository(ApiService apiService) {
        this.mApiService = apiService;
    }

    public Observable<JsonObject> getEmployeeList(){
        return mApiService.getEmployeeList();
    }

}
