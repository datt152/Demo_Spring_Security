
INSERT INTO customers (name, customer_since) VALUES
                                                 ('Nguyen Van A', '2022-01-15'),
                                                 ('Tran Thi B', '2022-05-20'),
                                                 ('Le Van C', '2023-03-10'),
                                                 ('Pham Thi D', '2024-02-01'),
                                                 ('Hoang Van E', '2024-06-12'),
                                                 ('Ngo Thi F', '2024-08-05'),
                                                 ('Vuu Minh G', '2025-01-20'),
                                                 ('Dang Thi H', '2025-03-28');


INSERT INTO product (name, price, in_stock) VALUES
                                                ('Laptop Dell XPS 13', 25000000.00, TRUE),
                                                ('Laptop ASUS Vivobook', 15000000.00, TRUE),
                                                ('MacBook Air M1', 30000000.00, FALSE),
                                                ('Chuot Logitech M330', 450000.00, TRUE),
                                                ('Tai nghe Sony WH-1000XM4', 5200000.00, TRUE),
                                                ('Ban phim co Keychron K6', 2200000.00, TRUE),
                                                ('Man hinh LG 24 inch', 3800000.00, TRUE),
                                                ('O cung SSD 1TB', 2800000.00, TRUE),
                                                ('Sach "Java Programming"', 150000.00, TRUE),
                                                ('Ao thun nam basic', 200000.00, TRUE),
                                                ('Quan jean nam', 350000.00, TRUE),
                                                ('Dien thoai Samsung Galaxy A54', 9000000.00, TRUE),
                                                ('Sac du phong 20000mAh', 450000.00, TRUE),
                                                ('Tui xach nu thoi trang', 650000.00, TRUE),
                                                ('Camera hanh trinh X200', 2990000.00, TRUE);


INSERT INTO comments (`text`, product_id) VALUES
                                              ('San pham chat luong tot', 1),
                                              ('Giao hang nhanh, dong goi can than', 1),
                                              ('Pin tot, dung ca ngay', 2),
                                              ('Man hinh sac net, hai long', 7),
                                              ('Gia hoi cao nhung dang tien', 3),
                                              ('Am thanh tuyet voi', 5),
                                              ('Ban phim go em', 6),
                                              ('SSD toc do nhanh, khoi dong cuc le', 8),
                                              ('Noi dung sach rat thuc te', 9),
                                              ('Vai mem, mac thoai mai', 10),
                                              ('Form quan dep, vua van', 11),
                                              ('Camera ghi hinh on trong dem', 15),
                                              ('Sac du phong nho gon, dung luong ok', 13),
                                              ('Tui xinh, phu hop di choi', 14),
                                              ('Chuot dung em, pin lau', 4),
                                              ('San pham on, dang tien', 12);


INSERT INTO orders (`date`, customer_id) VALUES
                                             ('2025-07-01 09:10:00', 1),
                                             ('2025-07-03 11:30:00', 2),
                                             ('2025-07-05 14:20:00', 3),
                                             ('2025-07-08 16:45:00', 4),
                                             ('2025-08-10 10:15:00', 5),
                                             ('2025-08-12 12:00:00', 6),
                                             ('2025-08-20 09:50:00', 7),
                                             ('2025-09-01 18:05:00', 8),
                                             ('2025-09-05 08:30:00', 1),
                                             ('2025-09-12 13:40:00', 2),
                                             ('2025-09-20 17:25:00', 3),
                                             ('2025-10-10 10:00:00', 5);


INSERT INTO order_lines (product_id, amount, purchase_price, order_id) VALUES
                                                                           (1, 1, 25000000.00, 1),
                                                                           (4, 2, 450000.00, 1),
                                                                           (9, 1, 150000.00, 1),

                                                                           (2, 1, 15000000.00, 2),
                                                                           (6, 1, 2200000.00, 2),

                                                                           (3, 1, 30000000.00, 3),

                                                                           (12, 1, 9000000.00, 4),
                                                                           (13, 2, 450000.00, 4),

                                                                           (10, 3, 200000.00, 5),
                                                                           (11, 1, 350000.00, 5),

                                                                           (8, 1, 2800000.00, 6),
                                                                           (5, 1, 5200000.00, 6),

                                                                           (1, 1, 25000000.00, 7),
                                                                           (15, 1, 2990000.00, 7),

                                                                           (14, 1, 650000.00, 8),

                                                                           (2, 2, 15000000.00, 9),
                                                                           (4, 1, 450000.00, 9),

                                                                           (9, 2, 150000.00, 10),
                                                                           (13, 1, 450000.00, 10),

                                                                           (7, 1, 3800000.00, 11),
                                                                           (6, 1, 2200000.00, 11),

                                                                           (1, 1, 25000000.00, 12),
                                                                           (8, 1, 2800000.00, 12),

                                                                           (3, 1, 30000000.00, 2),
                                                                           (5, 1, 5200000.00, 3),

                                                                           (11, 2, 350000.00, 8),
                                                                           (10, 1, 200000.00, 4);
