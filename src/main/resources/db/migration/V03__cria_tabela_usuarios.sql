create table usuarios (
  id bigint primary key generated always as identity,
  nome text not null,
  email text unique not null,
  senha text not null,
  data_criacao timestamp default now() not null
);