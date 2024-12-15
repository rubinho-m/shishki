INSERT INTO shishki_guests (name, surname, phone, email)
VALUES ('Михаил', 'Рубин', '+7-123-456-78-90', 'test@vk.ru'),
       ('Таисия', 'Пчелкина', '+7-987-654-32-10', 'test@vk.com'),
       ('Тамара', 'Петрова', '+7-952-812-10-20', 'staff@vk.com');

INSERT INTO shishki_accounts (login, password, guest_id, role)
VALUES ('admin', '$2a$10$KgOvtVlh.KZkxKbLsPmDJOPPvCxguGgstDSMHqxFVYYxcygeflnvC', 1, 'ADMIN'),
       ('staff', 'staff', 3, 'STAFF'),
       ('testUser', 'testUser', 2, 'USER');

INSERT INTO shishki_glampings (owner_id, address, description, photo_name)
VALUES (1, 'Лесная улица, 10', 'Комфортабельные домики на природе.', 'test.jpg');

INSERT INTO shishki_house_types (type, number_of_persons)
VALUES ('A-frame', 6),
       ('O-frame', 4);

INSERT INTO shishki_house_statuses (status)
VALUES ('ALLOWED'),
       ('FORBIDDEN');

INSERT INTO shishki_houses (glamping_id, house_type_id, status_id, cost, photo_name)
VALUES (1, 1, 1, 8000.00, 'test.jpg'),
       (1, 2, 1, 5000.00, 'test.jpg');

INSERT INTO shishki_bookings (user_id, house_id, unique_key, date_start, date_end)
VALUES (1, 2, 2828, '2023-09-01', '2023-09-05');

INSERT INTO shishki_booking_guests (guest_id, booking_id)
VALUES (1, 1),
       (2, 1);

INSERT INTO shishki_shop (name, description, price)
VALUES ('Жидкость для розжига', 'Жидкость для розжига мангала 1,5л', 500.00),
       ('Уголь', 'Уголь древесный березовый', 800.00);

INSERT INTO shishki_services (name, description, cost)
VALUES ('Аренда санок', 'Аренда санок одноместных грузоподъемностью до 70 кг', 1000.00),
       ('Трансфер', 'Трансфер из аэропорта', 3000.00),
       ('Аренда бани', 'Аренда бани', 5000.00);

INSERT INTO shishki_booking_services (service_id, booking_id)
VALUES (3, 1),
       (2, 1);

INSERT INTO shishki_booking_shop (shop_item_id, booking_id)
VALUES (2, 1);

INSERT INTO shishki_reviews (account_id, glamping_id, content, rating)
VALUES (1, 1, 'Лучшее место для отдыха просто великолепно организаторы молодцы!', 5);
