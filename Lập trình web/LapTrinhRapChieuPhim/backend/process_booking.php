<?php
session_start();
require_once '../database/database.php';

if (!isset($_SESSION['user_id'])) {
    echo 'Bạn cần đăng nhập để đặt vé';
    echo'<div style="margin-top: 20px;">
            <a href="../frontend/login.php" style="
                display: inline-block;
                margin-right: 10px;
                padding: 10px 20px;
                background-color: #2196F3;
                color: white;
                text-decoration: none;
                border-radius: 5px;
            ">Đăng nhập</a>
            </div>';
            die();
}

$user_id = $_SESSION['user_id'];
$movie_id = $_POST['movie_id'];
$seats = $_POST['seats'];
$status = "pending"; 
$booking_time = date('Y-m-d H:i:s');

$db = new Database();
$conn = $db->getConnection();

$stmt = $conn->prepare("INSERT INTO bookings (user_id, movie_id, seats, status, booking_time) VALUES (?, ?, ?, ?, ?)");
$stmt->bind_param("iisss", $user_id, $movie_id, $seats, $status, $booking_time);

// Nếu booking thành công
if ($stmt->execute()) {
    echo "<h2>🎉 Đặt vé thành công!</h2>";
    echo "<p>Cảm ơn bạn đã đặt vé xem phim.</p>";

    echo '<div style="margin-top: 20px;">
            <a href="../frontend/show_ticket.php" style="
                display: inline-block;
                margin-right: 10px;
                padding: 10px 20px;
                background-color: #2196F3;
                color: white;
                text-decoration: none;
                border-radius: 5px;
            ">🎫 Xem vé của bạn</a>

            <a href="../frontend/index.php" style="
                display: inline-block;
                padding: 10px 20px;
                background-color: #4CAF50;
                color: white;
                text-decoration: none;
                border-radius: 5px;
            ">🏠 Về trang chủ</a>
        </div>';
} else {
    echo "Lỗi khi đặt vé. Vui lòng thử lại.";
}
?>
