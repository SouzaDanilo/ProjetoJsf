package br.com.danilo.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.danilo.model.PessoaModel;
import br.com.danilo.model.UsuarioModel;
import br.com.danilo.repository.entity.PessoaEntity;
import br.com.danilo.repository.entity.UsuarioEntity;
import br.com.danilo.uteis.Uteis;

public class PessoaRepository {
	
	@Inject
	PessoaEntity pessoaEntity;
 
	EntityManager entityManager;
 
	/* M�TODO RESPONS�VEL POR SALVAR UMA NOVA PESSOA*/
	public void SalvarNovoRegistro(PessoaModel pessoaModel){
 
		entityManager =  Uteis.JpaEntityManager();
 
		pessoaEntity = new PessoaEntity();
		pessoaEntity.setEmail(pessoaModel.getEmail());
		pessoaEntity.setTelefone(pessoaModel.getTelefone());
		pessoaEntity.setNome(pessoaModel.getNome());
		pessoaEntity.setSenha(pessoaModel.getSenha());
 
		UsuarioEntity usuarioEntity = entityManager.find(UsuarioEntity.class, pessoaModel.getUsuarioModel().getCodigo()); 
 
		pessoaEntity.setUsuarioEntity(usuarioEntity);
 
		entityManager.persist(pessoaEntity);
 
	}
	
	/* M�TODO PARA CONSULTAR A PESSOA*/
	public List<PessoaModel> GetPessoas(){
 
		List<PessoaModel> pessoasModel = new ArrayList<PessoaModel>();
 
		entityManager =  Uteis.JpaEntityManager();
 
		Query query = entityManager.createNamedQuery("PessoaEntity.findAll");
 
		@SuppressWarnings("unchecked")
		Collection<PessoaEntity> pessoasEntity = (Collection<PessoaEntity>)query.getResultList();
 
		PessoaModel pessoaModel = null;
 
		for (PessoaEntity pessoaEntity : pessoasEntity) {
 
			pessoaModel = new PessoaModel();
			pessoaModel.setCodigo(pessoaEntity.getCodigo());
			pessoaModel.setEmail(pessoaEntity.getEmail());
			pessoaModel.setTelefone(pessoaEntity.getTelefone());
			pessoaEntity.setSenha(pessoaEntity.getSenha());
			pessoaModel.setNome(pessoaEntity.getNome());
 
 
			UsuarioEntity usuarioEntity =  pessoaEntity.getUsuarioEntity();			
 
			UsuarioModel usuarioModel = new UsuarioModel();
			usuarioModel.setUsuario(usuarioEntity.getUsuario());
 
			pessoaModel.setUsuarioModel(usuarioModel);
 
			pessoasModel.add(pessoaModel);
		}
 
		return pessoasModel;
 
	}
	
	/*CONSULTA UMA PESSOA CADASTRADA PELO C�DIGO */
	private PessoaEntity GetPessoa(int codigo){
 
		entityManager =  Uteis.JpaEntityManager();
 
		return entityManager.find(PessoaEntity.class, codigo);
	}
 
	/* ALTERA UM REGISTRO CADASTRADO NO BANCO DE DADOS*/
	public void AlterarRegistro(PessoaModel pessoaModel){
 
		entityManager =  Uteis.JpaEntityManager();
 
		PessoaEntity pessoaEntity = this.GetPessoa(pessoaModel.getCodigo());
 
		pessoaEntity.setEmail(pessoaModel.getEmail());
		pessoaEntity.setTelefone(pessoaModel.getTelefone());
		pessoaEntity.setNome(pessoaModel.getNome());
		pessoaEntity.setSenha(pessoaModel.getSenha());
 
		entityManager.merge(pessoaEntity);
	}
	
	
	 /*EXCLUI UM REGISTRO DO BANCO DE DADOS*/

	public void ExcluirRegistro(int codigo){
 
		entityManager =  Uteis.JpaEntityManager();		
 
		PessoaEntity pessoaEntity = this.GetPessoa(codigo);
 
		entityManager.remove(pessoaEntity);
	}

}
