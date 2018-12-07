package com.myz.rxjava2retrofits2test.http;

import com.myz.rxjava2retrofits2test.bean.TestBean;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by myz on 2018/12/7
 */
public interface AccessApi {
    @GET("top250")
    Call<TestBean> test(@Query("start") String start , @Query("count") String count);

    @GET("top250")
    Observable<TestBean> test2(@Query("start") String start , @Query("count") String count);

    @GET("top250")
    Observable<TestBean> getDbMovie(@Query("start") String start , @Query("count") String count);
}
