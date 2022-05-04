package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

public class Model {

	private String db40;
	private String file;
	private ObjectContainer db;

	public Model() {
		this.db40 = "BDOOVotacions.db4o";
		this.file = "lib/mataro2019.csv";
		new File(db40).delete();
		this.db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), db40);
		lecturaFichero();
	}

	public void lecturaFichero() {
		try {

			// Obrirem el fitxer i iniciarem el buffer per poder llegir aquest fitxer
			FileReader readFile = new FileReader(this.file);
			BufferedReader buffer = new BufferedReader(readFile);
			String lane;

			while ((lane = buffer.readLine()) != null) {
				String values[] = lane.split(";");

				// Omitimos el titulo del csv
				if (!values[3].equalsIgnoreCase("PARTIT")) {

					// Obtenemos valres de cada linea...
					String provincia = values[0].trim();
					String municipi = values[1].trim();
					String siglesPartit = values[2].trim();
					String nomPartit = values[3].trim();
					String vots = values[4].trim();
					String percent = values[5].trim().replaceAll("\\,", ".");

					// Substituim les , per .
					// percent = percent.replaceAll("\\,", ".");

					// Agregamos los valores
					Partit newPartit = new Partit(siglesPartit, nomPartit);
					Municipi newMunicipi = new Municipi(municipi, provincia);
					Resultat newresult = new Resultat(newPartit, newMunicipi, Integer.parseInt(vots),
							Double.parseDouble(percent));

					newMunicipi.setResultats(newresult);

					// Almacenamos en DB4o
					db.store(newPartit);
					db.store(newresult);
					db.store(newMunicipi);
				}
			}

			buffer.close();
			readFile.close();

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e, "Exception detected", JOptionPane.WARNING_MESSAGE);
		}

	}

	public void showPartits() {
		Partit partit = new Partit();
		ObjectSet<Partit> result = this.db.queryByExample(partit);

		while (result.hasNext()) {
			System.out.println(result.next().getNom());
			System.out.println(result.next().getSigles());
		}
	}
	
}