package web.controlevacinacao.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.controlevacinacao.model.Transacao;
import web.controlevacinacao.repository.TransacaoRepository;

@Service
@Transactional
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;

    public TransacaoService(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    public void salvar(Transacao transacao) {
        transacaoRepository.save(transacao);
    }

    public void alterar(Transacao transacao) {
        transacaoRepository.save(transacao);
    }

    public void remover(Long id) {
        transacaoRepository.deleteById(id);
    }

}
