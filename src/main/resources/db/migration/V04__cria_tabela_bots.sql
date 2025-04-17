create table bots (
  id bigint primary key generated always as identity,
  usuario_id bigint references usuarios (id),
  nome text not null,
  descricao text,
  data_criacao timestamp default now() not null
);