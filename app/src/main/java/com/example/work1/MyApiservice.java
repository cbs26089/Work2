package com.example.work1;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface MyApiservice {
    String urlstr="https://www.wanandroid.com/";
    @GET("project/list/1/json?cid=294")
    Observable<Bean> getdata();
}
