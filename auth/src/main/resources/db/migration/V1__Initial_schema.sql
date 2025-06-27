CREATE TYPE user_role AS ENUM ('user', 'admin');

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(100) NOT NULL,
    nome VARCHAR(100),
    validade DATE,
    compartilhados int default 0,
    role user_role NOT NULL
);

CREATE SEQUENCE users_seq START WITH 1 INCREMENT BY 1;
ALTER TABLE users ALTER COLUMN id SET DEFAULT nextval('users_seq');
