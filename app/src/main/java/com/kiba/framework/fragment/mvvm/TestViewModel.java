package com.kiba.framework.fragment.mvvm;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kiba.framework.fragment.mvvm.entity.TestEntity;


/**
 * Author: loaderman
 */
public class TestViewModel extends ViewModel {


    private MutableLiveData<TestEntity> liveData;

    public MutableLiveData<TestEntity> getTestEntityLiveData() {
        if (liveData == null) {
            liveData = new MutableLiveData<>();
        }
        return liveData;
    }

    public TestViewModel() {

    }


    public void getDataFromNet() {
        liveData.setValue(new TestEntity());
    }

}