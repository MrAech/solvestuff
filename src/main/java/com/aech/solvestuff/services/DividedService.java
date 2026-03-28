package com.aech.solvestuff.services;

import java.util.*;

import org.springframework.stereotype.Service;

import com.aech.solvestuff.models.CommonResponse;
import com.aech.solvestuff.models.InterpolReq;
import com.aech.solvestuff.models.Step;

@Service
public class DividedService {

  public CommonResponse<Map<String, Object>> solve(InterpolReq req) {
    CommonResponse<Map<String, Object>> res = new CommonResponse<>();

    double[] x = req.getX();
    double[] y = req.getY();
    double value = req.getValue();
    if (x == null || y == null || x.length != y.length || x.length == 0) {
      res.setErr("Invalid input arrays");
      res.setMessage("x and y must be same length and non-empty");
      return res;
    }
    int n = x.length;
    double[][] table = new double[n][n];
    for (int i = 0; i < n; i++) {
      table[i][0] = y[i];
    }
    for (int j = 1; j < n; j++) {
      for (int i = 0; i < n - j; i++) {
        table[i][j] = (table[i + 1][j - 1] - table[i][j - 1]) / (x[i + j] - x[i]);
      }
    }
    double result = table[0][0];
    double prod = 1.0;
    boolean logSteps = req.getIterConfig() != null && req.getIterConfig().isSteps();
    List<Step> steps = logSteps ? new ArrayList<>() : Collections.emptyList();
    for (int i = 1; i < n; i++) {
      prod *= (value - x[i - 1]);
      double term = table[0][i] * prod;
      result += term;
      if (logSteps) {
        Step step = new Step();
        step.setItrs(i);
        step.setX(new double[] { x[i] });
        step.setErr(Math.abs(term));
        step.setDesc(
            String.format("Divided diff term: f[x0..x%d]=%.4f, prod=%.4f, contrib=%.4f", i, table[0][i], prod, term));
        step.setValue(term);
        steps.add(step);
      }
    }

    Map<String, Object> resMap = new HashMap<>();
    resMap.put("interpolatedValue", result);
    res.setRes(resMap);
    res.setItrs(n);
    res.setConverged(true);
    if (logSteps) {
      res.setSteps(steps);
    }
    res.setMessage("Newton Divided Difference interpolation successful");
    return res;
  }
}
