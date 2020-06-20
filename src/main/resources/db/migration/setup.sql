CREATE DATABASE IF NOT EXISTS `payments`;
CREATE USER IF NOT EXISTS `user`@'localhost';
GRANT ALL ON payments.* TO 'user'@'localhost';
FLUSH PRIVILEGES;