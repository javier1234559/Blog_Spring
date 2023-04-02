package com.example.blog_springboot.controller;

import com.example.blog_springboot.model.Category;
import com.example.blog_springboot.model.Post;
import com.example.blog_springboot.model.User;
import com.example.blog_springboot.repository.PostRepository;
import com.example.blog_springboot.service.CategoryService;
import com.example.blog_springboot.service.PostService;
import com.example.blog_springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService ;
    @Autowired
    private CategoryService categoryService ;
    @Autowired
    private UserService userService ;

    @GetMapping(value = { "/", "/home" })
    public String getAllPosts(Model model) {
        List<Post> listPost = postService.getAllPosts();
        model.addAttribute("listPost", listPost);
        return "product/index";
    }

    @GetMapping("/{id}")
    public String getPostById(@PathVariable("id") int id, Model model) {
        Optional<Post> optionalPost = Optional.ofNullable(postService.getPostById(id));
        if (optionalPost.isPresent()) {
            Post post = postService.getPostById(id);
            model.addAttribute("post", post);
            return "product/detailpost";
        } else {
            return "product/index";
        }
    }

    @PostMapping("/createpost")
    public String createPost(@RequestParam("image") MultipartFile image,
                                           @RequestParam("title") String title,
                                           @RequestParam("category") String category,
                                           @RequestParam("editorData") String editorData) {
        Category newCategory = new Category();
        Post post = new Post();
        User currentUser = userService.getUserById(1);
        try {
            post.setDate(new Date());
            post.setTitle(title);
            post.setUser(currentUser);
            post.setContent(editorData);
            byte[] convertToByte = image.getBytes();
            post.setImage(convertToByte);

            Post savedpost = postService.createPost(post);
            newCategory.setPost(savedpost);
            newCategory.setName(category);
            categoryService.createCategory(newCategory);
            return "redirect:/";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/test/";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") int id) {
        Optional<Post> optionalPost = Optional.ofNullable(postService.getPostById(id));
        if (optionalPost.isPresent()) {
            postService.deletePost(postService.getPostById(id).getIdpost());
            return ResponseEntity.ok().body("Post has been deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable("id") int id, @RequestBody Post post) {
        Optional<Post> optionalPost = Optional.ofNullable(postService.getPostById(id));
        if (optionalPost.isPresent()) {
            Post existingPost = optionalPost.get();
            existingPost.setTitle(post.getTitle());
            existingPost.setContent(post.getContent());
            existingPost.setImage(post.getImage());
            existingPost.setView(post.getView());
            Post savedPost = postService.updatePost(id, post);
            return ResponseEntity.ok().body(savedPost);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}