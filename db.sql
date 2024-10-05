CREATE TABLE tickets (
    id UUID PRIMARY KEY,
    vatin VARCHAR(11) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(vatin, first_name, last_name)
);
