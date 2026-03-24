package com.aech.solvestuff.models;

import lombok.Data;

@Data
public class IterationConfig {
    private double tol;
    private int maxItrs;
    private boolean steps = false;
}
