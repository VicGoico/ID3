package CapaNegocio;

import java.util.ArrayList;

public class TDatos {	
	private String hijo_tipo;
	private String[] titulos;
	private ArrayList<String[]> datos;
	private TDatos padre;
	private int lineaMAX;

	public TDatos(ArrayList<String[]> datos, String hijo_tipo){
		this.datos = datos;
		this.hijo_tipo = hijo_tipo;
		this.padre = null;
		this.lineaMAX = 0;
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
	public int getLineaMAX(){
		for(String [] aux: this.datos){
			int cont = 0;
			for(int i = 0; i < aux.length; i++){
				cont += aux[i].length();
			}
			if(this.lineaMAX < cont){
				this.lineaMAX = cont;
			}
		}
		return this.lineaMAX;
	}
}
