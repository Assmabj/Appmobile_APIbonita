package com.example.isibpmn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.isibpmn.adapter.ProcessAdaptater;
import com.example.isibpmn.cookies.AddCookiesInterceptor;
import com.example.isibpmn.data.model.ListeInputs;
import com.example.isibpmn.service.RetrofitServices;

import java.util.ArrayList;
import java.util.List;

public class ProcessActivity extends AppCompatActivity {
    String process_id;
    String process_name;
    ProgressDialog progressDoalog;
    Context instance;
    Retrofit retrofitLogin;
    RetrofitServices loginApi;
    String id="";
    ListeInputs input;
    RecyclerView recycler;
    ProcessAdaptater adapter;

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);
      recycler =(RecyclerView)findViewById(R.id.recycler_process);
      instance=this;
        onProcessLisnner();

        /*Bundle b= getIntent().getExtras();
        if(b!=null)
        {

            process_id=b.getString(id);
            process( process_id);
        }
        else {
            Toast.makeText(this, "error data" , Toast.LENGTH_SHORT).show();

        }*/
        Intent intent=getIntent();


            process_id=getIntent().getStringExtra(id);
            if(process_id!=null)
            { Toast.makeText(this, " data"+process_id , Toast.LENGTH_SHORT).show();
                process( process_id);}
            else{ Toast.makeText(this, " data"+process_id , Toast.LENGTH_SHORT).show(); }

    }
    @Override
    protected void onStart() {

        super.onStart();



    }
    public void process(String id)
    {
        OkHttpClient client = new OkHttpClient();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();


        builder.addInterceptor(new AddCookiesInterceptor(this));
        client=builder.build();
        retrofitLogin = new Retrofit.Builder()
                .baseUrl("http://digitalisi.tn:8080/bonita/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        loginApi = retrofitLogin.create(RetrofitServices.class);
        Call<ListeInputs> call=loginApi.getProcess(process_id);
        call.enqueue(new Callback<ListeInputs>() {
            @Override
            public void onResponse(Call<ListeInputs> call, Response<ListeInputs> response) {
                if (response.isSuccessful()) {
                    Log.i("success", "liste process success");

                    input=  new ListeInputs();
                    input= response.body();
                    adapter= new ProcessAdaptater(instance,input.getInputs().get(0).getInputs());
                    recyclerView.setLayoutManager(new LinearLayoutManager(instance));
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(adapter);
                    Toast.makeText(instance, "liste process success", Toast.LENGTH_SHORT).show();
                    progressDoalog.dismiss();

                } else {
                    Log.i("liste failed", "liste  failed");

                    Toast.makeText(instance, "liste failed" + response.code(), Toast.LENGTH_SHORT).show();
                    progressDoalog.dismiss();

                }
            }


            @Override
            public void onFailure(Call<ListeInputs> call, Throwable t) {
                Log.i("fail", "on fail");
                Toast.makeText(instance, "fail connect liste" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDoalog.dismiss();

            }


        });

    }
    public void onProcessLisnner() {
        progressDoalog = new ProgressDialog(ProcessActivity.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();

    }
    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        process_id=getIntent().getStringExtra(id);
        if(process_id!=null)
        { Toast.makeText(this, " data"+process_id , Toast.LENGTH_SHORT).show();
            process( process_id);}
        else{ Toast.makeText(this, " data error", Toast.LENGTH_SHORT).show(); }

    }

}