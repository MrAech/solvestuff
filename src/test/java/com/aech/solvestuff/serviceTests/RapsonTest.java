package com.aech.solvestuff.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.aech.solvestuff.models.IterationConfig;
import com.aech.solvestuff.models.RapsonReq;
import com.aech.solvestuff.services.RapsonService;

public class RapsonTest {
  @Test
  void testStepsIncludedWhenRequested() {
    double[] coeffs = { 1, 0, -1, -2 };
    RapsonReq req = new RapsonReq();
    req.setCoeff(coeffs);
    req.setInitialGuess(1.5);
    IterationConfig config = new IterationConfig();
    config.setMaxItrs(100);
    config.setTol(1e-8);
    config.setSteps(true);
    req.setIterConfig(config);
    RapsonService service = new RapsonService();
    var response = service.solve(req);
    assertNull(response.getErr());
    assertTrue(response.isConverged());
    assertNotNull(response.getSteps());
    assertTrue(response.getSteps().size() > 0);
    assertEquals(response.getItrs(), response.getSteps().size());
  }

  @Test
  void testStepsOmittedWhenNotRequested() {
    double[] coeffs = { 1, 0, -1, -2 };
    RapsonReq req = new RapsonReq();
    req.setCoeff(coeffs);
    req.setInitialGuess(1.5);
    IterationConfig config = new IterationConfig();
    config.setMaxItrs(100);
    config.setTol(1e-8);
    config.setSteps(false);
    req.setIterConfig(config);
    RapsonService service = new RapsonService();
    var response = service.solve(req);
    assertNull(response.getErr());
    assertTrue(response.isConverged());
    assertNull(response.getSteps());
  }

  @Test
  void testSolveSimpleRoot() {
    // x^3 - x - 2 = 0, root near 1.521
    double[] coeffs = { 1, 0, -1, -2 };
    RapsonReq req = new RapsonReq();
    req.setCoeff(coeffs);
    req.setInitialGuess(1.5);
    IterationConfig config = new IterationConfig();
    config.setMaxItrs(100);
    config.setTol(1e-8);
    req.setIterConfig(config);
    RapsonService service = new RapsonService();
    var response = service.solve(req);
    assertNull(response.getErr());
    assertTrue(response.isConverged());
    assertNotNull(response.getRes());
    double root = (double) response.getRes().get("root");
    assertEquals(1.521, root, 1e-3);
  }

  @Test
  void testZeroDerivative() {
    // f(x) = x^3, f'(0) = 0 yup ZERO so...
    double[] coeffs = { 1, 0, 0, 0 };
    RapsonReq req = new RapsonReq();
    req.setCoeff(coeffs);
    req.setInitialGuess(0);
    req.setIterConfig(new IterationConfig());
    RapsonService service = new RapsonService();
    var response = service.solve(req);
    assertNotNull(response.getErr());
    assertEquals("Derivative vanished", response.getMessage());
  }
}
