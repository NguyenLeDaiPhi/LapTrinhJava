<?php
require_once 'auth.php';

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $auth = new Auth();
    $auth->register($_POST["name"], $_POST["email"], $_POST["password"], $_POST["confirm_password"]);
}
?>