package com.backend.tomato.controllers.v1;

import com.backend.tomato.services.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/cloudinary/upload")
@CrossOrigin("*")
public class CloudinaryController {

    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping
    public ResponseEntity<Map> uploadImage(@RequestParam("image")MultipartFile file){
        try{
            Map data=this.cloudinaryService.uploadImage(file);
            return new ResponseEntity<>(data, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
