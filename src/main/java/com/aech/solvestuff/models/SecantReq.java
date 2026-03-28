package com.aech.solvestuff.models;

import lombok.Data;

@Data
public class SecantReq {
  private double[] coeff;
  private double x0;
  private double x1;
  private IterationConfig iterConfig;
}
