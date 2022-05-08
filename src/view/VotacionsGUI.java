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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* IMPORT MODELOS */
import model.Model;
import model.Municipi;
import model.Partit;
import model.Resultat;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import controller.Controller;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class VotacionsGUI {

	private JFrame frame;
	private JTable table;
	private JTable tablaVotacions;
	private Model model;
	private JTextField inputMunicipi;
	private Controller controllerHelper;
	private JTextField inputPartit;
	private JTextField inputProvincia;

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
		this.controllerHelper = new Controller();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void generarGui() {

		/* VENTANA PRINCIPAL */
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
		JButton btnMostrar = new JButton("Mostrar Partits");
		btnMostrar.setBounds(488, 224, 183, 42);
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
		JButton btnMostrarMunicipis = new JButton("Mostrar Municipis");
		btnMostrarMunicipis.setBounds(185, 224, 183, 42);
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
		lblbuscarResultatsper.setText("<html><p style=\"text-align:center\">Buscar resultats <br>per partit");
		lblbuscarResultatsper.setOpaque(true);
		lblbuscarResultatsper.setHorizontalAlignment(SwingConstants.CENTER);
		lblbuscarResultatsper.setForeground(new Color(34, 46, 53));
		lblbuscarResultatsper.setFont(new Font("Dialog", Font.BOLD, 15));
		lblbuscarResultatsper.setBounds(27, 12, 164, 41);
		panelConsulta4.add(lblbuscarResultatsper);

		JButton cercaResultatsPerPartit = new JButton("Cerca");
		cercaResultatsPerPartit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Creamos una lista de municipis donde recibiremos el resultado de Model
				ArrayList<Resultat> listaResultats = new ArrayList<Resultat>();

				// Obtenemos el valor que nos escribe el usuario y lo ponemos en MAYUSCULAS
				String inputFormateado = inputPartit.getText().toUpperCase();

				// Creamos un objeto con el nombre de municipio que nos habían pasado
				Partit nomMunicipi = new Partit(inputFormateado, null);

				// Guardamos el resultado de la busqueda en la ArrayList
				listaResultats = model.showPartitByPartit(nomMunicipi);

				// Cramos una vista de resultados en el caso de obtener-lo
				VentanaResultatPartit windowResultPartit = new VentanaResultatPartit();

				// Le pasamos la lista de resultados para procesarlo uno a uno en la tabla
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
		lblbuscarResultatsper_1.setText("<html><p style=\"text-align:center\">Buscar resultats <br>per provincía");
		lblbuscarResultatsper_1.setOpaque(true);
		lblbuscarResultatsper_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblbuscarResultatsper_1.setForeground(new Color(34, 46, 53));
		lblbuscarResultatsper_1.setFont(new Font("Dialog", Font.BOLD, 15));
		lblbuscarResultatsper_1.setBounds(27, 12, 164, 41);
		panelConsulta5.add(lblbuscarResultatsper_1);

		JButton cercaResultatsPerProvincia = new JButton("Cerca");
		cercaResultatsPerProvincia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Creamos una lista de resultados donde recibiremos el resultado de Model
				ArrayList<Resultat> listaResultats = new ArrayList<Resultat>();

				// Obtenemos el valor que nos escribe el usuario y lo formateamos
				String inputFormateado = controllerHelper.upperText(inputProvincia.getText());

				// Creamos un objeto con el nombre de la provincia que nos han pasado
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
		lblNewLabel.setIcon(new ImageIcon("/home/xyamuza/eclipse-workspace/Act2/src/img/banner.jpg"));
		lblNewLabel.setBounds(-72, 0, 945, 190);
		frame.getContentPane().add(lblNewLabel);

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
