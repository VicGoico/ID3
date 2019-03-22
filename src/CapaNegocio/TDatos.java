package CapaNegocio;

import java.util.ArrayList;

public class TDatos {
	// Este atributo sobra
	private AlgoritmoLogica logica;
	
	private String[] titulosColumnas;
	private ArrayList<String[]> datos;

	public TDatos(String[] titulosColumna, ArrayList<String[]> datos){
		this.datos = datos;
		this.titulosColumnas = titulosColumna;
	}
	
	public String[] getTitulosColumna(){
		return this.titulosColumnas;
	}
	public void setTitulosColumna(String[] cosa){
		this.titulosColumnas = cosa;
	}
	public ArrayList<String[]> getDatos(){
		return this.datos;
	}
	public void setDatos(ArrayList<String[]> cosas){
		this.datos = cosas;
	}
}
