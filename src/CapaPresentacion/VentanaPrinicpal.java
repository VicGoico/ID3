package CapaPresentacion;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import CapaNegocio.AlgoritmoLogica;
import CapaNegocio.TDatos;

public class VentanaPrinicpal extends JFrame {
	private AlgoritmoLogica algoritmo;
	
	private JLabel mensaje;
	private JTable lienzo;
	private JButton otraVuelta;
	private JButton salir;
	
	private String[] columnas;
	private Object[][] tabla;
	
	private String[] titulosColumna;

	
	public VentanaPrinicpal(String[] titulosColumna, ArrayList<String[]> tablaConDatos){
		this.titulosColumna = titulosColumna;
		
		this.algoritmo = new AlgoritmoLogica(this.titulosColumna, tablaConDatos);
		this.algoritmo.primeraVuelta(titulosColumna, tablaConDatos, null);
		while(this.algoritmo.darVueltas()){}
		this.algoritmo.pintar2();
		int cont = 1;
		this.columnas = new String[titulosColumna.length];
		String []primer = this.algoritmo.getInforGeneral().get(0).getHijoTipo().split(":");
		this.columnas[0] = primer[1];
		for(int i= 1; i < this.columnas.length-1; i++){
			this.columnas[i] = "Condici�n "+cont;
			cont++;	
		}
		// A�ado el nombre del ultimo campo de los titulos
		this.columnas[this.columnas.length-1] = this.titulosColumna[this.titulosColumna.length-1];
		
		
		this.tabla = (Object[][]) algoritmo.getTabla();
		init();
		
		
		
	}
	public VentanaPrinicpal(){
		init();
	}

	private void init() {
		 this.setTitle("Algoritmo ID3");	
		 this.setLocationRelativeTo(null);
		 //this.setResizable(false);
		 
		 this.setSize(600, 400);
		 this.setLocation(400, 200);
		 this.setLayout(new BorderLayout());
		 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 
		 this.mensaje = new JLabel("Ramas generadas");
		 
		 this.add(this.mensaje, BorderLayout.NORTH);
		 
		 
		 this.lienzo = new JTable(this.tabla, this.columnas);
		 // Probar cual es el mejor tama�o
		 
		 this.lienzo.setEnabled(false);
		 this.add(new JScrollPane(this.lienzo, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
		// this.add(this.lienzo, BorderLayout.CENTER);
		 
		 JPanel panel = new JPanel();
		 panel.setLayout(new FlowLayout());
		 this.otraVuelta = new JButton("Otra vuelta");
		 this.otraVuelta.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Dar otra vuelta");
				VentanaSearch buscar = new VentanaSearch(algoritmo, getInstance(), titulosColumna);
				setVisible(false);
			}
		});
		panel.add(this.otraVuelta);
		
		this.salir = new JButton("Salir");
		this.salir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//JOptionPane.showMessageDialog(null, "Hasta luego");
				System.exit(0);
			}
		});
		panel.add(this.salir);
		this.add(panel, BorderLayout.SOUTH);
		 
		 
		 this.setVisible(true);
		 this.pack();
	}
	public VentanaPrinicpal getInstance(){
		return this;
	}
}
