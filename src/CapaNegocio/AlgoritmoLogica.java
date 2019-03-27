package CapaNegocio;

import java.util.ArrayList;
import java.util.HashMap;

public class AlgoritmoLogica {
	private boolean cierto;
	private String[] titulos;
	private ArrayList<String[]> tablaDatos;
	private HashMap<String, TDatos> inforGeneral;// String: hijoTipo y TDatos, todo el nodo
	
	// Todos los nodos del mismo nivel del arbol(grafo)
	private ArrayList<TDatos> mirar;
	private ArrayList<String> nombresImpo;
	
	// A lo mejor no hace falta que sean globales dentro de la clase, y nos valen con que sean locales
	/*private double minMerito;
	private int columnaDato;*/ // Como si fuera el ID*/
	
	
	
	public AlgoritmoLogica(){
		this.mirar = new ArrayList<>();// Getters y setter de este atributo
		this.inforGeneral = new HashMap<>();
		this.cierto = true;
	}
	
	public void primeraVuelta(String[] titulosColumna, ArrayList<String[]> tablaConDatos){
		double minMerito = 100.0;
		int columnaTitulo = 0;
		ArrayList<Double> meritos = new ArrayList<>();
		
		
		int tam = tablaConDatos.get(0).length;
		// Mirar positivo o negativo, si o no
		int tam_1 = tam-1;
		
		/*System.out.println("Tamano de columnas: "+tam);
		System.out.println("Tamano de filas: "+tablaConDatos.size());*/
		
		// For que se recorre las columnas
		for(int i = 0; i < tam_1; i++){
			ArrayList <String> nombres= new ArrayList<>();
			HashMap<String, TResultadosOperaciones> numTipos = new HashMap<>();
			String titulo = titulosColumna[i];
			
			// For que se recorre las filas
			for(int j = 0; j < tablaConDatos.size(); j++){
				// Miro que no este repetido
				String nombre = tablaConDatos.get(j)[i];
				String siOno = tablaConDatos.get(j)[tam_1];
				TResultadosOperaciones guardar;
				//System.out.println("Num: "+ j +"Nombre: "+nombre);
				if(!numTipos.containsKey(nombre)){
					guardar = new TResultadosOperaciones(nombre);
					guardar.setTotal(1);
					nombres.add(nombre);
					if(siOno.equalsIgnoreCase("si")){
						guardar.setNumPositivos(1);
					}
					else{
						guardar.setNumNegativos(1);
					}
					numTipos.put(nombre, guardar);
					
				}
				else {
					guardar = numTipos.get(nombre);
					//System.out.println("Nombre: "+nombre);
					guardar.setTotal(guardar.getTotal()+1);
					
					if(siOno.equalsIgnoreCase("si")){
						guardar.setNumPositivos(guardar.getNumPositivos()+1);
					}
					else{
						guardar.setNumNegativos(guardar.getNumNegativos()+1);
					}
				}
			}
			// Fin del primer conteo
			// Segundo conteo
			int N = tablaConDatos.size();
			// Hago unos pequeños calculos
			for(String nombre : nombres){
				TResultadosOperaciones guardar = numTipos.get(nombre);
				guardar.setPositivo(guardar.getNumPositivos()/(double)guardar.getTotal());
				guardar.setNegativo(guardar.getNumNegativos()/(double)guardar.getTotal());
				guardar.setR((double)guardar.getTotal()/(double)N);
			}
			// Tercer conteo
			double meritoParticular = 0;
			for(String nombre : nombres){
				TResultadosOperaciones guardar = numTipos.get(nombre);
				meritoParticular += guardar.getR()*infor(guardar.getPositivo(), guardar.getNegativo());
			}
			meritos.add(meritoParticular);
			if(minMerito > meritoParticular){
				minMerito = meritoParticular;
				columnaTitulo = i;
				this.nombresImpo = nombres;
			}
			
		}
		// Para hacer los calculos en la primera vuelta
		if(this.cierto){
			
			this.cierto = false;
			// Miro los nombres de un tipo determinado
			for(String nombre : this.nombresImpo){
				
				// Matriz que se guardara en TDatos
				ArrayList<String[]> datos = new ArrayList<>();
				
				// Miro la matriz de datos que me han pasado 
				for(int i = 0; i < tablaConDatos.size(); i++){
					// SI coincide con el nombre de a columna que estoy buscando se mete
					if(tablaConDatos.get(i)[columnaTitulo].equalsIgnoreCase(nombre)){
						// Actulizo el array y no tengo en cuenta el dato que se convierte en NOMBRE
						String[] aux = new String[tablaConDatos.get(i).length-1];
						int cont = 0;
						for(int j = 0; j < tablaConDatos.get(i).length; j++){
							if(!tablaConDatos.get(i)[j].equalsIgnoreCase(nombre)){
								aux[cont] = tablaConDatos.get(i)[j];
								cont++;
							}
						}
						datos.add(aux);
					}
					
				}
				TDatos nuevo = new TDatos(datos, nombre+":"+titulosColumna[columnaTitulo]);
				nuevo.setPadre(null);
				
				this.mirar.add(nuevo);
			}
			
			
			
		}
		// Recurisvo
		else{
			
		}
		
		// Pintar
		String pppp = this.mirar.get(0).getHijoTipo();
		String []aux = pppp.split(":");
		
		System.out.println(aux[0]);
		for(int i = 0; i < this.mirar.size(); i++){
			
			System.out.println(this.mirar.get(i).getHijoTipo());
			
			for(int j = 0; j < this.mirar.get(i).getDatos().size(); j++){
				for(int k = 0; k < this.mirar.get(i).getDatos().get(j).length; k++){
					System.out.print(this.mirar.get(i).getDatos().get(j)[k]+"	");
				}
				System.out.println("");
			}
			System.out.println("");
		}
		
		
		
		System.out.println("Mas pequeño es: "+minMerito);
		
		
		// Esto es para ver el resultado final
		/*for(int i = 0; i < meritos.size(); i++){
			System.out.println(titulosColumna[i] + ": " + meritos.get(i));
		}*/
		System.out.println("Acabo");
	}
	
	private double infor(double p, double n){
		return (-p*hacerLog(p)-n*hacerLog(n));
	}
	
	private double hacerLog(double dato){
		if(dato == 0){
			return 0.0;
		}
		return Math.log10(dato)/Math.log10(2);
	}
	
	public void darVueltas() {
		boolean cierto = true;
		ArrayList<TDatos> auxMirar = new ArrayList<>();

		for (TDatos nodo : this.mirar){
			this.inforGeneral.put(nodo.getHijoTipo(), nodo);
			
			
		}
		
		
		if (auxMirar.isEmpty()) {
			cierto = false;
		}

	}
}
