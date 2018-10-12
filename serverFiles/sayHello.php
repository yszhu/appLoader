<?php
$tempCat=array('name' =>"hello",'description'=>'http://192.168.3.4:8080/apks/hello.apk');
$tempDog=array('name' =>"Jack",'description'=>' love reading');
$result= array($tempCat,$tempDog);
echo json_encode($result);
  ?>