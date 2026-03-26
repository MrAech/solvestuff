package com.aech.solvestuff.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aech.solvestuff.models.RapsonReq;
import com.aech.solvestuff.services.RapsonService;

@RestController
@RequestMapping("/rapson")
public class RapsonController {

  @Autowired
  private RapsonService service;

  @PostMapping
  public ResponseEntity<?> solve(@RequestBody RapsonReq req) {
    var res = service.solve(req);
    if (res.getErr() != null) {
      return ResponseEntity.badRequest().body(res);
    }
    return ResponseEntity.ok(res);
  }
}
