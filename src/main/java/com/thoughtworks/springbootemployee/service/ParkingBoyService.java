package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.ParkingBoy;
import com.thoughtworks.springbootemployee.repository.ParkingBoyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingBoyService {
    @Autowired
    private ParkingBoyRepository parkingBoyRepository;

    public List<ParkingBoy> getAll() {
        return parkingBoyRepository.findAll();
    }

    public ParkingBoy getParkingBoyById(Integer id) {
        return parkingBoyRepository.findById(id).orElse(null);
    }

    public ParkingBoy createNewParkingBoy(ParkingBoy parkingBoy) {
        return parkingBoyRepository.save(parkingBoy);
    }


    public ParkingBoy updateParkingBoy(Integer id, ParkingBoy updateParkingBoy) {
        ParkingBoy existedParkingBoy = parkingBoyRepository.findById(id).orElse(null);
        if (existedParkingBoy == null) {
            return null;
        } else {
            existedParkingBoy.setNickName(updateParkingBoy.getNickName());
            return parkingBoyRepository.save(existedParkingBoy);
        }
    }

    public ParkingBoy deleteParkingBoyById(Integer id) {
        ParkingBoy parkingBoy = parkingBoyRepository.findById(id).orElse(null);
        if (parkingBoy == null) {
            return null;
        } else {
            parkingBoyRepository.delete(parkingBoy);
            return parkingBoy;
        }
    }
}
