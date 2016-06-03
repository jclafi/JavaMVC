package modal;

import java.util.List;

import javax.swing.JOptionPane;

import org.hibernate.HibernateException;
import org.hibernate.Session;

public class Tarefa {

	private long id_geral;
	private int cd_usuario;
	private String nm_tarefa;
	private int tempo_estimado;
	private int tempo_utilizado;
	private float percentual_desvio;
	private String finalizada;
	private String pausada;

	public String getPausada() {
		return pausada;
	}
	
	public String getPausadaExtenso() {
		if (pausada.equals("S"))
			return "Sim";
		else
			return "Não";
	}

	public void setPausada(String pausada) {
		this.pausada = pausada;
	}

	public long getId_geral() {
		return id_geral;
	}

	public void setId_geral(long id_geral) {
		this.id_geral = id_geral;
	}

	public int getCd_usuario() {
		return cd_usuario;
	}

	public void setCd_usuario(int cd_usuario) {
		this.cd_usuario = cd_usuario;
	}

	public String getNm_tarefa() {
		return nm_tarefa;
	}

	public void setNm_tarefa(String nm_tarefa) {
		this.nm_tarefa = nm_tarefa;
	}

	public int getTempo_estimado() {
		return tempo_estimado;
	}

	public void setTempo_estimado(int tempo_estimado) {
		this.tempo_estimado = tempo_estimado;
	}

	public int getTempo_utilizado() {
		return tempo_utilizado;
	}

	public void setTempo_utilizado(int tempo_utilizado) {
		this.tempo_utilizado = tempo_utilizado;
	}

	public float getPercentual_desvio() {
		return percentual_desvio;
	}

	public void setPercentual_desvio(float percentual_desvio) {
		this.percentual_desvio = percentual_desvio;
	}

	public String getFinalizada() {
		return finalizada;
	}

	public String getFinalizadaExtenso() {

		if (finalizada.equals("S"))
			return "Sim";
		else
			return "Não";
	}

	public void setFinalizada(String finalizada) {
		this.finalizada = finalizada;
	}

	public boolean salvaTarefa() {
		boolean ok = false;

		TarefaDao objPersistente = new TarefaDao();
		try {
			objPersistente.setObjRegra(this);
			ok = objPersistente.salvaTarefa();
		} finally {
			if (objPersistente != null)
				objPersistente = null;
		}

		return ok;

	}

	public boolean removeTarefa() {
		boolean ok = false;

		TarefaDao objPersistente = new TarefaDao();
		try {
			objPersistente.setObjRegra(this);
			ok = objPersistente.removeTarefa();
		} finally {
			if (objPersistente != null)
				objPersistente = null;
		}

		return ok;

	}

	public boolean carregaTarefa(long varIdTarefa) {
		boolean ok = false;

		TarefaDao objPersistente = new TarefaDao();
		try {
			ok = objPersistente.carregaTarefa(varIdTarefa);
			if (ok) {
				this.setId_geral(objPersistente.getObjRegra().getId_geral());
				this.setCd_usuario(objPersistente.getObjRegra().getCd_usuario());
				this.setNm_tarefa(objPersistente.getObjRegra().getNm_tarefa());
				this.setPercentual_desvio(objPersistente.getObjRegra().getPercentual_desvio());
				this.setTempo_estimado(objPersistente.getObjRegra().getTempo_estimado());
				this.setTempo_utilizado(objPersistente.getObjRegra().getTempo_utilizado());
				this.setFinalizada(objPersistente.getObjRegra().getFinalizada());
				this.setPausada(objPersistente.getObjRegra().getPausada());
			} else {
				this.setId_geral(0);
				this.setCd_usuario(0);
				this.setNm_tarefa("");
				this.setPercentual_desvio(0);
				this.setTempo_estimado(0);
				this.setTempo_utilizado(0);
				this.setFinalizada("");
				this.setPausada("");
			}
		} finally {
			if (objPersistente != null)
				objPersistente = null;
		}

		return ok;

	}

	public String toString() {

		return "Código: " + getId_geral() + " -  Nome: " + getNm_tarefa() + " -  Previsto: " + getTempo_estimado()
				+ " -  Utilizado: " + getTempo_utilizado() + " -  Desvio: " + getPercentual_desvio() + "% "
				+ " -  Pausada: " + getPausadaExtenso() + " -  Finalizada: " + getFinalizadaExtenso();

	}

	@SuppressWarnings("unchecked")
	public Tarefa[] listaTarefas(int varCd_Usuario) throws Exception {

		List<Tarefa> qryTarefa = null;

		Tarefa[] objTemp = null;

		Session session = TarefaDao.getSessionFactory();
		
		String query = "FROM Tarefa t where t.cd_usuario = :cd_usuario and upper(t.finalizada) = :finalizada order by t.id_geral, t.finalizada";

		try {

			qryTarefa = session.createQuery(query).setParameter("cd_usuario", varCd_Usuario).setParameter("finalizada", "N").list();

			if (qryTarefa.size() > 0) {
				objTemp = new Tarefa[qryTarefa.size()];
			
				int count = 0;
				for (Tarefa index : qryTarefa) {
					objTemp[count] = index;
					++count;
				}
			}

		} catch (HibernateException ex) {
			System.out.println("Exceção ao Listar Tarefas: " + ex.getMessage());
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
			throw new Exception("Atenção não há dados de Tarefas !");
	}

	public long getTarefaPk() {
		long longPK = 0;

		TarefaDao objPersistente = new TarefaDao();
		try {

			longPK = objPersistente.getTarefaPk();

		} finally {
			if (objPersistente != null)
				objPersistente = null;
		}

		return longPK;
	}
	
	public boolean equals(Object o) {
		
		boolean igual = false;
		
		if (o == null)
			return false;
		
		if (!(o instanceof Tarefa))
			return false;
		
		Tarefa task = (Tarefa) o;
		
		igual = ((this.id_geral == task.getId_geral()) &&
				 (this.cd_usuario == task.getCd_usuario()) &&
				 (this.nm_tarefa == task.getNm_tarefa()) &&
				 (this.tempo_estimado == task.getTempo_estimado()) &&
				 (this.tempo_utilizado == task.getTempo_utilizado()) &&
				 (this.percentual_desvio == task.getPercentual_desvio()) &&
				 (this.finalizada == task.getFinalizada()) &&
				 (this.pausada == task.getPausada()));
		
		return igual;
	}
	
	public int hashCode() {
		
		String atributos = (this.id_geral + 
							this.cd_usuario + 
							this.nm_tarefa + 
							this.tempo_estimado + 
							this.tempo_utilizado + 
							this.percentual_desvio + 
							this.finalizada + 
							this.pausada);
		
		return atributos.hashCode();
		
	}

	public float percentualDesvio(int estiHora, int utiliHora) {
		
		if ((estiHora <= 0) || (utiliHora <= 0))
			return 0f;
		
		float varTemp = 0;
		
		try {
			
			varTemp = (utiliHora * 100) / estiHora;
			
		}
		catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Exceção ao calcular o % Desvio. Mensagem: " + ex.getMessage());
			ex.printStackTrace();
			return 0f;
		}
		
		return varTemp;

	}
	
	public void disconnect() {

		TarefaDao objPersistente = new TarefaDao();
		try {
			objPersistente.hibernateDisconnect();
		} finally {
			if (objPersistente != null)
				objPersistente = null;
		}

	}

}