package com.aech.solvestuff.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aech.solvestuff.models.CommonResponse;
import com.aech.solvestuff.models.LinearSysReq;
import com.aech.solvestuff.services.JacobiService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/jacobi")
public class JacobiController {

  @Autowired
  private JacobiService service;

  @PostMapping
  public ResponseEntity<?> solve(@RequestBody LinearSysReq req) {
    CommonResponse<?> res = service.solve(req);
    if (res.getErr() != null) {
      return ResponseEntity.badRequest().body(res);
    }

    return ResponseEntity.ok(res);
  }

}
