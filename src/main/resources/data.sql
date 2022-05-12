-- user(id 7000, 7001) 생성
INSERT INTO `hidiscuss`.`user` (`id`, `created_at`, `last_modified_at`, `email`, `username`, `point`) VALUES (7000, NOW(), NOW(), '수홍', '1', 1);
INSERT INTO `hidiscuss`.`user` (`id`, `created_at`, `last_modified_at`, `email`, `username`, `point`) VALUES (7001, NOW(), NOW(), '동형', '1', 1);

-- discussion(id 7100, 7101, 7102) 생성
INSERT INTO `discussion` (`id`, `created_at`, `last_modified_at`, `live_review_available_times`, `live_review_required`, `priority`, `question`, `state`, `title`, `user_id`) VALUES
    (7100, '2022-05-01 00:07:14.000000', NOW(), '{\"times\": [{\"end\": \"2022-04-28T15:00:21.386Z\", \"start\": \"2022-04-27T11:02:21.386Z\"}]}', 1, 1, '0', 'NOT_REVIEWED', '1_테스트_수홍_낫리뷰', 7000);
INSERT INTO `discussion` (`id`, `created_at`, `last_modified_at`, `live_review_available_times`, `live_review_required`, `priority`, `question`, `state`, `title`, `user_id`) VALUES
    (7101, '2022-05-03 00:07:14.000000', NOW(), '{\"times\": [{\"end\": \"2022-04-28T15:00:21.386Z\", \"start\": \"2022-04-27T11:02:21.386Z\"}]}', 1, 2, '0', 'NOT_REVIEWED', '2_테스트_수홍_낫리뷰', 7000);
INSERT INTO `discussion` (`id`, `created_at`, `last_modified_at`, `live_review_available_times`, `live_review_required`, `priority`, `question`, `state`, `title`, `user_id`) VALUES
    (7102, '2022-05-05 00:07:14.000000', NOW(), '{\"times\": [{\"end\": \"2022-04-28T15:00:21.386Z\", \"start\": \"2022-04-27T11:02:21.386Z\"}]}', 1, 3, '0', 'NOT_REVIEWED', '3_테스트_동형_낫리뷰', 7001);
INSERT INTO `discussion` (`id`, `created_at`, `last_modified_at`, `live_review_available_times`, `live_review_required`, `priority`, `question`, `state`, `title`, `user_id`) VALUES
    (7103, '2022-05-07 00:07:14.000000', NOW(), '{\"times\": [{\"end\": \"2022-04-28T15:00:21.386Z\", \"start\": \"2022-04-27T11:02:21.386Z\"}]}', 1, 4, '0', 'REVIEWING', '4_테스트_동형_낫리뷰', 7001);
INSERT INTO `discussion` (`id`, `created_at`, `last_modified_at`, `live_review_available_times`, `live_review_required`, `priority`, `question`, `state`, `title`, `user_id`) VALUES
    (7104, '2022-05-09 00:07:14.000000', NOW(), '{\"times\": [{\"end\": \"2022-04-28T15:00:21.386Z\", \"start\": \"2022-04-27T11:02:21.386Z\"}]}', 1, 5, '0', 'NOT_REVIEWED', '5_테스트_동형_낫리뷰', 7001);
INSERT INTO `discussion` (`id`, `created_at`, `last_modified_at`, `live_review_available_times`, `live_review_required`, `priority`, `question`, `state`, `title`, `user_id`) VALUES
    (7105, '2022-05-02 00:07:14.000000', NOW(), '{\"times\": [{\"end\": \"2022-04-28T15:00:21.386Z\", \"start\": \"2022-04-27T11:02:21.386Z\"}]}', 1, 6, '0', 'NOT_REVIEWED', '6_테스트_동형_낫리뷰', 7001);
INSERT INTO `discussion` (`id`, `created_at`, `last_modified_at`, `live_review_available_times`, `live_review_required`, `priority`, `question`, `state`, `title`, `user_id`) VALUES
    (7106, '2022-05-04 00:07:14.000000', NOW(), '{\"times\": [{\"end\": \"2022-04-28T15:00:21.386Z\", \"start\": \"2022-04-27T11:02:21.386Z\"}]}', 1, 7, '0', 'NOT_REVIEWED', '7_테스트_동형_낫리뷰', 7001);
INSERT INTO `discussion` (`id`, `created_at`, `last_modified_at`, `live_review_available_times`, `live_review_required`, `priority`, `question`, `state`, `title`, `user_id`) VALUES
    (7107, '2022-05-06 00:07:14.000000', NOW(), '{\"times\": [{\"end\": \"2022-04-28T15:00:21.386Z\", \"start\": \"2022-04-27T11:02:21.386Z\"}]}', 1, 8, '0', 'NOT_REVIEWED', '8_테스트_동형_낫리뷰', 7001);
INSERT INTO `discussion` (`id`, `created_at`, `last_modified_at`, `live_review_available_times`, `live_review_required`, `priority`, `question`, `state`, `title`, `user_id`) VALUES
    (7108, '2022-05-08 00:07:14.000000', NOW(), '{\"times\": [{\"end\": \"2022-04-28T15:00:21.386Z\", \"start\": \"2022-04-27T11:02:21.386Z\"}]}', 1, 9, '0', 'NOT_REVIEWED', '9_테스트_동형_낫리뷰', 7001);

-- discussionCode(id 7200, 7201) 생성
INSERT INTO `discussion_code` (`id`, `created_at`, `last_modified_at`, `content`, `filename`, `language`, `discussion_id`) VALUES
    (7200, NOW(), NOW(), 'content', 'filename', 'language', 7100);
