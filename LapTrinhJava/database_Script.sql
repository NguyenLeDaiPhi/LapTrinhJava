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
