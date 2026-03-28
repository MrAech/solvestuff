package com.aech.solvestuff.models;

import lombok.Data;

@Data
public class RapsonReq {
  private double[] coeff;
  private double initialGuess;
  private IterationConfig iterConfig;
}
