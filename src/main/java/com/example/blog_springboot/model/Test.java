package com.example.blog_springboot.model;

import lombok.Getter;
import lombok.Setter;


public class Test {

    private Long id;
    private String title;
    private String content;
    private byte[] image;

    public byte[] getImage (){
        return image;
    }

    public void setImage(byte[] image){
        this.image = image ;
    }
}
