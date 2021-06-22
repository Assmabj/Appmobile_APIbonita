package com.example.isibpmn.service;

import com.example.isibpmn.data.model.Userid;
import com.example.isibpmn.data.model.ListeInputs;
import com.example.isibpmn.data.model.ListeProcess;

import retrofit2.Call;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;



import java.util.List;
import java.util.Map;

public interface RetrofitServices {

    @FormUrlEncoded
    @POST("loginservice")
    Call<Void> isValidUser(@FieldMap Map<String, String> param);


    @GET("API/system/session/unusedId")
    Call<Userid> getUserid();



    @GET("API/bpm/process")
    Call<List<ListeProcess>> getListeprocess(@Query(value="p", encoded=true) String p ,
                                             @Query(value="c", encoded=true) String c,
                                             @Query(value="o", encoded=true) String o,
                                             @Query(value="f", encoded=true)String f,
                                             @Query(value="f", encoded=true) String userid);

    @GET("API/bpm/process/{id}/contract")
    Call<ListeInputs> getProcess(@Path("id") String id);

}
