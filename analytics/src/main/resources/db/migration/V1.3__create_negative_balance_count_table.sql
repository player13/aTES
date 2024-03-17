CREATE TABLE negative_balance_count
(
    date  DATE   NOT NULL,
    count BIGINT NOT NULL,

    CONSTRAINT pkey_negative_balance_count_date
        PRIMARY KEY (date)
);
