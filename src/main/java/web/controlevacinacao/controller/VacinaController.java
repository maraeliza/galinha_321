package web.controlevacinacao.controller;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import web.controlevacinacao.filter.VacinaFilter;
import web.controlevacinacao.model.Vacina;
import web.controlevacinacao.pagination.PageWrapper;
import web.controlevacinacao.repository.VacinaRepository;
import web.controlevacinacao.service.VacinaService;

@Controller
public class VacinaController {

  private static final Logger logger = LoggerFactory.getLogger(
    VacinaController.class
  );

  private VacinaRepository vacinaRepository;
  private VacinaService vacinaService;

  public VacinaController(
    VacinaRepository vacinaRepository,
    VacinaService vacinaService
  ) {
    this.vacinaRepository = vacinaRepository;
    this.vacinaService = vacinaService;
  }


  @GetMapping("/vacinas")
  public String mostrarTodasVacinas(
      VacinaFilter filtro,
      Model model,
      @PageableDefault(size = 7) @SortDefault(sort = "codigo", direction = Sort.Direction.ASC) Pageable pageable,
      HttpServletRequest request) {
    Page<Vacina> pagina = vacinaRepository.pesquisar(filtro, pageable);
    logger.trace("Vacinas pesquisadas: {}", pagina.getContent());

    PageWrapper<Vacina> paginaWrapper = new PageWrapper<>(pagina, request);
    model.addAttribute("pagina", paginaWrapper);

    return "vacinas/listar";
  }
  @GetMapping("/vacinas/abrirPesquisar")
  public String abrirPesquisar() {
    return "vacinas/pesquisar";
  }

  @GetMapping("/vacinas/cadastrar")
  public String abrirCadastrar(Vacina vacina) {
    return "vacinas/cadastrar";
  }

  @GetMapping("/vacinas/pesquisar")
  public String pesquisar(
    VacinaFilter filtro,
    Model model,
    @PageableDefault(size = 7) @SortDefault(
      sort = "codigo",
      direction = Sort.Direction.ASC
    ) Pageable pageable,
    HttpServletRequest request
  ) {
    Page<Vacina> pagina = vacinaRepository.pesquisar(filtro, pageable);
    logger.trace("Vacinas pesquisadas: {}", pagina.getContent());

    PageWrapper<Vacina> paginaWrapper = new PageWrapper<>(pagina, request);
    model.addAttribute("pagina", paginaWrapper);

    return "vacinas/listar";
  }

  @GetMapping("/mensagem")
  public String mostrarMensagem(String mensagem, Model model) {
    model.addAttribute("msgLabel", mensagem);
    return "mensagem";
  }

  @GetMapping("/vacinas/alterar/{codigo}")
  public String mostrarAlterar(
    @PathVariable("codigo") Long codigo,
    Model model
  ) {
    Vacina vacina = vacinaRepository.findById(codigo).orElse(null);

    if (vacina != null) {
      model.addAttribute("vacina", vacina);
      return "vacinas/alterar";
    } else {
      model.addAttribute("mensagem", "Vacina não encontrada");
      return "mensagem";
    }
  }

  @GetMapping("/vacinas/remover/{codigo}")
  public String removerVacina(
    @PathVariable("codigo") Long codigo,
    RedirectAttributes atributos
  ) {
    vacinaService.desativar(codigo);
    atributos.addAttribute("mensagem", "Vacina removida com sucesso");
    return "redirect:/mensagem";
  }

  @PostMapping("/vacinas/cadastrar")
  public String cadastrar(Vacina vacina, RedirectAttributes atributos) {
    this.vacinaService.salvar(vacina);

    atributos.addAttribute("mensagem", "Vacina cadastrada com sucesso!");

    return "redirect:/mensagem";
  }

  @HxRequest
  @GetMapping("/vacinas/cadastrar")
  public String abrirCadastroVacinaHTMX(Vacina vacina) {
    return "vacinas/cadastrar :: formulario";
  }

  @PostMapping("/vacinas/alterar")
  public String alterarVacina(Vacina vacina, RedirectAttributes atributos) {
    this.vacinaService.alterar(vacina);

    atributos.addAttribute("mensagem", "Vacina atualizada com sucesso!");
    return "redirect:/mensagem";
  }
}
