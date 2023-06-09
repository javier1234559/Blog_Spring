package com.example.blog_springboot.service.impl;
import com.example.blog_springboot.dto.PostCreateDTO;
import com.example.blog_springboot.dto.PostDetailDTO;
import com.example.blog_springboot.dto.PostSearchDTO;
import com.example.blog_springboot.exception.ResourceExistException;
import com.example.blog_springboot.exception.ResourceNotFoundException;
import com.example.blog_springboot.model.Comment;
import com.example.blog_springboot.model.Post;
import com.example.blog_springboot.model.User;
import com.example.blog_springboot.repository.CommentRepository;
import com.example.blog_springboot.repository.PostRepository;
import com.example.blog_springboot.repository.UserRepository;
import com.example.blog_springboot.service.PictureStoredService;
import com.example.blog_springboot.service.PostService;
import com.example.blog_springboot.ultilies.Constant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.Principal;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    @Autowired
    private  PostRepository postRepository;

    @Autowired
    private UserRepository userRepository ;

    @Autowired
    private CommentRepository commentRepository ;

    @Autowired
    private  ModelMapper mapper;

    @Autowired
    private PictureStoredService pictureStoredService ;

    @Override
    public int getPostCount() {
        List<Post> posts = postRepository.findAll();
        return posts.size();
    }

    @Override
    public int getViewCount() {
        List<Post> posts = postRepository.findAll();
        int totalViews = 0;
        for (Post post : posts) {
            totalViews += post.getView();
        }
        return totalViews;
    }


    @Override
    public int getPendingPostCount() {
        List<Post> pendingPosts = postRepository.findByStatus(Constant.STATUS_POST_PENDDING);
        return pendingPosts.size();
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll() ;
    }


    @Override
    public List<PostDetailDTO> getPostByEmailUser(String email) {
        List<Post> list = postRepository.findPostsByUserEmail(email);
        List<PostDetailDTO> dtoList = list.stream()
                .map(post -> {
                    PostDetailDTO postDetailDTO = mapper.map(post, PostDetailDTO.class);
                    int listCommentToCount = commentRepository.getCommentQuantityByPost(post.getIdpost()) ;
                    postDetailDTO.setCommentQuantity(listCommentToCount);
                    return  postDetailDTO ;
                })
                .collect(Collectors.toList());
        return dtoList;
    }

    @Override
    public List<PostSearchDTO> getAllPostSearchDTO() {
        List<Post> list = postRepository.findAll();
        List<PostSearchDTO> dtoList = list.stream()
                .map(post -> mapper.map(post, PostSearchDTO.class))
                .collect(Collectors.toList());
        return dtoList;
    }

    @Override
    public List<PostDetailDTO> getAllPostDetailDTO() {
        List<Post> list = postRepository.findAll();
        List<PostDetailDTO> dtoList = list.stream()
                .map(post -> mapper.map(post, PostDetailDTO.class))
                .collect(Collectors.toList());
        return dtoList;
    }

    @Override
    public List<PostDetailDTO> getAllPostDetailDTOByStatus(String status) {
        List<Post> list = postRepository.findByStatus(status);
        List<PostDetailDTO> dtoList = list.stream()
                .map(post -> {
                    PostDetailDTO postDetailDTO = mapper.map(post, PostDetailDTO.class);
                    int listCommentToCount = commentRepository.getCommentQuantityByPost(post.getIdpost()) ;
                    postDetailDTO.setCommentQuantity(listCommentToCount);
                    return  postDetailDTO ;
                })
                .collect(Collectors.toList());
        return dtoList;
    }


    @Override
    public void changeStatus(int idpost, String status) {
        Optional<Post> optionalPost = postRepository.findById(idpost);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setStatus(status);
            postRepository.save(post);
        } else {
            throw new IllegalArgumentException("Invalid post ID: " + idpost);
        }
    }

//    @Override
//    public Post createPost(Post post) {
//        try {
//            return postRepository.save(post);
//        } catch (DataIntegrityViolationException ex) {
//            throw new RuntimeException("Error creating post", ex);
//        }
//    }

    @Override
    public Post createPostDTO (PostCreateDTO postcreatedto, Principal principal){
        try {
            Post post =  mapper.map(postcreatedto,Post.class);
            Optional<User> currentUser = userRepository.getUserByEmail(principal.getName());
            java.util.Date utilDate = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            post.setDate(sqlDate);
            post.setUser(currentUser.get());
            post.setView(0);
            post.setStatus(Constant.STATUS_POST_PENDDING);
            post.setImageurl(pictureStoredService.addPictureStoredString(postcreatedto.getData()));
            return postRepository.save(post);
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Error creating post", ex);
        }
    }


    @Override
    public Object updatePost(int id, PostCreateDTO postCreateDTO) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post updatedPost = optionalPost.get();

            mapper.getConfiguration().setSkipNullEnabled(true);
            mapper.map(postCreateDTO, updatedPost);

            if (postCreateDTO.getData() != null) {
                updatedPost.setImageurl(pictureStoredService.addPictureStoredString(postCreateDTO.getData()));
            }
            postRepository.save(updatedPost);
            return new Object() {
                public String title = postCreateDTO.getTitle();
                public String content = postCreateDTO.getContent();
                public String category = postCreateDTO.getCategory();

                public String getContent() {
                    return updatedPost.getContent();
                }
                public String getCategory() {
                    return updatedPost.getCategory();
                }
            };
        } else {
            throw new ResourceNotFoundException("Post not found with id: " + id);
        }
    }

    public String deletePost(int id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            postRepository.deleteById(id);
            return "Post" + Constant.DELETE_SUCCESSFULLY;
        } else {
            throw new ResourceExistException("Post " + Constant.DELETE_FAILED);
        }
    }

    @Override
    public PostDetailDTO getPostById(int id) {
        System.out.print(id);
        Optional<Post> post = postRepository.findById(id);
        if(post.isPresent()) {
            PostDetailDTO entityDTO =  mapper.map(post.get(),PostDetailDTO.class);
            return entityDTO;
        } else {
            return null;
        }
    }

    @Override
    public void increaseView(int idpost) {
        Optional<Post> optionalPost = postRepository.findById(idpost);
        if (optionalPost.isPresent()){
            Post post = optionalPost.get();
            int increaseView = post.getView() + 1 ;
            post.setView(increaseView);
            postRepository.save(post);
        }
    }

    public boolean canEditPost(int postId, Authentication authentication) {
        String currentUser = authentication.getName();
        Optional<Post> post = postRepository.findById(postId);

        return post != null && (post.get().getUser().getEmail().equals(currentUser) || isAdmin(authentication));
    }

    public boolean canDeletePost(int postId, Authentication authentication) {
        String currentUser = authentication.getName();
        Optional<Post> post = postRepository.findById(postId);
        return post != null && (post.get().getUser().getEmail().equals(currentUser) || isAdmin(authentication));
    }

    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
    }

}