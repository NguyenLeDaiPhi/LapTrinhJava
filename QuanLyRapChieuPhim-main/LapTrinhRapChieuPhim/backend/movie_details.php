<!-- movie_detail.php -->
<?php
require '../database/database.php';

if (isset($_GET['title'])) {
    $db = new Database();
    $conn = $db->getConnection();
    $title = $_GET['title'];

    $stmt = $conn->prepare("SELECT * FROM movies WHERE title = ?");
    $stmt->bind_param("s", $title);
    $stmt->execute();
    $result = $stmt->get_result();

    if ($result->num_rows > 0) {
        $movie = $result->fetch_assoc();
    } else {
        echo "Movie not found.";
        exit;
    }
} else {
    echo "No movie selected.";
    exit;
}
?>
