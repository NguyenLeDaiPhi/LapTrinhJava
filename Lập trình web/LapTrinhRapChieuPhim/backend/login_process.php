// login_process.php
<?php
session_start();
require_once 'auth.php';

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $auth = new Auth();
    $auth->login($_POST["email"], $_POST["password"]);
}
?>