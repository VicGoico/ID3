package CapaPresentacion;

import java.util.ArrayList;

import javax.swing.JFrame;

import CapaNegocio.AlgoritmoLogica;
import CapaNegocio.TDatos;

public class VentanaPrinicpal extends JFrame {
	private AlgoritmoLogica algoritmo;
	
	// Atributos 
	/*
	 * JLabel
	 * JTextField (ScrollBar)
	 * 2 JButton
	*/
	
	
	public VentanaPrinicpal(String[] titulosColumna, ArrayList<String[]> tablaConDatos){
		
		
		
		
		this.algoritmo = new AlgoritmoLogica();
		this.algoritmo.primeraVuelta(titulosColumna, tablaConDatos);
		
		
	}

}
