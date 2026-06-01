-- PostgreSQL schema for Shared Code Notebook

CREATE TABLE person (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL DEFAULT 'user'
);

CREATE TABLE project (
    id SERIAL PRIMARY KEY,
    owner_id INT NOT NULL REFERENCES person(id),
    title VARCHAR(200) NOT NULL,
    description TEXT,
    completed BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE programming_language (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE project_language (
    project_id INT NOT NULL REFERENCES project(id),
    programming_language_id INT NOT NULL REFERENCES programming_language(id),
    PRIMARY KEY (project_id, programming_language_id)
);
