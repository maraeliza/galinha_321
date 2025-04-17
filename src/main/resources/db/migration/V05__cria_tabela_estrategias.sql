create table estrategias (
  id bigint primary key generated always as identity,
  nome text not null,
  descricao text,
  parametros jsonb
);