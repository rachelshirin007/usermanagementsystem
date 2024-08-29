package com.ashville.usermanagementsystem.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "checkin")
public class CheckIn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer checkin_id;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private OurUsers user;  // Assuming the user entity is OurUsers

    private LocalDateTime checkinTime;
    private LocalDateTime checkoutTime;  // New field for checkout

    // Constructors
    public CheckIn() {}

    public CheckIn(OurUsers user, LocalDateTime checkinTime) {
        this.user = user;
        this.checkinTime = checkinTime;
    }

    // Getters and setters
    public Integer getCheckInId() {
        return checkin_id;
    }

    public void setCheckInId(Integer checkin_id) {
        this.checkin_id = checkin_id;
    }

    public OurUsers getUser() {
        return user;
    }

    public void setUser(OurUsers user) {
        this.user = user;
    }

    public LocalDateTime getCheckinTime() {
        return checkinTime;
    }

    public void setCheckinTime(LocalDateTime checkinTime) {
        this.checkinTime = checkinTime;
    }

    public LocalDateTime getCheckoutTime() {
        return checkoutTime;
    }

    public void setCheckoutTime(LocalDateTime checkoutTime) {
        this.checkoutTime = checkoutTime;
    }
}
