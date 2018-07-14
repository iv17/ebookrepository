insert into category(id, name) values (1, 'silver')
insert into category(id, name) values (2, 'gold')
insert into category(id, name) values (3, 'platinum')

insert into language(id, name) values (1, 'Srpski jezik')
insert into language(id, name) values (2, 'Engleski jezik')
insert into language(id, name) values (3, 'Ruski jezik')

insert into user(id, first_name, last_name, email, password, type, category_id) values (1, 'Ivana', 'Savin', 'ivana.unitedforce@gmail.com', '$2a$04$ebILB/F5i1WKXVL3h9VDv.RG5kWSnnMU20ShxUc6Yx2oD4N/HWOce', 'PRETPLATNIK', 1)

insert into ebook(id, title, author, keywords, publication_year, filename, MIME, category_id, language_id, cataloguer_id) values (1, 'Ana Karenjina', 'Lav Nikolajevic Tolstoj', 'Ana Karenjina, Grof Vronski, Ljevin', 1877, 'Ana Karenjina', 'pdf', 1, 3, 1)
insert into ebook(id, title, author, keywords, publication_year, filename, MIME, category_id, language_id, cataloguer_id) values (2, 'Sto godina samoce', 'Gabrijel Garsija Markes', 'Sto godina samoce', 1967, 'Sto godina samoce', 'pdf', 1, 1, 1)
insert into ebook(id, title, author, keywords, publication_year, filename, MIME, category_id, language_id, cataloguer_id) values (3, 'Lovac u zitu', 'Dzerom Dejvid Selindzer', 'Lovac u zitu', 1951, 'Lovac u zitu', 'pdf', 1, 2, 1)
