ALTER TABLE customers
ADD CONSTRAINT chk_customer_phone
CHECK (phone ~ '^01(0|1|2|5)[0-9]{8}$');