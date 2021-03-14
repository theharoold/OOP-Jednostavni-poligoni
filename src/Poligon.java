import java.util.ArrayList;

public class Poligon implements IMatematickiProracuni {

	private ArrayList<Teme> temena;
	private boolean slozen, nekolinearan;
	private String zaglavlje;
	
	public void setZaglavlje(String s) {
		zaglavlje = s;
	}
	
	public String getZaglavlje() {
		return zaglavlje;
	}
	
	public Poligon() {
		temena = new ArrayList<>();
	}
	
	
	
	public ArrayList<Teme> getTemena() {
		return temena;
	}



	public void setTemena(ArrayList<Teme> temena) {
		this.temena = temena;
	}



	public boolean isSlozen() {
		return slozen;
	}



	public void setSlozen(boolean slozen) {
		this.slozen = slozen;
	}



	public boolean isNekolinearan() {
		return nekolinearan;
	}



	public void setNekolinearan(boolean nekolinearan) {
		this.nekolinearan = nekolinearan;
	}



	public Poligon(Teme tacka) {
		temena = new ArrayList<>();
		temena.add(tacka);
	}
	
	public void dodajTeme(Teme tacka) {
		temena.add(tacka);
	}
	
	public void dodajKoordinate(double x, double y) {
		temena.add(new Teme(x, y));
	}
	
	// PROCEDURA VRACA DUZINU LINIJE OGRANICENE S TACKAMA i I i - 1
	// PRIMER: ZA UNOS 1 (PRVA LINIJA) PROCEDURA VRACA DUZINU IZMEDJU temena.get(1) I temena.get(0)
	@Override
	public double duzinaStranice(int i) {
		if (i == temena.size()) // dodati proveru za poslednju liniju
			return Math.sqrt(Math.pow(temena.get(0).getX() - temena.get(i-1).getX(),2) + Math.pow(temena.get(0).getY() - temena.get(i-1).getY(),2));
		return Math.sqrt(Math.pow(temena.get(i).getX() - temena.get(i-1).getX(),2) + Math.pow(temena.get(i).getY() - temena.get(i-1).getY(),2));
	}

	// Zbir duzina svih ivica/linija poligona
	@Override
	public double Obim() {
		double obim = 0.0f;
		for (int i = 1, n = temena.size(); i <= n; i++) {
			obim += duzinaStranice(i);
		}
		return obim;
	}

	@Override
	public double Povrsina() {
		double povrsina = 0;
		int j = temena.size() - 1; 

		for (int i=0; i<temena.size(); i++)
		{ 
			povrsina +=  (temena.get(j).getX()+temena.get(i).getX()) * (temena.get(j).getY()-temena.get(i).getY()); 
	 	 	j = i;
		}
	 	 return Math.abs(povrsina/2);
	}

	// Proverava da li su sve tacke nekolinearne
	// Ukoliko su sve tacke nekolinearne, funkcija vraca true
	// U suprotnom, false
	@Override 
	public void Kolinearnost() {
		this.nekolinearan = ProveraKolinearnosti();
	}
	
	private boolean ProveraKolinearnosti() {
		boolean x = true;
		for (int i = 0, n = temena.size(); i < n; i++) {
			if (i == n - 2) {
				x = (x && Determinanta(temena.get(i), temena.get(n-1), temena.get(0)));
			} else if (i == n - 1) {
				x = (x && Determinanta(temena.get(i), temena.get(1), temena.get(0)));
			} else {
				x = (x && Determinanta(temena.get(i), temena.get(i+1), temena.get(i+2)));
			}
		}
		
		return x;
	}
	
	private boolean Determinanta(Teme t1, Teme t2, Teme t3) {
		return (0 != (t1.getX() * t2.getY() + t2.getX() * t3.getY() + t3.getX() * t1.getY() - t1.getY() * t2.getX() - t2.getY() * t3.getX() - t3.getY() * t1.getX()) * 0.5);
	}

	// Ako funkcija vrati true, poligon je jednostavan
	@Override
	public void Slozenost() {
		boolean x = true;
		int i, j, n;
		for (i = 0, n = temena.size(); i < n; i++) {
			j = i + 2;
			for (j = i + 2; j < n - 1; j++) {
				boolean x1 = ProveraSlozenosti(temena.get(i), temena.get(j), temena.get(j+1));
				boolean x2 = ProveraSlozenosti(temena.get(i+1), temena.get(j), temena.get(j+1));
				boolean x3 = ProveraSlozenosti(temena.get(i), temena.get(i+1), temena.get(j));
				boolean x4 = ProveraSlozenosti(temena.get(i), temena.get(i+1), temena.get(j+1));
				x = (x && (x1 == x2) && (x3 == x4) );
			}
		}
		this.slozen = x;
	}
	
	private boolean ProveraSlozenosti(Teme t1, Teme t2, Teme t3) {
		boolean x = ( ( (t3.getY() - t1.getY() ) * (t2.getX() - t1.getX() ) ) > ( (t2.getY() - t1.getY() ) * (t3.getX() - t1.getX() ) ) );
		return x;
	}

	
}
