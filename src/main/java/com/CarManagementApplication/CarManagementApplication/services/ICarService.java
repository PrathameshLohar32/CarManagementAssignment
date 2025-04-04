package com.CarManagementApplication.CarManagementApplication.services;

import com.CarManagementApplication.CarManagementApplication.dtos.CreateCarRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ICarService {
    ResponseEntity<?> createCar(CreateCarRequest createCarRequest, List<MultipartFile> multipartFile);
}
