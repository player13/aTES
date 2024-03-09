CREATE TABLE user_account
(
    user_id UUID   NOT NULL,
    balance BIGINT NOT NULL,

    CONSTRAINT pkey_user_account_user_id
        PRIMARY KEY (user_id)
);
