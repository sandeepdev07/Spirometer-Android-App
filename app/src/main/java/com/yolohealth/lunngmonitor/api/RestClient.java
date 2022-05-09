package com.yolohealth.lunngmonitor.api;

import static okhttp3.logging.HttpLoggingInterceptor.Level.HEADERS;
import static okhttp3.logging.HttpLoggingInterceptor.Level.NONE;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.yolohealth.lunngmonitor.BuildConfig;
import com.yolohealth.lunngmonitor.LungMonitorApp;
import com.yolohealth.lunngmonitor.utils.SharedPrefUtils;
import com.yolohealth.lunngmonitor.widget.AppConstant;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RestClient {

    private static final String API_URL = AppConstant.base_url;


    public static RestInterface getClient() {
        return getRetrofit().create(RestInterface.class);
    }

    public static Retrofit getRetrofit() {
        GsonBuilder gBuilder = new GsonBuilder();
        gBuilder.registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory<>());

        Gson gson = gBuilder.create();

        return new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(provideOkHttpClient())
                .build();
    }

    public static class NullStringToEmptyAdapterFactory<T> implements TypeAdapterFactory {
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {

            Class<T> rawType = (Class<T>) type.getRawType();
            if (rawType != String.class) {
                return null;
            }
            return (TypeAdapter<T>) new StringAdapter();
        }
    }

    public static class StringAdapter extends TypeAdapter<String> {
        public String read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                //System.out.println(("null  updated to space");

                return "";
            }
            return reader.nextString();
        }

        public void write(JsonWriter writer, String value) throws IOException {
            if (value == null) {
                writer.nullValue();
                return;
            }
            writer.value(value);
        }
    }

//    public static boolean getStatus(Status status) {
//        if (status != null) {
//            if (status.getResult() == 1) {
//                //Common_Utils.showToast(status.getMessage());
//                Common_Utils.hideProgress();
//                return true;
//            } else {
//                Common_Utils.showToast(status.getMessage());
//                return false;
//            }
//        } else {
//            Common_Utils.showToast(AppConstant.SERVER_ERROR);
//            return false;
//        }
//    }

    private static OkHttpClient provideOkHttpClient() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(provideHttpLoggingInterceptor())
                //.addInterceptor(provideHeaderInterceptor())
                .build();

        return okHttpClient;
    }

    private static HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        HttpLoggingInterceptor httpLoggingInterceptor =
                new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Log.d("provideHttpLogIntercptr", message);
                    }
                });

        logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : NONE);
        httpLoggingInterceptor.setLevel(BuildConfig.DEBUG ? HEADERS : NONE);


        return logging;
    }

    public static Interceptor provideHeaderInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                String token = SharedPrefUtils.getToken(LungMonitorApp.getAppContext());

                Request request = chain.request().newBuilder().
                        header(AppConstant.AUTHORIZATION_TAG, token).
                        header("Accept", "application/json").
                        method(original.method(), original.body()).
                        build();

                return chain.proceed(request);
            }
        };
    }
}

