package com.example.work1;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 全选
     */
    private Button mQuan;
    /**
     * 编辑
     */
    private Button mBian;
    /**
     * 完成
     */
    private Button mWan;
    private Toolbar mTool;
    private RecyclerView mRec;
    private RecAdapter adapter;
    private String TAG="11111111111";
    int a=1;
    int c=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initrec();
    }

    private void initrec() {
        mRec.setLayoutManager(new LinearLayoutManager(this));
        mRec.addItemDecoration(new DividerItemDecoration(this,LinearLayout.VERTICAL));
        List<Bean.DataBean.DatasBean> bean=new ArrayList<>();
        adapter = new RecAdapter(bean, this);
        mRec.setAdapter(adapter);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyApiservice.urlstr)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        retrofit.create(MyApiservice.class).getdata()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Bean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Bean bean) {
                        Log.i("1111111111111111111", "onNext: 阿拉啦啦啦拉拉"+bean.toString());
                    adapter.bean.addAll(bean.getData().getDatas());
                    adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.i("11111111111111ereeor", "onNext: 巴卡娜"+e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initView() {
        mQuan = (Button) findViewById(R.id.quan);
        mQuan.setOnClickListener(this);
        mBian = (Button) findViewById(R.id.bian);
        mBian.setOnClickListener(this);
        mWan = (Button) findViewById(R.id.wan);
        mWan.setOnClickListener(this);
        mTool = (Toolbar) findViewById(R.id.tool);
        mRec = (RecyclerView) findViewById(R.id.rec);
        mTool.setTitle("");
        setSupportActionBar(mTool);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.quan:
                if (c==1){
                    adapter.quan(c);
                    mQuan.setText("取消全选");
                    adapter.notifyDataSetChanged();
                    c=2;
                }else if(c==2){
                    adapter.quan(c);
                    mQuan.setText("全选");
                    adapter.notifyDataSetChanged();
                    c=1;
                }
                adapter.qunaxian();
                break;
            case R.id.bian:
                a++;
                if (a%2!=1){
                    mQuan.setVisibility(View.VISIBLE);
                    mWan.setVisibility(View.VISIBLE);
                    mBian.setText("完成");
                    adapter.setboxed(1);
                }else {
                    mBian.setText("编辑");
                    mQuan.setVisibility(View.GONE);
                    mWan.setVisibility(View.GONE);
                    adapter.setboxed(0);
                }
                adapter.notifyDataSetChanged();
                break;
            case R.id.wan:
                adapter.quan(c);
                mQuan.setText("全选");
                adapter.notifyDataSetChanged();
                c=1;
               adapter.shanchu();
                break;
        }
    }
}
