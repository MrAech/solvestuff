package com.aech.solvestuff.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.aech.solvestuff.models.IterationConfig;
import com.aech.solvestuff.models.LinearSysReq;
import com.aech.solvestuff.services.GaussJordanService;

public class GaussJordanTest {
  @Test
  void testSolveSimpleSystem() {
    double[][] matrix = {
        { 2, 1, -1 },
        { -3, -1, 2 },
        { -2, 1, 2 }
    };
    double[] vector = { 8, -11, -3 };
    LinearSysReq req = new LinearSysReq();
    req.setMatrix(matrix);
    req.setVector(vector);
    req.setItrConfig(new IterationConfig());
    GaussJordanService service = new GaussJordanService();
    var response = service.solve(req);
    assertNull(response.getErr());
    assertEquals("Solved by Gauss-Jordan Elimination", response.getMessage());
    assertTrue(response.isConverged());
    assertEquals(1, response.getItrs());
    assertNotNull(response.getRes());
    double[] solution = (double[]) response.getRes().get("solution");
    assertEquals(2.0, solution[0], 1e-6);
    assertEquals(3.0, solution[1], 1e-6);
    assertEquals(-1.0, solution[2], 1e-6);
  }

  @Test
  void testSingularMatrixReturnsError() {
    double[][] matrix = {
        { 1, 2 },
        { 2, 4 }
    };
    double[] vector = { 3, 6 };
    LinearSysReq req = new LinearSysReq();
    req.setMatrix(matrix);
    req.setVector(vector);
    req.setItrConfig(new IterationConfig());
    GaussJordanService service = new GaussJordanService();
    var response = service.solve(req);
    assertNotNull(response.getErr());
    assertEquals("matrix is singular", response.getMessage());
  }
}
