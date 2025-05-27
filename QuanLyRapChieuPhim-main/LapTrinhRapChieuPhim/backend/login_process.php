<?php
session_start();
require_once 'auth.php';
require_once '../database/database.php';

// Tạo kết nối
$db = new Database();
$conn = $db->getConnection();

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $email = $_POST['email'];
    $password = $_POST['password'];

    // Truy vấn người dùng theo email
    $query = "SELECT * FROM users WHERE email = ?";
    $stmt = $conn->prepare($query);
    $stmt->bind_param("s", $email);
    $stmt->execute();
    $result = $stmt->get_result();

    // Kiểm tra người dùng
    if ($result->num_rows > 0) {
        $user = $result->fetch_assoc();

        if (password_verify($password, $user['password'])) {
            $_SESSION['user_id'] = $user['id'];
            $_SESSION['user_role'] = $user['role'];

            // Điều hướng theo vai trò
            if ($user['role'] === 'admin') {
                header("Location: ../backend/admin_dashboard.php");
            } else {
                header("Location: ../frontend/index.php");
            }
            exit();
        }
    }

    $_SESSION["login_error"] = "Email hoặc mật khẩu không đúng!";
    header("Location: ../frontend/login.php");
    exit();
}
?>
