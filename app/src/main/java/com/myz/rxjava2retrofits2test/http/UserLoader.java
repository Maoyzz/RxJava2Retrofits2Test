package com.myz.rxjava2retrofits2test.http;

import com.myz.rxjava2retrofits2test.bean.TestBean;

import io.reactivex.Observable;

/**
 * Created by myz on 2018/12/7
 **/
public class UserLoader extends ObjectLoader{

    private static volatile UserLoader instance = null;
    private AccessApi mService;

    private UserLoader(){
        mService = RetrofitManager.getInstance().create(AccessApi.class);
    }

    public static UserLoader getInstance(){
        if(instance == null){
            synchronized (UserLoader.class){
                if(instance == null){
                    instance = new UserLoader();
                }
            }
        }
        return instance;
    }


    public Observable<TestBean> getDbMovie(String start,String count){
        return observe(mService.getDbMovie(start,count));
    }


}
