package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import controller.RequestDispatcher;
import modal.Tarefa;
import modal.Usuario;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class TelaTarefa extends JDialog {

	private static final long serialVersionUID = 1L;
	private RequestDispatcher objAcao;
	private Tarefa objTarefaRegras;
	private Usuario objUsuarioRegras;
	private JPanel contentPane;
	private JTextField edtCodigo;
	private JTextField edtDescricao;
	private JComboBox<Usuario> comboUsuario;
	private JTextField edtEstimativa;
	private JTextField edtUtilizado;
	private JTextField edtDesvio;
	private JCheckBox chkFinalizado;
	private JCheckBox chkPausada;

	public Usuario getObjUsuarioRegras() {
		return objUsuarioRegras;
	}

	public void setObjUsuarioRegras(Usuario objUsuarioRegras) {
		this.objUsuarioRegras = objUsuarioRegras;
	}

	public Tarefa getObjTarefaRegras() {
		return objTarefaRegras;
	}

	public void setObjTarefaRegras(Tarefa objTarefaRegras) {
		this.objTarefaRegras = objTarefaRegras;
	}

	public RequestDispatcher getObjAcao() {
		return objAcao;
	}

	public void setObjAcao(RequestDispatcher objAcao) {
		this.objAcao = objAcao;
	}

	public TelaTarefa() {

		objTarefaRegras = new Tarefa();
		objUsuarioRegras = new Usuario();
		setTitle("Cadastro de Tarefas");
		setAlwaysOnTop(true);
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 396, 260);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		toFront();

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(montaBasico(), BorderLayout.NORTH);
		getContentPane().add(montaDados(), BorderLayout.CENTER);
		getContentPane().add(montaBotoes(), BorderLayout.SOUTH);

	}

	private JPanel montaBasico() {

		contentPane = new JPanel();
		contentPane.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel lblCdigoUsurio = new JLabel("C\u00F3digo:");
		lblCdigoUsurio.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(lblCdigoUsurio);
		edtCodigo = new JTextField("", 5);
		edtCodigo.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {

				validaCodigo();

			}
		});
		edtCodigo.setToolTipText("C\u00F3digo da Tarefa");
		edtCodigo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPane.add(edtCodigo);

		JLabel lblPausada = new JLabel("Pausada:");
		lblPausada.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(lblPausada);

		chkPausada = new JCheckBox("", false);
		chkPausada.setFont(new Font("Tahoma", Font.PLAIN, 18));
		chkPausada.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(chkPausada);		
		
		return contentPane;

	}

	private JPanel montaDados() {

		contentPane = new JPanel();
		contentPane.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel lblNmTarefa = new JLabel("Descrição:");
		lblNmTarefa.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(lblNmTarefa);

		edtDescricao = new JTextField("", 18);
		edtDescricao.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {

				arg0.setKeyChar(Character.toUpperCase(arg0.getKeyChar()));

			}
		});
		edtDescricao.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPane.add(edtDescricao);

		JLabel lblNmUsuario = new JLabel("Usuário:");
		lblNmUsuario.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(lblNmUsuario);

		comboUsuario = new JComboBox<Usuario>();
		try {
			for (Usuario index : objUsuarioRegras.listaUsuarios())
				comboUsuario.addItem(index);
		}
		catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
			ex.printStackTrace();
		}
			
		comboUsuario.setSelectedItem(null);
		comboUsuario.setFont(new Font("Tahoma", Font.PLAIN, 20));
		comboUsuario.setPreferredSize(new Dimension(277, 27));
		contentPane.add(comboUsuario);

		JLabel lblEstimativa = new JLabel("Previsto:");
		lblEstimativa.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(lblEstimativa);

		edtEstimativa = new JTextField("0", 6);
		edtEstimativa.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				
				edtEstimativa.selectAll();
				
			}
		});
		edtEstimativa.setToolTipText("Tempo Previsto para execu\u00E7\u00E3o em Horas fechadas");
		edtEstimativa.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPane.add(edtEstimativa);

		JLabel lblUtilizado = new JLabel("Utilizado:");
		lblUtilizado.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(lblUtilizado);

		edtUtilizado = new JTextField("0", 6);
		edtUtilizado.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
			
				edtUtilizado.selectAll();
			
			}
			@Override
			public void focusLost(FocusEvent arg0) {
			
				validaPercentualDesvio();
							
			}
		});
		edtUtilizado.setToolTipText("Tempo Utilizado para execu\u00E7\u00E3o em Horas Fechadas");
		edtUtilizado.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPane.add(edtUtilizado);

		JLabel lblDesvio = new JLabel("Perc. Desvio: ");
		lblDesvio.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(lblDesvio);

		edtDesvio = new JTextField("0", 6);
		edtDesvio.setToolTipText("Percentual de Desvio do Tempo Previsto e Utilizado");
		edtDesvio.setFont(new Font("Tahoma", Font.PLAIN, 18));
		edtDesvio.setEditable(false);
		contentPane.add(edtDesvio);

		JLabel lblFinalizada = new JLabel("Concluída:");
		lblFinalizada.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(lblFinalizada);

		chkFinalizado = new JCheckBox("", false);
		chkFinalizado.setFont(new Font("Tahoma", Font.PLAIN, 18));
		chkFinalizado.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(chkFinalizado);

		return contentPane;
	}

	private JPanel montaBotoes() {

		contentPane = new JPanel();
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER));

		JButton button = new JButton("Salvar");
		button.setFont(new Font("Tahoma", Font.PLAIN, 20));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (!validaTarefa(true)) 
					edtCodigo.grabFocus();
				else
					salvaTarefa();
			}
		});
		contentPane.add(button);

		JButton button_2 = new JButton("Excluir");
		button_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				removeTarefa();

			}
		});
		contentPane.add(button_2);

		JButton button_4 = new JButton("Listar");
		button_4.setFont(new Font("Tahoma", Font.PLAIN, 20));
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				listaTarefas();
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

	private void validaCodigo() {

		Long varLong = new Long(0);
		if (edtCodigo.getText().isEmpty()) {
			limparCampos(false);
			varLong = new Long(objTarefaRegras.getTarefaPk());
			edtCodigo.setText(varLong.toString());
		} else {
			varLong = new Long(edtCodigo.getText());
			carregarTarefa(varLong);
		}

	}

	private void carregarTarefa(long varTarefa) {

		if (!objTarefaRegras.carregaTarefa(varTarefa)) {
			JOptionPane.showMessageDialog(null, "Atenção tarefa inexistente !");
			limparCampos(true);
		} else {
			edtDescricao.setText(objTarefaRegras.getNm_tarefa());
			edtEstimativa.setText(String.valueOf(objTarefaRegras.getTempo_estimado()));
			edtUtilizado.setText(String.valueOf(objTarefaRegras.getTempo_utilizado()));
			edtDesvio.setText(String.valueOf(objTarefaRegras.getPercentual_desvio()));
			chkFinalizado.setSelected(objTarefaRegras.getFinalizada().equals("S"));
			chkPausada.setSelected(objTarefaRegras.getPausada().equals("S"));

			if (objUsuarioRegras.carregaUsuario(objTarefaRegras.getCd_usuario())) {	
				Usuario item;
				for (int i = 0; i < comboUsuario.getItemCount(); i++)
		        {
		            item = (Usuario) comboUsuario.getItemAt(i);
		            if (item.getCd_usuario() == objUsuarioRegras.getCd_usuario())
		            {
		            	comboUsuario.setSelectedIndex(i);
		                break;
		            }
		        }
			}
		}
	}

	private boolean validaTarefa(boolean mostraMensagem) {

		boolean ok = false;

		ok = ((!edtCodigo.getText().isEmpty()) && (!edtDescricao.getText().isEmpty())
				&& (comboUsuario.getSelectedItem() != null) && (!edtEstimativa.getText().isEmpty()));		
		
		if ((!ok) && (mostraMensagem)) {
			JOptionPane.showMessageDialog(null,
					"Atenção complete os dados da Tarefa: "
					+ "\n \n" + "Código, Descrição, Usuário e Tempo Estimado são campos obrigatórios !");
			return ok;
		}

		if ((ok) && (chkFinalizado.isSelected()))
			ok = ((! edtEstimativa.getText().isEmpty() && (! edtEstimativa.getText().equals("0") )) && 
				  ((! edtUtilizado.getText().isEmpty()) && (! edtUtilizado.getText().equals("0"))));

		if ((!ok) && (mostraMensagem)) {
			JOptionPane.showMessageDialog(null,	"Atenção complete os dados da Tarefa Finalizada: "
											  + " \n" + "Tempo Estimado e Utilizado são campos obrigatórios !");
		}
		
		return ok;

	}

	private void salvaTarefa() {

		objTarefaRegras.setId_geral(Long.parseLong(edtCodigo.getText()));
		objTarefaRegras.setNm_tarefa(edtDescricao.getText());
		objTarefaRegras.setTempo_estimado(Integer.parseInt(edtEstimativa.getText()));
		objTarefaRegras.setTempo_utilizado(Integer.parseInt(edtUtilizado.getText()));
		objTarefaRegras.setPercentual_desvio(Float.parseFloat(edtDesvio.getText()));
		
		Usuario objTemp = (Usuario) comboUsuario.getSelectedItem();
		objTarefaRegras.setCd_usuario(objTemp.getCd_usuario());
		
		if (chkFinalizado.isSelected())
			objTarefaRegras.setFinalizada("S");
		else
			objTarefaRegras.setFinalizada("N");
		
		if (chkPausada.isSelected())
			objTarefaRegras.setPausada("S");
		else
			objTarefaRegras.setPausada("N");	

		if (objTarefaRegras.salvaTarefa()) {
			JOptionPane.showMessageDialog(null, "Tarefa Salva com Sucesso !");
			limparCampos(true);
		} else
			JOptionPane.showMessageDialog(null, "Não foi possível salvar a Tarefa !");		
				
	}

	private void removeTarefa() {

		if (!validaTarefa(false))
			JOptionPane.showMessageDialog(null, "Atenção selecione a Tarefa a Excluir !");
		else {
			if (JOptionPane.showConfirmDialog(null, "Atenção Deseja Excluir a Tarefa ?", "Confirmação", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				if (objTarefaRegras.removeTarefa()) {
					JOptionPane.showMessageDialog(null, "Tarefa removida com Sucesso !");
					limparCampos(true);
				} else
					JOptionPane.showMessageDialog(null, "Não foi possível excluir a Tarefa !");
		}
	}

	private void listaTarefas() {

		objAcao.executeAction(RequestDispatcher.ActionName.LISTA_TAREFAS);

	}

	private void limparCampos(boolean validaFoco) {

		edtCodigo.setText("");
		edtDescricao.setText("");
		edtDesvio.setText("0");
		edtEstimativa.setText("0");
		edtUtilizado.setText("0");
		chkFinalizado.setSelected(false);
		chkPausada.setSelected(false);
		comboUsuario.setSelectedItem(null);
		
		if (validaFoco)
			edtCodigo.grabFocus();
	}
		
	private void validaPercentualDesvio() {
		
		if ((! edtEstimativa.getText().isEmpty()) && 
			(! edtUtilizado.getText().isEmpty())) {
			
			try {
					
				edtDesvio.setText(String.valueOf(objTarefaRegras.percentualDesvio(Integer.parseInt(edtEstimativa.getText()), 
																				  Integer.parseInt(edtUtilizado.getText()))));
					
			}
			catch (Exception ex) {
					
				JOptionPane.showMessageDialog(null, "Exceção ao identificar o % Desvio. Mensagem: " + ex.getMessage());
				ex.printStackTrace();
					
				edtDesvio.setText("0");		

			}	
		}
	}

	public void finalize() {

		if (objTarefaRegras != null) {
			objTarefaRegras = null;
		}

		if (objUsuarioRegras != null) {
			objUsuarioRegras = null;
		}

	}

}