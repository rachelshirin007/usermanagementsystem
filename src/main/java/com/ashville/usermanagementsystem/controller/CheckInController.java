package com.ashville.usermanagementsystem.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ashville.usermanagementsystem.services.CheckInServiceImpl;

@RestController
@RequestMapping("/checkin")
public class CheckInController {

    @Autowired
    private CheckInServiceImpl checkinService;

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
