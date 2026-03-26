package com.aech.solvestuff.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.aech.solvestuff.models.IterationConfig;
import com.aech.solvestuff.models.LinearSysReq;
import com.aech.solvestuff.services.GaussSeidelService;

public class GaussSeidelTest {
  @Test
  void testSolveSimpleSystem() {
    double[][] matrix = {
        { 10, -1, 2, 0 },
        { -1, 11, -1, 3 },
        { 2, -1, 10, -1 },
        { 0, 3, -1, 8 }
    };
    double[] vector = { 6, 25, -11, 15 };
    LinearSysReq req = new LinearSysReq();
    req.setMatrix(matrix);
    req.setVector(vector);
    IterationConfig config = new IterationConfig();
    config.setMaxItrs(100);
    config.setTol(1e-6);
    req.setItrConfig(config);
    GaussSeidelService service = new GaussSeidelService();
    var response = service.solve(req);
    assertNull(response.getErr());
    assertTrue(response.isConverged());
    assertNotNull(response.getRes());
    double[] solution = (double[]) response.getRes().get("solution");
    assertEquals(1.0, solution[0], 1e-2);
    assertEquals(2.0, solution[1], 1e-2);
    assertEquals(-1.0, solution[2], 1e-2);
    assertEquals(1.0, solution[3], 1e-2);
  }
}
