package CapaPresentacion;

import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import CapaNegocio.AlgoritmoLogica;

public class CargarFicheros {
	private String nombreFichero1 = "AtributosJuego.txt";
	private String nombreFicheroDatos = "Juego.txt";

	private String[] nombresColumna;
	private ArrayList<String[]> datos;

	public CargarFicheros() {
		this.nombresColumna = null;
		this.datos = new ArrayList<>();
		

		// Exito
		try {
			this.cargarFichero1();
			if (this.nombresColumna != null) {
				this.cargarFicheroDatos();
				if (!this.datos.isEmpty()) {
					//JOptionPane.showMessageDialog(null, "Los ficheros se cargaron con exito");
					// CONTINUAR POR AQUI
					//VentanaPrinicpal ventana = new VentanaPrinicpal(nombresColumna, datos);
					AlgoritmoLogica algoritmo = new AlgoritmoLogica();
					algoritmo.primeraVuelta(nombresColumna, datos);
					//pintar();
					//VentanaPrinicpal ve = new VentanaPrinicpal();
				} else {
					JOptionPane.showMessageDialog(null, "Esta vacio el fichero: " + this.nombreFicheroDatos);
				}
			} else {
				JOptionPane.showMessageDialog(null, "Esta vacio el fichero: " + this.nombreFichero1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Excepcion");
			JOptionPane.showMessageDialog(null, "Fallo al leer los fichero");
		}
	}

	private void pintar() {
		for(int i = 0; i < this.nombresColumna.length; i++){
			System.out.print(this.nombresColumna[i]+" ");
		}
		System.out.println("");
		for(int i = 0; i < this.datos.size(); i++){
			for(int j = 0; j < this.datos.get(i).length; j++){
				System.out.print(this.datos.get(i)[j]+" ");
			}
			System.out.println("");
		}
	}

	private void cargarFicheroDatos() throws IOException {
		String cadena;
		FileReader f = new FileReader(this.nombreFicheroDatos);
		BufferedReader b = new BufferedReader(f);
		while ((cadena = b.readLine()) != null) {
			//System.out.println(cadena);
			String[] aux = cadena.split(",");
			// Para que no coja lineas en blanco
			if(!aux[0].equalsIgnoreCase(""))
				this.datos.add(aux);
			
		}
		b.close();
	}

	

	// Metodo que lee el fichero de los nombres de las columnas
	private void cargarFichero1() throws IOException {
		String cadena;
		int tam = 1;
		FileReader f = new FileReader(this.nombreFichero1);
		BufferedReader b = new BufferedReader(f);
		while ((cadena = b.readLine()) != null) {
			//System.out.println(cadena);
			this.nombresColumna = cadena.split(",");
			
			
		}
		b.close();
	}

}
