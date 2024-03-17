CREATE TABLE user_account
(
    id             BIGSERIAL NOT NULL,
    user_public_id UUID      NOT NULL,
    balance        BIGINT    NOT NULL,

    CONSTRAINT pkey_user_account_id
        PRIMARY KEY (id),
    CONSTRAINT unique_user_account_user_public_id
        UNIQUE (user_public_id)
);
