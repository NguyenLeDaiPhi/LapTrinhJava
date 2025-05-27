<?php
require_once __DIR__ . '/database.php';
$db = new Database();
$conn = $db->getConnection();

$token = $_POST["token"];
$token_hash = hash("sha256", $token);

$sql = "SELECT * FROM users WHERE reset_token_hash = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("s", $token_hash);
$stmt->execute();
$result = $stmt->get_result();
$user = $result->fetch_assoc();

if($user == null) {
    die("Không tìm thấy token.");
}

if (strtotime($user["reset_token_expires_at"]) <= time()) {
    die("token has expired");
}

if (strlen($_POST["password"]) < 8) {
    die("Mật khẩu phải ít nhất 8 ký tự.");
}

if ( ! preg_match("/[a-z]/i", $_POST["password"])) {
    die("Mật khẩu phải ít nhất có 1 ký tự chữ cái.");
}

if ( ! preg_match("/[0-9]/", $_POST["password"])) {
    die("Mật khẩu phải ít nhất có 1 số.");
}

if ($_POST["password"] !== $_POST["password_confirmation"]) {
    die("Mật khẩu phải trùng với mật khẩu xác nhận.");
}

$password_hash = password_hash($_POST["password"], PASSWORD_DEFAULT);

$sql = "UPDATE users SET password = ?, reset_token_hash = NULL, reset_token_expires_at = NULL WHERE id = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("ss", $password_hash, $user["id"]);
$stmt->execute();

header("Location: ../frontend/login.php");
echo "<h2 style='color:green;'>Thay đổi mật khẩu thành công!</h2>";
echo "<p>Bạn có thể <a href='/frontend/login.php'> click vào đây để đăng nhập</a>.</p>";