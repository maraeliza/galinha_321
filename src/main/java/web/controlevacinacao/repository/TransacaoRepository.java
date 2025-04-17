package web.controlevacinacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.controlevacinacao.model.Transacao;
import web.controlevacinacao.repository.queries.transacao.TransacaoQueries;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long>, TransacaoQueries {

    List<Transacao> findByUsuarioId(Long usuarioId);

    List<Transacao> findByMoedaId(int moedaId);

    List<Transacao> findByTipo(Boolean tipo);
    
}
