package com.aech.solvestuff.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.aech.solvestuff.models.InterpolReq;
import com.aech.solvestuff.services.DividedService;

public class DividedTest {
  @Test
  void testInterpolateSimple() {
    // x: [1, 2, 3], y: [2, 3, 5], interpolate at 2.5
    double[] x = { 1, 2, 3 };
    double[] y = { 2, 3, 5 };
    InterpolReq req = new InterpolReq();
    req.setX(x);
    req.setY(y);
    req.setValue(2.5);
    DividedService service = new DividedService();
    var response = service.solve(req);
    assertNull(response.getErr());
    assertTrue(response.isConverged());
    assertNotNull(response.getRes());
    double val = (double) response.getRes().get("interpolatedValue");
    assertEquals(3.875, val, 1e-6);
  }

  @Test
  void testInvalidInput() {
    InterpolReq req = new InterpolReq();
    req.setX(new double[] { 1, 2 });
    req.setY(new double[] { 3 });
    req.setValue(1.5);
    DividedService service = new DividedService();
    var response = service.solve(req);
    assertNotNull(response.getErr());
    assertEquals("x and y must be same length and non-empty", response.getMessage());
  }

}
