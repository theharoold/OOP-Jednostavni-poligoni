import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JFileChooser;

public abstract class Validacija {

	// Metoda koristi klasu JFileChooser za odabir apsolutnog puta do datoteke
	public static File OdabirDatoteke(Poligon p) {
		JFileChooser fileChooser = new JFileChooser();
		File selectedFile = null;
		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
		    selectedFile = fileChooser.getSelectedFile();
		}
		return selectedFile;
	}
	
	// Metoda proverava da li je datoteka kreirana prema .poligon standardu 
	public static void ProveraDatoteke(File f, Poligon p) throws Exception {
		Scanner scanf = null;
		// Ucitavamo datoteku f odabranu putem JFileChooser-a u instancu Scanner klase
		try {
			scanf = new Scanner(f);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Datoteka nije pronadjena.");
		}
		try {
			// Podela zaglavlja na delove
			String[] s = (scanf.nextLine()).split(" - ");
			if (s[0].charAt(0) != '#') throw new Exception("Not a valid .poligon file.");
			p.setZaglavlje(s[1]);
			try {
				int n = Integer.parseInt(s[2]);
				p.setSlozen(Boolean.parseBoolean(s[3]));
				for (int i = 0; i < n; i++) {
					try {
						// Podela temena na delove t, x i y
						String[] tacke = scanf.nextLine().split(" ");
						double x = Double.parseDouble(tacke[1]);
						double y = Double.parseDouble(tacke[2]);
						p.dodajKoordinate(x, y);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Dogodila se greska prilikom ucitavanja datoteke.");
						
					}
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Format datoteke nije validan.");
			}
			
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, "Nije moguce otvoriti trazenu datoteku.");
		}
	}
	
	// Unos preko zadatih textbox polja
	public static void UnosTextbox(JTextField x, JTextField y, Poligon p) {
		try {
			p.dodajKoordinate(Double.parseDouble(x.getText()), Double.parseDouble(y.getText()));
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Greska: Uneti podaci nisu validni.");
		}
	}

	// Cuvanje poligona u zadatu datoteku
	public static void Ispis(Poligon p) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Sacuvajte datoteku kao...");   
		 
		int userSelection = fileChooser.showSaveDialog(null);
		 
		if (userSelection == JFileChooser.APPROVE_OPTION) {
		    File fileToSave = fileChooser.getSelectedFile();
		    try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(fileToSave));
				bw.write("# - "+p.getZaglavlje()+" - "+p.getTemena().size()+" - "+p.isSlozen());
				bw.newLine();
				for (Teme t:p.getTemena()) {
					bw.write("t "+t.getX()+" "+t.getY());
					bw.newLine();
				}
				bw.flush();
				bw.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Pisanje datoteke nije uspelo.");
			}
		    
		}
		
	}

	public static void ProveraKolinearnosti(Poligon p) throws Exception {
		p.Kolinearnost();
		if (!p.isNekolinearan()) {
			throw new Exception("Neka od temena su kolinearna.");
		}
	}
	
	public static void ProveraSlozenosti(Poligon p) throws Exception {
		p.Slozenost();
		if (!p.isSlozen()) 
			throw new Exception("Poligon je slozen.");
	}
	
}
