package web.controlevacinacao.repository.queries.transacao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import web.controlevacinacao.filter.TransacaoFilter;
import web.controlevacinacao.model.Transacao;

import java.util.List;

public interface TransacaoQueries {

	// Pesquisa simples sem paginação
	List<Transacao> pesquisar(TransacaoFilter filtro);

	// Pesquisa com paginação
	Page<Transacao> pesquisar(TransacaoFilter filtro, Pageable pageable);
	
	Page<Transacao> findByFiltro(TransacaoFilter filtro, Pageable pageable);
}
