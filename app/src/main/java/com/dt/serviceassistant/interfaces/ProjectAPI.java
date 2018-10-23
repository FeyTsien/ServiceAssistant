package com.dt.serviceassistant.interfaces;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Url;
/**
 * Created by zhiyuan on 17/1/11.
 */

public interface ProjectAPI {
    //http://www.baidu.com/aaa?key=123&qq=aaa

    //返回值类型是被观察者
    @GET
    Observable<String> getMethod(@Url String url);

    //post请求方式
    @FormUrlEncoded
    @POST
    Observable<String> postMethod(@Url String url, @FieldMap Map<String, String> map);

    //带有请求头的post请求方式
    @Headers({"os:android","version:1"})
    @FormUrlEncoded
    @POST
    Observable<String> postMethod(@Header("deviceId") String deviceId, @Header("uid") String uid, @Header("sign") String sign, @Url String url, @FieldMap Map<String, String> map);

    //带参数上传多张图片
    @Headers({"os:android"})
    @Multipart
    @POST
    Observable<ResponseBody> upLoadsImgs(@Header("deviceId") String deviceId, @Header("uid") String uid, @Header("sign") String sign, @Url String url, @PartMap Map<String, RequestBody> params, @Part MultipartBody.Part photo1, @Part MultipartBody.Part photo2, @Part MultipartBody.Part photo3, @Part MultipartBody.Part photo4, @Part MultipartBody.Part photo5, @Part MultipartBody.Part photo6);

    // 带参数上传单张图片
    @Headers({"os:android"})
    @Multipart
    @POST
    Observable<ResponseBody> upLoadImg(@Header("deviceId") String deviceId, @Header("uid") String uid, @Header("sign") String sign, @Url String url, @Part MultipartBody.Part photo, @PartMap Map<String, String> params);

    // 上传单张图片
    @Headers({"os:android"})
    @Multipart
    @POST
    Observable<ResponseBody> upLoadImg(@Header("deviceId") String deviceId, @Header("uid") String uid, @Header("sign") String sign, @Url String url, @Part MultipartBody.Part photo);
}
