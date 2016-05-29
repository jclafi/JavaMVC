package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import modal.Tarefa;
import controller.RequestDispatcher;
import java.awt.Font;

public class ListaTarefas extends JDialog {

	private static final long serialVersionUID = 1L;
	private Tarefa objTarefaRegras;

	public ListaTarefas() {
		setBounds(100, 100, 450, 300);

		getContentPane().add(dadosUsuario(), BorderLayout.NORTH);
		getContentPane().add(dadosTarefas(), BorderLayout.CENTER);
		getContentPane().add(montaBotoes(), BorderLayout.SOUTH);

		setTitle("Lista de Tarefas");
		setAlwaysOnTop(true);
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 450, 190);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		toFront();
		pack();
	}
	
	private JPanel dadosTarefas() {
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER));

		objTarefaRegras = new Tarefa();

		try {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			JList<?> listaUser = new JList(objTarefaRegras.listaTarefas(RequestDispatcher.getUsuarioLogado()));
			contentPane.add(listaUser);
		}
		catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
			ex.printStackTrace();
		}
		
		return contentPane;
	}

	private JPanel montaBotoes() {
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		JButton okButton = new JButton("OK");
		okButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
					setVisible(false);
			
			}
		});
		contentPanel.add(okButton);

		return contentPanel;
	}
	
	private JPanel dadosUsuario() {
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		JLabel lblCdigoUsurio = new JLabel("Usuário Logado: " + RequestDispatcher.getUsuarioLogado() + 
				" - " + RequestDispatcher.getNomeUsuarioLogado());
		lblCdigoUsurio.setHorizontalAlignment(SwingConstants.CENTER);
		lblCdigoUsurio.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPanel.add(lblCdigoUsurio);
		
		return contentPanel;
	}
	
	public void finalize() {
		if (objTarefaRegras != null)
			objTarefaRegras = null;
	}

}