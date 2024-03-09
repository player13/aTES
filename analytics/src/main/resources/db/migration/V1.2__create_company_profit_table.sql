CREATE TABLE company_profit
(
    date   DATE   NOT NULL,
    profit BIGINT NOT NULL,

    CONSTRAINT pkey_company_profit_date
        PRIMARY KEY (date)
);
