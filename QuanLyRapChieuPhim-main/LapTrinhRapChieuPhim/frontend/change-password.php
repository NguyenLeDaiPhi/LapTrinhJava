<?php
$token = $_GET["token"] ?? ""; // Prevent warning if token is not provided
?>
<!DOCTYPE html>
<html>
<head>
    <title>Thay đổi mật khẩu</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/water.css@2/out/water.css">
</head>
<body>

    <h1>Reset Password</h1>

    <form method="post" action="../database/process-reset-password.php">
        <input type="hidden" name="token" value="<?= htmlspecialchars($token) ?>">
        
        <label for="password">Mật khẩu mới</label>
        <input type="password" id="password" name="password" required>

        <label for="password_confirmation">Xác nhận mật khẩu mới</label>
        <input type="password" id="password_confirmation" name="password_confirmation" required>

        <button type="submit">Thay đổi mật khẩu</button>
    </form>

</body>
</html>
