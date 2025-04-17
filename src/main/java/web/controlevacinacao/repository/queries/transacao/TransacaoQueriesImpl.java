package web.controlevacinacao.repository.queries.transacao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import web.controlevacinacao.filter.TransacaoFilter;
import web.controlevacinacao.model.Transacao;
import web.controlevacinacao.repository.pagination.PaginacaoUtil;

import java.util.List;

public class TransacaoQueriesImpl implements TransacaoQueries {

  private static final Logger logger = LoggerFactory.getLogger(TransacaoQueriesImpl.class);

  @PersistenceContext
  private EntityManager em;

  @Override
  public List<Transacao> pesquisar(TransacaoFilter filtro) {
    StringBuilder queryTransacoes = new StringBuilder("select t from Transacao t");
    StringBuilder condicoes = new StringBuilder();

    criarCondicoes(filtro, condicoes);

    queryTransacoes.append(condicoes);
    TypedQuery<Transacao> typedQuery = em.createQuery(queryTransacoes.toString(), Transacao.class);
    typedQuery.setHint("hibernate.query.passDistinctThrough", false);

    preencherParametros(filtro, typedQuery);

    return typedQuery.getResultList();
  }

  @Override
  public Page<Transacao> pesquisar(TransacaoFilter filtro, Pageable pageable) {
    StringBuilder queryTransacoes = new StringBuilder("select t from Transacao t");
    StringBuilder condicoes = new StringBuilder();

    criarCondicoes(filtro, condicoes);
    if (condicoes.toString().isEmpty()) {
      condicoes.append(" where ");
    } else {
      condicoes.append(" and ");
    }
    condicoes.append(" t.dataTransacao >= :dataInicio AND t.dataTransacao <= :dataFim ");

    queryTransacoes.append(condicoes);

    PaginacaoUtil.prepararOrdemJPQL(queryTransacoes, pageable);
    TypedQuery<Transacao> typedQuery = em.createQuery(queryTransacoes.toString(), Transacao.class);
    PaginacaoUtil.prepararIntervalo(typedQuery, pageable);
    typedQuery.setHint("hibernate.query.passDistinctThrough", false);

    preencherParametros(filtro, typedQuery);

    List<Transacao> transacoes = typedQuery.getResultList();

    logger.info("Calculando o total de registros que o filtro retornará.");
    StringBuilder queryTotal = new StringBuilder("select count(t) from Transacao t");
    queryTotal.append(condicoes);
    TypedQuery<Long> typedQueryTotal = em.createQuery(queryTotal.toString(), Long.class);

    preencherParametros(filtro, typedQueryTotal);

    long totalTransacoes = typedQueryTotal.getSingleResult();
    logger.info("O filtro retornará {} registros.", totalTransacoes);

    return new PageImpl<>(transacoes, pageable, totalTransacoes);
  }

  private void criarCondicoes(TransacaoFilter filtro, StringBuilder condicoes) {
    boolean condicao = false;

    if (filtro.getUsuarioId() != null) {
      fazerLigacaoCondicoes(condicoes, condicao);
      condicoes.append("t.usuario.id = :usuarioId");
      condicao = true;
    }
    if (filtro.getMoedaId() != null) {
      fazerLigacaoCondicoes(condicoes, condicao);
      condicoes.append("t.moeda.id = :moedaId");
      condicao = true;
    }
    if (filtro.getTipo() != null) {
      fazerLigacaoCondicoes(condicoes, condicao);
      condicoes.append("t.tipo = :tipo");
      condicao = true;
    }
    if (filtro.getDataInicio() != null) {
      fazerLigacaoCondicoes(condicoes, condicao);
      condicoes.append("t.dataTransacao >= :dataInicio");
      condicao = true;
    }
    if (filtro.getDataFim() != null) {
      fazerLigacaoCondicoes(condicoes, condicao);
      condicoes.append("t.dataTransacao <= :dataFim");
      condicao = true;
    }
  }

