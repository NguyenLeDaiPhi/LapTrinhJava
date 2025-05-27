<?php
require_once 'user_management.php';

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    if (!empty($_POST["name"]) && !empty($_POST["email"]) && !empty($_POST["password"]) && !empty($_POST["role"])) {
        $user_management = new UserManagement();
        if($user_management->addUser($_POST["name"], $_POST["email"], $_POST["password"], $_POST["role"])) {
            echo "Thêm người dùng thành công!";
        } else {
            echo "Không thể thêm người dùng.";
        }
    } else {
        echo "Vui lòng điền đầy đủ thông tin người dùng.";
    }
} else {
    echo "Yêu cầu không hợp lệ.";
}
?>