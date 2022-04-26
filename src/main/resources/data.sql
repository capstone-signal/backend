-- user(id 7000) 생성
INSERT INTO `hidiscuss`.`user` (`id`, `created_at`, `last_modified_at`, `access_token`, `email`, `username`, `point`, `refresh_token`) VALUES (7000, NOW(), NOW(), '1', '1', '1', 1, '1');

-- discussion(id 7100) 생성
INSERT INTO `hidiscuss`.`discussion` (`id`, `created_at`, `last_modified_at`, `live_review_required`, `priority`, `question`, `state`, `title`, `user_id`) VALUES (7100, NOW(), NOW(), 0, 0, '0', 'NOT_REVIEWED', 'title', 7000);
-- discussionCode(id 7200, 7201) 생성
INSERT INTO `hidiscuss`.`discussion_code` (`id`, `created_at`, `last_modified_at`, `additions`, `changes`, `content`, `deletions`, `filename`, `sha`, `status`, `discussion_id`) VALUES (7200, NOW(), NOW(), 1, 1, '1', 1, '1', '1', 'ADDED', 7100);
INSERT INTO `hidiscuss`.`discussion_code` (`id`, `created_at`, `last_modified_at`, `additions`, `changes`, `content`, `deletions`, `filename`, `sha`, `status`, `discussion_id`) VALUES (7201, NOW(), NOW(), 1, 1, '1', 1, '1', '1', 'ADDED', 7100);

-- review(id 7300) 생성
INSERT INTO `hidiscuss`.`review` (`id`, `created_at`, `last_modified_at`, `accepted`, `review_type`, `discussion_id`, `reviewer_id`) VALUES (7300, NOW(), NOW(), 0, '0', 7100, 7000);

-- commentReviewDiff(id 7400, 7401) 생성


-- liveReviewDiff 생성

