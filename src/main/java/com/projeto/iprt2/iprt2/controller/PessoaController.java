package com.projeto.iprt2.iprt2.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.projeto.iprt2.iprt2.model.Pessoa;
import com.projeto.iprt2.iprt2.repository.PessoaRepository;
import com.projeto.iprt2.iprt2.repository.ProfissaoRepository;

@Controller
public class PessoaController {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	
	@Autowired
	private ReportUtil reportUtil;
	
	
	@Autowired
	private ProfissaoRepository profissaoRepository;

	@RequestMapping(method = RequestMethod.GET, value = "/cadastropessoa")
	public ModelAndView inicio() {
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		modelAndView.addObject("pessoaobj", new Pessoa());
		modelAndView.addObject("profissoes", profissaoRepository.findAll());
		return modelAndView;
		
	}
	@RequestMapping(method = RequestMethod.POST, value = "**/salvarpessoa", consumes = {"multipart/form-data"})
	public ModelAndView salvar(@Valid Pessoa pessoa, BindingResult bindingResult, final MultipartFile file) throws IOException {
		
		
		if (bindingResult.hasErrors()) {
			ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
			Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
			modelAndView.addObject("pessoas", pessoasIt);
			modelAndView.addObject("pessoaobj", pessoa);
			
			List<String> msg = new ArrayList<String>();
			for (ObjectError objectError : bindingResult.getAllErrors()) {
				msg.add(objectError.getDefaultMessage()); // vem das anotações @NotEmpty e outras
			}
			
			modelAndView.addObject("msg", msg);
			modelAndView.addObject("profissoes", profissaoRepository.findAll());
			return modelAndView;
		}
		
		if (file.getSize() > 0) { /*Cadastrando um curriculo*/
			pessoa.setCurriculo(file.getBytes());
		}else {
			if (pessoa.getId() != null && pessoa.getId() > 0) {// editando
				byte[] curriculoTempo = pessoaRepository.
						findById(pessoa.getId()).get().getCurriculo();
				pessoa.setCurriculo(curriculoTempo);
			}
		}
		pessoaRepository.save(pessoa);

		ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
		Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
		andView.addObject("pessoas", pessoasIt);
		andView.addObject("pessoaobj", new Pessoa());		
		return andView;

	}

		@RequestMapping(method = RequestMethod.GET, value = "/listapessoas")
		public ModelAndView pessoas() {
			ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
			Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
			andView.addObject("pessoas", pessoasIt);
			andView.addObject("pessoaobj", new Pessoa());
			return andView;
		}
		
		

		@GetMapping("/editarpessoa/{idpessoa}")
		public ModelAndView editar(@PathVariable("idpessoa") Long idpessoa) {
			
			Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);

			ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
			modelAndView.addObject("pessoaobj", pessoa.get());
			modelAndView.addObject("profissoes", profissaoRepository.findAll());
			return modelAndView;
			
		}
		
		@GetMapping("/removerpessoa/{idpessoa}")
		public ModelAndView excluir(@PathVariable("idpessoa") Long idpessoa) {
			
			pessoaRepository.deleteById(idpessoa);	
			
			ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
			modelAndView.addObject("pessoas", pessoaRepository.findAll());
			modelAndView.addObject("pessoaobj", new Pessoa());
			modelAndView.addObject("profissoes", profissaoRepository.findAll());
			return modelAndView;
			
		}
		
		@PostMapping("**/pesquisarpessoa")
		public ModelAndView pesquisar(@RequestParam("nomepesquisa") String nomepesquisa, @RequestParam("dizimista") String dizimista) {
			
			List<Pessoa> pessoas = new ArrayList<Pessoa>();		
			if (dizimista != null && !dizimista.isEmpty()) {
				pessoas = pessoaRepository.findPessoaByNameDizimista(nomepesquisa, dizimista);
			}else {
				pessoas = pessoaRepository.findPessoaByName(nomepesquisa);
			}
			
			
			
			ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
			modelAndView.addObject("pessoas", pessoas);
			modelAndView.addObject("pessoaobj", new Pessoa());
			return modelAndView;
			
		}
		
		
		@GetMapping("**/pesquisarpessoa")
		public void imprimePdf(@RequestParam("nomepesquisa") String nomepesquisa, 
				@RequestParam("dizimista") String dizimista,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			List<Pessoa> pessoas = new ArrayList<Pessoa>();
			
			if (dizimista != null && !dizimista.isEmpty()
					&& nomepesquisa != null && !nomepesquisa.isEmpty()) {/*Busca por nome e sexo*/
				
				pessoas = pessoaRepository.findPessoaByNameDizimista(nomepesquisa, dizimista);
				
			}else if (nomepesquisa != null && !nomepesquisa.isEmpty()) {/*Busca somente por nome*/
				
				pessoas = pessoaRepository.findPessoaByName(nomepesquisa);
				
			}
		else if (dizimista != null && !dizimista.isEmpty()) {/*Busca somente por sexo*/
			
			pessoas = pessoaRepository.findPessoaDizimista(dizimista);
			
		}
			else {/*Busca todos*/
				
				Iterable<Pessoa> iterator = pessoaRepository.findAll();
				for (Pessoa pessoa : iterator) {
					pessoas.add(pessoa);
				}
			}
			
			/*Chame o serviço que faz a geração do relatorio*/
			byte[] pdf = reportUtil.gerarRelatorio(pessoas, "pessoa", request.getServletContext());
			
		    /*Tamanho da resposta*/
			response.setContentLength(pdf.length);
			
			/*Definir na resposta o tipo de arquivo*/
			response.setContentType("application/octet-stream");
			
			/*Definir o cabeçalho da resposta*/
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", "relatorio.pdf");
			response.setHeader(headerKey, headerValue);
			
			/*Finaliza a resposta pro navegador*/
			response.getOutputStream().write(pdf);
			
		}
		
		
		@GetMapping("/detalhepessoa/{idpessoa}")
		public ModelAndView detalhepessoa(@PathVariable("idpessoa") Long idpessoa) {
			
			Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);

			ModelAndView modelAndView = new ModelAndView("cadastro/detalhepessoa");
			modelAndView.addObject("pessoaobj", pessoa.get());
			return modelAndView;
			
		}
		
}