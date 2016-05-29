package hibernate;

import javax.swing.JOptionPane;
import controller.ClassMap;
import org.hibernate.*;
import org.hibernate.cfg.*;

public class HbmSessionFactory {

	private static SessionFactory factory;

	public static SessionFactory getFactory() {
		return factory;
	}

	public static void setFactory(SessionFactory factory) {
		HbmSessionFactory.factory = factory;
	}

	public static boolean buildFactory() {

		if (getFactory() == null) {
			return buildFactory(ClassMap.getClasses());
		} else
			return (!getFactory().isClosed());
	}

	@SuppressWarnings("rawtypes")
	public static boolean buildFactory(Class[] classes) {

		Configuration config = new Configuration();
		try {

			for (Class index : classes)
				config.addClass(index);

			setFactory(config.configure().buildSessionFactory());

		} catch (HibernateException ex) {
			System.out.println("Exce��o ao criar a SessionFactory: " + ex.getMessage());
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Exce��o na cria��o da SessionFactory: " + ex.getMessage());
			return false;
		}

		return true;

	}

	public static void closeFactory() {
		if (getFactory() != null) {
			if (!getFactory().isClosed())
				getFactory().close();
			setFactory(null);
		}
	}
}