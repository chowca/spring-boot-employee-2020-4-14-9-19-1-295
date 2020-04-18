package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.ParkingBoy;
import com.thoughtworks.springbootemployee.repository.ParkingBoyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParkingBoyService {
    @Autowired
    private ParkingBoyRepository parkingBoyRepository;

    public ParkingBoy getParkingBoyById(Integer employeeId) {
        return parkingBoyRepository.findById(employeeId).orElse(null);
    }

    public ParkingBoy createNewParkingBoy(ParkingBoy parkingBoy) {
        return parkingBoyRepository.save(parkingBoy);
    }
}
