-- phpMyAdmin SQL Dump
-- version 5.2.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 17, 2025 at 05:02 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `rapchieuphim_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `admins`
--

CREATE TABLE `admins` (
  `id` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `blogs`
--

CREATE TABLE `blogs` (
  `id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `content` text NOT NULL,
  `author` varchar(100) NOT NULL,
  `image` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `bookings`
--

CREATE TABLE `bookings` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `movie_id` int(11) NOT NULL,
  `seats` varchar(50) DEFAULT NULL,
  `status` enum('pending','confirmed') DEFAULT 'pending',
  `booking_time` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bookings`
--

INSERT INTO `bookings` (`id`, `user_id`, `movie_id`, `seats`, `status`, `booking_time`) VALUES
(1, 3, 9, 'A2', 'pending', '2025-04-10 03:09:06'),
(2, 3, 9, 'A2', 'pending', '2025-04-11 04:34:00');

-- --------------------------------------------------------

--
-- Table structure for table `contact_messages`
--

CREATE TABLE `contact_messages` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `email` varchar(255) NOT NULL,
  `subject` varchar(255) NOT NULL,
  `message` text NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `faqs`
--

CREATE TABLE `faqs` (
  `id` int(11) NOT NULL,
  `question` text NOT NULL,
  `answer` text NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `movies`
--

CREATE TABLE `movies` (
  `id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `genre` varchar(100) DEFAULT NULL,
  `duration` int(11) DEFAULT NULL,
  `release_date` date DEFAULT NULL,
  `showtimes` text DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `poster` varchar(500) DEFAULT NULL,
  `description` text NOT NULL,
  `trailer_link` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `movies`
--

INSERT INTO `movies` (`id`, `title`, `genre`, `duration`, `release_date`, `showtimes`, `price`, `poster`, `description`, `trailer_link`, `created_at`) VALUES
(9, 'The Batman (2022)', 'Hành động, trinh thám', 240, '2022-02-02', '[\"14\"]', 200.00, 'TheBatman.png', 'Batman: Vạch trần sự thật (tiếng Anh: The Batman) là một bộ phim điện ảnh đề tài siêu anh hùng của Mỹ, dựa trên nhân vật cùng tên của loạt truyện tranh DC Comics. Phim do các hãng DC Films, 6th & Idaho, Dylan Clark Productions sản xuất và được Warner Bros. phát hành năm 2022, có sự tham gia của tài tử Robert Pattinson trong vai chính Bruce Wayne / Batman, cùng với các diễn viên Zoë Kravitz, Paul Dano, Jeffrey Wright, John Turturro, Peter Sarsgaard, Andy Serkis và Colin Farrell. Bộ phim theo chân nhân vật Batman, người đã chiến đấu chống tội phạm ở thành phố Gotham trong hai năm, phát hiện ra một vụ tham nhũng trong khi truy đuổi Riddler (Dano), một kẻ giết người hàng loạt nhắm vào giới thượng lưu của Gotham.', 'https://www.youtube.com/embed/mqqft2x_Aa4?si=K6WPvWvBuuWDbSHj', '2025-03-24 11:02:38'),
(10, 'Mickey 17', 'Hài, hành động, khoa học viễn tưởng', 120, '2025-03-26', '[\"14:00\",\" 12:00\",\" 18:00\",\" 21:00\"]', 20.00, 'Mickey17.png', 'Được chuyển thể từ tiểu thuyết Mickey 7 của nhà văn Edward Ashton, Cuốn tiểu thuyết xoay quanh các phiên bản nhân bản vô tính mang tên “Mickey”, dùng để thay thế con người thực hiện cuộc chinh phạt nhằm thuộc địa hóa vương quốc băng giá Niflheim. Mỗi khi một Mickey chết đi, một Mickey mới sẽ được tạo ra, với phiên bản được đánh số 1, 2, 3 tiếp theo. Mickey số 17 được cho rằng đã chết, để rồi một ngày kia, hắn quay lại và bắt gặp phiên bản tiếp theo của mình.', 'https://www.youtube.com/embed/osYpGSz_0i4?si=ZrUYbnJjA_R2qkWF', '2025-03-26 00:56:17'),
(12, 'Venom: Kèo Cuối', 'Hành ', 109, '2024-10-25', '[\"14:00\",\" 12:00\",\" 18:00\",\" 21:00\"]', 120.00, 'VenomTheLastDance.jpg', '​\"Venom: Kèo Cuối\" (tựa gốc: \"Venom: The Last Dance\") là phần kết hoành tráng của loạt phim về nhân vật phản anh hùng Venom, dựa trên truyện tranh Marvel. Trong phần này, Eddie Brock (Tom Hardy) và symbiote Venom tiếp tục hành trình đầy thử thách khi bị truy đuổi bởi chính phủ Mỹ và đối mặt với mối đe dọa từ Knull, ác thần cổ đại tìm cách giải phóng bản thân bằng cách thu thập Codex từ Venom. Bộ phim kết hợp những pha hành động mãn nhãn với chiều sâu tâm lý nhân vật, mang đến cho khán giả một trải nghiệm điện ảnh đáng nhớ.', 'https://www.youtube.com/embed/__2bjWbetsA?si=cQQCRAHzT-JmLmkC', '2025-03-30 02:04:24');

