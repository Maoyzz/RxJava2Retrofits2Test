package com.myz.rxjava2retrofits2test.http;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import okhttp3.Authenticator;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by myz on 2018/12/7
 **/
public class RetrofitManager {

    private static volatile RetrofitManager instance = null;
    private Retrofit mRetrofit = null;

    private RetrofitManager(){
        initRetrofit();
    }

    public static RetrofitManager getInstance(){
        if(instance == null){
            synchronized (RetrofitManager.class){
                if(instance == null){
                    instance = new RetrofitManager();
                }
            }
        }
        return instance;
    }

    private void initRetrofit(){
        //1.init OkHttpClient;
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Log.e("mao", "log: "+message );
                    }
                }).setLevel(HttpLoggingInterceptor.Level.BODY))
//                .authenticator(new Authenticator() {
//                    @Override
//                    public Request authenticate(Route route, Response response) throws IOException {
//                        return null;
//                    }
//                })
//                .cookieJar(new CookieJar() {
//                    @Override
//                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
//
//                    }
//
//                    @Override
//                    public List<Cookie> loadForRequest(HttpUrl url) {
//                        return null;
//                    }
//                })
                .build();
        //2.init Retrofit;
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

    public <T> T create(Class<T> service){
        return mRetrofit.create(service);
    }

    public static Request addSessionKey(Request oldRequest){
        HttpUrl.Builder authorizedUrlBuilder = oldRequest.url().newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host());
        // 新的请求
        Request.Builder requestBuilder =  oldRequest.newBuilder();
        requestBuilder.method(oldRequest.method(),
                oldRequest.body());

        //Add new SessionKey
//        String sessionKey = SharedPreferenceUtils.getString(QiyunApplication.getInstance(), SharedPreferenceUtils.SESSION_KEY, "");
//        if (sessionKey != null && !"".equals(sessionKey)){
//            //Url url = new Url(oldRequest.url().scheme() + "://" + );
//            requestBuilder.url(authorizedUrlBuilder.setEncodedQueryParameter("SessionKey",sessionKey).build());
//        }else{
//            requestBuilder.url(authorizedUrlBuilder.build());
//        }
//        requestBuilder.header("sessionID", SharedPreferenceUtils.getString(QiyunApplication.getInstance(), SharedPreferenceUtils.SESSIONID, ""));

        return requestBuilder.build();
    }


}
