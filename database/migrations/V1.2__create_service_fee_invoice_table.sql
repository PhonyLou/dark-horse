CREATE TABLE service_fee_invoice
(
    travel_contract_id bigserial PRIMARY KEY,
    amount numeric(20,10) not null,
    invoice_content varchar not null,
    invoice_number varchar(255) not null
)