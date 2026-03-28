// HAHAHAHA "InterPol" get it !! 
package com.aech.solvestuff.controllers.interpol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aech.solvestuff.models.CommonResponse;
import com.aech.solvestuff.models.InterpolReq;
import com.aech.solvestuff.services.DividedService;
import com.aech.solvestuff.services.LagrangeService;

@RestController
@RequestMapping("/interpol")
public class InterpolController {

  @Autowired
  private LagrangeService lagrangeService;

  @Autowired
  private DividedService dividedService;

  @PostMapping("/lagrange")
  public ResponseEntity<?> solveInterpol(@RequestBody InterpolReq req) {
    CommonResponse<?> res = lagrangeService.solve(req);

    if (res.getErr() != null) {
      return ResponseEntity.badRequest().body(res);
    }
    return ResponseEntity.ok(res);
  }

  @PostMapping("/divided")
  public ResponseEntity<?> solveDivided(@RequestBody InterpolReq req) {
    CommonResponse<?> res = dividedService.solve(req);

    if (res.getErr() != null) {
      return ResponseEntity.badRequest().body(res);
    }

    return ResponseEntity.ok(res);
  }
}
