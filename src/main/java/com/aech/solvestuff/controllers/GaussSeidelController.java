package com.aech.solvestuff.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aech.solvestuff.models.LinearSysReq;
import com.aech.solvestuff.services.GaussSeidelService;

@RestController
@RequestMapping("/gauss-seidel")
public class GaussSeidelController {
  @Autowired
  private GaussSeidelService gaussSeidelService;

  @PostMapping
  public ResponseEntity<?> solve(@RequestBody LinearSysReq request) {
    var res = gaussSeidelService.solve(request);
    if (res.getErr() != null) {
      return ResponseEntity.badRequest().body(res);
    }
    return ResponseEntity.ok(res);
  }
}
