package com.example.blog_springboot.controller;

import com.example.blog_springboot.dto.*;
import com.example.blog_springboot.model.Post;
import com.example.blog_springboot.model.TopPost;
import com.example.blog_springboot.model.User;
import com.example.blog_springboot.service.EmailService;
import com.example.blog_springboot.service.PostService;
import com.example.blog_springboot.service.TopPostService;
import com.example.blog_springboot.service.UserService;
import com.example.blog_springboot.ultilies.Constant;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeProductController {



    @Autowired
    private EmailService emailService;
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
                List<PostDetailDTO> listPost = postService.getAllPostDetailDTOByStatus(Constant.STATUS_POST_ACCEPTED);
                List<PostDetailDTO> listTopPost = topPostService.getAllTopPostsDTO();
                model.addAttribute("listPost", listPost);
                model.addAttribute("listTopPost", listTopPost);
                return "product/index";
            }
        } else {
            List<PostDetailDTO> listPost = postService.getAllPostDetailDTOByStatus(Constant.STATUS_POST_ACCEPTED);
            List<PostDetailDTO> listTopPost = topPostService.getAllTopPostsDTO();
            model.addAttribute("listPost", listPost);
            model.addAttribute("listTopPost", listTopPost);
            return "product/index";
        }
    }

    @GetMapping("/about")
    public String getAbout(){
        return "product/about";
    }

    @GetMapping("/contact")
    public String getContact(){
        return "product/contact";
    }

    @GetMapping("/createpost")
    public String getCreatePost(){
        return "product/createpost";
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
            return "product/login";
        }
    }

    @GetMapping("/register")
    public String getRegister(){
        return "product/register";
    }

    @GetMapping("/forgotpass")
    public String getForgotPass( ){
        return "product/forgotpass";
    }

    @GetMapping("/forgotpass/{email}")
    public String SendEmailCode(@PathVariable("email") String email,HttpSession session){
        System.out.println(email);
        String code = emailService.sendVerificationCode(email);
        session.setAttribute("code", code);
        return "product/forgotpass";
    }

    @GetMapping("/updatepost")
    public String getPostSetting(Model model,Principal principal){
        String email = principal.getName();
        List<PostDetailDTO> listPostDetailDTO = postService.getPostByEmailUser(email);
        model.addAttribute("listPostDetailDTO",listPostDetailDTO);
        return "product/updatepost";
    }

    @GetMapping("/usersetting")
    public String getUserSetting(Model model , Principal principal){
        UserDTO currentUser = userService.getUserByEmail(principal.getName());
        model.addAttribute("currentUser", currentUser);
        return "product/usersetting";
    }

    @GetMapping("/posts/{id}")
    public String getPostById(@PathVariable("id") int id, Model model , Authentication authentication) {
        Optional<PostDetailDTO> optionalPost = Optional.ofNullable(postService.getPostById(id));
        if (optionalPost.isPresent()) {
            PostDetailDTO post = postService.getPostById(id);
            List<PostDetailDTO> listTopPost = topPostService.getAllTopPostsDTO();
            model.addAttribute("post", post);
            model.addAttribute("listTopPost", listTopPost);

            //IncreateView
            postService.increaseView(post.getIdpost());

            // Add a boolean variable to the model that represents whether the user is authenticated or not
            boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
            model.addAttribute("isAuthenticated", isAuthenticated);
            return "product/detailpost";
        } else {
            return "product/index";
        }
    }

    @PostMapping ("/createnewpost")
    public ResponseEntity createPost(@ModelAttribute PostCreateDTO postdto ,Model model , Principal principal) throws IOException {
        postService.createPostDTO(postdto , principal);

//        String email = principal.getName();
//        List<PostDetailDTO> listPostDetailDTO = postService.getPostByEmailUser(email);
//        model.addAttribute("listPostDetailDTO",listPostDetailDTO);
//        return "updatepost";
//        String email = principal.getName();
//        List<PostDetailDTO> listPostDetailDTO = postService.getPostByEmailUser(email);

        // Assuming the post creation was successful
        return ResponseEntity.ok("{\"message\": \"Post created successfully\"}");
    }

    @GetMapping("/editposts/{id}")
    public String editPost(@PathVariable("id") int id, Model model) {
        PostDetailDTO updatepost = postService.getPostById(id);
        model.addAttribute("updatepost", updatepost);
        return "product/savepost";
    }


}
