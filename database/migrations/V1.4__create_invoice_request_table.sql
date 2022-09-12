CREATE TABLE service_fee_invoice_request
(
    travel_contract_id bigserial PRIMARY KEY,
    status varchar(255) NOT NULL,
    created_at timestamp,
    expired_at timestamp,
    last_update timestamp
)