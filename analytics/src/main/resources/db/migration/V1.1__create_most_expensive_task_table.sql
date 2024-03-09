CREATE TABLE most_expensive_task
(
    date   DATE   NOT NULL,
    reward BIGINT NOT NULL,

    CONSTRAINT pkey_most_expensive_task_date
        PRIMARY KEY (date)
);
