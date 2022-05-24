CREATE TABLE movies (
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(50) NOT NULL,
    about VARCHAR(250) NOT NULL,
    copyright VARCHAR(4) NOT NULL,
    duration TIME NOT NULL,
    count_like int,
    count_dislike int
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO movies (title, about, copyright, duration, count_like, count_dislike)
VALUES ('Back to the Future I', 'Marty McFly, a teenager from a small Californian town, is transported to the 1950s when the experiment of eccentric scientist Doc Brown.',
1985, '01:56:00', 0, 0);