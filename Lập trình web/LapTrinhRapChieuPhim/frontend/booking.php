<?php
require_once '../database/database.php';
$db = new Database();
$conn = $db->getConnection();

$movies = $conn->query("SELECT id, title, showtimes FROM movies");
?>

<!DOCTYPE html>
<html>
<head>
    <title>Đặt vé</title>
</head>
<body>
<h1>Chọn phim và đặt vé</h1>
<form method="post" action="../backend/process_booking.php">
    <label>Chọn phim:</label>
    <select name="movie_id" required>
        <?php while ($row = $movies->fetch_assoc()): ?>
            <option value="<?= $row['id'] ?>"><?= $row['title'] ?></option>
        <?php endwhile; ?>
    </select><br>

    <label>Chọn suất chiếu:</label>
    <input type="text" name="showtime" placeholder="VD: 14:00" required><br>

    <label>Chọn ghế (ví dụ A1,A2):</label>
    <input type="text" name="seats" required><br>

    <input type="hidden" name="user_id" value="1"> <!-- Tạm thời hardcode -->
    <button type="submit">Đặt vé</button>
</form>
</body>
</html>
