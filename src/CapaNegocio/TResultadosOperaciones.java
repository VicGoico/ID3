package CapaNegocio;

public class TResultadosOperaciones {
	private String nombre;
	private int total;
	private int numPositivos;
	private double positivo;
	private int numNegativos;
	private double negativo;
	private double r;
	
	public 	TResultadosOperaciones(String nombre){
		this.nombre = nombre;
		this.total = 0;
		this.numPositivos = 0;
		this.numNegativos = 0;	
	}
	public int getNumPositivos(){
		return this.numPositivos;
	}
	public void setNumPositivos(int numPositivos){
		this.numPositivos = numPositivos;
	}
	public int getNumNegativos(){
		return this.numNegativos;
	}
	public void setNumNegativos(int numNegativos){
		this.numNegativos = numNegativos;
	}
	public String getNombre(){
		return this.nombre;
	}
	public void setString(String nombre){
		this.nombre = nombre;
	}
	public int getTotal(){
		return this.total;
	}
	public void setTotal(int total){
		this.total = total;
	}
	public double getPositivo(){
		return this.positivo;
	}
	public void setPositivo(double positivo){
		this.positivo = positivo;
	}
	public double getNegativo(){
		return this.negativo;
	}
	public void setNegativo(double negativo){
		this.negativo = negativo;
	}
	public double getR(){
		return this.r;
	}
	public void setR(double r){
		this.r = r;
	}
}
