package com.example.blog_springboot.controller;

import com.example.blog_springboot.dto.PostCreateDTO;
import com.example.blog_springboot.dto.PostDetailDTO;
import com.example.blog_springboot.dto.PostSearchDTO;
import com.example.blog_springboot.model.Post;
import com.example.blog_springboot.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/posts")
//@CrossOrigin(origins = "http://localhost:8080")
public class PostController {

    @Autowired
    private PostService postService ;

    @GetMapping

    public ResponseEntity<List<PostSearchDTO>> getAllPostSearchDTO() {
        return ResponseEntity.ok(postService.getAllPostSearchDTO());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDetailDTO> getPostById(@PathVariable int id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

//    @PostMapping
//    public ResponseEntity<Post> createPost(@RequestBody Post post ){
//        return ResponseEntity.ok(postService.createPost(post));
//    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") int id) {
        return ResponseEntity.ok(postService.deletePost(id));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updatePost(
            @PathVariable("id") int id,
            @RequestParam("title") String title,
            @RequestParam("category") String category,
            @RequestParam("content") String content,
            @RequestPart("imagefile") MultipartFile imagefile
    ) {
        PostCreateDTO postdto = new PostCreateDTO();
        postdto.setTitle(title);
        postdto.setCategory(category);
        postdto.setContent(content);
        postdto.setData(imagefile);

        postService.updatePost(id, postdto);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Update successful");

        return ResponseEntity.ok(response);
    }


}