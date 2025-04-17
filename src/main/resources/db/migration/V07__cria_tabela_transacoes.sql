create table transacoes (
  id bigint primary key generated always as identity,
  data_transacao timestamp not null,
  moeda_id int not null,
  valor decimal(15, 2) not null,
  tipo boolean not null,
  usuario_id bigint not null,
  bot_id bigint not null
);
