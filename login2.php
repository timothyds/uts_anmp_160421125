<?php
$databaseHost = 'localhost';
$databaseName = 'utsanmp';
$databaseUsername = 'root';
$databasePassword = '';

$mysqli = new mysqli($databaseHost, $databaseUsername, $databasePassword, $databaseName);

if ($mysqli->connect_error) {
    $response["success"] = false;
    $response["message"] = "Koneksi database gagal: " . $mysqli->connect_error;
    echo json_encode($response);
    exit();
}

if(isset($_GET['username']) && isset($_GET['password'])) {
    $username = $mysqli->real_escape_string($_GET['username']);
    $password = $mysqli->real_escape_string($_GET['password']);

    $query = "SELECT * FROM users WHERE username = '$username' AND password = '$password'";
    $result = $mysqli->query($query);

    if ($result->num_rows > 0) {
        $row = $result->fetch_assoc();
        $response["success"] = true;
        $response["username"] = $row["username"];
        $response["password"] = $row["password"];
        echo json_encode($response);
    } else {
        $response["success"] = false;
        $response["message"] = "Login gagal";
        echo json_encode($response);
    }
} else {
    $response["success"] = false;
    $response["message"] = "Parameter username dan password tidak ditemukan";
    echo json_encode($response);
}

$mysqli->close();
?>
