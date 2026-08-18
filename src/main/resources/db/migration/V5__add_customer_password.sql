ALTER TABLE customers
ADD COLUMN password_hash VARCHAR(255);

UPDATE customers
SET password_hash = 'TEMP_PASSWORD_HASH'
WHERE password_hash IS NULL;

ALTER TABLE customers
ALTER COLUMN password_hash SET NOT NULL;