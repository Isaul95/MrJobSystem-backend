package com.mrjob.dto;

public class Response {

    private int Code;
    private String Descripcion;

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }


    /**
     *  -----   Constructures  -----
     * @param code
     * @param descripcion
     */
    public Response(int code, String descripcion) {
        Code = code;
        Descripcion = descripcion;
    }

    public Response() {
    }
}
