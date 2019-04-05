package CapaNegocio;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPopupMenu.Separator;

public class AlgoritmoLogica {
	private String[] titulos;
	private ArrayList<String[]> tablaDatos;
	// Todos los nodos del mismo nivel del arbol(grafo)
	private HashMap<Integer, ArrayList<TDatos>> inforGeneral;
	private int nivel = 0;
	private ArrayList<TDatos> mirar;
	private ArrayList<String> nombresImpo;
	private ArrayList<TDatos> pintarTDatos;
	private String[][] datos;	
	private String respuesta;
	
	
	public AlgoritmoLogica(String [] titulos, ArrayList<String[]> tablaDatos){
		this.titulos = titulos;
		this.tablaDatos = tablaDatos;
		this.mirar = new ArrayList<>();
		this.inforGeneral = new HashMap<>();
		this.pintarTDatos = new ArrayList<>();
		//Nuevo
		this.inforGeneral = new HashMap<>();
		this.nivel = 0;
	}
	// Metodo mas importante del algoritmo, aqui 
	// es donde se hacen todas las operaciones necesarias 
	// para conseguir el resultado deseado
	public void primeraVuelta(String[] titulosColumna, ArrayList<String[]> tablaConDatos, TDatos padre) {
		double minMerito = 100.0;
		int columnaTitulo = 0;
		ArrayList<Double> meritos = new ArrayList<>();
		ArrayList<TDatos> meterEnMirar = new ArrayList<>();
		int tam = tablaConDatos.get(0).length;
		int tam_1 = tam - 1;

		// Bucle que se recorre las columnas
		for (int i = 0; i < tam_1; i++) {
			ArrayList<String> nombres = new ArrayList<>();
			HashMap<String, TResultadosOperaciones> numTipos = new HashMap<>();

			// Bucle que se recorre las filas
			for (int j = 0; j < tablaConDatos.size(); j++) {
				// Miro que no este repetido
				String nombre = tablaConDatos.get(j)[i];
				String siOno = tablaConDatos.get(j)[tam_1];
				TResultadosOperaciones guardar;
				
				// Miro que no este repetido, lo que quiere decir que es nuevo
				// y por tanto lo inicializo
				if (!numTipos.containsKey(nombre)) {
					guardar = new TResultadosOperaciones(nombre);
					guardar.setTotal(1);
					nombres.add(nombre);
					// Cuento las veces que es positivo o negativo, si o no, etc
					if (siOno.equalsIgnoreCase("si")) {
						guardar.setNumPositivos(1);
					} else {
						guardar.setNumNegativos(1);
					}
					numTipos.put(nombre, guardar);

				} else {
					guardar = numTipos.get(nombre);
					guardar.setTotal(guardar.getTotal() + 1);

					if (siOno.equalsIgnoreCase("si")) {
						guardar.setNumPositivos(guardar.getNumPositivos() + 1);
					} else {
						guardar.setNumNegativos(guardar.getNumNegativos() + 1);
					}
				}
			}
			// Fin del primer conteo
			// Segundo conteo
			int N = tablaConDatos.size();
			// Hago unos pequeños calculos
			for (String nombre : nombres) {
				TResultadosOperaciones guardar = numTipos.get(nombre);
				guardar.setPositivo(guardar.getNumPositivos() / (double) guardar.getTotal());
				guardar.setNegativo(guardar.getNumNegativos() / (double) guardar.getTotal());
				guardar.setR((double) guardar.getTotal() / (double) N);
			}
			// Fin del segundo conteo
			// Tercer conteo
			// Calculo los meritos
			double meritoParticular = 0;
			for (String nombre : nombres) {
				TResultadosOperaciones guardar = numTipos.get(nombre);
				meritoParticular += guardar.getR() * infor(guardar.getPositivo(), guardar.getNegativo());
			}
			meritos.add(meritoParticular);
			
			// Elijo el merito mas pequeño
			if (minMerito > meritoParticular) {
				minMerito = meritoParticular;
				columnaTitulo = i;
				this.nombresImpo = nombres;
			}

		}
		
		// Redimensiono la tabla de datos
		String[] titulosTDatos = new String[titulosColumna.length - 1];
		int contador = 0;
		for (int i = 0; i < titulosColumna.length; i++) {
			if (!titulosColumna[i].equalsIgnoreCase(titulosColumna[columnaTitulo])) {
				titulosTDatos[contador] = titulosColumna[i];
				contador++;
			}
		}

		// Aqui nos guardaremos un ArrayList de tipo TDatos para una proxima vuelta
		// Miro los nombres de un tipo determinado
		for (String nombre : this.nombresImpo) {
			// Matriz que se guardara en TDatos
			ArrayList<String[]> datos = new ArrayList<>();
			// Miro la matriz de datos que me han pasado
			for (int i = 0; i < tablaConDatos.size(); i++) {
				// SI coincide con el nombre de a columna que estoy buscando se
				// mete
				if (tablaConDatos.get(i)[columnaTitulo].equalsIgnoreCase(nombre)) {
					// Actulizo el array y no tengo en cuenta el dato que se
					// convierte en NOMBRE
					String[] aux = new String[tablaConDatos.get(i).length - 1];
					int cont = 0;
					for (int j = 0; j < tablaConDatos.get(i).length; j++) {
						if (!tablaConDatos.get(i)[j].equalsIgnoreCase(nombre)) {
							aux[cont] = tablaConDatos.get(i)[j];
							cont++;
						}
					}
					datos.add(aux);
				}

			}
			TDatos nuevo = new TDatos(datos, nombre + ":" + titulosColumna[columnaTitulo]);

			nuevo.setTitulos(titulosTDatos);
			nuevo.setPadre(padre);
			meterEnMirar.add(nuevo);
		}

		
		for(TDatos datillos : meterEnMirar){
			this.mirar.add(datillos);
		}
		// Para saber si estamos al principio
		if(padre == null){
			this.inforGeneral.put(this.nivel, this.mirar);
		}
	}
	
