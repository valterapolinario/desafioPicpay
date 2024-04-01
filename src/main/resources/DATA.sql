
delete from USUARIOS;
delete from TRANSFERENCIAS;

insert into USUARIOS (id,nome,email,documento,senha,tipo_usuario,carteira,data_cadastro,version)
values ('ffc2a683-8f90-4571-98cb-de7e1bc21e3a', 'Fulano Salvador','fulano@gmail.com','18524847727','123456','COMUM',500,'2024-03-25T20:28:07',1);

insert into USUARIOS (id,nome,email,documento,senha,tipo_usuario,carteira,data_cadastro,version)
values ('ffc2a683-8f90-4571-98cb-de7e1bc21e2a', 'Cicrano Ferreira','rauanmoreira259@gmail.com','14121957000109','123456','LOJISTA',500,'2024-03-25T20:28:07',1);