<?php
require_once 'user_management.php';

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    if (!empty($_POST["user_id"])) {
        $user_management = new UserManagement();
        if($user_management->deleteUser($_POST["user_id"])) {
            echo "Xóa người dùng thành công!";
        } else {
            echo "Không thể xóa người dùng.";
        }
    } else {
        echo "Vui lòng cung cấp ID người dùng để xóa.";
    }
} else {
    echo "Yêu cầu không hợp lệ.";
}
?>