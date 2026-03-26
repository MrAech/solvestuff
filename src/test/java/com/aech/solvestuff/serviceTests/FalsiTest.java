package com.aech.solvestuff.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.aech.solvestuff.models.IterationConfig;
import com.aech.solvestuff.models.RootFindingReq;
import com.aech.solvestuff.services.FalsiService;

public class FalsiTest {

  @Test
  void testStepsIncludedWhenRequest() {
    double[] coeffs = { 1, 0, -1, -2 };
    RootFindingReq req = new RootFindingReq();
    req.setCoeff(coeffs);
    req.setLowerB(1);
    req.setUpperB(2);
    IterationConfig config = new IterationConfig();
    config.setMaxItrs(100);
    config.setTol(1e-8);
    config.setSteps(true);
    req.setIterConfig(config);
    FalsiService service = new FalsiService();
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
    RootFindingReq req = new RootFindingReq();
    req.setCoeff(coeffs);
    req.setLowerB(1);
    req.setUpperB(2);
    IterationConfig config = new IterationConfig();
    config.setMaxItrs(100);
    config.setTol(1e-8);
    config.setSteps(false);
    req.setIterConfig(config);
    FalsiService service = new FalsiService();
    var response = service.solve(req);
    assertNull(response.getErr());
    assertTrue(response.isConverged());
    assertNull(response.getSteps());
  }

  @Test
  void testSolveSimpleRoot() {
    double[] coeffs = { 1, 0, -1, -2 };
    RootFindingReq req = new RootFindingReq();
    req.setCoeff(coeffs);
    req.setLowerB(1);
    req.setUpperB(2);
    IterationConfig config = new IterationConfig();
    config.setMaxItrs(100);
    config.setTol(1e-8);
    req.setIterConfig(config);
    FalsiService service = new FalsiService();
    var response = service.solve(req);
    assertNull(response.getErr());
    assertTrue(response.isConverged());
    assertNotNull(response.getRes());
    double root = (double) response.getRes().get("root");
    assertEquals(1.521, root, 1e-3);
  }

  @Test
  void testNoRootBracketed() {
    double[] coeffs = { 1, 0, -1, -2 };
    RootFindingReq req = new RootFindingReq();
    req.setCoeff(coeffs);
    req.setLowerB(3);
    req.setUpperB(4);
    req.setIterConfig(new IterationConfig());
    FalsiService service = new FalsiService();
    var response = service.solve(req);
    assertNotNull(response.getErr());
    assertEquals("No root bracketed", response.getMessage());
  }

}
