<?php
$token = $_GET["token"];
$token_hash = hash("sha256", $token);

require_once __DIR__ . '/database.php';
$db = new Database();
$conn = $db->getConnection();

$sql = "SELECT * FROM users WHERE reset_token_hash = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("s", $token_hash);
$stmt->execute();
$result = $stmt->get_result();
$user = $result->fetch_assoc();

if($user == null) {
    die("token not found");
}

if (strtotime($user["reset_token_expires_at"]) <= time()) {
    die("Token hết hạn.");
}

echo "Token chưa hết hạn và còn có thể sử dụng được.";