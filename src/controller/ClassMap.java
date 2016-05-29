package controller;

import modal.Tarefa;
import modal.Usuario;

public class ClassMap {

	@SuppressWarnings("rawtypes")
	public static Class[] getClasses() {

		Class[] lista = { Usuario.class, Tarefa.class };

		return lista;
	}

}