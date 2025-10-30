package com.example.D0031N.Ladok;

public class ResultResponse {
    private String status;
    private String message;

    public ResultResponse() {}
    public ResultResponse(String status, String message) {
        this.status = status; this.message = message;
    }
    public String getStatus(){ return status; }
    public String getMessage(){ return message; }
    public void setStatus(String v){ this.status=v; }
    public void setMessage(String v){ this.message=v; }
}