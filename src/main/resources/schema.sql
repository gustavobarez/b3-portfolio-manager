CREATE DATABASE itau_tech_challenge;

USE itau_tech_challenge;

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
        code VARCHAR(10) NOT NULL UNIQUE,
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
        date_time TIMESTAMP NOT NULL,
        FOREIGN KEY (user_id) REFERENCES users (id),
        FOREIGN KEY (asset_id) REFERENCES assets (id)
    );

CREATE TABLE quotations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    asset_id BIGINT NOT NULL,
    unit_price DECIMAL(19, 4) NOT NULL,
    date_time TIMESTAMP NOT NULL,
    source VARCHAR(50),
    
    FOREIGN KEY (asset_id) REFERENCES assets (id),
    
    UNIQUE KEY uk_quotation_idempotent (asset_id, date_time, source)
);

CREATE TABLE
    positions (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        user_id BIGINT NOT NULL,
        asset_id BIGINT NOT NULL,
        quantity INT NOT NULL,
        average_price DECIMAL(19, 4) NOT NULL,
        profit_and_loss DECIMAL(19, 4) NOT NULL,
        UNIQUE (user_id, asset_id),
        FOREIGN KEY (user_id) REFERENCES users (id),
        FOREIGN KEY (asset_id) REFERENCES assets (id)
    );

-- index necessario para fazer a consulta otimizada

CREATE INDEX idx_operations_user_asset_time ON operations (user_id, asset_id, date_time);

-- consulta todas as operações de um usuário em determinado ativo nos últimos 30 dias.

SELECT
    *
FROM
    operations
WHERE
    user_id = 1
    AND asset_id = 1
    AND date_time >= DATE_SUB (NOW (), INTERVAL 30 DAY)
ORDER BY
    date_time DESC;

-- estrutura para atualizar posições com base nas cotações

DELIMITER $$
CREATE TRIGGER tg_update_pnl_position
AFTER INSERT ON quotations
FOR EACH ROW
BEGIN
    UPDATE
        positions
    SET
        profit_and_loss = (NEW.unit_price - average_price) * quantity
    WHERE
        asset_id = NEW.asset_id;
END$$

DELIMITER ;

-- create mock data to test via terminal and kafka consume

INSERT INTO users (id, name, email, brokerage_porcentage) VALUES
(1, 'Alice Johnson', 'alice.j@example.com', 0.00500),
(2, 'Bob Williams', 'bob.w@example.com', 0.01000);

INSERT INTO assets (id, code, name) VALUES
(101, 'ITUB4', 'ITAU UNIBANCO PN'),
(102, 'PETR4', 'PETROBRAS PN'),
(103, 'VALE3', 'VALE ON'),
(104, 'MGLU3', 'MAGAZINE LUIZA ON');

INSERT INTO operations (user_id, asset_id, quantity, unit_price, operation_type, brokerage_fee, date_time) VALUES
(1, 101, 100, 28.50, 'BUY', 4.90, '2025-08-15 10:30:00'),
(1, 101, 50, 29.10, 'BUY', 4.90, '2025-08-20 14:00:00'),
(1, 101, 75, 30.00, 'SELL', 4.90, '2025-09-01 11:00:00');

INSERT INTO operations (user_id, asset_id, quantity, unit_price, operation_type, brokerage_fee, date_time) VALUES
(1, 102, 200, 35.20, 'BUY', 10.00, '2025-08-18 16:15:00');

INSERT INTO operations (user_id, asset_id, quantity, unit_price, operation_type, brokerage_fee, date_time) VALUES
(2, 104, 1000, 2.50, 'BUY', 7.50, '2025-09-02 12:00:00');

INSERT INTO positions (user_id, asset_id, quantity, average_price, profit_and_loss) VALUES
(1, 101, 75, 28.70, 97.50),
(1, 102, 200, 35.20, -40.00);

INSERT INTO positions (user_id, asset_id, quantity, average_price, profit_and_loss) VALUES
(2, 104, 1000, 2.50, 150.00);

INSERT INTO quotations (asset_id, unit_price, date_time, source) VALUES
(101, 32.50, '2025-09-08 15:30:00', 'bloomberg'),
(101, 32.75, '2025-09-08 15:35:00', 'reuters'),
(102, 38.20, '2025-09-08 15:30:00', 'bloomberg'),
(103, 85.90, '2025-09-08 15:30:00', 'yahoo');