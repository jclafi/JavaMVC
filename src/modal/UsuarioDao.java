package modal;

import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import hibernate.HbmSessionFactory;

public class UsuarioDao {

	private Usuario objRegra;

	public Usuario getObjRegra() {
		return objRegra;
	}

	public void setObjRegra(Usuario objRegra) {
		this.objRegra = objRegra;
	}

	public static Session getSessionFactory() {

		return HbmSessionFactory.getFactory().openSession();

	}

	public int getUsuarioPk() {
		int pk = 1;

		// To do List - Substituir esta função por um store procedure no banco
		// de dados, verificar o uso de sequence
		final String CUSTOM_SQL = " select cd_usuario, nome, senha, ativo from usuario order by cd_usuario desc limit 1 ";

		// Cria a sessão
		Session session = HbmSessionFactory.getFactory().openSession();

		SQLQuery qryTeste = null;
		try {

			qryTeste = session.createSQLQuery(CUSTOM_SQL);

			@SuppressWarnings("unchecked")
			List<Object[]> rows = qryTeste.list();

			if ((rows != null) && (!rows.isEmpty())) {
				for (Object[] index : rows) {
					Integer varInt = new Integer(index[0].toString());
					pk = ++varInt;
					break;
				}
			} else
				return pk;

		} catch (HibernateException ex) {
			JOptionPane.showMessageDialog(null, "Exceção ao Executar SQL Customizado: " + ex.getMessage());
			ex.printStackTrace();
			return -1;
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

		return pk;
	}

	public boolean salvaUsuario() {

		// Verifica se tem Chave Primária válida
		if (getObjRegra().getCd_usuario() <= 0)
			getObjRegra().setCd_usuario(getUsuarioPk());

		// Cria a Session
		Session session = HbmSessionFactory.getFactory().openSession();

		// Cria a Transacation
		Transaction tx = null;

		try {

			tx = session.beginTransaction();

			if (getObjRegra() != null) {
				session.saveOrUpdate(getObjRegra());
				tx.commit();
			}
		} catch (HibernateException ex) {
			if (tx != null)
				tx.rollback();
			JOptionPane.showMessageDialog(null, "Exceção ao Salvar Usuário: " + ex.getMessage());
			ex.printStackTrace();
			return false;
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
			if (tx != null)
				tx = null;
		}

		return true;
	}

	public boolean removeUsuario() {

		// Objeto Session
		Session session = HbmSessionFactory.getFactory().openSession();

		// Objeto Transação
		Transaction tx = null;

		try {
			tx = session.beginTransaction();

			if (getObjRegra() != null) {
				session.delete(getObjRegra());
				tx.commit();
			} else
				return false;

		} catch (HibernateException ex) {
			if (tx != null)
				tx.rollback();
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Exceção ao Excluir Usuário: " + ex.getMessage());
			return false;
		} finally {
			if (session != null) {
				session.close();
			}
			if (tx != null)
				tx = null;
		}

		return true;
	}

	public boolean carregaUsuario(int cdUsuario) {

		// Objeto Session
		Session session = HbmSessionFactory.getFactory().openSession();

		try {
			setObjRegra(session.get(Usuario.class, cdUsuario));
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Exceção ao Carregar Usuário: " + ex.getMessage());
			return false;
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return ((getObjRegra() != null) && (getObjRegra().getCd_usuario() > 0));
	}

	public void hibernateDisconnect() {

		HbmSessionFactory.closeFactory();

	}

}
