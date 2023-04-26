package com.projeto.iprt2.iprt2.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.iprt2.iprt2.model.Profissao;



@Repository
@Transactional
public interface ProfissaoRepository extends CrudRepository<Profissao, Long>{
}
