#!/usr/bin/php
<?php
error_reporting(E_ALL);

ini_set("soap.wsdl_cache_enabled", "0"); // disabling WSDL cache

$client = new SoapClient('http://localhost:4242/soap?wsdl');
$client->sayHi();
$response = $client->getDates(42);
var_export($response);

print("ende.\n");
