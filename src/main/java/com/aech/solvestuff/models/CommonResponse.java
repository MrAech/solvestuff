package com.aech.solvestuff.models;

import java.util.List;

import lombok.Data;

@Data
public class CommonResponse<T> {
    private T res;
    private int itrs;
    private boolean converged;
    private List<Step> steps;
    private String message;
    private String err;
}
