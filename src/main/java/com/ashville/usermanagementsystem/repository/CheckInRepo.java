package com.ashville.usermanagementsystem.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import com.ashville.usermanagementsystem.entity.*;;

@Repository
@EnableJpaRepositories(basePackages = "com.ashville.usermanagementsystem.repository")
public interface CheckInRepo extends JpaRepository<CheckIn, Integer> {

    Optional<CheckIn> findFirstByUserOrderByCheckinTimeDesc(OurUsers user);  // Fetch the latest check-in for a user
}
