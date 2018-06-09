package com.bloom.persianstory.model.entities.Response;

/**
 * Created by amir on 6/2/2018.
 */

public class OperationResult<ResultType> {
    public static final String EXCEPTION_OCCURED_MSG = "خطا در پردازش اطلاعات";


    private String ErrorMessage;

    public ResultType data;

//    public ResultType getResult() {
//        return data;
//    }
//
//    public void setResult(ResultType result) {
//        this.data = result;
//    }

    public OperationResult() {
    }

    public static boolean isOk(OperationResult r) {

        return  r != null && r.getErrorMessage()!=null && r.getData() != null;
    }


    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    public ResultType getData() {
        return data;
    }

    public void setData(ResultType data) {
        this.data = data;
    }


}