CREATE TABLE coupon (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    code VARCHAR(100) NOT NULL,
    discount INTEGER NOT NULL,
    valid TIMESTAMP NOT NULL,
    events_id UUID, FOREIGN KEY  (events_id) REFERENCES events(id) ON DELETE CASCADE
)