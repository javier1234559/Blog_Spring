package com.example.blog_springboot.controller;

import com.example.blog_springboot.dto.*;
import com.example.blog_springboot.model.Post;
import com.example.blog_springboot.model.TopPost;
import com.example.blog_springboot.model.User;
import com.example.blog_springboot.service.PostService;
import com.example.blog_springboot.service.TopPostService;
import com.example.blog_springboot.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeProductController {

    @Autowired
    private PostService postService;

    @Autowired
    private TopPostService topPostService;
    @Autowired
    private UserService userService ;

    @GetMapping("/")
    public String getAllPostsHome(Model model , Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
                return "redirect:/admin";
            } else {
                List<PostDetailDTO> listPost = postService.getAllPostDetailDTO();
                List<PostDetailDTO> listTopPost = topPostService.getAllTopPostsDTO();
                model.addAttribute("listPost", listPost);
                model.addAttribute("listTopPost", listTopPost);
                return "product/index";
            }
        } else {
            List<PostDetailDTO> listPost = postService.getAllPostDetailDTO();
            List<PostDetailDTO> listTopPost = topPostService.getAllTopPostsDTO();
            model.addAttribute("listPost", listPost);
            model.addAttribute("listTopPost", listTopPost);
            return "product/index";
        }
    }

    @GetMapping("/about")
    public String getAbout(){
        return "/product/about";
    }
    @GetMapping("/contact")
    public String getContact(){
        return "/product/contact";
    }
    @GetMapping("/createpost")
    public String getCreatePost(){
        return "/product/createpost";
    }

    @GetMapping("/login")
    public String getLogin(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
                return "redirect:/admin";
            } else {
                return "redirect:/";
            }
        } else {
            return "/product/login";
        }
    }

    @GetMapping("/register")
    public String getRegister(){
        return "/product/register";
    }

    @GetMapping("/forgotpass")
    public String getForgotPass(){
        return "/product/forgotpass";
    }

    @GetMapping("/updatepost")
    public String getPostSetting(){
        return "/product/updatepost";
    }

    @GetMapping("/usersetting")
    public String getUserSetting(){
        return "/product/usersetting";
    }

    @GetMapping("/posts/{id}")
    public String getPostById(@PathVariable("id") int id, Model model) {
        Optional<PostDetailDTO> optionalPost = Optional.ofNullable(postService.getPostById(id));
        if (optionalPost.isPresent()) {
            PostDetailDTO post = postService.getPostById(id);
            model.addAttribute("post", post);
            return "product/detailpost";
        } else {
            return "product/index";
        }
    }

//    @PostMapping ("/login")
//    public String Login(@ModelAttribute UserLoginDTO userdto , HttpSession session ) throws IOException {
//        System.out.print(userdto);
//        User loginuser = userService.getUserByEmailAndPassword(userdto);
//        if (loginuser == null) {
//            return "redirect:/login";
//        } else {
//            session.setAttribute("user", loginuser); // add user to session
//            return "redirect:/";
//        }
//    }


    @PostMapping ("/createnewpost")
    public String createPost(@ModelAttribute PostCreateDTO postdto ) throws IOException {
        postService.createPostDTO(postdto);
        return "redirect:/";
    }

//    @PostMapping("/createpost")
//    public String createPost(@RequestParam("image") MultipartFile image,
//                             @RequestParam("title") String title,
//                             @RequestParam("category") String category,
//                             @RequestParam("editorData") String editorData) {
//        Post post = new Post();
//        User currentUser = userService.getUserById(1);
//        try {
////            post.setDate(new Date());
//            post.setTitle(title);
//            post.setUser(currentUser);
//            post.setContent(editorData);
//            byte[] convertToByte = image.getBytes();
////            post.setImage(convertToByte);
//
//
//            return "redirect:/";
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return "redirect:/test/";
//    }



}
