CREATE TABLE IF NOT EXISTS `users_roles`(
    `user_id` bigint(11) NOT NULL,
    `role_id` bigint(11) NOT NULL,
    `created_at` datetime DEFAULT current_timestamp(),
    `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    PRIMARY KEY (`user_id`, `role_id`),
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
    FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
)ENGINE = InnoDB DEFAULT CHARSET = utf8;