-- --------------------------------------------------------

--
-- Table structure for table `payments`
--

CREATE TABLE `payments` (
  `id` int(11) NOT NULL,
  `booking_id` int(11) NOT NULL,
  `amount` decimal(10,2) DEFAULT NULL,
  `payment_method` enum('credit_card','paypal','cash') DEFAULT NULL,
  `payment_status` enum('pending','confirmed') DEFAULT 'pending',
  `payment_date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('user','admin') DEFAULT 'user',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `reset_token_hash` varchar(64) DEFAULT NULL,
  `reset_token_expires_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `email`, `password`, `role`, `created_at`, `reset_token_hash`, `reset_token_expires_at`) VALUES
(3, 'Nguyen Le Dai Phi', 'nguyenledaiphi0252005@gmail.com', '$2y$10$0HHgNSwP.9oPsyYjCSn.B.l0rp0sHksND.OnIeYmdIbMgq9K3GEXa', 'user', '2025-03-05 05:07:41', NULL, NULL),
(5, 'Hoang Duc Trung', 'hoangductrung@gmail.com', '$2y$10$ubXGIF9tbFtZDDrmESK10eq.6Fr7CcXP7Rou7fgkPptskUsEfZdHK', 'user', '2025-03-05 05:18:20', NULL, NULL),
(6, 'Trinh Van Chinh', 'trinhvanchinh@gmail.com', '$2y$10$Zn6VKCXEsBJQybtUEL8A2eQHsUpxyhYTqtC8y2JitRMUnVsFWTI4O', 'user', '2025-03-05 05:31:26', NULL, NULL),
(7, 'Quang Huy', 'quanghuy@gmail.com', '$2y$10$moLjTKEdw3n0V5vroBMQK.jQa7vrYe2DXLHBHVNNGk/MTPYSP6yYy', 'user', '2025-03-05 05:32:49', NULL, NULL),
(9, 'Nguyễn Khang Đàm', 'khangdam@gmail.com', '$2y$10$ERDAJ.B0pzW.mM2UOHZOe.BqUpEgShwApZIupl44R6r3BA5tDf1aG', 'user', '2025-03-05 05:34:15', NULL, NULL),
(10, 'Nguyen Le Dai Thanh', 'Thanh@gmail.com', '$2y$10$BLe54.VpmVEnoP0AcTv1BO55xt8UMaYe0OGWMIZ5JltlF3gBji/eS', 'user', '2025-03-07 05:38:04', NULL, NULL),
(11, 'Nguyen Le Dai Long', 'Long@gmail.com', '$2y$10$7pHNilWA31UeOh5Vzih6J.Xo4iRKJartYrxC5TvFHqfCOu3luxFda', 'user', '2025-03-14 06:46:05', NULL, NULL),
(13, 'Nguyen Le Dai Phi', 'phiproga@gmail.com', '$2y$10$qlGPyTiAwdePreqTWvU9.epO99XshtlR8oud6SAH6VugjBHbQp8lq', 'user', '2025-03-21 09:14:00', NULL, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admins`
--
ALTER TABLE `admins`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `blogs`
--
ALTER TABLE `blogs`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `bookings`
--
ALTER TABLE `bookings`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `movie_id` (`movie_id`);

--
-- Indexes for table `contact_messages`
--
ALTER TABLE `contact_messages`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `faqs`
--
ALTER TABLE `faqs`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `movies`
--
ALTER TABLE `movies`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `payments`
--
ALTER TABLE `payments`
  ADD PRIMARY KEY (`id`),
  ADD KEY `booking_id` (`booking_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `reset_token_hash` (`reset_token_hash`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admins`
--
ALTER TABLE `admins`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `blogs`
--
ALTER TABLE `blogs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `bookings`
--
ALTER TABLE `bookings`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `contact_messages`
--
ALTER TABLE `contact_messages`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `faqs`
--
ALTER TABLE `faqs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `movies`
--
ALTER TABLE `movies`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `payments`
--
ALTER TABLE `payments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `admins`
--
ALTER TABLE `admins`
  ADD CONSTRAINT `admins_ibfk_1` FOREIGN KEY (`id`) REFERENCES `blogs` (`id`);

--
-- Constraints for table `bookings`
--
ALTER TABLE `bookings`
  ADD CONSTRAINT `bookings_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `bookings_ibfk_2` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `faqs`
--
ALTER TABLE `faqs`
  ADD CONSTRAINT `faqs_ibfk_1` FOREIGN KEY (`id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `payments`
--
ALTER TABLE `payments`
  ADD CONSTRAINT `payments_ibfk_1` FOREIGN KEY (`booking_id`) REFERENCES `bookings` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
