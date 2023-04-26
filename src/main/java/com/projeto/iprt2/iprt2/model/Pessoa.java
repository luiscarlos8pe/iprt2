package com.projeto.iprt2.iprt2.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;




@Entity
public class Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	
		@NotNull(message="Nome não pode ser nulo")
		@NotEmpty(message = "Nome não pode ser vazio")
	    private String nome;
		
		@NotNull(message="Nome não pode ser nulo")
		@NotEmpty(message = "Nome não pode ser vazio")
	    private String apelido;
	 
	    
	    @NotNull(message="Nome não pode ser nulo")
		@NotEmpty(message = "Nome não pode ser vazio")
	    private String email;
	    private String telefone;
	    private String dizimista;
	    
	    
	    @DateTimeFormat(pattern="yyyy-MM-dd")
		@Temporal(TemporalType.DATE)
		private Date dataNascimento;
	
	    @DateTimeFormat(pattern="yyyy-MM-dd")
		@Temporal(TemporalType.DATE)
	    private Date dataCoversao;
	    
	    @OneToOne(mappedBy="pessoa")
	    private Endereco endereco;
	    
	    
	    private String cep;
	    private String rua;
	    private String numero;
	    private String bairro;
	    private String cidade;
	    private String uf;
	    private String ibge;
	    
	    private String sexopessoa;
	
	    @ManyToOne
		private Profissao profissao;
		
	    
	    
	    public Profissao getProfissao() {
			return profissao;
		}

		public void setProfissao(Profissao profissao) {
			this.profissao = profissao;
		}

		public String getSexopessoa() {
			return sexopessoa;
		}

		public void setSexopessoa(String sexopessoa) {
			this.sexopessoa = sexopessoa;
		}

		public String getCep() {
			return cep;
		}

		public void setCep(String cep) {
			this.cep = cep;
		}

		public String getRua() {
			return rua;
		}

		public void setRua(String rua) {
			this.rua = rua;
		}

		public String getNumero() {
			return numero;
		}

		public void setNumero(String numero) {
			this.numero = numero;
		}

		public String getBairro() {
			return bairro;
		}

		public void setBairro(String bairro) {
			this.bairro = bairro;
		}

		public String getCidade() {
			return cidade;
		}

		public void setCidade(String cidade) {
			this.cidade = cidade;
		}

		public String getUf() {
			return uf;
		}

		public void setUf(String uf) {
			this.uf = uf;
		}

		public String getIbge() {
			return ibge;
		}

		public void setIbge(String ibge) {
			this.ibge = ibge;
		}

		public String getDizimista() {
			return dizimista;
		}

		public void setDizimista(String dizimista) {
			this.dizimista = dizimista;
		}

		public Date getDataNascimento() {
			return dataNascimento;
		}

		public void setDataNascimento(Date dataNascimento) {
			this.dataNascimento = dataNascimento;
		}

		public Date getDataCoversao() {
			return dataCoversao;
		}

		public void setDataCoversao(Date dataCoversao) {
			this.dataCoversao = dataCoversao;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

	

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public String getApelido() {
			return apelido;
		}

		public void setApelido(String apelido) {
			this.apelido = apelido;
		}

		public Endereco getEndereco() {
			return endereco;
		}

		public void setEndereco(Endereco endereco) {
			this.endereco = endereco;
		}

	
		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}


	public String getTelefone() {
			return telefone;
		}

		public void setTelefone(String telefone) {
			this.telefone = telefone;
		}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}