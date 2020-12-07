CREATE TABLE IF NOT EXISTS event(
id SERIAL PRIMARY KEY,
user_id INT NOT NULL,
ts TEXT,
latlong TEXT,
noun VARCHAR(10) NOT NULL,
verb VARCHAR(10) NOT NULL,
time_spent INT,
property_id INT,
timestamp TIMESTAMP
);
CREATE TABLE IF NOT EXISTS property(
id SERIAL PRIMARY KEY,
bank TEXT,
merchant_id INT,
value REAL,
mode TEXT,
text TEXT,
timestamp TIMESTAMP
);
CREATE TABLE IF NOT EXISTS notifications(
id SERIAL PRIMARY KEY,
notification TEXT,
event_id INT,
rule_tag varchar(10),
is_notified BOOLEAN,
timestamp TIMESTAMP
);
CREATE TABLE IF NOT EXISTS job_check(
id SERIAL PRIMARY KEY,
user_id INT,
event_id INT,
tag TEXT,
timestamp TIMESTAMP
);
