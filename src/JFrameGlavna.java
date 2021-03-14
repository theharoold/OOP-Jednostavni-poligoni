import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;

public class JFrameGlavna extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField x_text;
	private JTextField y_text;
	private JButton save_button;
	private CustomPanel cp;
	private JTable table;
	private Poligon p;
	private int count;
	private final int dimensions = 300;
	private final int table_width = 200;
	private boolean ucitano;
	private JTextField povrsina_textfield;
	private JTextField obim_textfield;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrameGlavna frame = new JFrameGlavna();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public JFrameGlavna() throws IOException {
		setTitle("PolyEdit 0.1.2020.1.24");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		ucitano = false;
		count = 0;
		
		
		x_text = new JTextField();
		x_text.setBounds(10, 54, 96, 20);
		contentPane.add(x_text);
		x_text.setColumns(10);
		
		y_text = new JTextField();
		y_text.setBounds(10, 78, 96, 20);
		contentPane.add(y_text);
		y_text.setColumns(10);
		
		p = new Poligon();
		p.setZaglavlje("Genericno_ime");
		
		JButton unos_button = new JButton("Unos");
		
		unos_button.setBounds(10, 109, 89, 23);
		contentPane.add(unos_button);
		
		BufferedImage unosImg = null;
		try {
		    unosImg = ImageIO.read(getClass().getResource("icons/open-file.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		Image dimg = unosImg.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
		
		ImageIcon unosIcon = new ImageIcon(dimg);
		
		BufferedImage saveImg = null;
		try {
		    saveImg = ImageIO.read(getClass().getResource("icons/save-file.jpg"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		Image simg = saveImg.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
		
		ImageIcon saveIcon = new ImageIcon(simg);
		
		JButton unosFile_btn = new JButton(unosIcon);
		
		unosFile_btn.setBounds(10, 11, 32, 32);
		unosFile_btn.setToolTipText("Open file");
		contentPane.add(unosFile_btn);
		
		save_button = new JButton(saveIcon);
		save_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Validacija.Ispis(p);
			}
		});
		save_button.setBounds(52, 11, 32, 32);
		save_button.setToolTipText("Save file");
		contentPane.add(save_button);
		
		cp = new CustomPanel(118,11,dimensions,dimensions);
		contentPane.add(cp);
		
		String[] zaglavlje = {"#", "X", "Y"};
		DefaultTableModel model = new DefaultTableModel(zaglavlje, 0) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int columnIndex) {
                return columnIndex != 0;
			}
		};
		table = new JTable(model);
		JScrollPane sp = new JScrollPane(table);
		
		table.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = table.rowAtPoint(evt.getPoint());
		        int col = table.columnAtPoint(evt.getPoint());
		        if (row >= 0 && col >= 1) {
		        	char c = ' ';
		        	switch (col) {
		        	case 1:
		        		c = 'X';
		        		break;
		        	case 2:
		        		c = 'Y';
		        		break;
		        	default:
		        		break;
		        	}
		            try {
		            	Double novaVrednost = Double.parseDouble(JOptionPane.showInputDialog("Unesite novu vrednost za "+c+" koordinatu "+(row+1)+". tacke" ));
		            	switch (c) {
		            	case 'X':
		            		p.getTemena().get(row).setX(novaVrednost);
		            		model.setValueAt(novaVrednost, row, col);
		            		break;
		            	case 'Y':
		            		p.getTemena().get(row).setY(novaVrednost);
		            		model.setValueAt(novaVrednost, row, col);
		            		break;
		            	default:
		            		break;
		            	}
		            	ucitano = true;
		            } catch (NumberFormatException nfe1) {
		            	JOptionPane.showMessageDialog(null, "Unos nije validan.");
		            }

		        }
		    }
		});
		
		table.setBounds(428, 11, table_width, dimensions);
		sp.setBounds(428, 11, table_width, dimensions);
		//model.addRow(new Object[]{0, 5, 5});
		contentPane.add(sp);
		
		JButton iscrtaj_button = new JButton("Iscrtaj");
		
		iscrtaj_button.setBounds(10, 137, 89, 23);
		contentPane.add(iscrtaj_button);
		
		JButton obrisi_button = new JButton("Obrisi");
		obrisi_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cp.removeAll();
				cp.removeData();
				cp.setCondition(false);
				cp.repaint();
			}
		});
		obrisi_button.setBounds(10, 165, 89, 23);
		contentPane.add(obrisi_button);
		
		JLabel povrsina_label = new JLabel("Povrsina:");
		povrsina_label.setBounds(648, 11, 67, 14);
		contentPane.add(povrsina_label);
		
		JLabel obim_label = new JLabel("Obim:");
		obim_label.setBounds(648, 38, 49, 14);
		contentPane.add(obim_label);
		
		povrsina_textfield = new JTextField();
		povrsina_textfield.setBounds(710, 9, 96, 20);
		contentPane.add(povrsina_textfield);
		povrsina_textfield.setColumns(10);
		
		obim_textfield = new JTextField();
		obim_textfield.setBounds(710, 35, 96, 20);
		contentPane.add(obim_textfield);
		obim_textfield.setColumns(10);
		
		JButton izracunaj_button = new JButton("Izracunaj");
		
		izracunaj_button.setBounds(678, 70, 89, 23);
		contentPane.add(izracunaj_button);;
		
		
		unos_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Validacija.UnosTextbox(x_text, y_text, p);
				Object[] o = {count, p.getTemena().get(count).getX(), p.getTemena().get(count).getY()};
				model.addRow(o);
				count++;
				ucitano = true;
			}
		});
		
		iscrtaj_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				p.Kolinearnost();
				p.Slozenost();
				try {
					
					
					Validacija.ProveraKolinearnosti(p);
					cp.setVertices(p.getTemena());
					cp.minmax();
					cp.newVertices();
					cp.setCondition(true);
					cp.repaint();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Tacke su kolinearne.");
				}
				cp.setVertices(p.getTemena());
				
			}
		});
		
		unosFile_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ucitano) {
					if (JOptionPane.showConfirmDialog(null, "Da li ste sigurni? Svi nesacuvani podaci bice izgubljeni.") == JOptionPane.YES_OPTION) {
						File f = Validacija.OdabirDatoteke(p);
						try {
							p = new Poligon();
							Validacija.ProveraDatoteke(f, p);
							count = 0;
							model.setRowCount(0);
							for (int i = 0; i < p.getTemena().size(); i++, count++) {
								Object[] o = {count, p.getTemena().get(i).getX(), p.getTemena().get(i).getY()};
								model.addRow(o);
							}
							ucitano = true;
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null, "Dogodila se greska prilikom ucitavanja datoteke.");
						}
					}
				}
				else {
					File f = Validacija.OdabirDatoteke(p);
					try {
						p = new Poligon();
						Validacija.ProveraDatoteke(f, p);
						count = 0;
						model.setRowCount(0);
						for (int i = 0; i < p.getTemena().size(); i++, count++) {
							Object[] o = {count, p.getTemena().get(i).getX(), p.getTemena().get(i).getY()};
							model.addRow(o);
						}
						ucitano = true;
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "Dogodila se greska prilikom ucitavanja datoteke.");
					}
				}
			}
		});
		
		izracunaj_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				p.Kolinearnost();
				p.Slozenost();
				try {
					Validacija.ProveraKolinearnosti(p);
					try {
						Validacija.ProveraSlozenosti(p);
						povrsina_textfield.setText(p.Povrsina()+"");
						obim_textfield.setText(p.Obim()+"");
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "Poligon mora biti jednostavan.");
						System.out.println("POLIGON JE SLOZEN");
					}
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Sve tri uzastopne tacke moraju biti nekolinearne.");
				}
				
			}
		});
	}
}
