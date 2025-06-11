// InstructorController.java
package com.example.jcertpre.controller;
import com.example.jcertpre.dto.*;
import com.example.jcertpre.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/instructors")
@CrossOrigin(origins = "*")
public class InstructorController {
    
    @Autowired
    private InstructorService instructorService;
    
    @PostMapping("/login")
    public ResponseEntity<InstructorLoginResponse> login(@RequestBody InstructorLoginRequest request) {
        InstructorLoginResponse response = instructorService.login(request);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody InstructorRegisterRequest request) {
        instructorService.register(request);
        return ResponseEntity.ok("Đăng ký thành công");
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<InstructorLoginResponse> getInstructor(@PathVariable Long id) {
        InstructorLoginResponse instructor = instructorService.getInstructorById(id);
        return ResponseEntity.ok(instructor);
    }
    
    @GetMapping
    public ResponseEntity<List<InstructorLoginResponse>> getAllInstructors() {
        List<InstructorLoginResponse> instructors = instructorService.getAllInstructors();
        return ResponseEntity.ok(instructors);
    }
}
