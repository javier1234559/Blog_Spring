drop database spring_blog ;
create database spring_blog;
use spring_blog;

INSERT INTO User (name, email, pass, phone, description, image, status, role)
VALUES    ('nhat', 'nhat1234559@gmail.com', '1', '01234559', '', '', 1, 1),
('John Doe', 'john.doe@example.com', 'password', '123456789', 'Web developer', '', 0, 1),
       ('Jane Smith', 'jane.smith@example.com', '123456', '987654321', '', '', 1, 2),
       ('Bob Johnson', 'bob.johnson@example.com', 'qwerty', '555555555', 'Marketing manager', '', 1, 2);
       
INSERT INTO Post (iduser, title, content, image, date, view)
VALUES (1, 'Example Post', 'This is an example post.', '', NOW(), 0);

SELECT * FROM POST ;
SELECT * FROM USER