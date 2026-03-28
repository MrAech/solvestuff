package com.aech.solvestuff.services;

import java.util.*;

import org.springframework.stereotype.Service;

import com.aech.solvestuff.models.CommonResponse;
import com.aech.solvestuff.models.InterpolReq;
import com.aech.solvestuff.models.Step;

@Service
public class LagrangeService {
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
    double result = 0;
    boolean logSteps = req.getIterConfig() != null && req.getIterConfig().isSteps();
    List<Step> steps = logSteps ? new ArrayList<>() : Collections.emptyList();

    for (int i = 0; i < n; i++) {
      double term = y[i];
      double denom = 1.0;
      double numer = 1.0;
      for (int j = 0; j < n; j++) {
        if (j != i) {
          numer *= (value - x[j]);
          denom *= (x[i] - x[j]);
        }
      }
      double li = numer / denom;
      double contrib = term * li;
      result += contrib;
      if (logSteps) {
        Step step = new Step();
        step.setItrs(i + 1);
        step.setX(new double[] { x[i] });
        step.setErr(Math.abs(contrib));
        step.setDesc(
            String.format("Lagrange basis L_%d(x): %.4f, contribution: y[%d]*L_%d(x) = %.4f", i, li, i, i, contrib));
        step.setValue(contrib);
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
    res.setMessage("Lagrange interpolation successful");
    return res;
  }
}
