-- Table: tb_products
CREATE TABLE IF NOT EXISTS tb_products (
    id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    price NUMERIC(38, 2) NOT NULL,
    category SMALLINT NOT NULL,
    imageurl VARCHAR(255) NOT NULL,
    CONSTRAINT tb_products_pkey PRIMARY KEY (id)
);