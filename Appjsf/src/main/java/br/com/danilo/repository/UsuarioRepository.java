package br.com.danilo.repository;

import java.io.Serializable;

import javax.persistence.Query;

import br.com.danilo.model.UsuarioModel;
import br.com.danilo.repository.entity.UsuarioEntity;
import br.com.danilo.uteis.Uteis;

public class UsuarioRepository implements Serializable {
	 
	 
	private static final long serialVersionUID = 1L;
 
	public UsuarioEntity ValidaUsuario(UsuarioModel usuarioModel){
 
		try {
 
			/*QUERY QUE VAI SER EXECUTADA (UsuarioEntity.findUser)*/ 	
			Query query = Uteis.JpaEntityManager().createNamedQuery("UsuarioEntity.findUser");
 
			/*PAR�METROS DA QUERY*/
			query.setParameter("usuario", usuarioModel.getUsuario());
			query.setParameter("senha", usuarioModel.getSenha());
 
			/*RETORNA O USU�RIO SE FOR LOCALIZADO*/
			return (UsuarioEntity)query.getSingleResult();
 
		} catch (Exception e) {
 
			return null;
		}
 
 
	}
}