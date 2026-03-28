package com.aech.solvestuff.controllers.direct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aech.solvestuff.models.LinearSysReq;
import com.aech.solvestuff.services.GaussEliService;
import com.aech.solvestuff.services.GaussJordanService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/direct")
public class DirectMethodController {

  @Autowired
  private GaussEliService eliService;

  @Autowired
  private GaussJordanService jordanService;

  @PostMapping("/gauss-elimination")
  public ResponseEntity<?> solveEli(@RequestBody LinearSysReq req) {
    var res = eliService.solve(req);
    if (res.getErr() != null) {
      return ResponseEntity.badRequest().body(res.getErr());
    }
    return ResponseEntity.ok(res);
  }

  @PostMapping("/gauss-jordan")
  public ResponseEntity<?> solveJordan(@RequestBody LinearSysReq req) {
    var res = jordanService.solve(req);
    if (res.getErr() != null) {
      return ResponseEntity.badRequest().body(res.getErr());
    }
    return ResponseEntity.ok(res);
  }
}
