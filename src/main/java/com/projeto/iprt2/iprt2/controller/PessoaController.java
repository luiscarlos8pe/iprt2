package com.projeto.iprt2.iprt2.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.servlet.ModelAndView;

import com.projeto.iprt2.iprt2.model.Pessoa;
import com.projeto.iprt2.iprt2.repository.PessoaRepository;

@Controller
public class PessoaController {
	
	@Autowired
	private PessoaRepository pessoaRepository;

	@RequestMapping(method = RequestMethod.GET, value = "/cadastropessoa")
	public ModelAndView inicio() {
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		modelAndView.addObject("pessoaobj", new Pessoa());
		return modelAndView;
		
	}
	@RequestMapping(method = RequestMethod.POST, value = "**/salvarpessoa")
	public ModelAndView salvar(@Valid Pessoa pessoa, BindingResult bindingResult) {
		
		
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
			return modelAndView;
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
			return modelAndView;
			
		}
		
		@GetMapping("/removerpessoa/{idpessoa}")
		public ModelAndView excluir(@PathVariable("idpessoa") Long idpessoa) {
			
			pessoaRepository.deleteById(idpessoa);	
			
			ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
			modelAndView.addObject("pessoas", pessoaRepository.findAll());
			modelAndView.addObject("pessoaobj", new Pessoa());
			return modelAndView;
			
		}
		
		@PostMapping("**/pesquisarpessoa")
		public ModelAndView pesquisar(@RequestParam("nomepesquisa") String nomepesquisa) {
			ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
			modelAndView.addObject("pessoas", pessoaRepository.findPessoaByName(nomepesquisa));
			modelAndView.addObject("pessoaobj", new Pessoa());
			return modelAndView;
		}
		
		@GetMapping("/detalhepessoa/{idpessoa}")
		public ModelAndView detalhepessoa(@PathVariable("idpessoa") Long idpessoa) {
			
			Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);

			ModelAndView modelAndView = new ModelAndView("cadastro/detalhepessoa");
			modelAndView.addObject("pessoaobj", pessoa.get());
			return modelAndView;
			
		}
		
}