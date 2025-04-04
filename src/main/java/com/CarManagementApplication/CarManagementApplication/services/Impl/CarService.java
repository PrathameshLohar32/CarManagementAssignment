package com.CarManagementApplication.CarManagementApplication.services.Impl;

import com.CarManagementApplication.CarManagementApplication.dtos.ApiResponse;
import com.CarManagementApplication.CarManagementApplication.dtos.CreateCarRequest;
import com.CarManagementApplication.CarManagementApplication.entities.Car;
import com.CarManagementApplication.CarManagementApplication.entities.User;
import com.CarManagementApplication.CarManagementApplication.exceptions.ApiException;
import com.CarManagementApplication.CarManagementApplication.exceptions.ResourceNotFoundException;
import com.CarManagementApplication.CarManagementApplication.repositories.CarRepository;
import com.CarManagementApplication.CarManagementApplication.repositories.UserRepository;
import com.CarManagementApplication.CarManagementApplication.services.ICarService;
import com.CarManagementApplication.CarManagementApplication.services.ImageUploadInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CarService implements ICarService {

    @Autowired
    CarRepository carRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ImageUploadInterface imageUploadService;


    @Override
    public ResponseEntity<?> createCar(CreateCarRequest createCarRequest, List<MultipartFile> multipartFiles) {
        if(multipartFiles.size() >= 10){
            throw new ApiException("upload upto 10 images only");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> userOptional = userRepository.findById(username);
        if(userOptional.isEmpty()){
            throw new ResourceNotFoundException("Unauthorized !!");
        }
        Car car = new Car();
        car.setTitle(createCarRequest.getTitle());
        car.setDescription(createCarRequest.getDescription());
        car.setTags(createCarRequest.getTags());
        car.setUser(userOptional.get());
        List<String> images = new ArrayList<>();
        for(MultipartFile file : multipartFiles){
            Map uploadRes = imageUploadService.upload(file);
            String imageUrl = (String) uploadRes.get("url");
            images.add(imageUrl);
        }
        car.setImages(images);
        try {
            carRepository.save(car);
        }catch (Exception e){
            throw new ApiException("error while saving car "+e.getMessage());
        }
        return ResponseEntity.ok(new ApiResponse("car created succesfully",true));
    }
}
