-- JCertPre Instructor Database Schema
-- Database for Instructor Certification and Training System

-- Drop existing tables if they exist (in reverse order due to foreign key constraints)
DROP TABLE IF EXISTS question_responses;
DROP TABLE IF EXISTS questions;
DROP TABLE IF EXISTS schedule_instructors;
DROP TABLE IF EXISTS schedules;
DROP TABLE IF EXISTS course_instructors;
DROP TABLE IF EXISTS courses;
DROP TABLE IF EXISTS instructors;

-- Create Instructors table
-- Thông tin tổng quan giảng viên
CREATE TABLE instructors (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    instructor_code VARCHAR(20) UNIQUE NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20),
    address TEXT,
    specialization VARCHAR(200),
    experience_years INT DEFAULT 0,
    qualification VARCHAR(500),
    status ENUM('ACTIVE', 'INACTIVE', 'SUSPENDED') DEFAULT 'ACTIVE',
    avatar_url VARCHAR(500),
    bio TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(50),
    updated_by VARCHAR(50)
);

-- Create Courses table
-- Thông tin khóa học
CREATE TABLE courses (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    course_code VARCHAR(20) UNIQUE NOT NULL,
    course_name VARCHAR(200) NOT NULL,
    description TEXT,
    duration_hours INT NOT NULL,
    max_participants INT DEFAULT 30,
    category VARCHAR(100),
    level ENUM('BEGINNER', 'INTERMEDIATE', 'ADVANCED') DEFAULT 'BEGINNER',
    prerequisites TEXT,
    objectives TEXT,
    materials TEXT,
    status ENUM('ACTIVE', 'INACTIVE', 'DRAFT') DEFAULT 'DRAFT',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(50),
    updated_by VARCHAR(50)
);

-- Create Schedules table
-- Lịch giảng
CREATE TABLE schedules (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    schedule_code VARCHAR(20) UNIQUE NOT NULL,
    course_id BIGINT NOT NULL,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    location VARCHAR(200),
    room VARCHAR(50),
    max_participants INT DEFAULT 30,
    current_participants INT DEFAULT 0,
    schedule_type ENUM('ONLINE', 'OFFLINE', 'HYBRID') DEFAULT 'OFFLINE',
    meeting_url VARCHAR(500),
    notes TEXT,
    status ENUM('SCHEDULED', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED') DEFAULT 'SCHEDULED',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(50),
    updated_by VARCHAR(50),
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE
);

-- Create Questions table
-- Câu hỏi học viên
CREATE TABLE questions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    question_code VARCHAR(20) UNIQUE NOT NULL,
    course_id BIGINT,
    schedule_id BIGINT,
    instructor_id BIGINT,
    student_name VARCHAR(100) NOT NULL,
    student_email VARCHAR(100),
    question_title VARCHAR(200) NOT NULL,
    question_content TEXT NOT NULL,
    question_type ENUM('GENERAL', 'TECHNICAL', 'ASSIGNMENT', 'FEEDBACK') DEFAULT 'GENERAL',
    priority ENUM('LOW', 'MEDIUM', 'HIGH', 'URGENT') DEFAULT 'MEDIUM',
    status ENUM('PENDING', 'IN_PROGRESS', 'ANSWERED', 'CLOSED') DEFAULT 'PENDING',
    is_anonymous BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE SET NULL,
    FOREIGN KEY (schedule_id) REFERENCES schedules(id) ON DELETE SET NULL,
    FOREIGN KEY (instructor_id) REFERENCES instructors(id) ON DELETE SET NULL
);

-- Create Question Responses table
-- Trả lời câu hỏi
CREATE TABLE question_responses (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    question_id BIGINT NOT NULL,
    instructor_id BIGINT NOT NULL,
    response_content TEXT NOT NULL,
    response_type ENUM('ANSWER', 'CLARIFICATION', 'FOLLOW_UP') DEFAULT 'ANSWER',
    is_public BOOLEAN DEFAULT TRUE,
    attachments TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE CASCADE,
    FOREIGN KEY (instructor_id) REFERENCES instructors(id) ON DELETE CASCADE
);

