// auth.php
<?php
require_once '../database/database.php';
class Auth {
    private $conn;

    public function __construct() {
        $db = new Database();
        $this->conn = $db->getConnection();
    }

    public function register($name, $email, $password, $confirm_password) {
        session_start();
        if (empty($name) || empty($email) || empty($password) || empty($confirm_password)) {
            $_SESSION["register_error"] = "Vui lòng điền đầy đủ thông tin!";
            header("Location: ../frontend/register.php");
            exit();
        }

        if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
            $_SESSION["register_error"] = "Email không hợp lệ!";
            header("Location: ../frontend/register.php");
            exit();
        }

        if ($password !== $confirm_password) {
            $_SESSION["register_error"] = "Mật khẩu xác nhận không khớp!";
            header("Location: ../frontend/register.php");
            exit();
        }

        $stmt = $this->conn->prepare("SELECT id FROM users WHERE email = ?");
        $stmt->bind_param("s", $email);
        $stmt->execute();
        $stmt->store_result();

        if ($stmt->num_rows > 0) {
            $_SESSION["register_error"] = "Email đã được đăng ký!";
            $stmt->close();
            header("Location: ../frontend/register.php");
            exit();
        }
        $stmt->close();

        $hashed_password = password_hash($password, PASSWORD_DEFAULT);
        $stmt = $this->conn->prepare("INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)");
        $role = "user";
        $stmt->bind_param("ssss", $name, $email, $hashed_password, $role);

        if ($stmt->execute()) {
            $_SESSION["register_success"] = "Đăng ký thành công! Vui lòng đăng nhập.";
            header("Location: ../frontend/login.php");
            exit();
        } else {
            $_SESSION["register_error"] = "Lỗi đăng ký: " . $stmt->error;
            header("Location: ../frontend/register.php");
            exit();
        }

        $stmt->close();
    }

    public function login($email, $password) {
        session_start();
        if (empty($email) || empty($password)) {
            $_SESSION["login_error"] = "Xin vui lòng nhập đầy đủ thông tin.";
            header("Location: ../frontend/login.php");
            exit();
        }

        $stmt = $this->conn->prepare("SELECT id, name, email, password, role FROM users WHERE email = ?");
        if (!$stmt) {
            $_SESSION["login_error"] = "Lỗi truy vấn: " . $this->conn->error;
            header("Location: ../frontend/login.php");
            exit();
        }

        if (!$stmt->bind_param("s", $email)) {
            $_SESSION["login_error"] = "Lỗi khi gán tham số: " . $stmt->error;
            header("Location: ../frontend/login.php");
            exit();
        }

        $stmt->execute();
        $stmt->store_result();
        $id = null;
        $name = null;
        $email_db = null;
        $hashed_password = null;
        $role = null;
        $hashed_password = password_hash($password, PASSWORD_DEFAULT);

        if ($stmt->num_rows === 1) {
            $stmt->bind_result($id, $name, $email_db, $hashed_password, $role);
            $stmt->fetch();
    
            if(password_verify($password, $hashed_password)) {
                $_SESSION["user_id"] = $id;
                $_SESSION["user_name"] = $name;
                $_SESSION["user_role"] = $role;
    
                //Redirect by role
                if($role == 'admin') {
                    header("Location: ../frontend/admin_dashboard.php");
                }
                else {
                    header("Location: ../frontend/index.php");
                }
                exit();
            } else {
                $_SESSION["login_error"] = "Mật khẩu sai, vui lòng nhập lại.";
            }
        } else {
            $_SESSION["login_error"] = "Tài khoản không tồn tại.";
        }
    
        $stmt->close();
    
        header("Location: ../frontend/login.php");
        exit();
    }
}
?>