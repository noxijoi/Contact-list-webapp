CREATE USER 'maskaliova'@'localhost' IDENTIFIED  BY 'zaqwsx';
GRANT ALL PRIVILEGES ON * . * TO 'maskaliova'@'localhost';
FLUSH  PRIVILEGES;


-- check timezone problems
SET @@global.time_zone = '+00:00';
SET @@session.time_zone = '+00:00';