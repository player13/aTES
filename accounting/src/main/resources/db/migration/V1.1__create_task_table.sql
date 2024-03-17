CREATE TABLE task
(
    id                BIGSERIAL    NOT NULL,
    public_id         UUID         NOT NULL,
    summary           VARCHAR(128) NOT NULL,
    description       TEXT         NOT NULL,
    assignment_fee    SMALLINT     NOT NULL,
    completion_reward SMALLINT     NOT NULL,

    CONSTRAINT pkey_task_id
        PRIMARY KEY (id),
    CONSTRAINT unique_task_public_id
        UNIQUE (public_id)
);
