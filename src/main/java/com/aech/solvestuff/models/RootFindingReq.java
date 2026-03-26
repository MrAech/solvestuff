package com.aech.solvestuff.models;

import lombok.Data;

@Data
public class RootFindingReq {
  private double[] coeff;
  private double lowerB;
  private double upperB;
  private IterationConfig iterConfig;
}
