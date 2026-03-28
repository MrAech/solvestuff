package com.aech.solvestuff.controllers.iterative;

import java.net.StandardProtocolFamily;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aech.solvestuff.models.CommonResponse;
import com.aech.solvestuff.models.LinearSysReq;
import com.aech.solvestuff.services.GaussSeidelService;
import com.aech.solvestuff.services.JacobiService;

@RestController
@RequestMapping("/iterative")
public class IterativeMethodController {

  @Autowired
  private GaussSeidelService seidelService;

  @Autowired
  private JacobiService jacobiService;

  @PostMapping("/seidel")
  public ResponseEntity<?> solveSeidel(@RequestBody LinearSysReq req) {
    CommonResponse<?> res = seidelService.solve(req);

    if (res.getErr() != null) {
      return ResponseEntity.badRequest().body(res);
    }
    return ResponseEntity.ok(res);
  }

  @PostMapping("/jacobi")
  public ResponseEntity<?> solveJacobi(@RequestBody LinearSysReq req) {
    CommonResponse<?> res = jacobiService.solve(req);

    if (res.getErr() != null) {
      return ResponseEntity.badRequest().body(res);
    }

    return ResponseEntity.ok(res);
  }
}
