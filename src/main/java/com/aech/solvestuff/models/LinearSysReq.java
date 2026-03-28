package com.aech.solvestuff.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LinearSysReq {
  @Schema(description = "Coefficient matrix of the linear system", example = "[[1,1],[3,-2]]")
  private double[][] matrix;
  @Schema(description = "Constant vector of the linear system", example = "[3,4]")
  private double[] vector;
  private IterationConfig itrConfig;
  // the ans for this btw is 2,1
}
