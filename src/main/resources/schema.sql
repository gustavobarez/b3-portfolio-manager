CREATE TABLE
    users (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(255) NOT NULL,
        email VARCHAR(255) NOT NULL UNIQUE,
        brokerage_porcentage DECIMAL(10, 5) NOT NULL
    );

CREATE TABLE
    assets (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        ticker VARCHAR(10) NOT NULL UNIQUE,
        name VARCHAR(255) NOT NULL
    );

CREATE TABLE
    operations (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        user_id BIGINT NOT NULL,
        asset_id BIGINT NOT NULL,
        quantity INT NOT NULL,
        unit_price DECIMAL(19, 4) NOT NULL,
        operation_type VARCHAR(6) NOT NULL,
        brokerage_fee DECIMAL(19, 4) NOT NULL,
        timestamp TIMESTAMP NOT NULL,
        FOREIGN KEY (user_id) REFERENCES users (id),
        FOREIGN KEY (asset_id) REFERENCES assets (id)
    );

CREATE TABLE
    quotations (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        asset_id BIGINT NOT NULL,
        unit_price DECIMAL(19, 4) NOT NULL,
        timestamp TIMESTAMP NOT NULL,
        FOREIGN KEY (asset_id) REFERENCES assets (id)
    );

CREATE TABLE
    positions (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        user_id BIGINT NOT NULL,
        asset_id BIGINT NOT NULL,
        quantity INT NOT NULL,
        average_price DECIMAL(19, 4) NOT NULL,
        p_and_l DECIMAL(19, 4) NOT NULL,
        UNIQUE (user_id, asset_id),
        FOREIGN KEY (user_id) REFERENCES users (id),
        FOREIGN KEY (asset_id) REFERENCES assets (id)
    );

