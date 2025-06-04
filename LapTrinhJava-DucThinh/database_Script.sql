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
-- Tạo bảng Giảng viên (Instructor)
IF OBJECT_ID('dbo.Instructor', 'U') IS NULL
BEGIN
    CREATE TABLE dbo.Instructor (
        Id BIGINT IDENTITY(1,1) PRIMARY KEY,
        Name NVARCHAR(100) NOT NULL,
        Email NVARCHAR(100) NOT NULL UNIQUE,
        Password NVARCHAR(255) NOT NULL,
        Specialization NVARCHAR(255),
        Bio NVARCHAR(MAX)
    );
END
GO

-- Tạo bảng Khóa học (Course)
IF OBJECT_ID('dbo.Course', 'U') IS NULL
BEGIN
    CREATE TABLE dbo.Course (
        Id BIGINT IDENTITY(1,1) PRIMARY KEY,
        Title NVARCHAR(255) NOT NULL,
        Level NVARCHAR(10) NOT NULL,
        Description NVARCHAR(MAX),
        CertificateType NVARCHAR(50) NOT NULL,
        CreatedDate DATETIME DEFAULT GETDATE()
    );
END
GO

-- Tạo bảng Lịch học (Schedule)
IF OBJECT_ID('dbo.Schedule', 'U') IS NULL
BEGIN
    CREATE TABLE dbo.Schedule (
        Id BIGINT IDENTITY(1,1) PRIMARY KEY,
        CourseId BIGINT NOT NULL,
        Title NVARCHAR(255) NOT NULL,
        StartTime DATETIME NOT NULL,
        EndTime DATETIME NOT NULL,
        Type NVARCHAR(50) NOT NULL,
        Link NVARCHAR(500),
        FOREIGN KEY (CourseId) REFERENCES dbo.Course(Id)
    );
END
GO

-- tạo bảng liên kết Giảng viên - Khóa học (Instructor_Course)
IF OBJECT_ID('dbo.Instructor_Course', 'U') IS NULL
BEGIN
    CREATE TABLE dbo.Instructor_Course (
        InstructorId BIGINT NOT NULL,
        CourseId BIGINT NOT NULL,
        AssignedDate DATETIME DEFAULT GETDATE(),
        PRIMARY KEY (InstructorId, CourseId),
        FOREIGN KEY (InstructorId) REFERENCES dbo.Instructor(Id),
        FOREIGN KEY (CourseId) REFERENCES dbo.Course(Id)
    );
END
GO
