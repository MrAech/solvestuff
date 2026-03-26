package com.aech.solvestuff.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.aech.solvestuff.models.IterationConfig;
import com.aech.solvestuff.models.LinearSysReq;
import com.aech.solvestuff.services.JacobiService;

public class JacobiTest {

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

    JacobiService service = new JacobiService();
    var res = service.solve(req);
    assertNull(res.getErr());
    assertTrue(res.isConverged());
    assertNotNull(res.getRes());
    double[] solution = (double[]) res.getRes().get("solution");
    assertEquals(1.0, solution[0], 1e-2);
    assertEquals(2.0, solution[1], 1e-2);
    assertEquals(-1.0, solution[2], 1e-2);
    assertEquals(1.0, solution[3], 1e-2);

  }
}
