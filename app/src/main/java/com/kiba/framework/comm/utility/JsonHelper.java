package com.kiba.framework.comm.utility;

import com.google.gson.Gson;

public class JsonHelper  {
    public static  String Serialize(Object obj){
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        return json;
    }
    public static  <T> T Deserialize(Class<T> tClass, String json){
        Gson gson = new Gson();
        return  gson.fromJson(json, tClass);
    }
}
