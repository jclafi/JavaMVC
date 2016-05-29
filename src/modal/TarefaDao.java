package modal;

import java.util.List;

import javax.swing.JOptionPane;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import hibernate.HbmSessionFactory;

public class TarefaDao {

	private Tarefa objRegra;

	public Tarefa getObjRegra() {
		return objRegra;
	}

	public void setObjRegra(Tarefa objRegra) {
		this.objRegra = objRegra;
	}

	public static Session getSessionFactory() {

		return HbmSessionFactory.getFactory().openSession();

	}

	public long getTarefaPk() {
		long pk = 1;

		// To do List - Substituir esta função por um store procedure no banco
		// de dados, verificar o uso de sequence
		final String CUSTOM_SQL = " select id_geral, cd_usuario, nm_tarefa, tempo_estimado, tempo_utilizado, percentual_desvio, "
				+ "finalizada, pausada from tarefa order by id_geral desc limit 1 ";

		// Cria a sessão
		Session session = HbmSessionFactory.getFactory().openSession();

		SQLQuery qryTeste = null;
		try {

			qryTeste = session.createSQLQuery(CUSTOM_SQL);

			@SuppressWarnings("unchecked")
			List<Object[]> rows = qryTeste.list();

			if ((rows != null) && (!rows.isEmpty())) {
				for (Object[] index : rows) {
					Long varInt = new Long(index[0].toString());
					pk = ++varInt;
					break;
				}
			} else
				return pk;

		} catch (HibernateException ex) {
			JOptionPane.showMessageDialog(null, "Exceção ao Executar SQL Customizado:" + ex.getMessage());
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

	public boolean salvaTarefa() {

		// Verifica se tem Chave Primária válida
		if (getObjRegra().getId_geral() <= 0)
			getObjRegra().setId_geral(getTarefaPk());

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
			JOptionPane.showMessageDialog(null, "Exceção ao Salvar Tarefa:" + ex.getMessage());
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

	public boolean removeTarefa() {

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
			JOptionPane.showMessageDialog(null, "Exceção ao Excluir Tarefa:" + ex.getMessage());
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

	public boolean carregaTarefa(long idTarefa) {

		// Objeto Session
		Session session = HbmSessionFactory.getFactory().openSession();

		try {
			setObjRegra(session.get(Tarefa.class, idTarefa));
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Exceção ao Carregar Tarefa: " + ex.getMessage());
			return false;
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return ((getObjRegra() != null) && (getObjRegra().getId_geral() > 0));
	}

	public void hibernateDisconnect() {

		HbmSessionFactory.closeFactory();

	}

}