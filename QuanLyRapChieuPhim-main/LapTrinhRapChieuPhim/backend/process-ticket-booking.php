<?php
session_start();
require_once '../database/database.php';

if (!isset($_SESSION['user_id'])) {
    echo json_encode(["status" => "error", "message" => "Bạn cần đăng nhập để đặt vé."]);
    exit;
}

$user_id = $_SESSION['user_id'];
$movie_id = $_POST['movie_id'];
$seats = $_POST['seats'];
$date = $_POST['date'];
$time = $_POST['time'];
$status = "pending";
$booking_time = date("Y-m-d H:i:s");

$db = new Database();
$conn = $db->getConnection();

$stmt = $conn->prepare("INSERT INTO bookings (user_id, movie_id, seats, status, booking_time) VALUES (?, ?, ?, ?, ?)");
$stmt->bind_param("iisss", $user_id, $movie_id, $seats, $status, $booking_time);

if ($stmt->execute()) {
    echo json_encode(["status" => "success", "message" => "Đặt vé thành công!"]);
} else {
    echo json_encode(["status" => "error", "message" => "Lỗi khi đặt vé."]);
}
?>
