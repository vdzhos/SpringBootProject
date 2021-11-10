package com.sbproject.schedule.exceptions.handlers;

public class ErrorMessage {
    String success;
    String error;


    public ErrorMessage(String success, String error) {
        this.success = success;
        this.error = error;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
