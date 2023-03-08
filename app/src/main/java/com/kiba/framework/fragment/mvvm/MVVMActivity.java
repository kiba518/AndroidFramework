package com.kiba.framework.fragment.mvvm;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.kiba.framework.R;
import com.kiba.framework.fragment.mvvm.entity.TestEntity;


public class MVVMActivity extends AppCompatActivity {


    private TestViewModel testViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testViewModel = new ViewModelProvider(this).get(TestViewModel.class);
        MutableLiveData<TestEntity> testViewModelData = testViewModel.getTestEntityLiveData();
        initData();
        testViewModelData.observe(this, new Observer<TestEntity>() {
            @Override
            public void onChanged(TestEntity testEntity) {

            }
        }); 

    }

    private void initData() {
        testViewModel.getDataFromNet();
    }
}
