package com.projeto.iprt2.iprt2.repository;

import java.util.List;

import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import com.projeto.iprt2.iprt2.model.Pessoa;

@Repository
@Transactional
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

	@Query("select p from Pessoa p where p.nome like %?1% ")
	List<Pessoa> findPessoaByName(String nomepesquisa);

	@Query("select p from Pessoa p where p.nome like %?1% and p.dizimista = ?2")
	List<Pessoa> findPessoaByNameDizimista(String nome, String dizimista);

	@Query("select p from Pessoa p where p.dizimista = ?1")
	List<Pessoa> findPessoaDizimista(String dizimista);

	default Page<Pessoa> findPessoaByNameDizimistaPage(String nome, String dizimista, Pageable pageable) {

		Pessoa pessoa = new Pessoa();
		pessoa.setNome(nome);
		pessoa.setDizimista(dizimista);

		ExampleMatcher ignoringExampleMatcher = ExampleMatcher.matchingAny()
				.withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
				.withMatcher("dizimista", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

		Example<Pessoa> example = Example.of(pessoa, ignoringExampleMatcher);

		Page<Pessoa> pessoas = findAll(example, pageable);

		return pessoas;

	}

	default Page<Pessoa> findPessoaByNamePage(String nome, Pageable pageable) {

		Pessoa pessoa = new Pessoa();
		pessoa.setNome(nome);

		ExampleMatcher ignoringExampleMatcher = ExampleMatcher.matchingAny().withMatcher("nome",
				ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

		Example<Pessoa> example = Example.of(pessoa, ignoringExampleMatcher);

		Page<Pessoa> pessoas = findAll(example, pageable);

		return pessoas;

	}

}