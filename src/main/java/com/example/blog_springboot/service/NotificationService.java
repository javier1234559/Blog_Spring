package com.example.blog_springboot.service;

import com.example.blog_springboot.model.Notification;

import java.util.List;

public interface NotificationService {
    Notification createNotification(Notification notification);
    Notification updateNotification(int id, Notification notification);
    void deleteNotification(int id);
    Notification getNotificationById(int id);
    List<Notification> getAllNotifications();
//    List<Notification> getNotificationsByUserId(int userId);
}
