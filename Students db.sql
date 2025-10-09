create database eag_student_info_system;

use eag_student_info_system;

create table ogrenciler(
	id INT PRIMARY KEY AUTO_INCREMENT,
    ogrenci_no INT UNIQUE NOT NULL,
    ad VARCHAR(50) NOT NULL,
    soyad VARCHAR(50) NOT NULL,
    bolum VARCHAR(100) NOT NULL,
    ortalama DOUBLE DEFAULT 0.0,
    kayit_tarihi TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE dersler (
    id INT PRIMARY KEY AUTO_INCREMENT,
    ogrenci_no INT NOT NULL,
    ders_adi VARCHAR(100) NOT NULL,
    FOREIGN KEY (ogrenci_no) REFERENCES ogrenciler(ogrenci_no)
);