<?php
require_once 'user_management.php';

$user_management = new UserManagement();
$users = $user_management->getAllUsers();

$users_array = array();
while($user = $users->fetch_assoc()) {
    // Không trả về mật khẩu khi lấy danh sách
    unset($user['password']);
    $users_array[] = $user;
}

header('Content-Type: application/json');
echo json_encode($users_array);
?>