package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

public class Model {

	private String db40;
	private String dataPath;
	private File f;
	private ObjectContainer db;

	HashMap<String, Municipi> newMunicipi;
	HashMap<String, Partit> newPartit;

	public Model() {
		this.db40 = "BDOOVotacions.db4o";
		this.dataPath = "lib/votacions.csv";

		new File(db40).delete();
		this.db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), db40);

		this.newMunicipi = new HashMap<String, Municipi>();
		this.newPartit = new HashMap<String, Partit>();

		lecturaFichero();
	}

	public void lecturaFichero() {
		try {

			// Obrirem el fitxer i iniciarem el buffer per poder llegir aquest fitxer
			FileReader readFile = new FileReader(this.dataPath);
			BufferedReader buffer = new BufferedReader(readFile);
			String linea;

			while ((linea = buffer.readLine()) != null) {
				String values[] = linea.split(";");

				// Omitimos el titulo del csv
				if (!values[0].equalsIgnoreCase("PROVÍNCIA")) {

					// Obtenemos valres de cada linea...
					String provincia = values[0].trim();
					String municipi = values[2].trim();

					String siglesPartit = values[3].trim();
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

						/* CONTROL DE PARTIT */
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
			JOptionPane.showMessageDialog(null, e, "Exception detected", JOptionPane.WARNING_MESSAGE);
		}

	}

	public void comprovarMunicipi(Municipi m) {

		ObjectSet result = this.db.queryByExample(m);

		// System.out.println(result.size());
		while (result.hasNext()) {
			// System.out.println(result.next());
		}
		if (result.size() == 0) {
			this.db.store(m);
		}

	}

	/**
	 * 1. Llistat de tots els partits.
	 */

	public void showPartits() {
		Partit partit = new Partit();
		ObjectSet<Partit> result = this.db.queryByExample(partit);

		while (result.hasNext()) {
			Partit aux = result.next();
			System.out.println(aux.getNom());
			System.out.println(aux.getSigles());
		}
	}

	/**
	 * 2. Llistat de tots els municipis.
	 */
	public void showMunicipis() {
		Municipi muni = new Municipi();
		ObjectSet<Municipi> result = this.db.queryByExample(muni);

		while (result.hasNext()) {
			Municipi aux = result.next();
			System.out.println(aux.getNom());
			System.out.println(aux.getProvincia());
		}
	}

	/**
	 * 3. Resultats per partit en un municipi donat.
	 */
	public void showPartitByMunicipi(Municipi nom) {

		ObjectSet<Municipi> result = this.db.queryByExample(nom);

		while (result.hasNext()) {

			Municipi aux = result.next();

			ArrayList<Resultat> auxResult = aux.getResultats();

			for (int x = 0; x < aux.getResultats().size(); x++) {

				System.out.println(auxResult.get(x).getPartit().getSigles() + " " + auxResult.get(x).getVots());

			}

		}
	}

	/**
	 * 4. Resultats per municipi d'un partit donat.
	 */

	public void showPartitByPartit(Partit sigles) {

		Resultat result = new Resultat(sigles, null, 0, 0);

		ObjectSet<Resultat> queryResult = db.queryByExample(result);

		while (queryResult.hasNext()) {

			Resultat auxResultat = queryResult.next();

			System.out.println(auxResultat.getVots() + " " + auxResultat.getMunicipi().getNom());

		}
	}
	
	/**
	 * 5. Resultats per partit en una província donada. 
	 */

	public void showResultByProvincia(Municipi objMunicipi) {
		
		Resultat result = new Resultat(null, objMunicipi, 0, 0);
		
		ObjectSet<Resultat> queryResult = db.queryByExample(result);
		
		while (queryResult.hasNext()) {

			Resultat auxResultat = queryResult.next();

			System.out.println(auxResultat.getVots() + " " + auxResultat.getPartit().getSigles() +" - "+auxResultat.getMunicipi().getNom() );

		}
		
	}
	
	
	
	
	
	
	
	
	
}