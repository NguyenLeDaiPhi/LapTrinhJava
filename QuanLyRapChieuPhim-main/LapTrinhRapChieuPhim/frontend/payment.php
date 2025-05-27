<?php
$booking_id = $_GET['booking_id'] ?? 0;
?>

<!DOCTYPE html>
<html>
<head><title>Thanh toán</title></head>
<body>
<h1>Thanh toán vé</h1>
<form method="post" action="process-payment.php">
    <input type="hidden" name="booking_id" value="<?= $booking_id ?>">
    <label>Phương thức thanh toán:</label>
    <select name="payment_method">
        <option value="Credit Card">Thẻ tín dụng</option>
        <option value="Momo">Momo</option>
        <option value="ZaloPay">ZaloPay</option>
    </select><br>

    <label>Số tiền (VNĐ):</label>
    <input type="number" name="amount" required><br>

    <button type="submit">Thanh toán</button>
</form>
</body>
</html>
