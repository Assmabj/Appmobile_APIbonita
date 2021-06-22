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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.isibpmn.adapter.ListeProcessAdapter;
import com.example.isibpmn.adapter.ProcessAdaptater;
import com.example.isibpmn.cookies.AddCookiesInterceptor;
import com.example.isibpmn.data.model.ListeProcess;
import com.example.isibpmn.data.model.Userid;
import com.example.isibpmn.service.RetrofitServices;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ListeProcessAdapter.ClickedItem{
    ProgressDialog progressDoalog;
    Context instance;
    Retrofit retrofitLogin;
    RetrofitServices loginApi;
    String id="";
    List<ListeProcess> lp;
    ListeProcessAdapter adapter;
    String cle="process_list";
    String name="pref";

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        lp=  new ArrayList<>();
        this.onProcessLisnner();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


        adapter = new ListeProcessAdapter(this::ClickedUser);
        recyclerView.setLayoutManager(new LinearLayoutManager(instance));
        adapter.setListProcess(instance,lp);
        recyclerView.invalidate();
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        init();



    }

    @Override
    protected void onStart() {

        super.onStart();



    }

 public void init() {

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

        Call<Userid> call=loginApi.getUserid();
        call.enqueue(new Callback<Userid>() {

            @Override
            public void onResponse(Call<Userid> call, Response<Userid> response) {
                if (response.isSuccessful()) {
                    Log.i("success", "id success");
                    id=response.body().getUser_id();
                    Toast.makeText(instance, "id  success"+id, Toast.LENGTH_SHORT).show();
                     lp=new ArrayList<>();
                    lp=listeprocess(id);

                    progressDoalog.dismiss();
                } else {
                    progressDoalog.dismiss();
                    Log.i("failed", "id failed");
                    Toast.makeText(instance, "id failed" + response.code(), Toast.LENGTH_SHORT).show();

                }
            }


            @Override
            public void onFailure(Call<Userid> call, Throwable t) {
                Log.i("fail", "on fail");
                progressDoalog.dismiss();
                Toast.makeText(instance, "fail connect" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }


        });

    }



    public List<ListeProcess> listeprocess(String id)
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
        Call<List<ListeProcess>> call=loginApi.getListeprocess("0","100","version%20desc","activationState=ENABLED","user_id="+id);
        call.enqueue(new Callback<List<ListeProcess>>() {
            @Override
            public void onResponse(Call<List<ListeProcess>> call, Response<List<ListeProcess>> response) {
                if (response.isSuccessful()) {
                    Log.i("success", "liste process success");
                    lp.clear();
                    lp=new ArrayList<>();
                    lp= response.body();

                    adapter.setListProcess(instance,lp);
                    adapter.notifyDataSetChanged();
                    recyclerView.invalidate();


                    //Toast.makeText(instance, "liste process success"+lp.get(0).getDisplayName(), Toast.LENGTH_SHORT).show();
                    progressDoalog.dismiss();

                } else {
                    Log.i("liste failed", "liste  failed");

                    Toast.makeText(instance, "liste failed" + response.code(), Toast.LENGTH_SHORT).show();
                    progressDoalog.dismiss();

                }
            }


            @Override
            public void onFailure(Call<List<ListeProcess>> call, Throwable t) {
                Log.i("fail", "on fail");
                Toast.makeText(instance, "fail connect liste" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDoalog.dismiss();

            }


        });
        return lp;
    }





    public void onProcessLisnner() {
        progressDoalog = new ProgressDialog(MainActivity.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();

    }
    /*Gson gson = new Gson();
    String js=gson.toJson(process);
    SharedPreferences.Editor shar=getSharedPreferences(name,instance.MODE_PRIVATE).edit();
                    shar.putString(cle,js);
                    shar.commit();*/
      /*lp.clear();
        SharedPreferences shared=getSharedPreferences(name,instance.MODE_PRIVATE);
        String list_sharedpref= shared.getString(cle,null);
        if(list_sharedpref!=null) {
            Gson gson = new Gson();
            Type type =new TypeToken<List<ListeProcess>>(){}.getType();
            lp=gson.fromJson(list_sharedpref,type);
            adapter = new ListeProcessAdapter(instance, lp);
            recyclerView.setLayoutManager(new LinearLayoutManager(instance));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
        }*/


    @Override
    public void ClickedUser(String id) {
        Intent intent = new Intent(MainActivity.this, ProcessActivity.class);

        intent.putExtra("id",id);



       startActivity(intent);
    }
}