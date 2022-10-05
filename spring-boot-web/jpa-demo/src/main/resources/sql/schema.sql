CREATE TABLE "tenant_link"
(
    "id"               integer,
    "create_time"      datetime,
    "last_update_time" datetime,
    "company_id"       varchar(64) NOT NULL,
    "tenant_id"        bigint      NOT NULL,
    PRIMARY KEY ("id"),
    CONSTRAINT "c_company_id" UNIQUE ("company_id"),
    CONSTRAINT "c_tenant_id" UNIQUE ("tenant_id")
);

CREATE TABLE "user_link"
(
    "id"               integer,
    "create_time"      datetime,
    "last_update_time" datetime,
    "old_user_id"      varchar(64) NOT NULL,
    "username"         varchar(30) NOT NULL,
    PRIMARY KEY ("id"),
    CONSTRAINT "c_username" UNIQUE ("username"),
    CONSTRAINT "c_old_user_id" UNIQUE ("old_user_id")
);