-- Create Course Instructors junction table (Many-to-Many relationship)
-- Liên kết giảng viên với khóa học
CREATE TABLE course_instructors (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    course_id BIGINT NOT NULL,
    instructor_id BIGINT NOT NULL,
    role ENUM('MAIN_INSTRUCTOR', 'ASSISTANT_INSTRUCTOR', 'GUEST_SPEAKER') DEFAULT 'MAIN_INSTRUCTOR',
    assigned_date DATE DEFAULT (CURRENT_DATE),
    notes TEXT,
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
    FOREIGN KEY (instructor_id) REFERENCES instructors(id) ON DELETE CASCADE,
    UNIQUE KEY unique_course_instructor (course_id, instructor_id)
);

-- Create Schedule Instructors junction table (Many-to-Many relationship)
-- Liên kết giảng viên với lịch dạy
CREATE TABLE schedule_instructors (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    schedule_id BIGINT NOT NULL,
    instructor_id BIGINT NOT NULL,
    role ENUM('MAIN_INSTRUCTOR', 'ASSISTANT_INSTRUCTOR', 'SUBSTITUTE') DEFAULT 'MAIN_INSTRUCTOR',
    preparation_notes TEXT,
    teaching_notes TEXT,
    status ENUM('ASSIGNED', 'CONFIRMED', 'COMPLETED', 'CANCELLED') DEFAULT 'ASSIGNED',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (schedule_id) REFERENCES schedules(id) ON DELETE CASCADE,
    FOREIGN KEY (instructor_id) REFERENCES instructors(id) ON DELETE CASCADE,
    UNIQUE KEY unique_schedule_instructor (schedule_id, instructor_id)
);

-- Create indexes for better performance
CREATE INDEX idx_instructors_email ON instructors(email);
CREATE INDEX idx_instructors_status ON instructors(status);
CREATE INDEX idx_instructors_specialization ON instructors(specialization);

CREATE INDEX idx_courses_code ON courses(course_code);
CREATE INDEX idx_courses_category ON courses(category);
CREATE INDEX idx_courses_status ON courses(status);

CREATE INDEX idx_schedules_course_id ON schedules(course_id);
CREATE INDEX idx_schedules_date_range ON schedules(start_date, end_date);
CREATE INDEX idx_schedules_status ON schedules(status);

CREATE INDEX idx_questions_course_id ON questions(course_id);
CREATE INDEX idx_questions_instructor_id ON questions(instructor_id);
CREATE INDEX idx_questions_status ON questions(status);
CREATE INDEX idx_questions_created_at ON questions(created_at);

CREATE INDEX idx_question_responses_question_id ON question_responses(question_id);
CREATE INDEX idx_question_responses_instructor_id ON question_responses(instructor_id);

-- Insert sample data
-- Sample Instructors
INSERT INTO instructors (instructor_code, full_name, email, phone, specialization, experience_years, status) VALUES
('INST001', 'Nguyễn Văn Anh', 'nguyen.van.anh@email.com', '0901234567', 'Java Programming', 5, 'ACTIVE'),
('INST002', 'Trần Thị Bình', 'tran.thi.binh@email.com', '0907654321', 'Web Development', 3, 'ACTIVE'),
('INST003', 'Lê Minh Cường', 'le.minh.cuong@email.com', '0912345678', 'Database Management', 7, 'ACTIVE'),
('INST004', 'Phạm Thị Dung', 'pham.thi.dung@email.com', '0909876543', 'Mobile Development', 4, 'ACTIVE');

