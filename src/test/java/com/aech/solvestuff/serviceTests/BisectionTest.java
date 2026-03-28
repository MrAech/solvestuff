package com.aech.solvestuff.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.aech.solvestuff.models.IterationConfig;
import com.aech.solvestuff.models.RootFindingReq;
import com.aech.solvestuff.services.BisectionService;

public class BisectionTest {
  @Test
  void testStepsIncludedWhenRequested() {
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
    BisectionService service = new BisectionService();
    var res = service.solve(req);
    assertNull(res.getErr());
    assertTrue(res.isConverged());
    assertNotNull(res.getSteps());
    assertTrue(res.getSteps().size() > 0);
    assertEquals(res.getItrs(), res.getSteps().size());
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
    BisectionService service = new BisectionService();
    var res = service.solve(req);
    assertNull(res.getErr());
    assertTrue(res.isConverged());
    assertNull(res.getSteps());
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
    BisectionService service = new BisectionService();
    var res = service.solve(req);
    assertNull(res.getErr());
    assertTrue(res.isConverged());
    assertNotNull(res.getRes());
    double root = (double) res.getRes().get("root");
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
    BisectionService service = new BisectionService();
    var res = service.solve(req);
    assertNotNull(res.getErr());
    assertEquals("No root bracketed", res.getMessage());
  }
}
