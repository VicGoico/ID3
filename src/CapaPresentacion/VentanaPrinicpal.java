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
import javax.swing.JTextField;

import CapaNegocio.AlgoritmoLogica;
import CapaNegocio.TDatos;

public class VentanaPrinicpal extends JFrame {
	private AlgoritmoLogica algoritmo;
	
	private JLabel mensaje;
	private JTextField lienzo;
	private JButton otraVuelta;
	private JButton salir;
	
	// Atributos 
	/*
	 * JLabel
	 * JTextField (ScrollBar)
	 * 2 JButton
	*/
	
	
	public VentanaPrinicpal(String[] titulosColumna, ArrayList<String[]> tablaConDatos){
		
		init();
		
		
		this.algoritmo = new AlgoritmoLogica();
		this.algoritmo.primeraVuelta(titulosColumna, tablaConDatos);
		
		
	}
	public VentanaPrinicpal(){
		init();
	}

	private void init() {
		 this.setTitle("Algoritmo ID3");	
		 this.setLocationRelativeTo(null);
		 //this.setResizable(false);
		 
		 this.setSize(600, 100);
		 this.setLocation(400, 200);
		 this.setLayout(new BorderLayout());
		 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 
		 JPanel panel = new JPanel();
		 panel.setLayout(new GridLayout(2, 1));
		 this.mensaje = new JLabel("Arbol generado");
		 panel.add(this.mensaje);
		 this.lienzo = new JTextField();
		 this.lienzo.setEditable(false);
		 // Probar cual es el mejor tama�o
		 this.lienzo.setSize(300, 500);
		 JPanel panel2 = new JPanel();
		// panel2.setLayout();
		 panel2.add(new JScrollPane(this.lienzo, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		 panel.add(panel2);
		 this.add(panel, BorderLayout.CENTER);
		 
		 
		 panel = new JPanel();
		 panel.setLayout(new FlowLayout());
		 this.otraVuelta = new JButton("Otra vuelta");
		 this.otraVuelta.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Dar otra vuelta");
			}
		});
		panel.add(this.otraVuelta);
		
		this.salir = new JButton("Salir");
		this.salir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Hasta luego");
				System.exit(0);
			}
		});
		panel.add(this.salir);
		this.add(panel, BorderLayout.SOUTH);
		 
		 
		 this.setVisible(true);
		 //this.pack();
	}

}
