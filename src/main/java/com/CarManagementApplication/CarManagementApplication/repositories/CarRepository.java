package com.CarManagementApplication.CarManagementApplication.repositories;

import com.CarManagementApplication.CarManagementApplication.entities.Car;
import com.CarManagementApplication.CarManagementApplication.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByUser(User user);
    List<Car> findByTitleContainingOrDescriptionContainingOrTagsContaining(String title, String description, String tags);
}
