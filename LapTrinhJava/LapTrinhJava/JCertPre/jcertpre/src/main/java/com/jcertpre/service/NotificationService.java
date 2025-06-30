package com.jcertpre.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcertpre.model.Notification;
import com.jcertpre.repository.NotificationRepository;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;
    
    public void saveNotification(Notification notification) {
        notificationRepository.save(notification);
    }

    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id).orElse(null);
    }
}
