package CapaNegocio;

public class TData {

	private String nombre; // Nombre del atributo
	private String respuesta; // La respuesta en el caso de que estemos al final
	private TData padre;// Padre del que parte, sera null si no hay mas padre a recorrer
	
	public TData(String nombre){
		this.nombre = nombre;
		this.padre = null;
	}
	
	public String getNombre(){
		return this.nombre;
	}
	public void setNombre(String nombre){
		this.nombre = nombre;
	}
	public String getRespuesta(){
		return this.respuesta;
	}
	public void setRespuesta(String respuesta){
		this.respuesta = respuesta;
	}
	public TData getPadre(){
		return this.padre;
	}
	public void setPadre(TData padre){
		this.padre = padre;
	}
}
