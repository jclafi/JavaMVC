package view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import hibernate.HbmSessionFactory;
import java.awt.Font;
import controller.RequestDispatcher;

public class FormularioPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private RequestDispatcher objAcao; 

	public RequestDispatcher getObjAcao() {
		return objAcao;
	}
	public void setObjAcao(RequestDispatcher objAcao) {
		this.objAcao = objAcao;
	}

	public FormularioPrincipal() {
		
		objAcao = new RequestDispatcher();		
		setTitle("Formulário Principal");		
		setBounds(100, 100, 300, 300);			
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setResizable(false);				
		adicionaComponentes();		
		adicionaMainMenu();
		toFront();	
	}
	
	private void adicionaComponentes() {
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new GridLayout(5, 1));
		
		JButton btnUsuario = new JButton("Cadastro Usuários");
		btnUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				chamaCadastroUsuario();
				
			}
		});
		btnUsuario.setToolTipText("Permite cadastrar usu\u00E1rios que executam tarefas");
		btnUsuario.setFont(new Font("Tahoma", Font.BOLD, 20));
		contentPane.add(btnUsuario);
		
		JButton btnListaUsuario = new JButton("Lista de Usuários");
		btnListaUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				chamaListaUsuario();
				
			}
		});
		btnListaUsuario.setToolTipText("Permite listar usu\u00E1rios que executam tarefas");
		btnListaUsuario.setFont(new Font("Tahoma", Font.BOLD, 20));
		contentPane.add(btnListaUsuario);

		JButton btnTarefa = new JButton("Cadastro Tarefa");
		btnTarefa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				chamaCadastroTarefa();
				
			}
		});
		btnTarefa.setToolTipText("Permite cadastrar Tarefas para Usu\u00E1rios");
		btnTarefa.setFont(new Font("Tahoma", Font.BOLD, 20));
		contentPane.add(btnTarefa);
		
		JButton btnListaTarefas = new JButton("Lista de Tarefas");
		btnListaTarefas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				chamaListaTarefas();
				
			}
		});
		btnListaTarefas.setToolTipText("Permite listar Tarefas de Usuários");
		btnListaTarefas.setFont(new Font("Tahoma", Font.BOLD, 20));
		contentPane.add(btnListaTarefas);

		JButton btnSair = new JButton("Sair");
		btnSair.setToolTipText("Fecha o Sistema");
		btnSair.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				finalizarSistema();
				
			}
		});
		contentPane.add(btnSair);
		
		setContentPane(contentPane);		
	}
	
	private void adicionaMainMenu() {
		//Cria a Barra de Menu e Item
		//===========================================			
		JMenuBar barra = new JMenuBar();			
		JMenu itemCadastros = new JMenu("Cadastros");
		JMenu itemListas = new JMenu("Listas");		
		JMenu itemSistema = new JMenu("Sistema");		
		barra.add(itemCadastros);
		barra.add(itemListas);
		barra.add(itemSistema);	
		
		JMenuItem itemLista_User = new JMenuItem("Lista de Usuários");
		itemLista_User.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				chamaListaUsuario();
			
			}
		});
		itemListas.add(itemLista_User);
		itemListas.addSeparator();
		JMenuItem itemLista_Task = new JMenuItem("Lista de Tarefas");
		itemLista_Task.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				chamaListaTarefas();
			
			}
		});
		itemListas.add(itemLista_Task);

		JMenuItem itemSub_Item = new JMenuItem("Cadastro de Usuários");
		itemSub_Item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				chamaCadastroUsuario();
			
			}
		});
		itemCadastros.add(itemSub_Item);
		itemCadastros.addSeparator();
		JMenuItem itemSub_Item_2 = new JMenuItem("Cadastro de Tarefas");
		itemSub_Item_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				chamaCadastroTarefa();
			
			}
		});
		itemCadastros.add(itemSub_Item_2);
		
		JMenuItem itemSub_Item_3 = new JMenuItem("About");
		itemSub_Item_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				JOptionPane.showMessageDialog(null, "Desenvolvedor: Julio Cesar Ledo Amaral Filho \n \n Projeto: Aplicação Beta SoftExpert \n "+
				"Plataforma: Java 8 \n Persistência: Hibernate 5.1 \n Design: Modal View Controller \n Database: PostgreSQL 9.2.4 64-bit");
			
			}
		});
		itemSistema.add(itemSub_Item_3);
		itemSistema.addSeparator();
		JMenuItem itemSub_Item_1 = new JMenuItem("Sair");
		itemSub_Item_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				finalizarSistema();
				
			}
		});
		itemSistema.add(itemSub_Item_1);
			
		setJMenuBar(barra);
	}
	
	private void chamaCadastroUsuario() {
		
		objAcao.executeAction(RequestDispatcher.ActionName.CADASTRO_USUARIO);
	
	}
	
	private void chamaCadastroTarefa() {
		
		objAcao.executeAction(RequestDispatcher.ActionName.CADASTRO_TAREFA);
	
	}
	
	private void chamaListaUsuario() {

		objAcao.executeAction(RequestDispatcher.ActionName.LISTA_USUARIOS);
		
	}
	
	private void chamaListaTarefas() {

		objAcao.executeAction(RequestDispatcher.ActionName.LISTA_TAREFAS);	
		
	}
	
	private void finalizarSistema() {
			HbmSessionFactory.closeFactory();
			System.gc();
			System.exit(0);
	}
	
	public void finalize() {
		if (objAcao != null)
			objAcao = null;
	}

}