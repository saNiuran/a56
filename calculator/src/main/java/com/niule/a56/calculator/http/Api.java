package com.niule.a56.calculator.http;


public class Api {
    private static ApiService apiService;
    private static ApiCache apiCache;

    private static final String BASE_URL = "https://api.56.5niule.com/";  //正式服务器
//    private static final String BASE_URL = "http://192.168.175.120:8888/";    //内网测试服务器


    public static String getURL() {
        return BASE_URL;
    }


    public static String getImageURL() {
        return "https://h5.56.5niule.com/images/";
    }

    public static ApiService getApiService() {
        if (apiService == null) {
            synchronized (Api.class) {
                if (apiService == null) {
                    apiService = XXApi.getInstance().getRetrofit(true).create(ApiService.class);
                }
            }
        }
        return apiService;
    }

    public static ApiCache getApiCache() {
        if (apiCache == null) {
            synchronized (Api.class) {
                if (apiCache == null) {
                    apiCache = XXApi.getInstance().getRxCache().using(ApiCache.class);
                }
            }
        }
        return apiCache;
    }
}