-- Sample Courses
INSERT INTO courses (course_code, course_name, description, duration_hours, category, level, status) VALUES
('JAVA101', 'Java Fundamentals', 'Khóa học cơ bản về lập trình Java', 40, 'Programming', 'BEGINNER', 'ACTIVE'),
('WEB201', 'Advanced Web Development', 'Phát triển web nâng cao với Spring Boot', 60, 'Web Development', 'INTERMEDIATE', 'ACTIVE'),
('DB301', 'Database Design & Optimization', 'Thiết kế và tối ưu hóa cơ sở dữ liệu', 35, 'Database', 'ADVANCED', 'ACTIVE'),
('MOB401', 'Mobile App Development', 'Phát triển ứng dụng di động Android/iOS', 50, 'Mobile', 'INTERMEDIATE', 'ACTIVE');

-- Sample Schedules
INSERT INTO schedules (schedule_code, course_id, title, start_date, end_date, start_time, end_time, location, status) VALUES
('SCH001', 1, 'Java Fundamentals - Batch 1', '2024-07-01', '2024-07-15', '08:00:00', '12:00:00', 'Phòng A101', 'SCHEDULED'),
('SCH002', 2, 'Web Development - Evening Class', '2024-07-08', '2024-07-30', '18:00:00', '21:00:00', 'Phòng B205', 'SCHEDULED'),
('SCH003', 3, 'Database Workshop', '2024-07-15', '2024-07-25', '14:00:00', '17:00:00', 'Phòng C301', 'SCHEDULED');

-- Sample Course-Instructor assignments
INSERT INTO course_instructors (course_id, instructor_id, role, status) VALUES
(1, 1, 'MAIN_INSTRUCTOR', 'ACTIVE'),
(2, 2, 'MAIN_INSTRUCTOR', 'ACTIVE'),
(3, 3, 'MAIN_INSTRUCTOR', 'ACTIVE'),
(4, 4, 'MAIN_INSTRUCTOR', 'ACTIVE'),
(1, 2, 'ASSISTANT_INSTRUCTOR', 'ACTIVE');

-- Sample Schedule-Instructor assignments
INSERT INTO schedule_instructors (schedule_id, instructor_id, role, status) VALUES
(1, 1, 'MAIN_INSTRUCTOR', 'CONFIRMED'),
(2, 2, 'MAIN_INSTRUCTOR', 'CONFIRMED'),
(3, 3, 'MAIN_INSTRUCTOR', 'ASSIGNED');

-- Sample Questions
INSERT INTO questions (question_code, course_id, instructor_id, student_name, student_email, question_title, question_content, question_type, status) VALUES
('Q001', 1, 1, 'Nguyễn Văn Học Viên', 'hocvien@email.com', 'Về cú pháp Java', 'Em muốn hỏi về cách sử dụng vòng lặp for trong Java?', 'TECHNICAL', 'PENDING'),
('Q002', 2, 2, 'Trần Thị Sinh Viên', 'sinhvien@email.com', 'Spring Boot Configuration', 'Làm sao để cấu hình database connection trong Spring Boot?', 'TECHNICAL', 'PENDING'),
('Q003', 1, 1, 'Lê Văn Học', 'hoc@email.com', 'Homework Assignment', 'Em có thể nộp bài tập muộn được không ạ?', 'ASSIGNMENT', 'ANSWERED');

-- Sample Question Responses
INSERT INTO question_responses (question_id, instructor_id, response_content, response_type) VALUES
(3, 1, 'Bạn có thể nộp muộn nhưng sẽ bị trừ 10% điểm. Hãy cố gắng hoàn thành đúng hạn nhé!', 'ANSWER');

-- Create views for common queries
-- View: Instructor Overview
CREATE VIEW v_instructor_overview AS
SELECT 
    i.id,
    i.instructor_code,
    i.full_name,
    i.email,
    i.specialization,
    i.experience_years,
    i.status,
    COUNT(DISTINCT ci.course_id) as total_courses,
    COUNT(DISTINCT si.schedule_id) as total_schedules,
    COUNT(DISTINCT q.id) as total_questions
