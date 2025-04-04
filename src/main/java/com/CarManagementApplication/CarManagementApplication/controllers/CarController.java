package com.CarManagementApplication.CarManagementApplication.controllers;

import com.CarManagementApplication.CarManagementApplication.dtos.CreateCarRequest;
import com.CarManagementApplication.CarManagementApplication.services.ICarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {

    @Autowired
    private ICarService carService;

    @PostMapping("/createcar")
    public ResponseEntity<?> createCar(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tags") List<String> tags,
            @RequestParam("files") List<MultipartFile> files) {

        CreateCarRequest createCarRequest = new CreateCarRequest();
        createCarRequest.setTitle(title);
        createCarRequest.setDescription(description);
        createCarRequest.setTags(tags);

        return carService.createCar(createCarRequest, files);
    }
}
