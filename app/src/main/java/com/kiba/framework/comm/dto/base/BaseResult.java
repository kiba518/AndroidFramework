package com.kiba.framework.comm.dto.base;

public class BaseResult {
    public String UserNo;
    public String UserName;
    public String ArgIP;
    public int RecordCount;
    public Boolean IsError;
    public Boolean IsSuccess;
    public String Message;
    public Object Object;
    public String ObjectType;
    public String ResultType;
    public Object ObjectOther;
    public String ObjectTypeOther;
    public int Code;

    @Override
    public String toString() {
        return "BaseResult{" +
                "UserNo='" + UserNo + '\'' +
                ", UserName='" + UserName + '\'' +
                ", ArgIP='" + ArgIP + '\'' +
                ", RecordCount=" + RecordCount +
                ", IsError=" + IsError +
                ", IsSuccess=" + IsSuccess +
                ", Message='" + Message + '\'' +
                ", Object=" + Object +
                ", ObjectType='" + ObjectType + '\'' +
                ", ResultType='" + ResultType + '\'' +
                ", ObjectOther=" + ObjectOther +
                ", ObjectTypeOther='" + ObjectTypeOther + '\'' +
                ", Code=" + Code +
                '}';
    }

    public String getUserNo() {
        return UserNo;
    }

    public void setUserNo(String userNo) {
        UserNo = userNo;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getArgIP() {
        return ArgIP;
    }

    public void setArgIP(String argIP) {
        ArgIP = argIP;
    }

    public int getRecordCount() {
        return RecordCount;
    }

    public void setRecordCount(int recordCount) {
        RecordCount = recordCount;
    }

    public Boolean getError() {
        return IsError;
    }

    public void setError(Boolean error) {
        IsError = error;
    }

    public Boolean getSuccess() {
        return IsSuccess;
    }

    public void setSuccess(Boolean success) {
        IsSuccess = success;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public java.lang.Object getObject() {
        return Object;
    }

    public void setObject(java.lang.Object object) {
        Object = object;
    }

    public String getObjectType() {
        return ObjectType;
    }

    public void setObjectType(String objectType) {
        ObjectType = objectType;
    }

    public String getResultType() {
        return ResultType;
    }

    public void setResultType(String resultType) {
        ResultType = resultType;
    }

    public java.lang.Object getObjectOther() {
        return ObjectOther;
    }

    public void setObjectOther(java.lang.Object objectOther) {
        ObjectOther = objectOther;
    }

    public String getObjectTypeOther() {
        return ObjectTypeOther;
    }

    public void setObjectTypeOther(String objectTypeOther) {
        ObjectTypeOther = objectTypeOther;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }
}
