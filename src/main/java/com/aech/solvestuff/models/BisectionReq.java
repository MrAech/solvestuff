package com.aech.solvestuff.models;

import lombok.Data;

@Data
public class BisectionReq {
  private double[] coeff;
  private double lowerB;
  private double upperB;
  private IterationConfig iterConfig;
}
