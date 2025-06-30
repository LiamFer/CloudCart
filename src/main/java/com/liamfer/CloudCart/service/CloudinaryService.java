package com.liamfer.CloudCart.service;

import com.cloudinary.Cloudinary;

public class CloudinaryService {
    private final Cloudinary cloudinary;
    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

//    public void addImage(){
//        cloudinary.uploader().upload();
//    }
}
