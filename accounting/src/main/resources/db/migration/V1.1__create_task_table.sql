CREATE TABLE task
(
    id                UUID         NOT NULL,
    summary           VARCHAR(128) NOT NULL,
    description       TEXT         NOT NULL,
    assignment_fee    SMALLINT     NOT NULL,
    completion_reward SMALLINT     NOT NULL,

    CONSTRAINT pkey_task_id
        PRIMARY KEY (id)
);
