CREATE TABLE address (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    city VARCHAR(100) NOT NULL,
    uf VARCHAR(2) NOT NULL,
    events_ids UUID, FOREIGN KEY (events_ids) REFERENCES events(id) ON DELETE CASCADE
);