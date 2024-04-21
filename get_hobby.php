<?php
$databaseHost = 'localhost'; 
$databaseName = 'utsanmp'; 
$databaseUsername = 'root'; 
$databasePassword = ''; 
$mysqli = new mysqli($databaseHost, $databaseUsername, $databasePassword, $databaseName);
if ($mysqli->connect_error) {
    die("Koneksi database gagal: " . $mysqli->connect_error);
}
$query = "SELECT * FROM hobby";
$result = $mysqli->query($query);
$rows = array();
while ($row = $result->fetch_assoc()) {
    $rows[] = $row;
}
echo json_encode($rows);

$mysqli->close();
?>
