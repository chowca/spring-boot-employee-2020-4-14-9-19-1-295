package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.ParkingBoy;
import com.thoughtworks.springbootemployee.service.ParkingBoyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking-boys")
public class ParkingBoyController {
    @Autowired
    private ParkingBoyService service;

    @GetMapping
    public ResponseEntity<List<ParkingBoy>> getAllParkingBoys() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingBoy> get(@PathVariable Integer id) {
        ParkingBoy targetedParkingBoy = service.getParkingBoyById(id);
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

    @PutMapping("/{id}")
    public ResponseEntity<ParkingBoy> update(@PathVariable Integer id, @RequestBody ParkingBoy updateParkingBoy) {
        ParkingBoy updatedParkingBoy = service.updateParkingBoy(id, updateParkingBoy);
        if (updatedParkingBoy != null) {
            return new ResponseEntity<>(updatedParkingBoy, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ParkingBoy> delete(@PathVariable Integer id) {
        ParkingBoy deletedParkingBoy = service.deleteParkingBoyById(id);
        if (deletedParkingBoy != null) {
            return new ResponseEntity<>(deletedParkingBoy, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
