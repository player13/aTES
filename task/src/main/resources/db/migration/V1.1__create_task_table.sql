CREATE TABLE task
(
    id          UUID         NOT NULL,
    summary     VARCHAR(128) NOT NULL,
    description TEXT         NOT NULL,
    status      VARCHAR(16)  NOT NULL,
    executor_id UUID         NOT NULL,

    CONSTRAINT pkey_task_id
        PRIMARY KEY (id),
    CONSTRAINT fkey_task_executor_id_user_id
        FOREIGN KEY (executor_id)
            REFERENCES "user" (id)
);
