package com.aech.solvestuff.controllers.rootFinding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aech.solvestuff.services.BisectionService;
import com.aech.solvestuff.services.FalsiService;
import com.aech.solvestuff.services.RapsonService;
import com.aech.solvestuff.services.SecantService;
import com.aech.solvestuff.models.CommonResponse;
import com.aech.solvestuff.models.RootFindingReq;
import com.aech.solvestuff.models.SecantReq;
import com.aech.solvestuff.models.RapsonReq;

@RestController
@RequestMapping("/root")
public class RootFindingController {

  @Autowired
  private BisectionService bisectionService;

  @Autowired
  private FalsiService falsiService;

  @Autowired
  private RapsonService rapsonService;

  @Autowired
  private SecantService secantService;

  @PostMapping("/bisection")
  public ResponseEntity<?> solveBisection(@RequestBody RootFindingReq req) {
    CommonResponse<?> res = bisectionService.solve(req);

    if (res.getErr() != null) {
      return ResponseEntity.badRequest().body(res);
    }
    return ResponseEntity.ok(res);
  }

  @PostMapping("/falsi")
  public ResponseEntity<?> solveFalsi(@RequestBody RootFindingReq req) {
    CommonResponse<?> res = falsiService.solve(req);

    if (res.getErr() != null) {
      return ResponseEntity.badRequest().body(res);
    }
    return ResponseEntity.ok(res);
  }

  @PostMapping("/rapson")
  public ResponseEntity<?> solveRapson(@RequestBody RapsonReq req) {
    CommonResponse<?> res = rapsonService.solve(req);
    if (res.getErr() != null) {
      return ResponseEntity.badRequest().body(res);
    }

    return ResponseEntity.ok(res);
  }

  @PostMapping("/secant")
  public ResponseEntity<?> solveSecant(@RequestBody SecantReq req) {
    CommonResponse<?> res = secantService.solve(req);

    if (res.getErr() != null) {
      return ResponseEntity.badRequest().body(res);
    }
    return ResponseEntity.ok(res);
  }

}
