package web.controlevacinacao.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import web.controlevacinacao.filter.TransacaoFilter;
import web.controlevacinacao.model.Transacao;
import web.controlevacinacao.pagination.PageWrapper;
import web.controlevacinacao.repository.TransacaoRepository;
import web.controlevacinacao.service.TransacaoService;

@Controller
@RequestMapping("/transacoes")
public class TransacaoController {

  private static final Logger logger = LoggerFactory.getLogger(TransacaoController.class);

  private final TransacaoRepository transacaoRepository;
  private final TransacaoService transacaoService;

  public TransacaoController(TransacaoRepository transacaoRepository, TransacaoService transacaoService) {
    this.transacaoRepository = transacaoRepository;
    this.transacaoService = transacaoService;
  }

  @GetMapping({ "", "/" })
  public String mostrarTodasTransacoes(
      @RequestParam(value = "filtro", required = false) TransacaoFilter filtro,
      Model model,
      @PageableDefault(size = 7) @SortDefault(sort = "dataTransacao", direction = Sort.Direction.DESC) Pageable pageable,
      HttpServletRequest request) {

    Page<Transacao> pagina = transacaoRepository.pesquisar(filtro, pageable);
    logger.trace("Transações pesquisadas: {}", pagina.getContent());

    PageWrapper<Transacao> paginaWrapper = new PageWrapper<>(pagina, request);
    model.addAttribute("pagina", paginaWrapper);

    return "transacoes/listar";
  }

  @GetMapping("/cadastrar")
  public String abrirCadastrar(Transacao transacao) {
    return "transacoes/cadastrar";
  }

  @PostMapping("/cadastrar")
  public String cadastrar(@ModelAttribute Transacao transacao, RedirectAttributes atributos) {
    transacaoService.salvar(transacao);
    atributos.addAttribute("mensagem", "Transação cadastrada com sucesso!");
    return "redirect:/mensagem";
  }

  @GetMapping("/alterar/{id}")
  public String mostrarAlterar(@PathVariable("id") Long id, Model model) {
    Transacao transacao = transacaoRepository.findById(id).orElse(null);

    if (transacao != null) {
      model.addAttribute("transacao", transacao);
      return "transacoes/alterar";
    } else {
      model.addAttribute("mensagem", "Transação não encontrada");
      return "mensagem";
    }
  }

  @PostMapping("/alterar")
  public String alterar(@ModelAttribute Transacao transacao, RedirectAttributes atributos) {
    transacaoService.alterar(transacao);
    atributos.addAttribute("mensagem", "Transação atualizada com sucesso!");
    return "redirect:/mensagem";
  }

  @GetMapping("/remover/{id}")
  public String remover(@PathVariable("id") Long id, RedirectAttributes atributos) {
    transacaoService.remover(id);
    atributos.addAttribute("mensagem", "Transação removida com sucesso");
    return "redirect:/mensagem";
  }

  @GetMapping("/mensagem")
  public String mostrarMensagem(@RequestParam String mensagem, Model model) {
    model.addAttribute("msgLabel", mensagem);
    return "mensagem";
  }
}
