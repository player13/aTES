CREATE TABLE "transaction"
(
    id              BIGSERIAL                NOT NULL,
    user_account_id BIGINT                   NOT NULL,
    task_id         BIGINT                   NOT NULL,
    amount          BIGINT                   NOT NULL,
    reason          TEXT                     NOT NULL,
    timestamp       TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT pkey_transaction_id
        PRIMARY KEY (id),
    CONSTRAINT fkey_transaction_user_account_id_user_account_id
        FOREIGN KEY (user_account_id)
            REFERENCES user_account (id),
    CONSTRAINT fkey_transaction_task_id_task_id
        FOREIGN KEY (task_id)
            REFERENCES task (id)
);
