package com.myz.rxjava2retrofits2test.http;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by myz on 2018/12/7
 **/
public class MyObserver<T> implements Observer<T> {

    private Disposable d;
    private ObserverListener<T> listener;

    public MyObserver(ObserverListener<T> listener){
        this.listener = listener;
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.d = d;
    }

    @Override
    public void onNext(T t) {
        listener.onNext(t,d);
    }

    @Override
    public void onError(Throwable e) {
        listener.onError(e);
    }

    @Override
    public void onComplete() {

    }

    public interface ObserverListener<T>{
        void onNext(T t,Disposable d);
        void onError(Throwable e);
    }


}
