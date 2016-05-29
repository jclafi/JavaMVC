package controller;

import hibernate.HbmSessionFactory;
import javax.swing.JOptionPane;
import controller.RequestDispatcher;

public class MainClass {

	public static void main(String[] args) {

		if (MainClass.criaSessionFactoryHibernate()) {

			RequestDispatcher objAcao = new RequestDispatcher();
			try {
				objAcao.setActionNow(RequestDispatcher.ActionName.TELA_LOGIN);
				if (!objAcao.executeAction())
					MainClass.finalizaAplicacao();
				else {
					objAcao.setActionNow(RequestDispatcher.ActionName.TELA_PRINCIPAL);
					if (!objAcao.executeAction())
						MainClass.finalizaAplicacao();
				}
			} finally {
				if (objAcao != null)
					objAcao = null;
			}

		} else {
			JOptionPane.showMessageDialog(null, "Falha ao criar a Session Factory do Hibernate!");
			MainClass.finalizaAplicacao();
		}

	}

	public static boolean criaSessionFactoryHibernate() {

		if ((!HbmSessionFactory.buildFactory()) || (HbmSessionFactory.getFactory() == null))
			return false;
		else
			return true;
	}

	public static void finalizaAplicacao() {
		HbmSessionFactory.closeFactory();
		System.gc();
		System.exit(0);
	}

}