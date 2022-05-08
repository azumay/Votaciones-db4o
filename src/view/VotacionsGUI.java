package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;

/* IMPORT MODELOS */
import model.Model;
import model.Municipi;
import model.Partit;
import model.Resultat;

/* COLECCIONES */
import java.util.ArrayList;


import controller.Controller;



public class VotacionsGUI {

	private JFrame frame;
	private Model model;
	private JTextField inputMunicipi;
	private Controller controllerHelper;
	private JTextField inputPartit;
	private JTextField inputProvincia;

	

	/**
	 * Create the application.
	 */
	public VotacionsGUI() {
		generarGui();
		this.model = new Model();
		this.controllerHelper = new Controller();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void generarGui() {

		/* VENTANA PRINCIPAL - TAMAÑO FIJO*/
		frame = new JFrame();
		frame.setTitle("Votacions");
		frame.setBounds(100, 100, 850, 565);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setBackground(new Color(34, 46, 53));
		frame.getContentPane().setLayout(null);

		/* POSICIONAMIENTO AUTO DE LA VENTANA */

		Dimension sizeH, sizeW;

		sizeH = Toolkit.getDefaultToolkit().getScreenSize();
		sizeW = this.frame.getSize();

		this.frame.setLocation(((sizeH.width - sizeW.width) / 2), (sizeH.height - sizeW.height) / 2);

		/* BTN CONSULTA PARTITS */
		
		JButton btnMostrar = new JButton("<html><p style=\"text-align:center\">Mostrar <br>Partits</p></html>");
		btnMostrar.setIcon(new ImageIcon(VotacionsGUI.class.getResource("/img/partidos.png")));
		btnMostrar.setBounds(488, 224, 160, 42);
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

		/* BTN CONSULTA PARTITS */
		
		JButton btnMostrarMunicipis = new JButton("<html><p style=\"text-align:center\">Mostrar <br>Municipis</p></html>");
		btnMostrarMunicipis.setIcon(new ImageIcon(VotacionsGUI.class.getResource("/img/municipi.png")));
		btnMostrarMunicipis.setBounds(208, 224, 160, 42);
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

		/* ZONA DE CONSULTA RESULTAT PER MUNICIPI DONAT */

		JPanel panelConsulta3 = new JPanel();
		panelConsulta3.setBounds(25, 334, 229, 149);
		frame.getContentPane().add(panelConsulta3);
		panelConsulta3.setLayout(null);

		inputMunicipi = new JTextField();
		inputMunicipi.setBounds(27, 54, 164, 32);
		panelConsulta3.add(inputMunicipi);
		inputMunicipi.setColumns(10);

		JButton cercaResultatsPerMunicipi = new JButton("Cerca");
		cercaResultatsPerMunicipi.setIcon(new ImageIcon(VotacionsGUI.class.getResource("/img/lupa.png")));
		cercaResultatsPerMunicipi.setBounds(27, 98, 164, 25);
		panelConsulta3.add(cercaResultatsPerMunicipi);
		cercaResultatsPerMunicipi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Creamos una lista de municipis donde recibiremos el resultado de Model
				ArrayList<Municipi> listaResultats = new ArrayList<Municipi>();

				// Ponemos Mayuscula cada palabra para evitar problemas al buscar en la DB
				Controller controllerHelper = new Controller();

				// Obtenemos el valor que nos escribe el usuario y lo formateamos
				String inputFormateado = controllerHelper.upperText(inputMunicipi.getText());

				// Creamos un objeto con el nombre de municipio que nos habían pasado
				Municipi nomMunicipi = new Municipi(inputFormateado, null);

				// Guardamos el resultado de la busqueda en la ArrayList
				listaResultats = model.showPartitByMunicipi(nomMunicipi);

				// Cramos una vista de resultados en el caso de obtener-lo
				VentanaResultatMunicipi windowResultMuni = new VentanaResultatMunicipi();

				// Le pasamos la lista de resultados para procesarlo uno a uno en la tabla
				windowResultMuni.setResultat(listaResultats);
				windowResultMuni.rellenarTabla();
				windowResultMuni.setVisible(true);

			}
		});
		cercaResultatsPerMunicipi.setFont(new Font("Dialog", Font.BOLD, 12));
		
		JLabel lblbuscarResultatsper_3 = new JLabel();
		lblbuscarResultatsper_3.setToolTipText("");
		lblbuscarResultatsper_3.setText("<html><p style=\"text-align:center\">Buscar resultats <br>per MUNICIPI");
		lblbuscarResultatsper_3.setOpaque(true);
		lblbuscarResultatsper_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblbuscarResultatsper_3.setForeground(new Color(34, 46, 53));
		lblbuscarResultatsper_3.setFont(new Font("Dialog", Font.BOLD, 15));
		lblbuscarResultatsper_3.setBounds(27, 12, 164, 41);
		panelConsulta3.add(lblbuscarResultatsper_3);

		/* ZONA DE CONSULTA RESULTAT PER PARTIT DONAT */

		JPanel panelConsulta4 = new JPanel();
		panelConsulta4.setLayout(null);
		panelConsulta4.setBounds(309, 334, 229, 149);
		frame.getContentPane().add(panelConsulta4);

		inputPartit = new JTextField();
		inputPartit.setColumns(10);
		inputPartit.setBounds(27, 55, 164, 32);
		panelConsulta4.add(inputPartit);

		JLabel lblbuscarResultatsper = new JLabel();
		lblbuscarResultatsper.setToolTipText("");
		lblbuscarResultatsper.setText("<html><p style=\"text-align:center\">Buscar resultats <br>per PARTIT");
		lblbuscarResultatsper.setOpaque(true);
		lblbuscarResultatsper.setHorizontalAlignment(SwingConstants.CENTER);
		lblbuscarResultatsper.setForeground(new Color(34, 46, 53));
		lblbuscarResultatsper.setFont(new Font("Dialog", Font.BOLD, 15));
		lblbuscarResultatsper.setBounds(27, 12, 164, 41);
		panelConsulta4.add(lblbuscarResultatsper);

		JButton cercaResultatsPerPartit = new JButton("Cerca");
		cercaResultatsPerPartit.setIcon(new ImageIcon(VotacionsGUI.class.getResource("/img/lupa.png")));
		cercaResultatsPerPartit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Creamos una lista de municipis donde recibiremos el resultado de Model
				ArrayList<Resultat> listaResultats = new ArrayList<Resultat>();

				// Obtenemos el valor que nos escribe el usuario y lo ponemos en MAYUSCULAS
				String inputFormateado = inputPartit.getText().toUpperCase();

				// Creamos un objeto con el nombre del partido que nos habían pasado
				Partit nomMunicipi = new Partit(inputFormateado, null);

				// Guardamos el resultado de la busqueda en la ArrayList
				listaResultats = model.showPartitByPartit(nomMunicipi);

				// Cramos una vista de resultados en el caso de obtener-lo
				VentanaResultatPartit windowResultPartit = new VentanaResultatPartit();

				// Le pasamos la lista de resultados para agregarlos uno a uno en la tabla
				windowResultPartit.setResultat(listaResultats);
				windowResultPartit.rellenarTabla();
				windowResultPartit.setVisible(true);

			}
		});
		cercaResultatsPerPartit.setFont(new Font("Dialog", Font.BOLD, 12));
		cercaResultatsPerPartit.setBounds(27, 99, 164, 25);
		panelConsulta4.add(cercaResultatsPerPartit);

		/* ZONA DE CONSULTA RESULTAT PER PROVINCIA DONAT */
		
		JPanel panelConsulta5 = new JPanel();
		panelConsulta5.setLayout(null);
		panelConsulta5.setBounds(585, 334, 229, 149);
		frame.getContentPane().add(panelConsulta5);

		inputProvincia = new JTextField();
		inputProvincia.setColumns(10);
		inputProvincia.setBounds(27, 55, 164, 32);
		panelConsulta5.add(inputProvincia);

		JLabel lblbuscarResultatsper_1 = new JLabel();
		lblbuscarResultatsper_1.setToolTipText("");
		lblbuscarResultatsper_1.setText("<html><p style=\"text-align:center\">Buscar resultats <br>per PROVINCÍA");
		lblbuscarResultatsper_1.setOpaque(true);
		lblbuscarResultatsper_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblbuscarResultatsper_1.setForeground(new Color(34, 46, 53));
		lblbuscarResultatsper_1.setFont(new Font("Dialog", Font.BOLD, 15));
		lblbuscarResultatsper_1.setBounds(27, 12, 164, 41);
		panelConsulta5.add(lblbuscarResultatsper_1);

		JButton cercaResultatsPerProvincia = new JButton("Cerca");
		cercaResultatsPerProvincia.setIcon(new ImageIcon(VotacionsGUI.class.getResource("/img/lupa.png")));
		cercaResultatsPerProvincia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Creamos una lista de resultados donde recibiremos el resultado de Model
				ArrayList<Resultat> listaResultats = new ArrayList<Resultat>();

				// Obtenemos el valor que nos escribe el usuario y lo formateamos
				String inputFormateado = controllerHelper.upperText(inputProvincia.getText());

				// Creamos un objeto con el nombre de la PROVINCIA que nos han pasado
				Municipi nomProvincia = new Municipi(null, inputFormateado);

				// Guardamos el resultado de la busqueda en la ArrayList
				listaResultats = model.showResultByProvincia(nomProvincia);

				// Cramos una vista de resultados en el caso de obtener-lo
				VentanaResultatProvincia windowResultProvincia = new VentanaResultatProvincia();

				// Le pasamos la lista de resultados para agregarlos uno a uno en la tabla
				windowResultProvincia.setResultat(listaResultats);
				windowResultProvincia.rellenarTabla();
				windowResultProvincia.setVisible(true);

			}
		});
		cercaResultatsPerProvincia.setFont(new Font("Dialog", Font.BOLD, 12));
		cercaResultatsPerProvincia.setBounds(27, 99, 164, 25);
		panelConsulta5.add(cercaResultatsPerProvincia);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(VotacionsGUI.class.getResource("/img/banner-App.png")));
		lblNewLabel.setBounds(0, -18, 997, 230);
		frame.getContentPane().add(lblNewLabel);

		// Generar el menu superior
		JMenuBar barra = new JMenuBar();
		JMenu programa = new JMenu("Menu");
		JMenuItem acerca = new JMenuItem("Acerca de...");
		acerca.setIcon(new ImageIcon(VotacionsGUI.class.getResource("/img/info.png")));
		JMenuItem salir = new JMenuItem("Salir");
		salir.setIcon(new ImageIcon(VotacionsGUI.class.getResource("/img/close.png")));

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
				JOptionPane.showMessageDialog(null,
						"Aplicación para consultar resultados de votaciones mediante un CSV. Autor Xavi Yamuza",
						"Acerca de...", JOptionPane.INFORMATION_MESSAGE);
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
