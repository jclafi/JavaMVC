package controller;

import javax.swing.JOptionPane;
import view.*;

public class RequestDispatcher {

	private static int usuarioLogado;
	private static String nomeUsuarioLogado;

	public static enum ActionName {
		INDEFINIDA, TELA_LOGIN, CADASTRO_USUARIO, CADASTRO_TAREFA, TELA_PRINCIPAL, LISTA_USUARIOS, LISTA_TAREFAS
	};

	private ActionName actionNow;

	public static String getNomeUsuarioLogado() {
		return nomeUsuarioLogado;
	}

	public static void setNomeUsuarioLogado(String nomeUsuarioLogado) {
		RequestDispatcher.nomeUsuarioLogado = nomeUsuarioLogado;
	}

	public static int getUsuarioLogado() {
		return usuarioLogado;
	}

	public static void setUsuarioLogado(int usuarioLogado) {
		RequestDispatcher.usuarioLogado = usuarioLogado;
	}

	public ActionName getActionNow() {
		return actionNow;
	}

	public void setActionNow(ActionName actionNow) {
		this.actionNow = actionNow;
	}

	public RequestDispatcher(ActionName actionNow) {
		setActionNow(actionNow);
	}

	public RequestDispatcher() {
		setActionNow(RequestDispatcher.ActionName.INDEFINIDA);
	}

	public boolean executeAction() {

		return executeAction(getActionNow());

	}

	public boolean executeAction(ActionName varActionNow) {

		try {

			switch (varActionNow) {
			case INDEFINIDA: {
				JOptionPane.showMessageDialog(null, "Atenção ação de acesso Indefinida");
				break;
			}
			case TELA_LOGIN:
				return executaLogin();
			case CADASTRO_TAREFA:
				return cadastraTarefa();
			case CADASTRO_USUARIO:
				return cadastraUsuario();
			case TELA_PRINCIPAL:
				return chamaTelaPrincipal();
			case LISTA_USUARIOS:
				return listaUsuarios();
			case LISTA_TAREFAS:
				return listaTarefas();
			}

			return false;
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Exceção ao executar a ação. Mensagem: " + ex.getMessage());
			ex.printStackTrace();
			return false;
		}

	}

	private boolean executaLogin() {

		boolean LoginOk = false;

		TelaLogin frmTelaLogin = new TelaLogin();
		try {

			frmTelaLogin.setVisible(true);
			LoginOk = frmTelaLogin.isLoginOk();

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Exceção ao Executar o Login no Sistema. Mensagem: " + ex.getMessage());
			ex.printStackTrace();
			return false;
		} finally {
			if (frmTelaLogin != null) {
				frmTelaLogin.finalize();
				frmTelaLogin.dispose();
			}
		}

		return LoginOk;

	}

	private boolean cadastraTarefa() {

		TelaTarefa frmCadTarefa = new TelaTarefa();
		try {

			frmCadTarefa.setObjAcao(this);
			frmCadTarefa.setVisible(true);

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null,
					"Exceção ao Executar o Cadastro de Tarefas. Mensagem: " + ex.getMessage());
			ex.printStackTrace();
			return false;
		} finally {
			if (frmCadTarefa != null) {
				frmCadTarefa.finalize();
				frmCadTarefa.dispose();
			}
		}

		return true;

	}

	private boolean cadastraUsuario() {

		TelaUsuario frmCadUsuario = new TelaUsuario();
		try {

			frmCadUsuario.setObjAcao(this);
			frmCadUsuario.setVisible(true);

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null,
					"Exceção ao Executar o Cadastro de Usuários. Mensagem: " + ex.getMessage());
			ex.printStackTrace();
			return false;
		} finally {
			if (frmCadUsuario != null) {
				frmCadUsuario.finalize();
				frmCadUsuario.dispose();
			}
		}

		return true;

	}

	private boolean chamaTelaPrincipal() {

		FormularioPrincipal frmPrincipal = new FormularioPrincipal();
		try {
			frmPrincipal.setVisible(true);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null,
					"Exceção ao abrir o formulário Principal do Sistema. Mensagem: " + ex.getMessage());
			ex.printStackTrace();
			return false;
		}

		return true;

	}

	private boolean listaUsuarios() {

		ListaUsuarios objLista = new ListaUsuarios();
		try {
			objLista.setVisible(true);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null,
					"Exceção ao Executar o Listagem de Usuários. Mensagem: " + ex.getMessage());
			ex.printStackTrace();
			return false;
		} finally {
			if (objLista != null) {
				objLista.finalize();
				objLista.dispose();
			}
		}

		return true;

	}

	private boolean listaTarefas() {

		ListaTarefas objLista = new ListaTarefas();
		try {
			objLista.setVisible(true);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null,
					"Exceção ao Executar o Listagem de Tarefas. Mensagem: " + ex.getMessage());
			ex.printStackTrace();
			return false;
		} finally {
			if (objLista != null)
				objLista.finalize();
			objLista.dispose();
		}

		return true;

	}

}