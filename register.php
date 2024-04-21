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

if(isset($_POST['username']) && isset($_POST['password'])) {
    $username = $mysqli->real_escape_string($_POST['username']);
    $password = $mysqli->real_escape_string($_POST['password']);

    $checkQuery = "SELECT * FROM users WHERE username = '$username'";
    $checkResult = $mysqli->query($checkQuery);

    if ($checkResult->num_rows > 0) {
        $response["success"] = false;
        $response["message"] = "Username sudah digunakan";
        echo json_encode($response);
    } else {
        $insertQuery = "INSERT INTO users (username, password) VALUES ('$username', '$password')";
        if ($mysqli->query($insertQuery) === TRUE) {
            $response["success"] = true;
            $response["message"] = "Registrasi berhasil";
            echo json_encode($response);
        } else {
            $response["success"] = false;
            $response["message"] = "Registrasi gagal: " . $mysqli->error;
            echo json_encode($response);
        }
    }
} else {
    $response["success"] = false;
    $response["message"] = "Parameter username dan password tidak ditemukan";
    echo json_encode($response);
}

$mysqli->close();
?>
