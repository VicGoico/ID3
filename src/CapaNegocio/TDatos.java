package CapaNegocio;

import java.util.ArrayList;

public class TDatos {	
	private String[] titulosColumnas;
	private ArrayList<String[]> datos;
	private String nombrePadre;

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
	public String getNombrePadre(){
		return this.nombrePadre;
	}
	public void setNombrePadre(String padre){
		this.nombrePadre = padre;
	}
}