INSERT INTO `discussion_code` (`id`, `created_at`, `last_modified_at`, `content`, `filename`, `language`, `discussion_id`) VALUES
    (7201, NOW(), NOW(), 'content', 'filename', 'language', 7100);

-- review(id 7300) 생성
INSERT INTO `hidiscuss`.`review` (`id`, `created_at`, `last_modified_at`, `accepted`, `review_type`, `discussion_id`, `reviewer_id`) VALUES(7300, NOW(), NOW(), 0, 'COMMENT', 7100, 7000);
INSERT INTO `hidiscuss`.`review` (`id`, `created_at`, `last_modified_at`, `accepted`, `review_type`, `discussion_id`, `reviewer_id`) VALUES
    (7301, NOW(), NOW(), 0, 'LIVE', 7100, 7000);

-- commentReviewDiff(id 7400, 7401) 생성
INSERT INTO `hidiscuss`.`comment_review_diff` (`id`, `created_at`, `last_modified_at`, `code_after`, `code_locate`, `comment`, `discussion_code_id`, `review_id`) VALUES
    (7400, NOW(), NOW(), 'ss', '{}', 'd', 7200, 7300);
INSERT INTO `hidiscuss`.`comment_review_diff` (`id`, `created_at`, `last_modified_at`, `code_after`, `code_locate`, `comment`, `discussion_code_id`, `review_id`) VALUES
    (7401, NOW(), NOW(), 'ss', '{}', 'd', 7200, 7300);


-- liveReviewDiff(id 7500) 생성
INSERT INTO `hidiscuss`.`live_review_diff` (`id`, `created_at`, `last_modified_at`, `code_after`, `discussion_code_id`, `review_id`) VALUES
    (7500, NOW(), NOW(), 'code_after', 7201, 7301);

-- liveReviewReservation(id 7600) 생성
INSERT INTO `hidiscuss`.`review_reservation` (`id`, `created_at`, `last_modified_at`, `isdone`, `review_start_date`, `reviewee_participated`, `reviewer_participated`, `discussion_id`, `review_id`, `reviewer_id`) VALUES
    (7600, NOW(), NOW(), 0, '2022-05-12 16:00:02.947000', 0, 0, 7100, NULL, 7000);
-- liveReviewReservation(id 7601) 생성
INSERT INTO `hidiscuss`.`review_reservation` (`id`, `created_at`, `last_modified_at`, `isdone`, `review_start_date`, `reviewee_participated`, `reviewer_participated`, `discussion_id`, `review_id`, `reviewer_id`) VALUES
    (7601, NOW(), NOW(), 0, '2022-05-12 15:00:02.947000', 0, 0, 7100, NULL, 7000);
INSERT INTO `hidiscuss`.`review_reservation` (`id`, `created_at`, `last_modified_at`, `isdone`, `review_start_date`, `reviewee_participated`, `reviewer_participated`, `discussion_id`, `review_id`, `reviewer_id`) VALUES
    (7602, NOW(), NOW(), 0, '2022-05-12 16:30:02.947000', 0, 0, 7100, NULL, 7000);

-- discussion_tag(id 7700 - 7704) 생성
INSERT INTO `hidiscuss`.`discussion_tag` (`id`, `created_at`, `last_modified_at`, `discussion_id`, `tag_id`) VALUES
    (7700, NOW(), NOW(), 7100, 1);
INSERT INTO `hidiscuss`.`discussion_tag` (`id`, `created_at`, `last_modified_at`, `discussion_id`, `tag_id`) VALUES
    (7701, NOW(), NOW(), 7100, 2);
INSERT INTO `hidiscuss`.`discussion_tag` (`id`, `created_at`, `last_modified_at`, `discussion_id`, `tag_id`) VALUES
    (7702, NOW(), NOW(), 7100, 3);
INSERT INTO `hidiscuss`.`discussion_tag` (`id`, `created_at`, `last_modified_at`, `discussion_id`, `tag_id`) VALUES
    (7703, NOW(), NOW(), 7101, 1);
INSERT INTO `hidiscuss`.`discussion_tag` (`id`, `created_at`, `last_modified_at`, `discussion_id`, `tag_id`) VALUES
    (7704, NOW(), NOW(), 7101, 3);
INSERT INTO `hidiscuss`.`discussion_tag` (`id`, `created_at`, `last_modified_at`, `discussion_id`, `tag_id`) VALUES
    (7705, NOW(), NOW(), 7102, 3);
INSERT INTO `hidiscuss`.`discussion_tag` (`id`, `created_at`, `last_modified_at`, `discussion_id`, `tag_id`) VALUES
    (7706, NOW(), NOW(), 7103, 1);
INSERT INTO `hidiscuss`.`discussion_tag` (`id`, `created_at`, `last_modified_at`, `discussion_id`, `tag_id`) VALUES
    (7707, NOW(), NOW(), 7104, 1);
INSERT INTO `hidiscuss`.`discussion_tag` (`id`, `created_at`, `last_modified_at`, `discussion_id`, `tag_id`) VALUES
    (7708, NOW(), NOW(), 7105, 1);
INSERT INTO `hidiscuss`.`discussion_tag` (`id`, `created_at`, `last_modified_at`, `discussion_id`, `tag_id`) VALUES
    (7709, NOW(), NOW(), 7106, 1);
INSERT INTO `hidiscuss`.`discussion_tag` (`id`, `created_at`, `last_modified_at`, `discussion_id`, `tag_id`) VALUES
    (7710, NOW(), NOW(), 7107, 1);
INSERT INTO `hidiscuss`.`discussion_tag` (`id`, `created_at`, `last_modified_at`, `discussion_id`, `tag_id`) VALUES
    (7711, NOW(), NOW(), 7108, 1);