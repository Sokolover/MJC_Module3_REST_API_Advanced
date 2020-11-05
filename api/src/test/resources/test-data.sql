INSERT INTO tag (name)
VALUES ('first'),
       ('second'),
       ('third');

INSERT INTO tag (id, name)
VALUES (13, 'films'),
       (14, 'fun');

INSERT INTO gift_certificate(name, description, price, createDate, createDateTimeZone, lastUpdateDate,
                             lastUpdateDateTimeZone, duration)
VALUES ('netflix', '5 any films', 6.7, '2020-10-23T09:37:39.000', '+03:00', '2020-12-23T09:37:39.000', '+03:00', 5),
       ('ivi', '2 any films', 3.5, '2019-10-23T09:37:39.000', '+03:00', '2019-12-23T09:37:39.000', '+03:00', 2),
       ('megogo', '3 any films', 7.4, '2018-10-23T09:37:39.000', '+03:00', '2018-12-23T09:37:39.000', '+03:00', 13);

INSERT INTO gift_certificate(id, name, description, price, createDate, createDateTimeZone, lastUpdateDate,
                             lastUpdateDateTimeZone, duration)
VALUES (15, 'netflix', '5 any films', '5.55', '2020-10-23T09:37:39.000', '+03:00', '2020-10-23T14:37:39.000', '+03:00',
        '10'),
       (16, 'netflix', '5 any films', '5.55', '2020-10-23T09:37:39.000', '+03:00', '2020-10-23T15:37:39.000', '+03:00',
        '10'),
       (17, 'ivi', '7 any films', '4.50', '2020-10-23T09:37:39.000', '+03:00', '2020-10-23T16:37:39.000', '+03:00',
        '10'),
       (18, 'ivi', '7 any films', '4.50', '2020-10-23T09:37:39.000', '+03:00', '2020-10-23T17:37:39.000', '+03:00',
        '10');

INSERT INTO tag_has_gift_certificate (tag_id, gift_certificate_id)
VALUES (13, 16),
       (13, 17),
       (13, 18),
       (13, 19),
       (14, 16),
       (14, 17),
       (14, 18),
       (14, 19)
