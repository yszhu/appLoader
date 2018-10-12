<?php
$tempCat=array('name' =>"Tom",'description'=>' love music');
$tempDog=array('name' =>"Jack",'description'=>' love reading');
$result= array($tempCat,$tempDog);
echo json_encode($result);
  ?>