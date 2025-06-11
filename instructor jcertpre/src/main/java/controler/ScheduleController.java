// ScheduleController.java
package com.example.jcertpre.controller;
import com.example.jcertpre.dto.ScheduleRequest;
import com.example.jcertpre.service.ScheduleService;
import com.example.jcertpre.model.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@CrossOrigin(origins = "*")
public class ScheduleController {
    
    @Autowired
    private ScheduleService scheduleService;
    
    @PostMapping
    public ResponseEntity<Schedule> createSchedule(@RequestBody ScheduleRequest request) {
        Schedule schedule = scheduleService.createSchedule(request);
        return ResponseEntity.ok(schedule);
    }
    
    @GetMapping
    public ResponseEntity<List<Schedule>> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        return ResponseEntity.ok(schedules);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getSchedule(@PathVariable Long id) {
        Schedule schedule = scheduleService.getScheduleById(id);
        return ResponseEntity.ok(schedule);
    }
    
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Schedule>> getSchedulesByCourse(@PathVariable Long courseId) {
        List<Schedule> schedules = scheduleService.getSchedulesByCourse(courseId);
        return ResponseEntity.ok(schedules);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Schedule> updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequest request) {
        Schedule schedule = scheduleService.updateSchedule(id, request);
        return ResponseEntity.ok(schedule);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.ok("Xóa lịch giảng thành công");
    }
}