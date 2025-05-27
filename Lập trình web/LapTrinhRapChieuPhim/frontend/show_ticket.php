<?php
session_start();
require_once '../database/database.php';

if (!isset($_SESSION['user_id'])) {
    die("Bạn cần đăng nhập để xem vé.");
}

$user_id = $_SESSION['user_id'];

$db = new Database();
$conn = $db->getConnection();

// Lấy vé mới nhất của user
$sql = "SELECT 
        b.id AS booking_id, 
        m.title, 
        m.price, 
        b.seats, 
        b.booking_time, 
        p.payment_method, 
        p.payment_status 
    FROM bookings b 
    JOIN movies m ON b.movie_id = m.id 
    LEFT JOIN payments p ON b.id = p.booking_id 
    WHERE b.user_id = ? 
    ORDER BY b.booking_time DESC 
    LIMIT 1";

$stmt = $conn->prepare($sql);
$stmt->bind_param("i", $user_id);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows === 0) {
    echo "Bạn chưa có vé nào.";
    exit;
}

$ticket = $result->fetch_assoc();
// Tính tổng tiền
$seatList = explode(',', $ticket['seats']);
$seatCount = count($seatList);
$pricePerSeat = $ticket['price'];
$comboPrice = 50000;

// Giả sử nếu người dùng chọn combo, họ ghi thêm ghế là "combo" vào chuỗi seats
$hasCombo = in_array('combo', array_map('trim', $seatList));

$total = $seatCount * $pricePerSeat;
if ($hasCombo) {
    $total += $comboPrice;
}

?>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Vé của bạn</title>
    <style>
        body { font-family: Arial; padding: 20px; }
        .ticket {
            border: 2px dashed #4CAF50;
            padding: 20px;
            width: 400px;
            margin: auto;
            background-color: #f9f9f9;
        }
        .ticket h2 { color: #4CAF50; }
    </style>
</head>
<body>

<div class="ticket">
    <h2>🎟️ Vé xem phim</h2>
    <p><strong>Phim:</strong> <?= htmlspecialchars($ticket['title']) ?></p>
    <p><strong>Ghế:</strong> <?= htmlspecialchars($ticket['seats']) ?></p>
    <p><strong>Thời gian đặt:</strong> <?= $ticket['booking_time'] ?></p>
    <p><strong>Giá vé 1 ghế:</strong> <?= $pricePerSeat ?> VNĐ</p>
    <p><strong>Số ghế:</strong> <?= htmlspecialchars($ticket['seats']) ?></p>

    <?php if ($hasCombo): ?>
        <p><strong>Combo bắp + nước:</strong> <?= $comboPrice ?> VNĐ</p>
    <?php endif; ?>

    <p><strong>Tổng cộng:</strong> <?= $total ?> VNĐ</p>
    <p><strong>Thanh toán:</strong> <?= $ticket['payment_status'] ?> (<?= $ticket['payment_method'] ?>)</p>
    <p><strong>Mã đặt vé:</strong> <?= $ticket['booking_id'] ?></p>
    </div>
    </div>

<div style="text-align: center; margin-top: 20px;">
    <a href="../frontend/index.php" style="
        padding: 10px 20px;
        background-color: #4CAF50;
        color: white;
        text-decoration: none;
        border-radius: 5px;
    ">🏠 Quay lại trang chủ</a>
</div>

</body>
</html>

</body>
</html>
