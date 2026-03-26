package com.aech.solvestuff.services;

import java.util.*;

import org.springframework.stereotype.Service;

import com.aech.solvestuff.models.BisectionReq;
import com.aech.solvestuff.models.CommonResponse;
import com.aech.solvestuff.models.Step;

@Service
public class BisectionService {
  public CommonResponse<Map<String, Object>> solve(BisectionReq req) {
    CommonResponse<Map<String, Object>> res = new CommonResponse<>();
    double[] coeffs = req.getCoeff();
    double a = req.getLowerB();
    double b = req.getUpperB();
    int maxIter = req.getIterConfig() != null ? req.getIterConfig().getMaxItrs() : 100;
    double tol = req.getIterConfig() != null ? req.getIterConfig().getTol() : 1e-8;
    if (coeffs == null || coeffs.length == 0) {
      res.setErr("Coefficients missing");
      res.setMessage("Invalid input");
      return res;
    }

    if (a >= b) {
      res.setErr("Lower bound must be less than upper bound");
      res.setMessage("Invalid bounds");
      return res;
    }

    boolean logSteps = req.getIterConfig() != null && req.getIterConfig().isSteps();
    List<Step> steps = logSteps ? new ArrayList<>() : Collections.emptyList();
    double fa = evalPoly(coeffs, a);
    double fb = evalPoly(coeffs, b);
    if (fa * fb > 0) {
      res.setErr("Function has same sign at both bounds");
      res.setMessage("No root bracketed");
      return res;
    }

    double root = Double.NaN;
    boolean converged = false;
    double prev = a;
    int iter = 0;
    for (; iter < maxIter; iter++) {
      double mid = (a + b) / 2.0;
      double fmid = evalPoly(coeffs, mid);
      double error = Math.abs(mid - prev);
      if (logSteps) {
        Step step = new Step();
        step.setItrs(iter + 1);
        step.setX(new double[] { mid });
        step.setErr(error);
        steps.add(step);
      }
      if (Math.abs(fmid) < tol || error < tol) {
        root = mid;
        converged = true;
        break;
      }
      if (fa * fmid < 0) {
        b = mid;
        fb = fmid;
      } else {
        a = mid;
        fa = fmid;
      }
      prev = mid;
    }
    Map<String, Object> result = new HashMap<>();
    result.put("root", root);
    res.setRes(result);
    res.setItrs(iter + 1);
    res.setConverged(converged);
    if (logSteps) {
      res.setSteps(steps);
    }
    res.setMessage(converged ? "Converged successfully" : "Did not converge");
    if (!converged)
      res.setErr("Did not converge");
    return res;
  }

  private double evalPoly(double[] coeffs, double x) {
    double res = 0;
    for (int i = 0; i < coeffs.length; i++) {
      res += coeffs[i] * Math.pow(x, coeffs.length - 1 - i);
    }
    return res;
  }
}
