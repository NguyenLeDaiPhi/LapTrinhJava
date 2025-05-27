<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thêm phim mới</title>
</head>
<body>
    <h2>Thêm phim mới</h2>
    <form action="../backend/add_movies_process.php" method="POST" enctype="multipart/form-data">
        <label>Tiêu đề phim:</label><br>
        <input type="text" name="title" required><br><br>

        <label>Thể loại:</label><br>
        <input type="text" name="genre" required><br><br>

        <label>Thời lượng (phút):</label><br>
        <input type="number" name="duration" required><br><br>

        <label>Ngày phát hành:</label><br>
        <input type="date" name="release_date" required><br><br>

        <label>Suất chiếu (phân cách bởi dấu phẩy):</label><br>
        <input type="text" name="showtimes" placeholder='14:00,18:00,21:00'><br><br>

        <label>Giá vé:</label><br>
        <input type="number" step="0.01" name="price" required><br><br>

        <label>Hình Poster (jpg/png):</label><br>
        <input type="file" name="poster" required><br><br>

        <label>Mô tả phim:</label><br>
        <textarea name="description" rows="4" cols="50"></textarea><br><br>

        <label>Trailer Link (YouTube):</label><br>
        <input type="url" name="trailer_link"><br><br>

        <button type="submit">Thêm phim</button>
    </form>
</body>
</html>
