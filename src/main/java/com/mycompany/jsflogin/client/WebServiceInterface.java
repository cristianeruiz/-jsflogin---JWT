package com.mycompany.jsflogin.client;

import okhttp3.RequestBody;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;


/**
 *
 * @author cristiane
 */

public interface WebServiceInterface {
    
    @POST("auth/signin")
            @Headers( {
            "Accept-Encoding: gzip,deflate",
            "Content-Type: Application/Json;charset=UTF-8",
            "Accept: Application/Json",
            "User-Agent: Retrofit 2.3.0"
    } )
    Call <JSONObject> auth(@Body RequestBody usuario);
    
    //@GET("test/{usuario}")
    //Object getUsuario(@RequestHeader("Authorization") String accesssToken, @PathVariable("usuario") String usuario);
    
    //@GET("items")
    //Call <JSONObject> getItems(@Header("Authorization") String accesssToken);
    
    @GET("items")
    @Headers( {
        "Accept-Encoding: gzip,deflate",
        "Content-Type: Application/Json;charset=UTF-8",
        "Accept: Application/Json",
        "User-Agent: Retrofit 2.3.0"
    } )
    Call <JSONObject> getItems(@Header("Authorization") String accesssToken);
    
}
