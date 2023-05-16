package com.example.blog_springboot.service.impl;

import com.example.blog_springboot.dto.PostDetailDTO;
import com.example.blog_springboot.dto.UserDTO;
import com.example.blog_springboot.dto.UserLoginDTO;
import com.example.blog_springboot.dto.UserRegisterDTO;
import com.example.blog_springboot.exception.ResourceExistException;
import com.example.blog_springboot.exception.ResourceNotFoundException;
import com.example.blog_springboot.model.User;
import com.example.blog_springboot.repository.UserRepository;
import com.example.blog_springboot.service.UserService;


import com.example.blog_springboot.ultilies.Constant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Environment environment;

    @Value("${default.user.icon.url}")
    private String defaultUserIconUrl;


    @Override
    public User createUserDashBoard(User user) {
        String email = user.getEmail();
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        user.setPass(passwordEncoder.encode(user.getPass()));

        return userRepository.save(user);
    }


    private boolean hasAdminRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> list = userRepository.findAll();
        List<UserDTO> dtoList = list.stream()
                .map(post -> mapper.map(post, UserDTO.class))
                .collect(Collectors.toList());
       return dtoList ;
    }

    public UserDTO getUserByEmail(String email) {
        Optional<User> optionalUser = userRepository.getUserByEmail(email);
        if (optionalUser.isPresent()) {
            UserDTO userDTO  =  mapper.map(optionalUser.get(),UserDTO.class);
            return userDTO;
        } else {
            return  null ;
        }
    }

    @Override
    public String resetPassword(String email , String pass){
         String encode = passwordEncoder.encode(pass);
         Optional<User> optionalUser = userRepository.getUserByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPass(encode);
            userRepository.save(user);
            return "Reset password successful";
        }
        return "Not found user name with that email !";
    }

    @Override
    public User createUserRegister(UserRegisterDTO userRegisterDTO) {
        try {
            userRegisterDTO.setPass(passwordEncoder.encode(userRegisterDTO.getPass()));
            User newuser  =  mapper.map(userRegisterDTO,User.class);
            newuser.setDescription("");
            newuser.setImageurl(defaultUserIconUrl);
            newuser.setPhone("");
            newuser.setRole("ROLE_USER");
            newuser.setStatus(1);
            return userRepository.save(newuser);
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Error creating user", ex);
        }
    }

    @Override
    public UserDTO updateUser(int id, UserDTO user) {
        System.out.println(defaultUserIconUrl);
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User updatedUser = optionalUser.get();
            if (user.getName() != null) {
                updatedUser.setName(user.getName());
            }
            if (user.getEmail() != null  && !user.getEmail().trim().isEmpty()) {
                updatedUser.setEmail(user.getEmail());
            }
            if (user.getPhone() != null) {
                updatedUser.setPhone(user.getPhone());
            }
            if (user.getDescription() != null) {
                updatedUser.setDescription(user.getDescription());
            }
            if (user.getImageurl() != null) {
                updatedUser.setImageurl(user.getImageurl());
            }
            if (user.getStatus() != 0 && hasAdminRole()) {
                updatedUser.setStatus(user.getStatus());
            }
            if (user.getRole() != null && hasAdminRole()) {
                updatedUser.setRole(user.getRole());
            }
            userRepository.save(updatedUser);
            return user;
        } else {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
    }

    @Override
    public User getUserById(int id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
    }

    @Override
    public User getUserByEmailAndPassword(UserLoginDTO userLoginDTO) {
        Optional<User> optionalUser = userRepository.getUserByEmailAndPass(userLoginDTO.getEmail(),userLoginDTO.getPass());
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new ResourceNotFoundException("User not found with id: " + userLoginDTO.getEmail());
        }
    }

    public String deleteUser(int id) {
        Optional<User> post = userRepository.findById(id);
        if (post.isPresent()) {
            userRepository.deleteById(id);
            return "User" + Constant.DELETE_SUCCESSFULLY;
        } else {
            throw new ResourceExistException("User " + Constant.DELETE_FAILED);
        }
    }

}
