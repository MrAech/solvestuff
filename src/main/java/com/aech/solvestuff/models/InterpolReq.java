package com.aech.solvestuff.models;

import lombok.Data;

@Data
public class InterpolReq {
  private double[] x;
  private double[] y;
  private double value;
  private IterationConfig iterConfig;
}
