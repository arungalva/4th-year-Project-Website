CREATE USER 'eggplant'@'localhost' IDENTIFIED BY 'emoji';
GRANT ALL PRIVILEGES ON * . * TO 'eggplant'@'localhost';

CREATE DATABASE IF NOT EXISTS `sysc4806` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
