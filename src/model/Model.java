package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

/* DB4O */
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import controller.Controller;
import exception.ErrorVotacions;


public class Model {

	private String db40;
	private String dataPath;
	private Controller controllerHelper;
	private ObjectContainer db;

	HashMap<String, Municipi> newMunicipi;
	HashMap<String, Partit> newPartit;

	public Model() {
		this.db40 = "Votacions.db4o";
		this.dataPath = "lib/votacions.csv";

		new File(db40).delete();
		this.db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), db40);

		this.newMunicipi = new HashMap<String, Municipi>();
		this.newPartit = new HashMap<String, Partit>();
		this.controllerHelper = new Controller();
		lecturaFichero();
	}

	public void lecturaFichero() {
		try {
		
			// Empezamos la lectura del CSV
			FileReader readFile = new FileReader(this.dataPath);
			BufferedReader buffer = new BufferedReader(readFile);
			String linea;

			while ((linea = buffer.readLine()) != null) {
				String values[] = linea.split(";");

				// Omitimos el titulo del CSV con la primera columna
				if (!values[0].equalsIgnoreCase("PROVÍNCIA")) {

					// Obtenemos los valores de cada linea...
					String municipi = "";
					String provincia = values[0].trim();
					if (values[2].trim() != "") {
						municipi = controllerHelper.formeteoTexto(values[2].trim());
					}
					String siglesPartit = values[3].trim().toUpperCase();
					String nomPartit = values[4].trim();

					if (municipi.equals("") && provincia.equals("")) {

						/* CONTROL DE PARTIT */
						Partit objPartit = null;

						if (!this.newPartit.containsKey(siglesPartit)) {
							objPartit = new Partit(siglesPartit, nomPartit);
							this.newPartit.put(siglesPartit, objPartit);
							db.store(this.newPartit);

						} else {
							objPartit = this.newPartit.get(siglesPartit);
						}

					} else {

						String vots = values[5].trim();
						if (vots.equals("-")) {
							vots = "0";
						}

						String percent = values[6].trim().replaceAll("\\,", ".");
						if (percent.equals("-")) {
							percent = "0";
						}

						/* CONTROL DE PARTIT2 para pasarselo a Municipi */
						Partit objPartit = null;

						if (!this.newPartit.containsKey(siglesPartit)) {
							objPartit = new Partit(siglesPartit, nomPartit);
							this.newPartit.put(siglesPartit, objPartit);
							db.store(this.newPartit);

						} else {
							objPartit = this.newPartit.get(siglesPartit);
						}

						/* CONTROL DE MUNICIPIS */
						Municipi objMunicipi = null;

						if (!this.newMunicipi.containsKey(municipi)) {
							objMunicipi = new Municipi(municipi, provincia);
							this.newMunicipi.put(municipi, objMunicipi);
							db.store(this.newMunicipi);

						} else {
							objMunicipi = this.newMunicipi.get(municipi);
						}

						Resultat newResult = new Resultat(objPartit, objMunicipi, Integer.parseInt(vots),
								Double.parseDouble(percent));

						ArrayList<Resultat> aux = objMunicipi.getResultats();

						aux.add(newResult);

						db.store(newResult);

					}

				}
			}
			buffer.close();
			readFile.close();

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error al procesar el CSV", e.getMessage(), JOptionPane.WARNING_MESSAGE);
			
		}

	}

	/**
	 * 1. Llistat de tots els partits. OK
	 */

	public ArrayList<Partit> showPartits() {
		Partit partit = new Partit();
		ObjectSet<Partit> result = this.db.queryByExample(partit);

		ArrayList<Partit> plantilla = new ArrayList<Partit>();
		;

		while (result.hasNext()) {
			Partit interador = new Partit();
			Partit aux = result.next();

			interador.setNom(aux.getNom());
			interador.setSigles(aux.getSigles());

			plantilla.add(interador);

		}
		return plantilla;
	}

	/**
	 * 2. Llistat de tots els municipis. OK
	 */
	public ArrayList<Municipi> showMunicipis() {
		Municipi muni = new Municipi();
		ObjectSet<Municipi> result = this.db.queryByExample(muni);

		ArrayList<Municipi> plantilla = new ArrayList<Municipi>();
		;

		while (result.hasNext()) {
			Municipi iterador = new Municipi();
			Municipi aux = result.next();

			iterador.setNom(aux.getNom());
			iterador.setProvincia(aux.getProvincia());

			plantilla.add(iterador);
		}

		return plantilla;
	}

	/**
	 * 3. Resultats per partit en un municipi donat. OK
	 */
	public ArrayList<Municipi> showPartitByMunicipi(Municipi nom) {

		ArrayList<Municipi> plantilla = new ArrayList<Municipi>();

		try {
			ObjectSet<Municipi> result = this.db.queryByExample(nom);
			if (result.size() == 0) {
				throw new ErrorVotacions("No existen datos con ese municipio", "E301");
			}

			Municipi aux = result.get(0);
			Municipi iterador = new Municipi();

			for (int x = 0; x < result.size(); x++) {

				iterador.setNom(aux.getNom());
				iterador.setProvincia(aux.getProvincia());
				iterador.setResultats(result.get(x).getResultats());

				plantilla.add(iterador);
			}

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null, f, "Exception detected", JOptionPane.WARNING_MESSAGE);
		}
		return plantilla;
	}

	/**
	 * 4. Resultats per municipi d'un partit donat. OK
	 */

	public ArrayList<Resultat> showPartitByPartit(Partit sigles) {

		ArrayList<Resultat> plantilla = new ArrayList<Resultat>();

		try {

			Resultat result = new Resultat(sigles, null, 0, 0);

			ObjectSet<Resultat> queryResult = db.queryByExample(result);

			if (queryResult.size() == 0) {
				throw new ErrorVotacions("No existen datos con ese partido", "E401");
			}

			for (int x = 0; x < queryResult.size(); x++) {
				Resultat iterador = new Resultat();

				Resultat auxResultat = queryResult.next();

				if (!queryResult.get(x).getMunicipi().getNom().equals("")) {

					iterador.setMunicipi(auxResultat.getMunicipi());
					iterador.setPartit(queryResult.get(x).getPartit());
					iterador.setVots(queryResult.get(x).getVots());
					iterador.setPercent(queryResult.get(x).getPercent());
					plantilla.add(iterador);

				}

			}
		} catch (Exception f) {
			JOptionPane.showMessageDialog(null, f, "Exception detected", JOptionPane.WARNING_MESSAGE);
		}
		return plantilla;
	}

	/**
	 * 5. Resultats per partit en una província donada.
	 */

	public ArrayList<Resultat> showResultByProvincia(Municipi objMunicipi) {

		ArrayList<Resultat> plantilla = new ArrayList<Resultat>();
		try {
			Resultat result = new Resultat(null, objMunicipi, 0, 0);

			ObjectSet<Resultat> queryResult = db.queryByExample(result);
			if (queryResult.size() == 0) {
				throw new ErrorVotacions("No existen datos con esa provincia", "E501");
			}

			for (int x = 0; x < queryResult.size(); x++) {
				Resultat iterador = new Resultat();

				Resultat auxResultat = queryResult.next();

				if (!queryResult.get(x).getMunicipi().getNom().equals("")) {

					iterador.setMunicipi(auxResultat.getMunicipi());
					iterador.setPartit(queryResult.get(x).getPartit());
					iterador.setVots(queryResult.get(x).getVots());
					iterador.setPercent(queryResult.get(x).getPercent());
					plantilla.add(iterador);

				}

			}
		} catch (Exception f) {
			JOptionPane.showMessageDialog(null, f, "Exception detected", JOptionPane.WARNING_MESSAGE);
		}
		return plantilla;
	}

}