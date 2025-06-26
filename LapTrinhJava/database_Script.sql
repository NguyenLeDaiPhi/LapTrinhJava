-- Tạo database nếu chưa có
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'JcertPre')
BEGIN
    CREATE DATABASE JcertPre;
END
GO

USE JcertPre;
GO

-- Tạo bảng Learner
IF OBJECT_ID('dbo.Learner', 'U') IS NULL
BEGIN
    CREATE TABLE dbo.Learner (
        Id BIGINT IDENTITY(1,1) PRIMARY KEY,
        Name NVARCHAR(100) NOT NULL,
        Email NVARCHAR(100) NOT NULL UNIQUE,
        Password NVARCHAR(255) NOT NULL
    );
END
GO
-- Nhớ tạo thêm bảng liên kết(learner_course) giữa learner và course
    CREATE TABLE learner_course (
    learner_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    PRIMARY KEY (learner_id, course_id),
    FOREIGN KEY (learner_id) REFERENCES learner(id),
    FOREIGN KEY (course_id) REFERENCES course(id)
    -- này mới demo với id thôi dự định còn in ra tên learner và course nữa
);

