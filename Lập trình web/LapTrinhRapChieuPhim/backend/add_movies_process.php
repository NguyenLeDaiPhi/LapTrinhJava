<?php
require 'movies.php';

if($_SERVER["REQUEST_METHOD"] == "POST") {
    $movies = new Movies();
    $movies->addMovie($_POST["title"], $_POST["genre"], $_POST["duration"], $_POST["release_date"], $_POST["showtimes"], $_POST["price"], $_FILES['poster'], $_POST["description"], $_POST["trailer_link"], date("Y-m-d H:i:s"));
}
?>