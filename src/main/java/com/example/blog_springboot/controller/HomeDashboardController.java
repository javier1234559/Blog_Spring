package com.example.blog_springboot.controller;

import com.example.blog_springboot.dto.PostDetailDTO;
import com.example.blog_springboot.dto.StatisticDTO;
import com.example.blog_springboot.dto.UserDTO;
import com.example.blog_springboot.model.Post;
import com.example.blog_springboot.model.User;
import com.example.blog_springboot.service.CommentService;
import com.example.blog_springboot.service.PostService;
import com.example.blog_springboot.service.TopPostService;
import com.example.blog_springboot.service.UserService;
import com.example.blog_springboot.ultilies.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/admin")
public class HomeDashboardController {

    @Autowired
    private PostService postService;

    @Autowired
    private TopPostService topPostService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService ;

    @GetMapping("/error")
    public String ErrorDashboard() {
        return "dashboard/404";
    }

    @GetMapping({"","/"})
    public String HomeDashboard(Model model) {
        //GetBanner

        //GetTopPost
        List<PostDetailDTO> listPost = postService.getAllPostDetailDTO();
        List<PostDetailDTO> listTopPost = topPostService.getAllTopPostsDTO();
        model.addAttribute("listPost", listPost);
        model.addAttribute("listTopPost", listTopPost);

        //- CRUD o ben controlerr rieng
        return "dashboard/index";
    }

    @GetMapping("/accoundashboard")
    public String AccountDashboard(Model model) {
        //GetStatistic
        StatisticDTO statistic = new StatisticDTO();
        statistic.setPostCount(postService.getPostCount());
        statistic.setViewCount(postService.getViewCount());
        statistic.setCommentCount(commentService.getCommentCount());
        statistic.setPendingPostCount(postService.getPendingPostCount());
        model.addAttribute("statistic", statistic);

        //GetAllUserAccount
        List<UserDTO> listUserDTO = userService.getAllUsers();
        model.addAttribute("listUserDTO", listUserDTO);
        return "dashboard/accountdashboard";
    }

    @GetMapping("/createaccount")
    public String CreateNewAccount() {
        return "dashboard/createaccount";
    }

    @GetMapping("/updateaccount/{id}")
    public String UpdateAccount(@PathVariable("id") int id ,Model model ) {
        User updateUser = userService.getUserById(id);
        model.addAttribute("updateUser", updateUser);
        return "dashboard/updateaccount";
    }

    @GetMapping("/postdashboard")
    public String PostDashboard(Model model) {
        //GetALLPostPedding
        List<PostDetailDTO> listPostDetailDTO = postService.getAllPostDetailDTOByStatus(Constant.STATUS_POST_PENDDING);
        model.addAttribute("listPostDetailDTO", listPostDetailDTO);
        return "dashboard/postdashboard";
    }

    @GetMapping("/postaccepted")
    public String PostAccepted(Model model) {
        //GetALLPostAccepted
        List<PostDetailDTO> listPostDetailDTO = postService.getAllPostDetailDTOByStatus(Constant.STATUS_POST_ACCEPTED);
        model.addAttribute("listPostDetailDTO", listPostDetailDTO);
        return "dashboard/postaccepted";
    }

    @GetMapping("/postrejected")
    public String PostRejected(Model model) {
        //GetALLPostRejected
        List<PostDetailDTO> listPostDetailDTO = postService.getAllPostDetailDTOByStatus(Constant.STATUS_POST_REJECTED);
        model.addAttribute("listPostDetailDTO", listPostDetailDTO);
        return "dashboard/postrejected";
    }

    @GetMapping("/posts/action/{idpost}/{status}")
    public String handleAction(@PathVariable("idpost") int idpost, @PathVariable("status") String status) {

        switch (status) {
            case Constant.STATUS_POST_ACCEPTED:
                postService.changeStatus(idpost, Constant.STATUS_POST_ACCEPTED);
                return "redirect:/admin/postaccepted";
            case Constant.STATUS_POST_PENDDING:
                postService.changeStatus(idpost, Constant.STATUS_POST_PENDDING);
                return "redirect:/admin/postdashboard";
            case Constant.STATUS_POST_REJECTED:
                postService.changeStatus(idpost, Constant.STATUS_POST_REJECTED);
                return "redirect:/admin/postrejected";
            default:
                return "redirect:/admin/error";
        }
    }

}
