<?php
    class Database {
        private $host = "127.0.0.1";
        private $port = 3306;
        private $username = "root";
        private $password = "";
        private $database = "rapchieuphim_db";
        public $conn;

        public function __construct() {
            $this->connection();
        }

        public function connection() {
            $this->conn = new mysqli(
                $this->host,
                $this->username,
                $this->password,
                $this->database,
                $this->port
            );

            if($this->conn->connect_error) {
                die("Kết nối thất bại.");
            }
        }
        public function getConnection() {
            return $this->conn;
        }
    }
?>