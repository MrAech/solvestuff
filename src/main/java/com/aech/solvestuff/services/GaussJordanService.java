package com.aech.solvestuff.services;

import org.springframework.stereotype.Service;

import com.aech.solvestuff.models.CommonResponse;
import com.aech.solvestuff.models.LinearSysReq;

import java.util.*;

@Service
public class GaussJordanService {
  public CommonResponse<Map<String, Object>> solve(LinearSysReq req) {
    CommonResponse<Map<String, Object>> res = new CommonResponse<>();

    double[][] a = Arrays.stream(req.getMatrix())
        .map(double[]::clone).toArray(double[][]::new);
    double[] b = req.getVector().clone();
    int n = a.length;

    if (a == null || b == null || a.length != b.length) {
      res.setErr("Matrix and vector size mismatch or null");
      res.setMessage("Invalid Input");
      return res;
    }
    for (int i = 0; i < n; i++) {
      if (Math.abs(a[i][i]) < 1e-12) {
        res.setErr("Zero diagonal element at row " + i);
        res.setMessage("Matrix is singular");
        return res;
      }
    }

    for (int i = 0; i < n; i++) {

      // TODO: I do realise this is same code as Gauss Eli. and can be taken out as a
      // common fun. but will do that later
      int max = i;
      for (int k = i + 1; k < n; k++) {
        if (Math.abs(a[k][i]) > Math.abs(a[max][i])) {
          max = k;
        }
      }
      double[] temp = a[i];
      a[i] = a[max];
      a[max] = temp;
      double t = b[i];
      b[i] = b[max];
      b[max] = t;

      if (Math.abs(a[i][i]) < 1e-12) {
        res.setErr("Matrix is singular ie diagonal are zero");
        res.setMessage("matrix is singular");
        return res;
      }

      double pvt = a[i][i];
      for (int j = 0; j < n; j++) {
        a[i][j] /= pvt;
      }
      b[i] /= pvt;

      for (int k = 0; k < n; k++) {
        if (k == i)
          continue;
        double factor = a[k][i];
        for (int j = 0; j < n; j++) {
          a[k][j] -= factor * a[i][j];
        }
        b[k] -= factor * b[i];
      }
    }

    Map<String, Object> result = new HashMap<>();
    result.put("solution", Arrays.copyOf(b, n));
    res.setRes(result);
    res.setItrs(1);
    res.setConverged(true);
    res.setSteps(Collections.emptyList());
    res.setMessage("Solved by Gauss-Jordan Elimination");
    return res;
  }
}
