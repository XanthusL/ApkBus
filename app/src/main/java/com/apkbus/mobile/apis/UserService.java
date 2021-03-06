package com.apkbus.mobile.apis;

import com.apkbus.mobile.bean.LoginInfo;
import com.apkbus.mobile.bean.MobWrapper;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by liyiheng on 16/9/23.
 */

public interface UserService {

    @GET("rigister")
    Observable<MobWrapper> register(@Query("key") String key,
                                    @Query(value = "username", encoded = true) String username,
                                    @Query("password") String password,
                                    @Query("email") String email);

    @GET("login")
    Observable<MobWrapper<LoginInfo>> login(@Query("key") String key,
                                            @Query(value = "username", encoded = true) String username,
                                            @Query("password") String password);

    @GET("profile/query")
    Observable<MobWrapper<String>> getProfileItem(@Query("key") String key,
                                                  @Query("uid") String uid,
                                                  @Query("item") String item);


    @GET("profile/put")
    Observable<MobWrapper> setProfile(@Query("key") String key,
                                              @Query("uid") String uid,
                                              @Query("token") String token,
                                              @Query("item") String item,
                                              @Query("value") String value);
}
