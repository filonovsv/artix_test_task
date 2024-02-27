DROP TABLE IF EXISTS bonus_cards;
DROP TABLE IF EXISTS operations;

CREATE TABLE bonus_cards
(
    id     BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    amount BIGINT NOT NULL DEFAULT 0
);

CREATE TABLE operations
(
    id            BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    bonus_card_id BIGINT,
    amount_change BIGINT
);
