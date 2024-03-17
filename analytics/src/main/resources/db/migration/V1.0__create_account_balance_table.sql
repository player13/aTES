CREATE TABLE account_balance
(
    user_id UUID   NOT NULL,
    balance BIGINT NOT NULL,

    CONSTRAINT pkey_account_balance_user_id
        PRIMARY KEY (user_id)
);
