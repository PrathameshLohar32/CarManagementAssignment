package com.CarManagementApplication.CarManagementApplication.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public interface ImageUploadInterface {
    public Map upload(MultipartFile multipartFile);
}
