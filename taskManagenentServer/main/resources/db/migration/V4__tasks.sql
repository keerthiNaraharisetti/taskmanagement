CREATE TABLE IF NOT EXISTS `tasks`(
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(256) NOT NULL,
  `description` VARCHAR(256)  DEFAULT NULL,
  `status` int(2) DEFAULT 1,
  `due_date` datetime,
  `completion_date` datetime,
  `created_by` bigint(11) NOT NULL,
  `updated_by` bigint(11),
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  FOREIGN KEY (`created_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  FOREIGN KEY (`updated_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
)ENGINE = InnoDB DEFAULT CHARSET = utf8;