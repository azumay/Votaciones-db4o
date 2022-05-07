package view;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import model.Municipi;
import java.awt.Color;
import javax.swing.JButton;

public class VentanaMunicipi extends JFrame {


	ArrayList <Municipi> municipi;

	private JTable table;
	private DefaultTableModel dtm;
	private JPanel panel;


	public VentanaMunicipi() {
		setSize(600, 343);
		setTitle("Consulta de Municipis");
		setResizable(false);

		Dimension sizeH, sizeW;

		sizeH = Toolkit.getDefaultToolkit().getScreenSize();
		sizeW = getSize();

		setLocation(((sizeH.width - sizeW.width) / 2), (sizeH.height - sizeW.height) / 2);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panel);
		panel.setLayout(null);

		JLabel lb1Titulo = new JLabel("LLISTAT DE MUNICIPIS");
		lb1Titulo.setForeground(Color.WHITE);
		lb1Titulo.setBackground(new Color(34, 46, 53));
		lb1Titulo.setOpaque(true);
		lb1Titulo.setHorizontalAlignment(SwingConstants.CENTER);
		lb1Titulo.setFont(new Font("Dialog", Font.PLAIN, 25));
		lb1Titulo.setBounds(0, 0, 600, 50);
		panel.add(lb1Titulo);

		
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 61, 550, 208);
		
			
		panel.add(scrollPane);

		table = new JTable();

		
		dtm = new DefaultTableModel();
		
		table.setModel(dtm);
		
		
		dtm.addColumn("MUNICIPI");
		dtm.addColumn("PROVINCÍA");
		
		
		
		
		scrollPane.setViewportView(table);
		
		JButton btnTancar = new JButton("Tancar");
		btnTancar.setForeground(Color.WHITE);
		btnTancar.setBackground(new Color(153, 0, 0));
		btnTancar.setBounds(241, 281, 117, 25);
		panel.add(btnTancar);
		
		btnTancar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});

	}

	public void rellenarTabla() {

		ArrayList<Municipi> listaMunicipis = this.municipi;
		
		
		for (Municipi municipi : listaMunicipis) {
			Object[] fila = new Object[2];
			
			fila[0]=municipi.getNom();
			fila[1]=municipi.getProvincia();
			
			dtm.addRow(fila);
			
		}

	}


	public void setMunicipi(ArrayList<Municipi> municipi) {
		this.municipi = municipi;
	}

	public ArrayList<Municipi> getMunicipi() {
		return municipi;
	}
	
}
