package com.aech.solvestuff.services;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.aech.solvestuff.models.CommonResponse;
import com.aech.solvestuff.models.LinearSysReq;

@Service
public class GaussEliService {
    public CommonResponse<Map<String, Object>> solve(LinearSysReq request) {
        CommonResponse<Map<String, Object>> response = new CommonResponse<>();
        if (request.getMatrix() == null || request.getVector() == null) {
            response.setErr("Matrix and/or vector is null");
            response.setMessage("Invalid input");
            return response;
        }
        double[][] a = Arrays.stream(request.getMatrix()).map(double[]::clone).toArray(double[][]::new);
        double[] b = request.getVector().clone();
        int n = a.length;
        if (a.length != b.length) {
            response.setErr("Matrix and vector size mismatch");
            response.setMessage("Invalid input");
            return response;
        }
        for (int i = 0; i < n; i++) {
            if (Math.abs(a[i][i]) < 1e-12) {
                response.setErr("Zero diagonal element at row " + i);
                response.setMessage("Matrix is singular");
                return response;
            }
        }
        for (int i = 0; i < n; i++) {
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
                response.setErr("Matrix is singular or nearly singular");
                response.setMessage("Matrix is singular");
                return response;
            }
            for (int k = i + 1; k < n; k++) {
                double factor = a[k][i] / a[i][i];
                for (int j = i; j < n; j++) {
                    a[k][j] -= factor * a[i][j];
                }
                b[k] -= factor * b[i];
            }
        }
        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = b[i];
            for (int j = i + 1; j < n; j++) {
                sum -= a[i][j] * x[j];
            }
            x[i] = sum / a[i][i];
        }
        Map<String, Object> result = new HashMap<>();
        result.put("solution", Arrays.copyOf(x, n));
        response.setRes(result);
        response.setItrs(1);
        response.setConverged(true);
        response.setSteps(Collections.emptyList());
        response.setMessage("Solved by Gaussian elimination");
        return response;
    }
}


