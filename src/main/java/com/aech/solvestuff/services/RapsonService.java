package com.aech.solvestuff.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.aech.solvestuff.models.CommonResponse;
import com.aech.solvestuff.models.RapsonReq;
import com.aech.solvestuff.models.Step;

@Service
public class RapsonService {
  public CommonResponse<Map<String, Object>> solve(RapsonReq req) {
    CommonResponse<Map<String, Object>> res = new CommonResponse<>();
    double[] coeff = req.getCoeff();
    double x = req.getInitialGuess();
    int maxIter = req.getIterConfig() != null ? req.getIterConfig().getMaxItrs() : 100;
    double tol = req.getIterConfig() != null ? req.getIterConfig().getTol() : 1e-8;
    if (coeff == null || coeff.length == 0) {
      res.setErr("Coefficients missing");
      res.setMessage("Invalid input");
      return res;
    }
    boolean logSteps = req.getIterConfig() != null && req.getIterConfig().isSteps();
    List<Step> steps = logSteps ? new ArrayList<>() : Collections.emptyList();
    boolean converged = false;
    int iter = 0;
    double dfx0 = evalPoly(derivative(coeff), x);
    if (Math.abs(dfx0) < 1e-12) {
      res.setErr("Zero derivative");
      res.setMessage("Derivative vanished");
      res.setConverged(false);
      Map<String, Object> result = new HashMap<>();
      result.put("root", x);
      res.setRes(result);
      res.setItrs(1);
      if (logSteps) {
        Step step = new Step();
        step.setItrs(1);
        step.setX(new double[] { x });
        step.setErr(Math.abs(evalPoly(coeff, x)));
        steps.add(step);
        res.setSteps(steps);
      }
      return res;
    }

    for (; iter < maxIter; iter++) {
      double fx = evalPoly(coeff, x);
      // DEFINITY !!!!!!
      double dfx = evalPoly(derivative(coeff), x);
      if (logSteps) {
        Step step = new Step();
        step.setItrs(iter + 1);
        step.setX(new double[] { x });
        step.setErr(Math.abs(fx));
        steps.add(step);
      }
      if (Math.abs(fx) < tol) {
        converged = true;
        break;
      }
      if (Math.abs(dfx) < 1e-12) {
        res.setErr("Zero derivative");
        res.setMessage("Derivative vanished");
        res.setConverged(false);
        Map<String, Object> result = new HashMap<>();
        result.put("root", x);
        res.setRes(result);
        res.setItrs(iter + 1);
        if (logSteps) {
          res.setSteps(steps);
        }
        return res;
      }
      x = x - fx / dfx;
    }

    Map<String, Object> result = new HashMap<>();
    result.put("root", x);
    res.setRes(result);
    res.setItrs(iter + 1);
    res.setConverged(converged);
    if (logSteps) {
      res.setSteps(steps);
    }
    if (res.getMessage() == null) {
      res.setMessage(converged ? "Converged successfully" : "Did not converge");
    }
    if (!converged && res.getErr() == null)
      res.setErr("Did not converge");
    return res;

  }

  private double evalPoly(double[] coeff, double x) {
    double res = 0;
    for (int i = 0; i < coeff.length; i++) {
      res += coeff[i] * Math.pow(x, coeff.length - 1 - i);
    }
    return res;
  }

  private double[] derivative(double[] coeff) {
    int n = coeff.length;
    if (n <= 1) {
      return new double[] { 0 };
    }
    double[] derv = new double[n - 1];
    for (int i = 0; i < n - 1; i++) {
      derv[i] = coeff[i] * (n - 1 - i);
    }

    return derv;
  }
}
