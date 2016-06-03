package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import modal.Usuario;
import java.awt.Font;
import javax.swing.SwingConstants;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import controller.RequestDispatcher;

public class TelaUsuario extends JDialog {

	private static final long serialVersionUID = 1L;
	private RequestDispatcher objAcao;
	private JPanel contentPane = new JPanel();
	private Usuario objUsuarioRegras = new Usuario();
	private JTextField edtCodigo;
	private JTextField edtNome;
	private JPasswordField edtSenha;
	private JCheckBox chkAtivo;

	public Usuario getObjUsuarioRegras() {
		return objUsuarioRegras;
	}

	public void setObjUsuarioRegras(Usuario objUsuarioRegras) {
		this.objUsuarioRegras = objUsuarioRegras;
	}

	public RequestDispatcher getObjAcao() {
		return objAcao;
	}

	public void setObjAcao(RequestDispatcher objAcao) {
		this.objAcao = objAcao;
	}

	public TelaUsuario() {
		// Objeto Usuário Regras
		objUsuarioRegras = new Usuario();

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(new JPanel(), BorderLayout.NORTH);
		getContentPane().add(montaDados(), BorderLayout.CENTER);
		getContentPane().add(montaBotoes(), BorderLayout.SOUTH);

		setTitle("Cadastro de Usuários");
		setAlwaysOnTop(true);
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 450, 190);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		toFront();
		edtCodigo.grabFocus();
	}

	private JPanel montaDados() {

		contentPane = new JPanel();
		contentPane.setLayout(new GridLayout(4, 2));

		JLabel lblCdigoUsurio = new JLabel("C\u00F3digo:");
		lblCdigoUsurio.setHorizontalAlignment(SwingConstants.CENTER);
		lblCdigoUsurio.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(lblCdigoUsurio);
		edtCodigo = new JTextField("", 20);
		edtCodigo.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {

				validaCodigo();

			}
		});
		edtCodigo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPane.add(edtCodigo);

		JLabel lblNomeUsurio = new JLabel("Nome Usu\u00E1rio:");
		lblNomeUsurio.setHorizontalAlignment(SwingConstants.CENTER);
		lblNomeUsurio.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(lblNomeUsurio);
		edtNome = new JTextField("", 80);
		edtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {

				arg0.setKeyChar(Character.toUpperCase(arg0.getKeyChar()));

			}
		});
		edtNome.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPane.add(edtNome);

		JLabel lblSenhaUsurio = new JLabel("Senha Usu\u00E1rio:");
		lblSenhaUsurio.setHorizontalAlignment(SwingConstants.CENTER);
		lblSenhaUsurio.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(lblSenhaUsurio);
		edtSenha = new JPasswordField("", 30);
		edtSenha.setEchoChar('*');
		edtSenha.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {

				e.setKeyChar(Character.toUpperCase(e.getKeyChar()));

			}
		});
		edtSenha.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPane.add(edtSenha);

		JLabel lblUsurioAtivo = new JLabel("Ativo:");
		lblUsurioAtivo.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsurioAtivo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(lblUsurioAtivo);
		chkAtivo = new JCheckBox("", true);
		chkAtivo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		chkAtivo.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(chkAtivo);

		return contentPane;
	}

	private JPanel montaBotoes() {

		contentPane = new JPanel();
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER));

		JButton button = new JButton("Salvar");
		button.setFont(new Font("Tahoma", Font.PLAIN, 20));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (!validaUsuario()) {
					JOptionPane.showMessageDialog(null, "Atenção complete os dados do Usuário !");
					edtCodigo.grabFocus();
				} else
					salvaUsuario();
			}
		});
		contentPane.add(button);

		JButton button_2 = new JButton("Excluir");
		button_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				removeUsuario();

			}
		});
		contentPane.add(button_2);

		JButton button_4 = new JButton("Listar");
		button_4.setFont(new Font("Tahoma", Font.PLAIN, 20));
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				listaUsuarios();
				limparCampos(true);

			}
		});
		contentPane.add(button_4);

		JButton button_3 = new JButton("Sair");
		button_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				setVisible(false);

			}
		});
		contentPane.add(button_3);

		return contentPane;

	}

	@SuppressWarnings("deprecation")
	private boolean validaUsuario() {

		return ((!edtCodigo.getText().isEmpty()) && (!edtNome.getText().isEmpty()) && (!edtSenha.getText().isEmpty()));

	}

	private void limparCampos(boolean validaFoco) {

		edtCodigo.setText("");
		edtNome.setText("");
		edtSenha.setText("");
		chkAtivo.setSelected(true);
		if (validaFoco)
			edtCodigo.grabFocus();

	}

	@SuppressWarnings("deprecation")
	private void salvaUsuario() {

		objUsuarioRegras.setCd_usuario(Integer.parseInt(edtCodigo.getText()));
		objUsuarioRegras.setNome(edtNome.getText());
		objUsuarioRegras.setSenha(edtSenha.getText());
		if (chkAtivo.isSelected())
			objUsuarioRegras.setAtivo("S");
		else
			objUsuarioRegras.setAtivo("N");

		if (objUsuarioRegras.salvaUsuario()) {
			JOptionPane.showMessageDialog(null, "Usuário Salvo com Sucesso !");
			limparCampos(true);
		} else
			JOptionPane.showMessageDialog(null, "Não foi possível salvar o Usuário !");
	}

	private void carregarUsuario(int varCdUsuario) {

		if (!objUsuarioRegras.carregaUsuario(varCdUsuario)) {
			JOptionPane.showMessageDialog(null, "Atenção usuário inexistente !");
			limparCampos(true);
		} else {
			edtNome.setText(objUsuarioRegras.getNome());
			edtSenha.setText(objUsuarioRegras.getSenha());
			chkAtivo.setSelected(objUsuarioRegras.getAtivo().toString().equals("S"));
		}

	}

	private void removeUsuario() {

		if (!validaUsuario())
			JOptionPane.showMessageDialog(null, "Atenção selecione o Usuário a Excluir !");
		else {
			if (JOptionPane.showConfirmDialog(null, "Deseja Excluir o Usuário e seu Histórico de Tarefas ?",
					"Confirmação", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				if (objUsuarioRegras.removeUsuario()) {
					JOptionPane.showMessageDialog(null, "Usuário removido com Sucesso !");
					limparCampos(true);
				} else
					JOptionPane.showMessageDialog(null, "Não foi possível excluir o Usuário !");
		}

	}

	private void listaUsuarios() {

		objAcao.executeAction(RequestDispatcher.ActionName.LISTA_USUARIOS);

	}

	private void validaCodigo() {

		if (edtCodigo.getText().isEmpty()) {
			limparCampos(false);
			edtCodigo.setText(String.valueOf(objUsuarioRegras.getUsuarioPk()));
		} 
		else 
			carregarUsuario(Integer.parseInt(edtCodigo.getText()));
		
	}

	public void finalize() {
		if (objUsuarioRegras != null)
			objUsuarioRegras = null;
	}

}