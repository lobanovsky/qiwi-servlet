CREATE SCHEMA qiwi;

CREATE TABLE qiwi.agent
(
  id       INT PRIMARY KEY NOT NULL,
  phone    VARCHAR(30)     NOT NULL,
  password VARCHAR(255)    NOT NULL
)
  ENGINE = innodb
  DEFAULT CHARSET = utf8;

ALTER TABLE qiwi.agent ADD CONSTRAINT unique_id UNIQUE (id);
ALTER TABLE qiwi.agent ADD CONSTRAINT unique_phone UNIQUE (phone);


CREATE TABLE qiwi.account
(
  id       INT PRIMARY KEY NOT NULL,
  agent_id INT             NOT NULL,
  amount   DECIMAL(15, 2)  NOT NULL
)
  ENGINE = innodb
  DEFAULT CHARSET = utf8;

ALTER TABLE qiwi.account ADD CONSTRAINT unique_id UNIQUE (id);

ALTER TABLE qiwi.account
ADD CONSTRAINT account_agent_fk
FOREIGN KEY (agent_id)
REFERENCES agent (id)
  ON DELETE CASCADE
  ON UPDATE CASCADE;
