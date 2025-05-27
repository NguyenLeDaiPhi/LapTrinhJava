<?php
require_once 'user_management.php';

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    if (!empty($_POST["id"]) && !empty($_POST["name"]) && !empty($_POST["email"]) && !empty($_POST["role"])) {
        $user_management = new UserManagement();
        
        if(!empty($_POST["password"])) {
            // Nếu có cập nhật mật khẩu
            if($user_management->updateUserWithPassword($_POST["id"], $_POST["name"], $_POST["email"], $_POST["password"], $_POST["role"])) {
                echo "Cập nhật người dùng thành công!";
            } else {
                echo "Không thể cập nhật người dùng.";
            }
        } else {
            // Nếu không cập nhật mật khẩu
            if($user_management->updateUser($_POST["id"], $_POST["name"], $_POST["email"], $_POST["role"])) {
                echo "Cập nhật người dùng thành công!";
            } else {
                echo "Không thể cập nhật người dùng.";
            }
        }
    } else {
        echo "Vui lòng điền đầy đủ thông tin người dùng.";
    }
} else {
    echo "Yêu cầu không hợp lệ.";
}
?>