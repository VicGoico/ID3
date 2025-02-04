package CapaNegocio;

import java.util.ArrayList;

// Transfer para mas adelante poder acceder a la informacion generada en el algoritmo
public class TDatos {	
	private String hijo_tipo;
	private String[] titulos;
	private ArrayList<String[]> datos;
	private TDatos padre;

	public TDatos(ArrayList<String[]> datos, String hijo_tipo){
		this.datos = datos;
		this.hijo_tipo = hijo_tipo;
		this.padre = null;
	}
	
	public String[] getTitulos(){
		return this.titulos;
	}
	public void setTitulos(String[] titulos){
		this.titulos = titulos;
	}
	
	public TDatos getPadre(){
		return this.padre;
	}
	public void setPadre(TDatos padre){
		this.padre = padre;
	}
	
	public ArrayList<String[]> getDatos(){
		return this.datos;
	}
	public void setDatos(ArrayList<String[]> cosas){
		this.datos = cosas;
	}
	
	public String getHijoTipo(){
		return this.hijo_tipo;
	}
	public void setHijoTipo(String hijo_tipo){
		this.hijo_tipo = hijo_tipo;
	}
}
