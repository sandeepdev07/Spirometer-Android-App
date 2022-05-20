package com.yolohealth.spirometer.api;

import com.yolohealth.spirometer.model.SessionExpiredEvent;
import com.yolohealth.spirometer.model.commonresponse.ApiResponse;

import org.greenrobot.eventbus.EventBus;

import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {
    public static final String ErrorMessage = "Servers cannot be reached. Please try again";

    public static String parseError(retrofit2.Response<?> response) {

        if (response.code() == 401) {
            EventBus.getDefault().post(new SessionExpiredEvent(true));
            return "";
        }

        Converter<ResponseBody, ApiResponse> converter =
                RestClient.getRetrofit()
                        .responseBodyConverter(ApiResponse.class, new Annotation[0]);


        ApiResponse error;
        // Common_utils.hideProgress();
        try {
            error = converter.convert(response.errorBody());
            return error.getMessage();
        } catch (Exception e) {
            return ErrorMessage;
        }
    }

    public static String parseLoginError(Response<?> response) {

        Converter<ResponseBody, ApiResponse> converter =
                RestClient.getRetrofit()
                        .responseBodyConverter(ApiResponse.class, new Annotation[0]);


        ApiResponse error;
        // Common_utils.hideProgress();
        try {
            error = converter.convert(response.errorBody());
            return error.getMessage();
        } catch (Exception e) {
            return ErrorMessage;
        }
    }

//    public static void parseErrorThrow(Throwable throwable) {
//        if (throwable == null)
//            return;
//
//        if(Common_Utils.isNotNullOrEmpty(throwable.getMessage())){
//            Common_Utils.showToast(throwable.getMessage());
//        }
//    }
}

