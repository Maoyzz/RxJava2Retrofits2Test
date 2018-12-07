package com.myz.rxjava2retrofits2test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.myz.rxjava2retrofits2test.bean.TestBean;
import com.myz.rxjava2retrofits2test.http.AccessApi;
import com.myz.rxjava2retrofits2test.http.MyObserver;
import com.myz.rxjava2retrofits2test.http.UserLoader;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.IOException;

import javax.security.auth.login.LoginException;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;



//TODO RxJava2简单学习1.新增被观察者FlowAble -> 观察者对应原来的Subscriber。
//TODO              2.原来的Observable ->对应原来的Observer。

public class MainActivity extends AppCompatActivity {

    AccessApi NetService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        initOb();
//        initRt();
        initFinal();
    }

    /**
     * RxJava2使用
     */
    private void initOb() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {

            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });


        Flowable.create(new FlowableOnSubscribe<Object>() {
            @Override
            public void subscribe(FlowableEmitter<Object> emitter) throws Exception {

            }
        }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onSubscribe(Subscription s) {

                    }

                    @Override
                    public void onNext(Object o) {

                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * //TODO 待完成的地方
     * 1.可以将Retrofit写个manager封装起来
     * 2.然后被观察者Observable的基础设置如设置线程可以封装成类减少代码的重复设置
     */
    private void initRt(){
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Log.e("mao", "log: "+message );
                    }
                }).setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.douban.com/v2/movie/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        NetService = retrofit.create(AccessApi.class);
        //1.Retrofit单独的接口请求
        Call<TestBean> call = NetService.test("25","25");
        call.enqueue(new Callback<TestBean>() {
            @Override
            public void onResponse(Call<TestBean> call, Response<TestBean> response) {
                Log.e("mao", "onResponse: "+response.body().toString());
            }

            @Override
            public void onFailure(Call<TestBean> call, Throwable t) {
                Log.e("mao", "onFailure: "+t );
            }
        });
        //2.
        NetService.test2("25","25")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TestBean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(TestBean testBean) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 上面未封装
     * 下面封装完成
     */
    private void initFinal(){
        UserLoader.getInstance().getDbMovie("1","1").subscribe(new MyObserver<TestBean>(new MyObserver.ObserverListener<TestBean>() {
            @Override
            public void onNext(TestBean testBean, Disposable d) {
                Toast.makeText(MainActivity.this,"load success!!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

}
