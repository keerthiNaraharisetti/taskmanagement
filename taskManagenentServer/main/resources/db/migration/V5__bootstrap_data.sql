LOCK TABLES `roles` WRITE;
INSERT INTO `roles`(`id`, `name`) VALUES (1, 'ADMIN');
INSERT INTO `roles`(`id`, `name`) VALUES (2, 'USER');
UNLOCK TABLES;

LOCK TABLES `users` WRITE;
INSERT INTO `users`(`id`, `name`, `username`, `password`) VALUES(1,'Keerthi', 'Key-admin', '$2a$10$A8TDsFH/dZQzu/urTBQwjOu/zpjobgqf66IsSBf3wwbfFlzIfSmbW');
INSERT INTO `users`(`id`, `name`, `username`, `password`) VALUES(2,'Revanth', 'Rev', '$2a$10$l8PtdVxLAVUuiV5xAYmf/unnKkPNg0ERJbZwvCTLTIORKFKVy2enG');
UNLOCK TABLES;

LOCK TABLES `users_roles` WRITE;
INSERT INTO `users_roles`(`user_id`, `role_id`) VALUES(1, 1);
INSERT INTO `users_roles`(`user_id`, `role_id`) VALUES(1, 2);
INSERT INTO `users_roles`(`user_id`, `role_id`) VALUES(2, 2);
UNLOCK TABLES;

LOCK TABLES `tasks` WRITE;
INSERT INTO `tasks`(`id`, `title`, `description`, `status`, `due_date`, `completion_date`, `created_by`, `updated_by`) VALUES(1, 'Task 1', 'Complete project proposal', 1, NULL, NULL, 1, NULL);
INSERT INTO `tasks`(`id`, `title`, `description`, `status`, `due_date`, `completion_date`, `created_by`, `updated_by`) VALUES(2, 'Task 2', 'Review and update documentation', 2, NULL, NULL, 2, NULL);
UNLOCK TABLES;