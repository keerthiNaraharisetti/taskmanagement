CREATE TABLE IF NOT EXISTS `users`(
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(256) NOT NULL,
  `username` VARCHAR(256) NOT NULL,
  `password` VARCHAR(256) NOT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;