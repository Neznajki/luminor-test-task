UPDATE currency
SET coefficient = 1
WHERE iso_code = 'EUR';
UPDATE currency
SET coefficient = 1.12
WHERE iso_code = 'USD';
