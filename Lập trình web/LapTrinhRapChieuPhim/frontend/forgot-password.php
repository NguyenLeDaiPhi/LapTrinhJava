<!DOCTYPE html>
<html>
<head>
    <title>Quên mật khẩu</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/water.css@2/out/water.css">
</head>
<body>

    <h1>Quên mật khẩu</h1>

    <form method="post" action="../database/send-password-reset.php">
        <label for="email">Email</label>
        <input type="email" name="email" id="email" required>
        <button type="submit">Gửi</button>
    </form>

</body>
</html>
