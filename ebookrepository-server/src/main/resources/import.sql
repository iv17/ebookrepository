insert into category(id, name) values (1, 'silver')
insert into category(id, name) values (2, 'gold')
insert into category(id, name) values (3, 'platinum')

insert into language(id, name) values (1, 'Srpski jezik')
insert into language(id, name) values (2, 'Engleski jezik')
insert into language(id, name) values (3, 'Ruski jezik')

insert into user(id, first_name, last_name, email, password, type, category_id) values (1, 'Ivana', 'Savin', 'ivana.unitedforce@gmail.com', '$2a$10$FwPCMhIcqiVq9PxlqgKSiOzv8aQmRHY3RNrWyYkjHYaz6E5k5sc7i', 'PRETPLATNIK', 1)
insert into user(id, first_name, last_name, email, password, type, category_id) values (2, 'admin', 'admin', 'admin@gmail.com', '$2a$10$FwPCMhIcqiVq9PxlqgKSiOzv8aQmRHY3RNrWyYkjHYaz6E5k5sc7i', 'ADMIN', null)