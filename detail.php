<?php
$databaseHost = 'localhost';
$databaseName = 'utsanmp'; 
$databaseUsername = 'root';
$databasePassword = '';

$mysqli = new mysqli($databaseHost, $databaseUsername, $databasePassword, $databaseName);

if ($mysqli->connect_error) {
    die("Koneksi database gagal: " . $mysqli->connect_error);
}

if(isset($_GET['id'])) {
    $id = $mysqli->real_escape_string($_GET['id']);
    $query = "SELECT * FROM hobby WHERE id = $id";
    $result = $mysqli->query($query);
    if ($result->num_rows > 0) {
        $row = $result->fetch_assoc();

        echo json_encode($row);
    } else {
        echo "Data tidak ditemukan";
    }
} else {
    echo "Parameter ID tidak ditemukan";
}

$mysqli->close();
?>
