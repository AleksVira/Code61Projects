package com.umbrellait.lab1_java;

public class NotImplementedException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Not implemented";
    }
}