	// Metodo que calcula el resultado de la formula infor
	private double infor(double p, double n){
		return (-p*hacerLog(p)-n*hacerLog(n));
	}
	
	// Metodo que calcula logaritmos en base 10
	private double hacerLog(double dato){
		if(dato == 0){
			return 0.0;
		}
		return Math.log10(dato)/Math.log10(2);
	}
	
	// Metodo que dara vueltas hasta que lleguemos al final del arbol
	public boolean darVueltas() {
		boolean cierto = true;
		ArrayList<TDatos> auxMirar = this.mirar;
		this.mirar = new ArrayList<>();
		this.pintarTDatos = new ArrayList<>();
		
		// Miro que no se puedan dar mas vueltas
		if(auxMirar.isEmpty()){
			//System.out.println("No se puede mas");
			cierto = false;
		}
		else{
			this.nivel++;
			// Me recorro toda las lista de TDatos generada en la anterior vuelta
			for (TDatos nodo : auxMirar){
				// Me aseguro de que se llega he llegado al final de la rama
				if(nodo.getDatos().size() == 1 && nodo.getDatos().get(0).length == 1)
					this.pintarTDatos.add(nodo);
				else {
					this.pintarTDatos.add(nodo);
					primeraVuelta(nodo.getTitulos(), nodo.getDatos(), nodo);
				}
				this.inforGeneral.put(this.nivel, this.pintarTDatos);
			}
		}		
		return cierto;
	}
	
	// Metodo que se guardara como quedan los datos
	// despues de haber ejecutado el algoritmo 
	// y haber optenido el arbol
	public void guardarDatosParaMostrarlos(){
		ArrayList<TDatos> pintar = this.inforGeneral.get(this.nivel);
		this.datos = new String[pintar.size()][this.titulos.length];
		
		int i = 0;
		for(TDatos data: pintar){
			int j = this.titulos.length-1;
			
			this.datos[i][j] =  pintaTabla(data.getDatos());
			j--;
			while(data != null){
				String [] div = data.getHijoTipo().split(":");
				this.datos[i][j] = div[0];
				j--;
				data = data.getPadre();
			}
			
			i++;
		}
	}

	// Metodo que devuelve los o el valor de la tabla de "datos"
	private String pintaTabla(ArrayList<String[]> datos){
		String tabla = "";
		for(int i = 0; i < datos.size(); i++){
			for(int j = 0; j < datos.get(0).length; j++){
				tabla += datos.get(i)[j];
			}
		}
		return tabla;
	}
	
	// Busca si la rama que nos ha metido el usuario, existe
	public boolean search(HashMap<String, Integer> data1){
		boolean cierto = false;
		ArrayList<TDatos> lista = this.inforGeneral.get(this.nivel);
		int i = 0;
		// Me miro todos los posibles TDatos, o sea todas las ramas finales
		while(!cierto &&  i < lista.size()){
			TDatos data = lista.get(i);
			this.respuesta = pintaTabla(data.getDatos());
			boolean mirar = false;
			// Me recorro todo el TDato para ver que estan todas las condiciones de la rama
			while (data != null && !mirar) {
				String[] div = data.getHijoTipo().split(":");
				// Miro todo el array que me han pasado para ver si coincide
				// alguna

				if (data1.containsKey(div[0])) {
					data = data.getPadre();
				} else {
					mirar = true;
				}
			}
			// Miro si ha salido la rama que estaba buscando
			if(!mirar){
				cierto = true;
			}
			i++;
		}
		return cierto;
	}
	
	public String[][] getTabla(){
		return this.datos;
	}
	public String[] getTitulos(){
		return this.titulos;
	}
	public ArrayList<String[]> getTablaDatos(){
		return this.tablaDatos;
	}
	public ArrayList<TDatos> getInforGeneral(){
		return this.inforGeneral.get(0);
	}
	public String getRespuesta(){
		return this.respuesta;
	}
}
