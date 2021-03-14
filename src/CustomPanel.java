import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;

public class CustomPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int width, height, pivot, diff;
	private ArrayList<Teme> temena, novaTemena;
	private double min_x, max_x, min_y, max_y;
	private boolean condition;
	
	public void removeData() {
		temena = new ArrayList<>();
		novaTemena = new ArrayList<>();
		min_x = min_y = Double.MAX_VALUE;
		max_x = max_y = Double.MIN_VALUE;
	}
	
	// Javni parametrizovani konstruktor
	public CustomPanel(int x, int y, int width, int height) {
		this.setBackground(Color.white);
		Border blackline = BorderFactory.createLineBorder(Color.black);
		this.setBorder(blackline);
		this.width = width;
		this.height = height;
		this.setBounds(x, y, this.width, this.height);
		this.pivot = height / 2;
		this.diff = pivot - 50;
		temena = new ArrayList<>();
		novaTemena = new ArrayList<>();
		condition = true;
	}
	
	/*
	public void addPoint(Vertex v) {
		vertices.add(v);
	} */
	
	public void setVertices(ArrayList<Teme> v) {
		temena = v;
	}
	
	// Odredjivanje min i max koordinata
	public void minmax() {
		this.min_x = this.max_x = temena.get(0).getX();
		this.min_y = this.max_y = temena.get(0).getY();
		for (Teme p:temena) {
			if (p.getX() > max_x) {
				max_x = p.getX();
			}
			if (p.getX() < min_x) {
				min_x = p.getX();
			}
			if (p.getY() > max_y) {
				max_y = p.getY();
			}
			if (p.getY() < min_y) {
				min_y = p.getY();
			}
		}
		
		// Izbegavamo deljenje s nulom
		max_x = (max_x == 0) ? 1 : max_x;
		min_x = (min_x == 0) ? 1 : min_x;
		max_y = (max_y == 0) ? 1 : max_y;
		min_y = (min_y == 0) ? 1 : min_y;
	}
	
	// Proporcija:
	// x_new : n = x : max
	// x_new = n * x / max
	// n predstavlja 
	
	// ArrayList<Vertex> vertices_new = vertices;
	public void newVertices() {
		double x1, y1;
		double mx = (Math.abs(max_x) > Math.abs(min_x)) ? Math.abs(max_x) : Math.abs(min_x);
		double my = (Math.abs(max_y) > Math.abs(min_y)) ? Math.abs(max_y) : Math.abs(min_y);
		double m = (mx > my) ? mx : my;
		for (Teme p:temena) {
			
			if (p.getX() > 0) {
				// 150 - 250
				x1 = (Math.abs(p.getX()) * diff / m) + pivot;
			} else if (p.getX() < 0) {
				// 50 - 150
				x1 = pivot - Math.abs(Math.abs(p.getX()) * diff / m);
			} else { 
				// 150
				x1 = pivot;
			}
			
			if (p.getY() > 0) {
				// 50 - 150
				y1 = pivot - (Math.abs(p.getY()) * diff / m);
			} else if (p.getY() < 0) {
				// 150 - 250
				y1 = pivot + Math.abs(Math.abs(p.getY()) * diff / m);
			} else { 
				y1 = pivot;
			}
			novaTemena.add(new Teme(x1, y1));
			
		}
	}
	
	public void setCondition(boolean x) {
		condition = x;
	}
	
	@Override
    public void paintComponent(Graphics g)
    {
		super.paintComponent(g);
		g.setColor(Color.LIGHT_GRAY);
		g.drawLine(0, 150, 300, 150);
		g.drawLine(150, 0, 150, 300);
		int x1,x2,y1,y2;
		if (condition) {
			g.setColor(Color.black);
			for (int i = 0; i < novaTemena.size(); i++) {
				if (i == (novaTemena.size() - 1)) {
					x1 = (int) Math.round(novaTemena.get(i).getX());
					y1 = (int) Math.round(novaTemena.get(i).getY());
					x2 = (int) Math.round(novaTemena.get(0).getX());
					y2 = (int) Math.round(novaTemena.get(0).getY());
				} else {
					x1 = (int) Math.round(novaTemena.get(i).getX());
					y1 = (int) Math.round(novaTemena.get(i).getY());
					x2 = (int) Math.round(novaTemena.get(i + 1).getX());
					y2 = (int) Math.round(novaTemena.get(i + 1).getY());
				}
				g.drawLine(x1, y1, x2, y2);
			} 
		}
	
    }
}
