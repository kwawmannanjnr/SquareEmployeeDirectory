package com.kwawannan.SquareEmployeeDirectory.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kwawannan.SquareEmployeeDirectory.R;
import com.kwawannan.SquareEmployeeDirectory.adapters.EmployeeListAdapter;
import com.kwawannan.SquareEmployeeDirectory.di.ViewModelFactory;
import com.kwawannan.SquareEmployeeDirectory.model.ApiResponse;
import com.kwawannan.SquareEmployeeDirectory.model.EmployeesResponse;
import com.kwawannan.SquareEmployeeDirectory.utils.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public class EmployeeListActivity extends BaseActivity {

    private final String TAG = getClass().getSimpleName();
    private RecyclerView mRecyclerView;
    private EmployeeListAdapter mEmployeeListAdapter;

    @BindView(R.id.spinner_loading)
    ProgressBar spinner;
    @BindView(R.id.content_employees)
    FrameLayout mEmployeeContent;
    @BindView(R.id.content_error)
    FrameLayout mErrorView;
    @BindView(R.id.tv_error)
    TextView mErrorTV;

    @Inject
    ViewModelFactory viewModelFactory;

    EmployeeListViewModel employeeListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mRecyclerView = findViewById(R.id.employee_recycler_view);
        mEmployeeListAdapter = new EmployeeListAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mEmployeeListAdapter);

        employeeListViewModel = ViewModelProviders.of(this,viewModelFactory).get(EmployeeListViewModel.class);
        loadEmployees(isConnected());

    }

    public void loadEmployees(boolean isConnected) {
        if(isConnected){
            employeeListViewModel.getEmployeeList();
            subscribeToViewmodel();
        }else {
            displayErrorView(getString(R.string.network_unavailable));
        }
    }

    private void subscribeToViewmodel() {
        employeeListViewModel.getmEmployeeListLiveData().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {
                switch ( apiResponse.status) {
                    case LOADING:
                        spinner.setVisibility(View.VISIBLE);
                        Log.d(TAG,"Loading");
                        break;
                    case SUCCESS:
                        spinner.setVisibility(View.GONE);
                        Log.d(TAG,"SUCCESS");
                        renderResponse((EmployeesResponse)apiResponse.data);
                        break;
                    case ERROR:
                        spinner.setVisibility(View.GONE);
                        displayErrorView(getString(R.string.error_fetching_employees));
                        //Toast.makeText(EmployeeListActivity.this,"Error", Toast.LENGTH_SHORT).show();
                        Log.d(TAG,apiResponse.error);
                        break;
                    default:
                        break;
                }

            }
        });
    }

    private void renderResponse(EmployeesResponse response) {
        Log.d(TAG,"Employees returned = "+response.getEmployees().size());
        if(response.getEmployees().size() == 0){
            displayErrorView(getString(R.string.employees_empty));
        }else {
            mEmployeeListAdapter.setmEmployeeList(response.getEmployees());
        }
    }

    public void displayErrorView(String error){
        mEmployeeContent.setVisibility(View.GONE);
        mErrorView.setVisibility(View.VISIBLE);
        mErrorTV.setText(error);
    }
}
