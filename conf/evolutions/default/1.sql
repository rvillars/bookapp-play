# Author schema
 
# --- !Ups
 
CREATE TABLE "Author" (
    "id" bigint(20) NOT NULL AUTO_INCREMENT,
    "firstname" varchar(255) NOT NULL,
    "lastname" varchar(255) NOT NULL,
    PRIMARY KEY ("id")
);

INSERT INTO "Author"("id", "firstname", "lastname") values(0,'Roger','Villars');
INSERT INTO "Author"("id", "firstname", "lastname") values(1,'Homer','Simpson');

CREATE TABLE "Book" (
    "id" bigint(20) NOT NULL AUTO_INCREMENT,
    "title" varchar(255) NOT NULL,
    "releaseDate" date NOT NULL,
    "authorId" bigint(20) NOT NULL,
    PRIMARY KEY ("id")
);

INSERT INTO "Book"("id", "title", "releaseDate", "authorId") values(0,'Herr der Ringe - Die Gefaehrten','2013-04-21',0);
INSERT INTO "Book"("id", "title", "releaseDate", "authorId") values(1,'Herr der Ringe - Die zwei Tuerme','2013-04-22',0);
 
# --- !Downs
 
DROP TABLE "Author";
DROP TABLE "Book";