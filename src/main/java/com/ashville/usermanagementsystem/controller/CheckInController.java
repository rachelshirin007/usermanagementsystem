package com.ashville.usermanagementsystem.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ashville.usermanagementsystem.services.CheckInService;

@RestController
@RequestMapping("/api/checkin")
public class CheckInController {

    @Autowired
    private CheckInService checkinService;

    @PostMapping("/user/checkin/{userId}")
    public ResponseEntity<String> checkIn(@PathVariable Integer userId) {
        String response = checkinService.checkIn(userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/user/checkout/{userId}")
    public ResponseEntity<String> checkOut(@PathVariable Integer userId) {
        String response = checkinService.checkOut(userId);
        return ResponseEntity.ok(response);
    }
}
