package com.kwawannan.SquareEmployeeDirectory.views;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.kwawannan.SquareEmployeeDirectory.model.ApiResponse;
import com.kwawannan.SquareEmployeeDirectory.model.Employee;
import com.kwawannan.SquareEmployeeDirectory.model.EmployeesResponse;
import com.kwawannan.SquareEmployeeDirectory.model.Position;
import com.kwawannan.SquareEmployeeDirectory.repositories.ApiRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.util.ArrayList;
import java.util.Comparator;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class EmployeeListViewModel extends ViewModel {

    private final CompositeDisposable mDisposables = new CompositeDisposable();
    private final MutableLiveData<ApiResponse> mEmployeeListLiveData = new MutableLiveData<>();
    private ApiRepository mRepository;
    private final String TAG = getClass().getSimpleName();

    @Inject
    public EmployeeListViewModel(@NonNull ApiRepository repository) {
        this.mRepository = repository;
    }

    public void getEmployeeList(){
        mDisposables.add(mRepository.getEmployeeList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> mEmployeeListLiveData.setValue(ApiResponse.loading()))
                .doOnError((e) -> mEmployeeListLiveData.setValue(ApiResponse.error(e.getMessage(),null)))
                .subscribe(
                        response ->{
                            EmployeesResponse employees = new EmployeesResponse();
                            try{
                                ArrayList employeeList = new ArrayList<Employee>();
                                JsonArray list = response.getAsJsonArray("employees");
                                for (int i = 0; i< list.size();i++){
                                    JsonObject employee = list.get(i).getAsJsonObject();
                                    Employee current = new Employee();
                                    current.setFullName(employee.get("full_name").getAsString());
                                    current.setUuid(employee.get("uuid").getAsString());
                                    current.setPhoneNumber(employee.get("phone_number").getAsString());
                                    current.setEmailAddress(employee.get("email_address").getAsString());
                                    current.setBiography(employee.get("biography").getAsString());
                                    current.setPhotoUrlLarge(employee.get("photo_url_large").getAsString());
                                    current.setPhotoUrlSmall(employee.get("photo_url_small").getAsString());
                                    current.setTeam(employee.get("team").getAsString());
                                    String pos = employee.get("employee_type").getAsString();
                                    switch (pos){
                                        case "FULL_TIME":
                                            current.setEmployeeType(Position.FULL_TIME);
                                        case "PART_TIME":
                                            current.setEmployeeType(Position.PART_TIME);
                                        case "CONTRACTOR":
                                            current.setEmployeeType(Position.CONTRACTOR);
                                    }
                                    employeeList.add(current);
                                }
                                employees.setEmployees(employeeList);

                            }catch (JsonParseException e){
                                Log.e(TAG, e.getMessage());
                            }
                            employees.getEmployees().sort(Comparator.comparing(Employee::getFullName));
                            mEmployeeListLiveData.setValue(ApiResponse.success(employees));
                        },
                        error -> mEmployeeListLiveData.setValue(ApiResponse.error(error.getMessage(),null)))
        );
    }

    public MutableLiveData<ApiResponse> getmEmployeeListLiveData() {
        return mEmployeeListLiveData;
    }

    @Override
    protected void onCleared() {
        mDisposables.clear();
    }
}
