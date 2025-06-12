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
-- Thêm cột Role vào bảng Learner
ALTER TABLE dbo.Learner
ADD Role NVARCHAR(20) NOT NULL DEFAULT 'USER';
GO

-- Them tai khoan voi quyen truy cap admin
INSERT INTO Learner (Name, Email, Password, Role)
VALUES (
    'Admin User',
    'admin@example.com',
    '$2a$10$7JfFhFeVNR7ddq4Aa1/il.T/8TqvPxUba9dyHQ1fwZs1F5Io6ZPWS', -- admin123
    'ADMIN'
);
GO