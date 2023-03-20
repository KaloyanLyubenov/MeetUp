package com.example.meetup.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.UUID;

@Service
public class ImageCloudService {

    private Cloudinary cloudinary;

    public ImageCloudService() {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "ddwhij8d7",
                "api_key", "449948567675992",
                "api_secret", "m8C9BOMiM2Y7y0WVArOuOX6IlNo",
                "secure", true));
    }

    public String saveImage(MultipartFile multipartFile){
        String imageId = UUID.randomUUID().toString();
        Map params = ObjectUtils.asMap(
                "public_id", imageId,
                "overwrite", true,
                "resource_type", "image"
        );

        Map upload;

        File tmpFile = new File(imageId);
        try{
            Files.write(tmpFile.toPath(), multipartFile.getBytes());
            upload = cloudinary.uploader().upload(tmpFile, params);
            Files.delete((tmpFile.toPath()));
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        String url = upload.get("url").toString();

        return url;
    }
}
