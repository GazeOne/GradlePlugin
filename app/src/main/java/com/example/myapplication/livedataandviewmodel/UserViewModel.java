package com.example.myapplication.livedataandviewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

public class UserViewModel extends ViewModel
        implements BaseViewModel<User> {

    private String TAG = UserViewModel.class.getSimpleName();

    private MutableLiveData<User> liveUser;

    //自定义的UserViewModel 继承系统的 ViewModel，将 User 封装成 MutableLiveData：
    // if(liveUser == null){ liveUser = new MutableLiveData<User>(); }

    public MutableLiveData<User> getData() {
        if (liveUser == null) {
            liveUser = new MutableLiveData<User>();
        }

        liveUser.setValue(loadData());
        return this.liveUser;
    }

    public void changeData() {
        if (liveUser != null) {
            liveUser.setValue(loadData());
        }
    }

    @Override
    public User loadData() {
        User user = new User();
        user.userId = RandomUtil.getRandomNumber();
        user.name = RandomUtil.getChineseName();
        user.phone = RandomUtil.getRandomPhone();
        Log.i(TAG, "loadData(): " + user.toString());
        return user;
    }

    @Override
    public void clearData() {

    }

}

