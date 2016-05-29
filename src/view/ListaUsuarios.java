package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.RequestDispatcher;
import modal.Usuario;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class ListaUsuarios extends JDialog {

	private static final long serialVersionUID = 1L;
	private Usuario objUsuarioRegras;
	
	public ListaUsuarios() {

		objUsuarioRegras = new Usuario();
		objUsuarioRegras.setUsoLista(true);

		setBounds(100, 100, 450, 300);

		getContentPane().add(dadosLogin(), BorderLayout.NORTH);
		getContentPane().add(dadosUser(), BorderLayout.CENTER);
		getContentPane().add(montaBotoes(), BorderLayout.SOUTH);

		setTitle("Lista de Usuários");
		setAlwaysOnTop(true);
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 450, 190);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		toFront();
		pack();
		
	}
	
	private JPanel dadosLogin() {
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

	private JPanel dadosUser() {
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER));
				
		try {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			JList<?> listaUser = new JList(objUsuarioRegras.listaUsuarios());
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
	
	public void finalize() {
		if (objUsuarioRegras != null)
			objUsuarioRegras = null;
	}

}
