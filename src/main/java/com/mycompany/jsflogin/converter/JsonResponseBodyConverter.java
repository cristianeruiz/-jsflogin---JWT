package com.mycompany.jsflogin.converter;

import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Converter;

import java.io.IOException;
import java.lang.reflect.Type;

final class JsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private Type type;

    JsonResponseBodyConverter(Type type) {
        this.type =  type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
            if (type.getTypeName().equals(JSONObject.class.getTypeName()) ) {
                JSONObject jsonObj = new JSONObject(value.string());
                return (T) jsonObj;
            } else if (type.getTypeName().equals(JSONArray.class.getTypeName()) ){
                JSONArray jsonArray = new JSONArray(value.string());
                return (T) jsonArray;
            }
            return (T) value;
        } catch(JSONException e) {
            return null;
        }
    }
}
