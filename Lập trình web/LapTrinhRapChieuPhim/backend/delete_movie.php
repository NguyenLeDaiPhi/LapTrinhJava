<?php
require_once 'movies.php';

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    if (!empty($_POST["title"])) {
        $delete_movie = new Movies();
        $delete_movie->deleteMovie($_POST["title"]); // Delete movie by title
    } else {
        echo "Vui lòng nhập tên phim để xóa.";
    }
} else {
    echo "Yêu cầu không hợp lệ.";
}
?>
