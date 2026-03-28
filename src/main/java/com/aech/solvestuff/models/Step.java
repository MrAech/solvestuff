package com.aech.solvestuff.models;

import lombok.Data;

@Data
public class Step {
  private int itrs;
  private double[] x;
  private double err;

  private double[] residual;
  private double residualNorm;

  private String desc;
  private Double value;
}
