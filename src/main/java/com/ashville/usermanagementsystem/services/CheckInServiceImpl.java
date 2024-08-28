package com.ashville.usermanagementsystem.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashville.usermanagementsystem.entity.CheckIn;
import com.ashville.usermanagementsystem.entity.OurUsers;
import com.ashville.usermanagementsystem.repository.CheckInRepo;
import com.ashville.usermanagementsystem.repository.UsersRepo;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CheckInServiceImpl {

    @Autowired
    private CheckInRepo checkInRepo;

    @Autowired
    private UsersRepo usersRepo;

    public String checkIn(Integer userId) {
        Optional<OurUsers> userOpt = usersRepo.findById(userId);
        if (userOpt.isPresent()) {
            OurUsers user = userOpt.get();
            CheckIn checkin = new CheckIn(user, LocalDateTime.now());
            checkInRepo.save(checkin);
            return "Check-in successful!";
        } else {
            return "User not found!";
        }
    }

    public String checkOut(Integer userId) {
        Optional<OurUsers> userOpt = usersRepo.findById(userId);
        if (userOpt.isPresent()) {
            OurUsers user = userOpt.get();
            Optional<CheckIn> lastCheckinOpt = checkInRepo.findFirstByUserOrderByCheckinTimeDesc(user);
            if (lastCheckinOpt.isPresent()) {
                CheckIn lastCheckin = lastCheckinOpt.get();
                if (lastCheckin.getCheckoutTime() == null) {
                    lastCheckin.setCheckoutTime(LocalDateTime.now());
                    checkInRepo.save(lastCheckin);
                    return "Check-out successful!";
                } else {
                    return "You have already checked out!";
                }
            } else {
                return "No check-in found for today!";
            }
        } else {
            return "User not found!";
        }
    }
}
