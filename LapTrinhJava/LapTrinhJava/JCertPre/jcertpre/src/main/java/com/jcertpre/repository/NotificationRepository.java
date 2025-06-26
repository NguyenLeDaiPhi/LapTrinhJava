package com.jcertpre.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jcertpre.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long>{
    
}