FROM instructors i
LEFT JOIN course_instructors ci ON i.id = ci.instructor_id AND ci.status = 'ACTIVE'
LEFT JOIN schedule_instructors si ON i.id = si.instructor_id AND si.status IN ('ASSIGNED', 'CONFIRMED')
LEFT JOIN questions q ON i.id = q.instructor_id AND q.status IN ('PENDING', 'IN_PROGRESS')
GROUP BY i.id, i.instructor_code, i.full_name, i.email, i.specialization, i.experience_years, i.status;

-- View: Course Schedule Overview
CREATE VIEW v_course_schedule_overview AS
SELECT 
    c.id as course_id,
    c.course_code,
    c.course_name,
    s.id as schedule_id,
    s.schedule_code,
    s.start_date,
    s.end_date,
    s.location,
    s.status as schedule_status,
    GROUP_CONCAT(CONCAT(i.full_name, ' (', si.role, ')') SEPARATOR ', ') as instructors
FROM courses c
LEFT JOIN schedules s ON c.id = s.course_id
LEFT JOIN schedule_instructors si ON s.id = si.schedule_id AND si.status IN ('ASSIGNED', 'CONFIRMED')
LEFT JOIN instructors i ON si.instructor_id = i.id
WHERE c.status = 'ACTIVE'
GROUP BY c.id, c.course_code, c.course_name, s.id, s.schedule_code, s.start_date, s.end_date, s.location, s.status;

-- Stored Procedure: Get Instructor Workload
DELIMITER //
CREATE PROCEDURE GetInstructorWorkload(
    IN instructor_id BIGINT,
    IN start_date DATE,
    IN end_date DATE
)
BEGIN
    SELECT 
        s.schedule_code,
        c.course_name,
        s.start_date,
        s.end_date,
        s.start_time,
        s.end_time,
        s.location,
        si.role,
        s.status
    FROM schedules s
    JOIN courses c ON s.course_id = c.id
    JOIN schedule_instructors si ON s.id = si.schedule_id
    WHERE si.instructor_id = instructor_id
    AND s.start_date >= start_date
    AND s.end_date <= end_date
    AND si.status IN ('ASSIGNED', 'CONFIRMED')
    ORDER BY s.start_date, s.start_time;
END //
DELIMITER ;

-- Function: Calculate Instructor Rating (placeholder for future implementation)
DELIMITER //
CREATE FUNCTION GetInstructorRating(instructor_id BIGINT) 
RETURNS DECIMAL(3,2)
READS SQL DATA
DETERMINISTIC
BEGIN
    -- Placeholder logic - in real implementation, this would calculate based on feedback
    DECLARE rating DECIMAL(3,2) DEFAULT 4.50;
    RETURN rating;
END //
DELIMITER ;

-- Create triggers for audit trail
DELIMITER //
CREATE TRIGGER trg_instructors_update 
    BEFORE UPDATE ON instructors
    FOR EACH ROW 
BEGIN
    SET NEW.updated_at = CURRENT_TIMESTAMP;
END //

CREATE TRIGGER trg_courses_update 
    BEFORE UPDATE ON courses
    FOR EACH ROW 
BEGIN
    SET NEW.updated_at = CURRENT_TIMESTAMP;
END //

CREATE TRIGGER trg_schedules_update 
    BEFORE UPDATE ON schedules
    FOR EACH ROW 
BEGIN
    SET NEW.updated_at = CURRENT_TIMESTAMP;
END //
DELIMITER ;

-- Grant permissions (adjust as needed for your environment)
-- GRANT SELECT, INSERT, UPDATE, DELETE ON jcertpre.* TO 'jcertpre_user'@'localhost';
-- FLUSH PRIVILEGES;

-- End of script
SELECT 'JCertPre Instructor Database Schema created successfully!' as message;