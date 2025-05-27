<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Delete Movie</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            margin-top: 50px;
        }
        .container {
            max-width: 400px;
            margin: auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 10px;
            background-color: #f9f9f9;
        }
        input {
            width: 90%;
            padding: 10px;
            margin-bottom: 10px;
        }
        button {
            padding: 10px 15px;
            background-color: red;
            color: white;
            border: none;
            cursor: pointer;
            font-size: 16px;
        }
        button:hover {
            background-color: darkred;
        }
    </style>
</head>
<body>

    <div class="container">
        <h2>Delete Movie</h2>
        <form action="../backend/delete_movie.php" method="POST">
            <input type="text" name="title" placeholder="Enter movie title" required>
            <button type="submit">Delete Movie</button>
        </form>
    </div>

</body>
</html>