  private void preencherParametros(TransacaoFilter filtro, TypedQuery<?> typedQuery) {
    if (filtro.getUsuarioId() != null) {
      typedQuery.setParameter("usuarioId", filtro.getUsuarioId());
    }
    if (filtro.getMoedaId() != null) {
      typedQuery.setParameter("moedaId", filtro.getMoedaId());
    }
    if (filtro.getTipo() != null) {
      typedQuery.setParameter("tipo", filtro.getTipo());
    }
    if (filtro.getDataInicio() != null) {
      typedQuery.setParameter("dataInicio", filtro.getDataInicio());
    }
    if (filtro.getDataFim() != null) {
      typedQuery.setParameter("dataFim", filtro.getDataFim());
    }
  }

  private void fazerLigacaoCondicoes(StringBuilder condicoes, boolean condicao) {
    if (!condicao) {
      condicoes.append(" where ");
    } else {
      condicoes.append(" and ");
    }
  }
  
  @Override
  public Page<Transacao> findByFiltro(TransacaoFilter filtro, Pageable pageable) {
    StringBuilder query = new StringBuilder("SELECT t FROM Transacao t WHERE 1=1 ");

    // Criar condições de filtro dinamicamente
    if (filtro.getUsuarioId() != null) {
      query.append("AND t.usuario.id = :usuarioId ");
    }

    if (filtro.getMoedaId() != null) {
      query.append("AND t.moeda.id = :moedaId ");
    }

    if (filtro.getTipo() != null) {
      query.append("AND t.tipo = :tipo ");
    }

    if (filtro.getDataInicio() != null) {
      query.append("AND t.dataTransacao >= :dataInicio ");
    }

    if (filtro.getDataFim() != null) {
      query.append("AND t.dataTransacao <= :dataFim ");
    }

    TypedQuery<Transacao> typedQuery = em.createQuery(query.toString(), Transacao.class);

    // Preencher parâmetros da query
    if (filtro.getUsuarioId() != null) {
      typedQuery.setParameter("usuarioId", filtro.getUsuarioId());
    }

    if (filtro.getMoedaId() != null) {
      typedQuery.setParameter("moedaId", filtro.getMoedaId());
    }

    if (filtro.getTipo() != null) {
      typedQuery.setParameter("tipo", filtro.getTipo());
    }

    if (filtro.getDataInicio() != null) {
      typedQuery.setParameter("dataInicio", filtro.getDataInicio());
    }

    if (filtro.getDataFim() != null) {
      typedQuery.setParameter("dataFim", filtro.getDataFim());
    }

    // Paginação
    PaginacaoUtil.prepararIntervalo(typedQuery, pageable);

    List<Transacao> transacoes = typedQuery.getResultList();

    // Contar o total de registros para a paginação
    String countQuery = "SELECT COUNT(t) FROM Transacao t WHERE 1=1 ";

    if (filtro.getUsuarioId() != null) {
      countQuery += "AND t.usuario.id = :usuarioId ";
    }

    if (filtro.getMoedaId() != null) {
      countQuery += "AND t.moeda.id = :moedaId ";
    }

    if (filtro.getTipo() != null) {
      countQuery += "AND t.tipo = :tipo ";
    }

    if (filtro.getDataInicio() != null) {
      countQuery += "AND t.dataTransacao >= :dataInicio ";
    }

    if (filtro.getDataFim() != null) {
      countQuery += "AND t.dataTransacao <= :dataFim ";
    }

    TypedQuery<Long> countTypedQuery = em.createQuery(countQuery, Long.class);

    // Preencher parâmetros da contagem
    if (filtro.getUsuarioId() != null) {
      countTypedQuery.setParameter("usuarioId", filtro.getUsuarioId());
    }

    if (filtro.getMoedaId() != null) {
      countTypedQuery.setParameter("moedaId", filtro.getMoedaId());
    }

    if (filtro.getTipo() != null) {
      countTypedQuery.setParameter("tipo", filtro.getTipo());
    }

    if (filtro.getDataInicio() != null) {
      countTypedQuery.setParameter("dataInicio", filtro.getDataInicio());
    }

    if (filtro.getDataFim() != null) {
      countTypedQuery.setParameter("dataFim", filtro.getDataFim());
    }

    long totalTransacoes = countTypedQuery.getSingleResult();

    return new PageImpl<>(transacoes, pageable, totalTransacoes);
  }
}
