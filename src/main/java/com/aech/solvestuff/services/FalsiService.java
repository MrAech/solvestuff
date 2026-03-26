package com.aech.solvestuff.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.aech.solvestuff.models.CommonResponse;
import com.aech.solvestuff.models.RootFindingReq;
import com.aech.solvestuff.models.Step;

@Service
public class FalsiService {

  public CommonResponse<Map<String, Object>> solve(RootFindingReq request) {
    CommonResponse<Map<String, Object>> response = new CommonResponse<>();
    double[] coeffs = request.getCoeff();
    double a = request.getLowerB();
    double b = request.getUpperB();
    int maxIter = request.getIterConfig() != null ? request.getIterConfig().getMaxItrs() : 100;
    double tol = request.getIterConfig() != null ? request.getIterConfig().getTol() : 1e-8;
    if (coeffs == null || coeffs.length == 0) {
      response.setErr("Coefficients missing");
      response.setMessage("Invalid input");
      return response;
    }
    if (a >= b) {
      response.setErr("Lower bound must be less than upper bound");
      response.setMessage("Invalid bounds");
      return response;
    }
    boolean logSteps = request.getIterConfig() != null && request.getIterConfig().isSteps();
    List<Step> steps = logSteps ? new ArrayList<>() : Collections.emptyList();
    double fa = evalPoly(coeffs, a);
    double fb = evalPoly(coeffs, b);
    if (fa * fb > 0) {
      response.setErr("Function has same sign at both bounds");
      response.setMessage("No root bracketed");
      return response;
    }
    double root = Double.NaN;
    boolean converged = false;
    double prev = a;
    int iter = 0;
    for (; iter < maxIter; iter++) {
      double mid = (a * fb - b * fa) / (fb - fa);
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
    response.setRes(result);
    response.setItrs(iter + 1);
    response.setConverged(converged);
    if (logSteps) {
      response.setSteps(steps);
    }
    response.setMessage(converged ? "Converged successfully" : "Did not converge");
    if (!converged)
      response.setErr("Did not converge");
    return response;
  }

  private double evalPoly(double[] coeffs, double x) {
    double res = 0;
    for (int i = 0; i < coeffs.length; i++) {
      res += coeffs[i] * Math.pow(x, coeffs.length - 1 - i);
    }
    return res;
  }
}
