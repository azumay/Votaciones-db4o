package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.util.Iterator;

/* IMPORT MODELOS */
import model.Model;
import model.Municipi;
import model.Partit;
import model.Resultat;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class VotacionsGUI {

	private JFrame frame;
	private JTable table;
	private JTable tablaVotacions;
	private Model model;
	private JTextField inputMunicipi;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VotacionsGUI window = new VotacionsGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.toString(), "Error:", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VotacionsGUI() {
		generarGui();
		this.model = new Model();

		// Municipi muni = new Municipi("Avinyonet del Penedès", null);
		// model.showPartitByMunicipi(muni);

		//Partit partido = new Partit("VOX", null);
		// model.showPartitByPartit(partido);

		// Municipi muni = new Municipi(null, "Barcelona");
		// model.showResultByProvincia(muni);


	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void generarGui() {

		/* VENTANA PRINCIPAL */
		frame = new JFrame();
		frame.setTitle("Votacions");
		frame.setBounds(100, 100, 700, 465);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setBackground(new Color(34, 46, 53));
		frame.getContentPane().setLayout(null);

		/* POSICIONAMIENTO AUTO DE LA VENTANA */

		Dimension sizeH, sizeW;

		sizeH = Toolkit.getDefaultToolkit().getScreenSize();
		sizeW = this.frame.getSize();

		this.frame.setLocation(((sizeH.width - sizeW.width) / 2), (sizeH.height - sizeW.height) / 2);

		/* BTN CONSULTA PARTITS*/
		JButton btnMostrar = new JButton("Mostrar Partits");
		btnMostrar.setBounds(26, 42, 183, 42);
		btnMostrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ArrayList<Partit> listaPartits = new ArrayList<Partit>();
				listaPartits = model.showPartits();

				VentanaPartit windowPartits = new VentanaPartit();
				windowPartits.setPartit(listaPartits);
				windowPartits.rellenarTabla();
				windowPartits.setVisible(true);

			}
		});
		frame.getContentPane().add(btnMostrar);
		
		/* BTN CONSULTA PARTITS*/
		JButton btnMostrarMunicipis = new JButton("Mostrar Municipis");
		btnMostrarMunicipis.setBounds(26, 105, 183, 42);
		btnMostrarMunicipis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ArrayList<Municipi> listaMunicipis = new ArrayList<Municipi>();
				listaMunicipis = model.showMunicipis();

				VentanaMunicipi windowMunicipi = new VentanaMunicipi();
				windowMunicipi.setMunicipi(listaMunicipis);
				windowMunicipi.rellenarTabla();
				windowMunicipi.setVisible(true);

			}
		});
		frame.getContentPane().add(btnMostrarMunicipis);
		
		/* ZONA DE CONSULTA RESULTAT PER MUNICIPI DONAT*/
		
		JPanel panelConsulta3 = new JPanel();
		panelConsulta3.setBounds(26, 244, 229, 149);
		frame.getContentPane().add(panelConsulta3);
		panelConsulta3.setLayout(null);
		
		inputMunicipi = new JTextField();
		inputMunicipi.setBounds(27, 55, 164, 32);
		panelConsulta3.add(inputMunicipi);
		inputMunicipi.setColumns(10);
		
	
		JLabel lblBuscarResultatsPer = new JLabel();
		lblBuscarResultatsPer.setForeground(new Color(34, 46, 53));
		lblBuscarResultatsPer.setOpaque(true);
		lblBuscarResultatsPer.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscarResultatsPer.setFont(new Font("Dialog", Font.BOLD, 15));
		
		lblBuscarResultatsPer.setText("<html><p style=\"text-align:center\">Buscar resultats <br>per municipi");
		lblBuscarResultatsPer.setToolTipText("");
		lblBuscarResultatsPer.setBounds(27, 12, 164, 41);
		panelConsulta3.add(lblBuscarResultatsPer);
		
		JButton cercaResultatsPerMunicipi = new JButton("Cerca");
		cercaResultatsPerMunicipi.setBounds(27, 99, 164, 25);
		panelConsulta3.add(cercaResultatsPerMunicipi);
		cercaResultatsPerMunicipi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ArrayList<Resultat> listaResultats = new ArrayList<Resultat>();
				Municipi nomMunicipi = new Municipi(inputMunicipi.getText(), null);
				
				listaResultats = model.showPartitByMunicipi(nomMunicipi);
				VentanaResultatMunicipi windowResultMuni = new VentanaResultatMunicipi();
				windowResultMuni.setResultat(listaResultats);
				windowResultMuni.rellenarTabla();
				windowResultMuni.setVisible(true);
				
				
				
			}
		});
		cercaResultatsPerMunicipi.setFont(new Font("Dialog", Font.BOLD, 12));

		// Generar el menu superior
		JMenuBar barra = new JMenuBar();
		JMenu programa = new JMenu("Menu");
		JMenuItem acerca = new JMenuItem("Acerca de...");
		JMenuItem salir = new JMenuItem("Salir");

		this.frame.setJMenuBar(barra);
		barra.add(programa);
		programa.add(acerca);
		programa.add(salir);
		programa.addSeparator();
		programa.setMnemonic('P');

		/* ACIONES DEL MENU */

		salir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		acerca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Aplicación para consultar resultados de votaciones mediante un CSV. Autor Xavi Yamuza", "Acerca de...",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});

	}
	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
}
