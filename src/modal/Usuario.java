package modal;

import java.util.List;

import javax.swing.JOptionPane;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

public class Usuario {

	private int cd_usuario;
	private String nome;
	private String senha;
	private String ativo;
	private boolean usoLista;

	public boolean isUsoLista() {
		return usoLista;
	}

	public void setUsoLista(boolean usoLista) {
		this.usoLista = usoLista;
	}

	public int getCd_usuario() {
		return cd_usuario;
	}

	public void setCd_usuario(int cd_usuario) {
		this.cd_usuario = cd_usuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getAtivo() {
		return ativo;
	}

	public String getAtivoExtenso() {
		if (ativo.equals("S"))
			return "Sim";
		else
			return "Não";
	}

	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}

	public Usuario() {

		setUsoLista(false);

	}

	public boolean salvaUsuario() {
		boolean ok = false;

		UsuarioDao objPersistente = new UsuarioDao();
		try {
			objPersistente.setObjRegra(this);
			ok = objPersistente.salvaUsuario();
		} finally {
			if (objPersistente != null)
				objPersistente = null;
		}

		return ok;

	}

	public boolean removeUsuario() {
		boolean ok = false;

		UsuarioDao objPersistente = new UsuarioDao();
		try {
			objPersistente.setObjRegra(this);
			ok = objPersistente.removeUsuario();
		} finally {
			if (objPersistente != null)
				objPersistente = null;
		}

		return ok;

	}

	public boolean carregaUsuario(int varCdUsuario) {
		boolean ok = false;

		UsuarioDao objPersistente = new UsuarioDao();
		try {
			ok = objPersistente.carregaUsuario(varCdUsuario);
			if (ok) {
				this.setCd_usuario(objPersistente.getObjRegra().getCd_usuario());
				this.setNome(objPersistente.getObjRegra().getNome());
				this.setSenha(objPersistente.getObjRegra().getSenha());
				this.setAtivo(objPersistente.getObjRegra().getAtivo());
			} else {
				this.setCd_usuario(0);
				this.setNome("");
				this.setSenha("");
				this.setAtivo("");
			}
		} finally {
			if (objPersistente != null)
				objPersistente = null;
		}

		return ok;

	}

	public boolean validaLogin(String login, String senha) {

		final String CUSTOM_SQL = " select cd_usuario, nome, senha, ativo from usuario   "
				+ " where upper(nome) = :login and upper(senha) = :senha "
				+ " and upper(ativo) = 'S' Limit 1						 ";

		// Cria a sessão
		Session session = UsuarioDao.getSessionFactory();

		SQLQuery qryTeste = null;
		try {

			qryTeste = session.createSQLQuery(CUSTOM_SQL);
			qryTeste.setParameter("login", login);
			qryTeste.setParameter("senha", senha);

			if ((qryTeste == null) || (qryTeste.list().isEmpty()))
				return false;
			else {
				@SuppressWarnings("unchecked")
				List<Object[]> rows = qryTeste.list();

				for (Object[] index : rows) {
					Integer varInt = new Integer(index[0].toString());
					setCd_usuario(varInt);
					setNome(index[1].toString());
					setSenha(index[2].toString());
					setAtivo(index[3].toString());
				}
			}
		} catch (HibernateException ex) {
			JOptionPane.showMessageDialog(null, "Exceção ao Executar SQL Customizado: " + ex.getMessage());
			ex.printStackTrace();
			return false;
		} finally {
			if (qryTeste != null) {
				if (!qryTeste.list().isEmpty())
					qryTeste.list().clear();
				qryTeste = null;
			}

			if (session != null) {
				session.close();
				session = null;
			}
		}

		return true;

	}

	@SuppressWarnings("unchecked")
	public Usuario[] listaUsuarios() throws Exception {

		List<Usuario> qryUsuario = null;

		Usuario[] objTemp = null;

		// Cria a Session
		Session session = UsuarioDao.getSessionFactory();

		try {

			qryUsuario = session.createQuery("FROM Usuario u order by u.nome").list();

			if (qryUsuario.size() > 0) {
				objTemp = new Usuario[qryUsuario.size()];
				
				int count = 0;
				for (Usuario index : qryUsuario) {
					objTemp[count] = index;
					objTemp[count].setUsoLista(this.isUsoLista());
					++count;
				}
			}

		} catch (HibernateException ex) {
			System.out.println("Exceção ao Listar Usuários: " + ex.getMessage());
			ex.printStackTrace();
			return null;
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}

		if (objTemp != null)
			return objTemp;
		else
			throw new Exception("Atenção não há dados de Usuários !");

	}

	public int getUsuarioPk() {
		int intPK = 0;

		UsuarioDao objPersistente = new UsuarioDao();
		try {

			intPK = objPersistente.getUsuarioPk();

		} finally {
			if (objPersistente != null)
				objPersistente = null;
		}

		return intPK;
	}

	public String toString() {

		if (isUsoLista())
			return "Código: " + getCd_usuario() + " -  Descrição: " + getNome() + " -  Senha: ****** -  Ativo: "
					+ getAtivoExtenso();
		else
			return getCd_usuario() + " - " + getNome() + " - Ativo: " + getAtivoExtenso();

	}

	public void disconnect() {

		UsuarioDao objPersistente = new UsuarioDao();
		try {
			objPersistente.hibernateDisconnect();
		} finally {
			if (objPersistente != null)
				objPersistente = null;
		}

	}

}