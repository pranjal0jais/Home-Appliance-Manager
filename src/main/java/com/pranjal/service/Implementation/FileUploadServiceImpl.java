package com.pranjal.service.Implementation;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
public class FileUploadServiceImpl {

    @Autowired
    private Cloudinary cloudinary;
    public Map<String, String> uploadFile(MultipartFile file) {
        try {
            Map upload = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap());
            String url = upload.get("secure_url").toString();
            String fileName = url.substring(url.lastIndexOf("/") + 1);
            String publicId = fileName.substring(0, fileName.lastIndexOf("."));
            log.info("Uploaded File: {}", publicId);
            return Map.of("secure_url", upload.get("secure_url").toString(), "public_id", publicId);
        }catch(IOException e){
            log.error(e.getLocalizedMessage());
        }
        return Map.of("secure_url", "Null", "public_id", "Null");
    }
    
    public String delete(String public_id) {
        try {
            Map delete = cloudinary.uploader().destroy(public_id, ObjectUtils.emptyMap());
            log.info(delete.toString());
            return delete.get("result").toString();
        }catch(IOException e){
            log.error(e.getLocalizedMessage());
        }
        return "Null";
    }
}
