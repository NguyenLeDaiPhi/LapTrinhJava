<?php
require_once '../database/database.php';

class UserManagement {
    private $conn;

    public function __construct() {
        $db = new Database();
        $this->conn = $db->getConnection();
    }
    
    public function getAllUsers() {
        $sql = "SELECT * FROM users";
        $result = $this->conn->query($sql);
        return $result;
    }
    
    public function addUser($name, $email, $password, $role) {
        $hashed_password = password_hash($password, PASSWORD_DEFAULT);
        
        $stmt = $this->conn->prepare("INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)");
        $stmt->bind_param("ssss", $name, $email, $hashed_password, $role);
        
        if($stmt->execute()) {
            return true;
        } else {
            return false;
        }
    }
    
    public function getUserById($id) {
        $stmt = $this->conn->prepare("SELECT * FROM users WHERE id = ?");
        $stmt->bind_param("i", $id);
        $stmt->execute();
        $result = $stmt->get_result();
        return $result->fetch_assoc();
    }
    
    public function updateUser($id, $name, $email, $role) {
        $stmt = $this->conn->prepare("UPDATE users SET name = ?, email = ?, role = ? WHERE id = ?");
        $stmt->bind_param("sssi", $name, $email, $role, $id);
        
        if($stmt->execute()) {
            return true;
        } else {
            return false;
        }
    }
    
    public function updateUserWithPassword($id, $name, $email, $password, $role) {
        $hashed_password = password_hash($password, PASSWORD_DEFAULT);
        $stmt = $this->conn->prepare("UPDATE users SET name = ?, email = ?, password = ?, role = ? WHERE id = ?");
        $stmt->bind_param("ssssi", $name, $email, $hashed_password, $role, $id);
        
        if($stmt->execute()) {
            return true;
        } else {
            return false;
        }
    }
    
    public function deleteUser($id) {
        $stmt = $this->conn->prepare("DELETE FROM users WHERE id = ?");
        $stmt->bind_param("i", $id);
        
        if($stmt->execute()) {
            return true;
        } else {
            return false;
        }
    }
}
?>