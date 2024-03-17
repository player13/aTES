CREATE TABLE "transaction"
(
    id              BIGSERIAL                NOT NULL,
    user_id         UUID                     NOT NULL,
    amount          BIGINT                   NOT NULL,
    reason          TEXT                     NOT NULL,
    timestamp       TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT pkey_transaction_id
        PRIMARY KEY (id),
    CONSTRAINT fkey_transaction_user_id_user_account_user_id
        FOREIGN KEY (user_id)
            REFERENCES user_account (user_id)
);
