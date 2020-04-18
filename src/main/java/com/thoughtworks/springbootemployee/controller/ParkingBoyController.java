package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.ParkingBoy;
import com.thoughtworks.springbootemployee.service.ParkingBoyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parking-boys")
public class ParkingBoyController {
    @Autowired
    private ParkingBoyService service;

    @GetMapping("/{employeeId}")
    public ResponseEntity<ParkingBoy> get(@PathVariable Integer employeeId) {
        ParkingBoy targetedParkingBoy = service.getParkingBoyById(employeeId);
        if (targetedParkingBoy != null) {
            return new ResponseEntity<>(targetedParkingBoy, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ParkingBoy> create(@RequestBody ParkingBoy parkingBoy) {
        return new ResponseEntity<>(service.createNewParkingBoy(parkingBoy), HttpStatus.CREATED);
    }
}
