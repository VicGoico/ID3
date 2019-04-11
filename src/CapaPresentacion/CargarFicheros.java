package CapaPresentacion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class CargarFicheros {
	// Nombre de los ficheros a leer para obtener los datos
	private String nombreFichero1 = "AtributosJuego.txt";
	private String nombreFicheroDatos = "Juego.txt";

	private String[] nombresColumna;
	private ArrayList<String[]> datos;

	public CargarFicheros() {
		this.nombresColumna = null;
		this.datos = new ArrayList<>();
		
		try {
			this.cargarFichero1();
			// Compruebo que se han cargado bien los nombres de las columnas
			if (this.nombresColumna != null) {
				this.cargarFicheroDatos();
				// Compruebo que se han cargado bien los datos de la matriz
				if (!this.datos.isEmpty()) {
					//JOptionPane.showMessageDialog(null, "Los ficheros se cargaron con exito");
					//@SuppressWarnings("unused")
					//VentanaPrinicpal ventana = new VentanaPrinicpal(nombresColumna, datos);
					VentanaSearch ventana = new VentanaSearch(nombresColumna, datos);
				} else {
					// Mensaje en caso de fallo
					JOptionPane.showMessageDialog(null, "Esta vacio el fichero: " + this.nombreFicheroDatos);
				}
			} else {
				// Mensaje en caso de fallo
				JOptionPane.showMessageDialog(null, "Esta vacio el fichero: " + this.nombreFichero1);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			// System.out.println("Excepcion En CargarFicheros");
			JOptionPane.showMessageDialog(null, "Fallo al leer los fichero, asegurate que el ejecutable esta en la misma carpeta que los ficheros");
		}
	}	

	// Metodo que carga los datos del fichero que tiene la matriz con todos los datos necesarios
	private void cargarFicheroDatos() throws IOException {
		String cadena;
		FileReader f = new FileReader(this.nombreFicheroDatos);
		BufferedReader b = new BufferedReader(f);
		while ((cadena = b.readLine()) != null) {
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
		FileReader f = new FileReader(this.nombreFichero1);
		BufferedReader b = new BufferedReader(f);
		while ((cadena = b.readLine()) != null) {
			this.nombresColumna = cadena.split(",");
		}
		b.close();
	}

}
