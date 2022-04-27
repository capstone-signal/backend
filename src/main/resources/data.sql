-- user(id 7000) 생성
INSERT INTO `hidiscuss`.`user` (`id`, `created_at`, `last_modified_at`, `access_token`, `email`, `username`, `point`, `refresh_token`) VALUES (7000, NOW(), NOW(), '1', '1', '1', 1, '1');

-- discussion(id 7100) 생성
INSERT INTO `discussion` (`id`, `created_at`, `last_modified_at`, `live_review_available_times`, `live_review_required`, `priority`, `question`, `state`, `title`, `user_id`) VALUES
    (7100, NOW(), NOW(), '{\"times\": [{\"end\": \"2022-04-28T15:00:21.386Z\", \"start\": \"2022-04-27T11:02:21.386Z\"}]}', 1, 0, '0', 'NOT_REVIEWED', 'title', 7000);

-- discussionCode(id 7200, 7201) 생성
INSERT INTO `hidiscuss`.`discussion_code` (`id`, `created_at`, `last_modified_at`, `additions`, `changes`, `content`, `deletions`, `filename`, `sha`, `status`, `discussion_id`) VALUES (7200, NOW(), NOW(), 1, 1, '1', 1, '1', '1', 'ADDED', 7100);
INSERT INTO `hidiscuss`.`discussion_code` (`id`, `created_at`, `last_modified_at`, `additions`, `changes`, `content`, `deletions`, `filename`, `sha`, `status`, `discussion_id`) VALUES (7201, NOW(), NOW(), 1, 1, '1', 1, '1', '1', 'ADDED', 7100);

-- review(id 7300) 생성
INSERT INTO `hidiscuss`.`review` (`id`, `created_at`, `last_modified_at`, `accepted`, `review_type`, `discussion_id`, `reviewer_id`) VALUES(7300, NOW(), NOW(), 0, 'COMMENT', 7100, 7000);
INSERT INTO `hidiscuss`.`review` (`id`, `created_at`, `last_modified_at`, `accepted`, `review_type`, `discussion_id`, `reviewer_id`) VALUES
    (7301, NOW(), NOW(), 0, 'LIVE', 7100, 7000);

-- commentReviewDiff(id 7400, 7401) 생성
INSERT INTO `hidiscuss`.`comment_review_diff` (`id`, `created_at`, `last_modified_at`, `code_after`, `code_locate`, `comment`, `discussion_code_id`, `review_id`) VALUES
    (7400, NOW(), NOW(), 'string', 'string', 'string', 7200, 7300);
INSERT INTO `hidiscuss`.`comment_review_diff` (`id`, `created_at`, `last_modified_at`, `code_after`, `code_locate`, `comment`, `discussion_code_id`, `review_id`) VALUES
    (7401, NOW(), NOW(), 'string', 'string', 'string', 7200, 7300);

-- liveReviewDiff(id 7500) 생성
INSERT INTO `hidiscuss`.`live_review_diff` (`id`, `created_at`, `last_modified_at`, `code_after`, `discussion_code_id`, `review_id`) VALUES
    (7500, NOW(), NOW(), 'code_after', 7201, 7301);

-- liveReviewReservation(id 7600) 생성
INSERT INTO `review_reservation` (`id`, `created_at`, `last_modified_at`, `isdone`, `review_start_date`, `reviewee_participated`, `reviewer_participated`, `discussion_id`, `review_id`, `reviewer_id`) VALUES
    (7600, NOW(), NOW(), 0, '2022-04-27 18:00:02.947000', 0, 0, 7100, NULL, 7000);