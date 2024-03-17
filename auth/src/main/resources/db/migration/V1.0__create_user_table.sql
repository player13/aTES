CREATE TABLE "user"
(
    id        BIGSERIAL   NOT NULL,
    public_id UUID        NOT NULL,
    login     VARCHAR(64) NOT NULL,
    password  VARCHAR(64) NOT NULL,
    role      VARCHAR(16) NOT NULL,

    CONSTRAINT pkey_user_id
        PRIMARY KEY (id),
    CONSTRAINT unique_user_public_id
        UNIQUE (public_id),
    CONSTRAINT unique_user_login
        UNIQUE (login)
);
