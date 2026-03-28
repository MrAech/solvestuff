package com.aech.solvestuff.services;

import java.util.*;

import org.springframework.stereotype.Service;

import com.aech.solvestuff.models.CommonResponse;
import com.aech.solvestuff.models.SecantReq;
import com.aech.solvestuff.models.Step;

import ch.qos.logback.core.boolex.EvaluationException;

@Service
public class SecantService {
  public CommonResponse<Map<String, Object>> solve(SecantReq req) {
    CommonResponse<Map<String, Object>> res = new CommonResponse<>();

    double[] coeffs = req.getCoeff();
    double x0 = req.getX0();
    double x1 = req.getX1();
    int maxIter = req.getIterConfig() != null ? req.getIterConfig().getMaxItrs() : 100;
    double tol = req.getIterConfig() != null ? req.getIterConfig().getTol() : 1e-8;
    if (coeffs == null || coeffs.length == 0) {
      res.setErr("Coefficients missing");
      res.setMessage("Invalid input");
      return res;
    }
    boolean logSteps = req.getIterConfig() != null && req.getIterConfig().isSteps();
    List<Step> steps = logSteps ? new ArrayList<>() : Collections.emptyList();
    boolean converged = false;
    double prev = x0;
    double curr = x1;
    int iter = 0;

    // double checking
    double fprev0 = evalPoly(coeffs, prev);
    double fcurr0 = evalPoly(coeffs, curr);
    if (Math.abs(curr - prev) < 1e-12 && Math.abs(fcurr0 - fprev0) < 1e-12) {
      res.setErr("Zero denominator");
      res.setMessage("Secant denominator vanished");
      res.setConverged(false);
      Map<String, Object> result = new HashMap<>();
      result.put("root", curr);
      res.setRes(result);
      res.setItrs(1);
      if (logSteps) {
        Step step = new Step();
        step.setItrs(1);
        step.setX(new double[] { curr });
        step.setErr(Math.abs(fcurr0));
        steps.add(step);
        res.setSteps(steps);
      }
      return res;
    }

    for (; iter < maxIter; iter++) {

      double fprev = evalPoly(coeffs, prev);
      double fcurr = evalPoly(coeffs, curr);

      if (Math.abs(fcurr - fprev) < 1e-12) {
        res.setErr("Zero denominator");
        res.setMessage("Secant denominator vanished");
        res.setConverged(false);
        Map<String, Object> result = new HashMap<>();
        result.put("root", curr);
        res.setRes(result);
        res.setItrs(iter + 1);
        Step step = new Step();
        step.setItrs(iter + 1);
        step.setX(new double[] { curr });
        step.setErr(Math.abs(fcurr));
        steps.add(step);
        res.setSteps(steps);
        return res;
      }
      double next = curr - fcurr * (curr - prev) / (fcurr - fprev);
      double error = Math.abs(next - curr);
      if (logSteps) {
        Step step = new Step();
        step.setItrs(iter + 1);
        step.setX(new double[] { next });
        step.setErr(error);
        steps.add(step);
      }
      if (Math.abs(evalPoly(coeffs, next)) < tol || error < tol) {
        curr = next;
        converged = true;
        break;
      }
      prev = curr;
      curr = next;
    }

    Map<String, Object> result = new HashMap<>();
    result.put("root", curr);
    res.setRes(result);
    res.setItrs(iter + 1);
    res.setConverged(converged);
    if (logSteps) {
      res.setSteps(steps);
    }

    if (res.getMessage() == null) {
      res.setMessage(converged ? "Converged successfully" : "Did not converge");
    }
    if (!converged && res.getErr() == null
        && (res.getMessage() == null || res.getMessage().equals("Did not converge"))) {
      res.setErr("Did not converge");
    }

    return res;
  }

  private double evalPoly(double[] coeff, double x) {
    double res = 0;
    for (int i = 0; i < coeff.length; i++) {
      res += coeff[i] * Math.pow(x, coeff.length - 1 - i);
    }
    return res;
  }
}
