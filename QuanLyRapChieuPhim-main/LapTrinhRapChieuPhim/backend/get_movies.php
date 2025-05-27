<?php
header('Content-Type: application/json'); // Báo cho trình duyệt biết phản hồi là JSON

// Thông tin kết nối cơ sở dữ liệu - CẬP NHẬT DỰA TRÊN THÔNG TIN CỦA BẠN (XAMPP mặc định)
$servername = "localhost"; // Máy chủ mặc định trên XAMPP
$username = "root"; // Tên người dùng mặc định trên XAMPP
$password = ""; // Mật khẩu để trống trên XAMPP mặc định
$dbname = "rapchieuphim_db"; // Tên cơ sở dữ liệu từ schema bạn cung cấp

// Tạo kết nối
$conn = new mysqli($servername, $username, $password, $dbname);

// Kiểm tra kết nối
if ($conn->connect_error) {
    // Nếu kết nối thất bại, trả về lỗi JSON và dừng script
    http_response_code(500); // Mã lỗi máy chủ nội bộ
    echo json_encode(["error" => "Kết nối cơ sở dữ liệu thất bại: " . $conn->connect_error]);
    exit();
}

// Truy vấn dữ liệu phim từ bảng 'movies'
// Chọn tất cả các cột cần thiết theo schema bạn cung cấp
$sql = "SELECT id, title, genre, duration, release_date, showtimes, price, poster, description, trailer_link FROM movies";
$result = $conn->query($sql);

$movies_array = array(); // Mảng để chứa dữ liệu phim

if ($result) { // Kiểm tra xem truy vấn có thành công không
    if ($result->num_rows > 0) {
        // Lấy dữ liệu từng hàng và thêm vào mảng
        while($row = $result->fetch_assoc()) {
            $movies_array[] = $row;
        }
    }
    // Nếu num_rows là 0, mảng movies_array sẽ rỗng, là phản hồi JSON hợp lệ
} else {
    // Xử lý lỗi khi thực thi truy vấn
    http_response_code(500);
    echo json_encode(["error" => "Lỗi truy vấn cơ sở dữ liệu: " . $conn->error]);
    $conn->close();
    exit();
}

// Đóng kết nối cơ sở dữ liệu
$conn->close();

// Trả về dữ liệu phim dưới dạng JSON
echo json_encode($movies_array);
?>