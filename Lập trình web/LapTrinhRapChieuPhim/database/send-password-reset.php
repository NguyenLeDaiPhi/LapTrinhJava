<?php
session_start();
require_once __DIR__ . '/database.php';
require_once __DIR__ . '/mailer.php';

$db = new Database();
$conn = $db->getConnection();

$email = $_POST["email"];
$token = bin2hex(random_bytes(16));
$token_hash = hash("sha256", $token);
$expiry = date("Y-m-d H:i:s", time() + 60 * 30);

$sql = "UPDATE users SET reset_token_hash = ?, reset_token_expires_at = ? WHERE email = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("sss", $token_hash, $expiry, $email);
$stmt->execute();

if ($conn->affected_rows) {
    $mail = require __DIR__ . "/mailer.php";
    $mail->setFrom("noreply@example.com");
    $mail->addAddress($email);
    $mail->Subject = "Password Reset";
    $mail->Body = <<<END
    Click <a href="http://localhost:3000/LapTrinhRapChieuPhim/frontend/change-password.php?token=$token">vào đây</a> để thay đổi mật khẩu.
    END;
    try {
        $mail->send();
    } catch (Exception $e) {
        echo "Không thể gửi mail, lỗi: {$mail->ErrorInfo}";
    }
}

echo "Mail đã gửi, vui lòng kiểm tra.";