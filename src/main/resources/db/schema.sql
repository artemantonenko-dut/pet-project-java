CREATE TABLE IF NOT EXISTS schedule_items (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    planned_date DATE NOT NULL,
    start_time TIME NOT NULL,
    duration_minutes INTEGER NOT NULL CHECK (duration_minutes > 0),
    category VARCHAR(20) NOT NULL,
    note VARCHAR(250)
);