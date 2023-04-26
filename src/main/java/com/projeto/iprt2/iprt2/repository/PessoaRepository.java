package com.projeto.iprt2.iprt2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.iprt2.iprt2.model.Pessoa;


@Repository
@Transactional
public interface PessoaRepository extends CrudRepository<Pessoa, Long> {
	
	@Query("select p from Pessoa p where p.nome like %?1% ")
	List<Pessoa> findPessoaByName(String nomepesquisa);
	
	@Query("select p from Pessoa p where p.nome like %?1% and p.dizimista = ?2")
	List<Pessoa> findPessoaByNameDizimista(String nome, String dizimista);
	
	@Query("select p from Pessoa p where p.dizimista = ?1")
	List<Pessoa> findPessoaDizimista(String dizimista);
	



	

}