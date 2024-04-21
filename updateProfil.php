<?php
$databaseHost = 'localhost';
$databaseName = 'utsanmp';
$databaseUsername = 'root'; 
$databasePassword = ''; 
$mysqli = new mysqli($databaseHost, $databaseUsername, $databasePassword, $databaseName);
if ($mysqli->connect_error) {
    die("Koneksi database gagal: " . $mysqli->connect_error);
}

if(isset($_POST['username']) && isset($_POST['password']) && isset($_POST['new_username']) && isset($_POST['new_password'])) {
    $currentUsername = $mysqli->real_escape_string($_POST['username']);
    $currentPassword = $mysqli->real_escape_string($_POST['password']);
    $newUsername = $mysqli->real_escape_string($_POST['new_username']);
    $newPassword = $mysqli->real_escape_string($_POST['new_password']);
    $query = "SELECT * FROM users WHERE username = '$currentUsername' AND password = '$currentPassword'";
    $result = $mysqli->query($query);

    if ($result->num_rows > 0) {
        $updateQuery = "UPDATE users SET username = '$newUsername', password = '$newPassword' WHERE username = '$currentUsername'";
        if ($mysqli->query($updateQuery) === TRUE) {
            $response["success"] = true;
            $response["message"] = "Data berhasil diperbarui";
            echo json_encode($response);
        } else {
            $response["success"] = false;
            $response["message"] = "Update gagal: " . $mysqli->error;
            echo json_encode($response);
        }
    } else {
        $response["success"] = false;
        $response["message"] = "Username dan/atau password saat ini tidak sesuai";
        echo json_encode($response);
    }
} else {
    $response["success"] = false;
    $response["message"] = "Parameter username dan password baru tidak ditemukan";
    echo json_encode($response);
}
$mysqli->close();
?>
