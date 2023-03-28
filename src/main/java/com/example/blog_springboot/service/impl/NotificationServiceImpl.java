package com.example.blog_springboot.service.impl;

import com.example.blog_springboot.model.Notification;
import com.example.blog_springboot.repository.NotificationRepository;
import com.example.blog_springboot.service.NotificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public Notification updateNotification(int id, Notification notification) {
        Notification updatedNotification = notificationRepository.findById(id).orElse(null);
        if (updatedNotification != null) {
            updatedNotification.setImage(notification.getImage());
            updatedNotification.setTitle(notification.getTitle());
            updatedNotification.setContent(notification.getContent());
            updatedNotification.setUser(notification.getUser());
            notificationRepository.save(updatedNotification);
        }
        return updatedNotification;
    }

    @Override
    public void deleteNotification(int id) {
        notificationRepository.deleteById(id);
    }

    @Override
    public Notification getNotificationById(int id) {
        return notificationRepository.findById(id).orElse(null);
    }

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

//    @Override
//    public List<Notification> getNotificationsByUserId(int userId) {
//        return notificationRepository.findByUserId(userId);
//    }
}