-- Tạo bảng Instructor
CREATE DATABASE IF NOT EXISTS jcertpre CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE jcertpre;

CREATE TABLE `Instructor` (
  `Id` BIGINT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(100) NOT NULL,
  `Email` VARCHAR(100) NOT NULL UNIQUE,
  `Password` VARCHAR(255) NOT NULL,
  `Specialization` VARCHAR(255),
  `Bio` TEXT,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tạo bảng Course (Khóa học)
CREATE TABLE `Course` (
  `Id` BIGINT NOT NULL AUTO_INCREMENT,
  `Title` VARCHAR(255) NOT NULL,
  `Description` TEXT,
  `Level` VARCHAR(10),
  `CertificateType` VARCHAR(50),
  `InstructorId` BIGINT NOT NULL,
  PRIMARY KEY (`Id`),
  FOREIGN KEY (`InstructorId`) REFERENCES `Instructor`(`Id`)
    ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tạo bảng Schedule (Lịch dạy)
CREATE TABLE `Schedule` (
  `Id` BIGINT NOT NULL AUTO_INCREMENT,
  `Title` VARCHAR(255),
  `StartTime` DATETIME,
  `EndTime` DATETIME,
  `Mode` VARCHAR(50),         -- Hình thức học: Online / Offline
  `Location` VARCHAR(255),    -- Link Zoom hoặc địa điểm lớp
  `CourseId` BIGINT NOT NULL,
  `InstructorId` BIGINT NOT NULL,
  PRIMARY KEY (`Id`),
  FOREIGN KEY (`CourseId`) REFERENCES `Course`(`Id`)
    ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`InstructorId`) REFERENCES `Instructor`(`Id`)
    ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
