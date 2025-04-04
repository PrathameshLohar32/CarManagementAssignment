package com.CarManagementApplication.CarManagementApplication.services.Impl;

import com.CarManagementApplication.CarManagementApplication.exceptions.ApiException;
import com.CarManagementApplication.CarManagementApplication.services.ImageUploadInterface;
import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class ImageUploadService implements ImageUploadInterface {

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public Map upload(MultipartFile multipartFile) {
        try {
            Map data = this.cloudinary.uploader().upload(multipartFile.getBytes(),Map.of());
            return data;
        } catch (IOException e) {
            throw new ApiException("Image Upload fail");
        }
    }
}
