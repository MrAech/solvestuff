package com.aech.solvestuff.services;

import java.util.*;

import org.springframework.stereotype.Service;

import com.aech.solvestuff.models.CommonResponse;
import com.aech.solvestuff.models.IterationConfig;
import com.aech.solvestuff.models.LinearSysReq;
import com.aech.solvestuff.models.Step;

@Service
public class GaussSeidelService {
  public CommonResponse<Map<String, Object>> solve(LinearSysReq req) {
    CommonResponse<Map<String, Object>> res = new CommonResponse<>();

    double[][] a = req.getMatrix();
    double[] b = req.getVector();

    IterationConfig config = req.getItrConfig();
    int n = a.length;

    if (a == null || b == null || a.length != b.length) {
      res.setErr("Matrix and vector size mismatch or null");
      res.setMessage("Invalid input");
      return res;
    }

    for (int i = 0; i < n; i++) {
      if (Math.abs(a[i][i]) < 1e-12) {
        res.setErr("Zero diagonal element at row " + i);
        res.setMessage("Matrix is not diagonally dominant or singular");
        return res;
      }
    }

    double[] x = new double[n];
    Arrays.fill(x, 0.0);
    int maxIter = config != null ? config.getMaxItrs() : 100;
    double tol = config != null ? config.getTol() : 1e-8;
    boolean logSteps = config != null && config.isSteps();
    List<Step> steps = logSteps ? new ArrayList<>() : Collections.emptyList();
    boolean converged = false;
    double prevErr = Double.MAX_VALUE;
    int iter = 0;
    for (; iter < maxIter; iter++) {
      double err = 0.0;
      for (int i = 0; i < n; i++) {
        double sum = b[i];
        for (int j = 0; j < n; j++) {
          if (j != i)
            sum -= a[i][j] * x[j];
        }
        double xOld = x[i];
        x[i] = sum / a[i][i];
        err = Math.max(err, Math.abs(x[i] - xOld));
      }
      if (logSteps) {
        Step step = new Step();
        step.setItrs(iter + 1);
        step.setX(Arrays.copyOf(x, n));
        step.setErr(err);
        // Compute residual r = Ax - b
        double[] residual = new double[n];
        double norm = 0.0;
        for (int k = 0; k < n; k++) {
          double sum = 0.0;
          for (int j = 0; j < n; j++) {
            sum += a[k][j] * x[j];
          }
          residual[k] = sum - b[k];
          norm += residual[k] * residual[k];
        }
        norm = Math.sqrt(norm);
        step.setResidual(residual);
        step.setResidualNorm(norm);
        steps.add(step);
      }
      if (err < tol) {
        converged = true;
        break;
      }
      if (err > prevErr && iter > 0) {
        res.setMessage("Divergence detected");
        break;
      }
      prevErr = err;
    }

    Map<String, Object> result = new HashMap<>();
    result.put("solution", Arrays.copyOf(x, n));
    res.setRes(result);
    res.setItrs(iter + 1);
    res.setConverged(converged);
    if (logSteps) {
      res.setSteps(steps);
    }
    res.setMessage(
        converged ? "Converged successfully" : (res.getMessage() != null ? res.getMessage() : "Did not converge"));
    return res;
  }
}
