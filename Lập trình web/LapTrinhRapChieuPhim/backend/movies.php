<?php
require_once '../database/database.php';

class Movies {
    private $conn;
    public function __construct() {
        $db = new Database();
        $this->conn = $db->getConnection();
    }

    public function getMovieDetails($title) {
        $sql = "SELECT * FROM movies WHERE title = ?";
        $stmt = $this->conn->prepare($sql);
        $stmt->bind_param("s", $title);
        $stmt->execute();
        $result = $stmt->get_result();
        return $result->fetch_assoc();
    }    

    public function showMovies() {
        $sql = "SELECT * FROM movies"; // Fetch all movies from the database
        $result = $this->conn->query($sql);
        if ($result->num_rows > 0) {
            while($row = $result->fetch_assoc()) {
                echo '<div class="movie-card">';
                echo '<a href="details.php?title=' . urlencode($row["title"]) . '">
                <img src="../frontend/img/' . $row["poster"] . '" alt="' . $row["title"] . '" class="movie-poster">
                </a>';
                echo '<h3><a href="details.php?title=' . urlencode($row["title"]) . '">' . $row["title"] . '</a></h3>';
                echo '<p><strong>Thể loại:</strong> ' . $row["genre"] . '</p>';
                echo '<p><strong>Ngày ra mắt:</strong> ' . $row["release_date"] . '</p>';
                echo '<p><strong>Thời lượng:</strong> ' . $row["duration"] . ' phút</p>';
                echo '<p><strong>Giá: </strong>' . $row["price"] . ' đồng</p>';
                echo '<a href="' . $row["trailer_link"] . '" target="_blank">Xem trailer </a>';
                echo '<a href="../frontend/ticket.html"' . $row["trailer_link"] . '" target="_blank">Mua vé</a>';
                echo '</div>';
            }
        } else {
            echo "<p>No movies found.</p>";
        } 
    }

    public function addMovie($title, $genre, $duration, $release_date, $showtimes, $price, $poster, $description, $trailer_link, $created_at) {
        $title = $_POST["title"];
        $genre = $_POST["genre"];
        $duration = $_POST["duration"];
        $release_date = $_POST["release_date"];
        $showtimes = json_encode(explode(',', $_POST['showtimes']));
        $price = $_POST['price'];
        $description = $_POST['description'];
        $trailer_link = $_POST['trailer_link'];
        $created_at = date('Y-m-d H:i:s');
        $poster = $_FILES['poster']['name'];
        $poster_tmp = $_FILES['poster']['tmp_name'];
        $poster_path = "../frontend/img/" .$poster;

        if (move_uploaded_file($poster_tmp, $poster_path)) {
            $stmt = $this->conn->prepare("INSERT INTO movies (title, genre, duration, release_date, showtimes, price, poster, description, trailer_link, created_at) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            $stmt->bind_param("ssissdssss", $title, $genre, $duration, $release_date, $showtimes, $price, $poster, $description, $trailer_link, $created_at);

            if($stmt->execute()) {
                echo "Phim đã được thêm thành công.";
            } else {
                echo "Lỗi, xin vui lòng thử lại.";
            }
        } else {
            echo "Không thể upload được hình ảnh, vui lòng thử lại.";
        }
    }

    public function deleteMovie($title) {
        $sql = "DELETE FROM movies WHERE title = ?";
        $stmt = $this->conn->prepare($sql);
        if (!$stmt) {
            die ("Lỗi truy vấn, vui lòng kiểm tra lại database.");
        }

        $stmt->bind_param("s", $title);
        if($stmt->execute()) {
            echo "Phim đã được thêm thành công, vui lòng kiểm tra lại.";
            header("Location: ../frontend/showMovies.php");
        } else {
            echo "Lỗi, không thể xóa phim này.";
        }
    }

    public function getOtherMovies($currentTitle, $limit = 10) {
        $sql = "SELECT * FROM movies WHERE title != ? LIMIT ?";
        $stmt = $this->conn->prepare($sql);
        $stmt->bind_param("si", $currentTitle, $limit);
        $stmt->execute();
        $result = $stmt->get_result();
        return $result;
    }
    
}
?>