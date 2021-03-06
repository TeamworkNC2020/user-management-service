CREATE TABLE friend_request_info
(
    friend_request_id bigint GENERATED BY DEFAULT AS IDENTITY
        CONSTRAINT friend_request_info_pkey PRIMARY KEY,
    user_id           bigint NOT NULL REFERENCES user_info,
    recipient_id      bigint NOT NULL REFERENCES user_info
);

CREATE TABLE user_friend_requests
(
    user_id           bigint NOT NULL REFERENCES user_info,
    friend_request_id bigint NOT NULL REFERENCES friend_request_info,
    CONSTRAINT user_friend_requests_pkey PRIMARY KEY (user_id, friend_request_id)
);