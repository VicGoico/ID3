package CapaNegocio;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPopupMenu.Separator;

public class AlgoritmoLogica {
	private String[] titulos;
	private ArrayList<String[]> tablaDatos;
	private HashMap<Integer, ArrayList<TDatos>> inforGeneral;// Integer: Numero de vueltas, ArrayList<TDatos>: informacion de cada nivel
	private int nivel = 0;
	
	// Todos los nodos del mismo nivel del arbol(grafo)
	private ArrayList<TDatos> mirar;
	private ArrayList<String> nombresImpo;
	private ArrayList<TDatos> pintarTDatos;
	private ArrayList<String[]> todasLasRamas;
	
	// A lo mejor no hace falta que sean globales dentro de la clase, y nos valen con que sean locales
	/*private double minMerito;
	private int columnaDato;*/ // Como si fuera el ID*/
	
	
	
	public AlgoritmoLogica(){
		this.mirar = new ArrayList<>();// Getters y setter de este atributo
		this.inforGeneral = new HashMap<>();
		this.pintarTDatos = new ArrayList<>();
		//Nuevo
		this.inforGeneral = new HashMap<>();
		this.todasLasRamas = new ArrayList<>();
		this.nivel = 0;
	}
	
	public void primeraVuelta(String[] titulosColumna, ArrayList<String[]> tablaConDatos, TDatos padre) {
		double minMerito = 100.0;
		int columnaTitulo = 0;
		ArrayList<Double> meritos = new ArrayList<>();
		ArrayList<TDatos> meterEnMirar = new ArrayList<>();

		int tam = tablaConDatos.get(0).length;
		// Mirar positivo o negativo, si o no
		int tam_1 = tam - 1;

		/*
		 * System.out.println("Tamano de columnas: "+tam); System.out.println(
		 * "Tamano de filas: "+tablaConDatos.size());
		 */

		// For que se recorre las columnas
		for (int i = 0; i < tam_1; i++) {
			ArrayList<String> nombres = new ArrayList<>();
			HashMap<String, TResultadosOperaciones> numTipos = new HashMap<>();
			String titulo = titulosColumna[i];

			// For que se recorre las filas
			for (int j = 0; j < tablaConDatos.size(); j++) {
				// Miro que no este repetido
				String nombre = tablaConDatos.get(j)[i];
				String siOno = tablaConDatos.get(j)[tam_1];
				TResultadosOperaciones guardar;
				// System.out.println("Num: "+ j +"Nombre: "+nombre);
				if (!numTipos.containsKey(nombre)) {
					guardar = new TResultadosOperaciones(nombre);
					guardar.setTotal(1);
					nombres.add(nombre);
					if (siOno.equalsIgnoreCase("si")) {
						guardar.setNumPositivos(1);
					} else {
						guardar.setNumNegativos(1);
					}
					numTipos.put(nombre, guardar);

				} else {
					guardar = numTipos.get(nombre);
					// System.out.println("Nombre: "+nombre);
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
			// Tercer conteo
			double meritoParticular = 0;
			for (String nombre : nombres) {
				TResultadosOperaciones guardar = numTipos.get(nombre);
				meritoParticular += guardar.getR() * infor(guardar.getPositivo(), guardar.getNegativo());
			}
			meritos.add(meritoParticular);
			if (minMerito > meritoParticular) {
				minMerito = meritoParticular;
				columnaTitulo = i;
				this.nombresImpo = nombres;
			}

		}
		String[] titulosTDatos = new String[titulosColumna.length - 1];
		int contador = 0;
		for (int i = 0; i < titulosColumna.length; i++) {
			if (!titulosColumna[i].equalsIgnoreCase(titulosColumna[columnaTitulo])) {
				titulosTDatos[contador] = titulosColumna[i];
				contador++;
			}
		}
		// Para hacer los calculos en la primera vuelta

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

			// Si es la primera vuelta no tienen PADRE
			/*if (this.cierto) {
				nuevo.setPadre(null);
			} else {*/
			nuevo.setPadre(padre);
			//}

			meterEnMirar.add(nuevo);
		}

		// Se cambiara en la primera vuelta
		/*if (this.cierto) {
			this.cierto = false;
		}*/

		// Pintar
		/*String pppp = meterEnMirar.get(0).getHijoTipo();
		String[] aux = pppp.split(":");

		System.out.println(aux[1]);
		for (int i = 0; i < meterEnMirar.size(); i++) {

			System.out.println(meterEnMirar.get(i).getHijoTipo());

			for (int j = 0; j < meterEnMirar.get(i).getDatos().size(); j++) {
				for (int k = 0; k < meterEnMirar.get(i).getDatos().get(j).length; k++) {
					System.out.print(meterEnMirar.get(i).getDatos().get(j)[k] + "	");
				}
				System.out.println("");
			}
			System.out.println("");
		}*/

		//System.out.println("Mas pequeño es: " + minMerito);

		// Esto es para ver el resultado final
		/*
		 * for(int i = 0; i < meritos.size(); i++){
		 * System.out.println(titulosColumna[i] + ": " + meritos.get(i)); }
		 */
		for(TDatos datillos : meterEnMirar){
			this.mirar.add(datillos);
		}
		if(padre == null){
			this.inforGeneral.put(this.nivel, this.mirar);
		}
		/*System.out.println("Acabo");
		System.out.println("----------------------------------------------------------");
		System.out.println("----------------------------------------------------------");
		System.out.println("----------------------------------------------------------");
		System.out.println("----------------------------------------------------------");
		System.out.println("----------------------------------------------------------");*/
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
		// Esto me servira para mas adelante
		boolean cierto = true;
		ArrayList<TDatos> auxMirar = this.mirar;

		this.mirar = new ArrayList<>();
		this.pintarTDatos = new ArrayList<>();
		if(auxMirar.isEmpty()){
			System.out.println("No se puede mas");
		}
		else{
			this.nivel++;
			for (TDatos nodo : auxMirar){
				if(nodo.getDatos().size() == 1 && nodo.getDatos().get(0).length == 1)
					this.pintarTDatos.add(nodo);
				else {
					this.pintarTDatos.add(nodo);
					primeraVuelta(nodo.getTitulos(), nodo.getDatos(), nodo);
				}
				this.inforGeneral.put(this.nivel, this.pintarTDatos);
			}
		}		
		if (auxMirar.isEmpty()) {
			cierto = false;
		}
	}
	private String ponerNLineasHorizontal(int n){
		String result = "";
		for(int i = 0; i < n; i++){
			result += "|";
		}
		return result;
	}
	private String ponerNLineasVerticales(int n){
		String result = "";
		for(int i = 0; i < n; i++){
			result += "-";
		}
		return result;
	}
	private String ponerNEspaciosEnBlanco(int n){
		String result = "";
		for(int i = 0; i < n; i++){
			result += " ";
		}
		return result;
	}
	public void pintar4(){
		ArrayList<String> todo = new ArrayList<>();
		
		ArrayList<TDatos> pintar = this.inforGeneral.get(this.nivel);
		
		int max = 0;
		String total = "";
		HashMap<String, Integer> posiciones = new HashMap<>();
		// Primero me guardare las tablas
		for(TDatos data: pintar){
			String linea = "";
			// Empezamos
			if(total.equalsIgnoreCase("")){
				ArrayList<String> ruta = new ArrayList<>();
				String tabla = "";
				boolean cierto = true;
				// Miro toda la rama
				while(data != null){
					ruta.add(data.getHijoTipo());
					
					// Para coger la tabla
					if(cierto){
						cierto = false;
						//for(int i = 0; i < data.getDatos().size(); i++){
							tabla = pintaTabla(data.getDatos());
						//}
						
					}
					data = data.getPadre();
				}
				int fin = ruta.size();
				for(int i = fin-1; i >= 0; i--){
					String [] div = ruta.get(i).split(":");
					posiciones.put(div[1], div[1].length()+3);
					linea += div[1]+ponerNLineasVerticales(3);
					
					posiciones.put(div[0], div[0].length()+3);
					linea += div[0]+ponerNLineasVerticales(3);
				}
				linea += tabla;
				total += linea+System.lineSeparator();
			}
		}
		System.out.println(total);
		
		
	}
	public void pintar3(){
		String total = "";
		
		if(this.nivel == 0){
			ArrayList<TDatos> pintar = this.inforGeneral.get(this.nivel);
			int [] tamanos = new int[pintar.size()];
			int pos = 0;
			String [] principio = pintar.get(0).getHijoTipo().split(":");

			total += principio[1]+System.lineSeparator();
			
			for(int i = 0; i < 3; i++){
				total += ponerNLineasHorizontal(1)+System.lineSeparator();
			}
			
			boolean primero = true;
			for(TDatos data: pintar){
				if(!primero){
					total += ponerNLineasVerticales(3);
					//manos[pos] += 3;
					pos++;
				}
				else{
					primero = false;
				}
				String jefe = data.getHijoTipo();
				String[] aux = jefe.split(":");
				//tamanos[pos] = data.getLineaMAX()-aux[0].length();
				total += aux[0] + ponerNLineasVerticales(data.getLineaMAX());
				
				System.out.println();
			}
			total += System.lineSeparator();
			for(int i = 0; i < 3; i++){
				for(int j = 0; j < pintar.size(); j++){
					System.out.println(tamanos[j]);
					total += ponerNLineasHorizontal(1)+ponerNEspaciosEnBlanco(0)+" ";
				}
				total += System.lineSeparator();
			}
			int max = 0;
			for(int i = 0; i < pintar.size(); i++){
				if(max < pintar.get(i).getDatos().size()){
					max = pintar.get(i).getDatos().size();
				}
			}
			int i = 0;
			while(i < max){
				String linea = "";
				for(TDatos data : pintar){
					if(data.getDatos().size()<i){
						linea += pintaTabla2(data.getDatos().get(i));
					}
					else{
						linea += ponerNEspaciosEnBlanco(data.getLineaMAX());
					}
					linea += ponerNEspaciosEnBlanco(3);
				}
				total += linea+System.lineSeparator();
				i++;
			}
		}
		System.out.println(total);
		
	}
	
	public void pintar2(){
		int cont = 1;
		String general = "";
		
		ArrayList<TDatos> pintar = this.inforGeneral.get(this.nivel);
		for(TDatos data: pintar){
			boolean cierto = true;
			String pinto = "";
			String tabla = "";
			while(data != null){
				pinto += data.getHijoTipo() + "	";
				
				if(cierto){
					cierto = false;
					tabla += pintaTabla(data.getDatos());
				}
				data = data.getPadre();
			}
			String guardar = cont + " " + pinto + System.lineSeparator() +tabla;
			System.out.println(guardar);
			String [] aux = pinto.split("	");
			this.todasLasRamas.add(aux);
			cont++;
		}
		System.out.println(general);
	}
	private String pintaTabla2(String[] datos){
		String tabla = "";
		for(int j = 0; j < datos.length; j++){
			tabla += datos[j]+ "	";
		}
		
		return tabla;
	}
	private String pintaTabla(ArrayList<String[]> datos){
		String tabla = "";
		for(int i = 0; i < datos.size(); i++){
			for(int j = 0; j < datos.get(0).length; j++){
				tabla += datos.get(i)[j]+ "	";
			}
			tabla += System.lineSeparator();
		}
		return tabla;
	}
	
	public void pintar(){
		int cont = 1;
		for(TDatos data: this.pintarTDatos){
			boolean cierto = true;
			String pinto = "";
			while(data != null){
				// Para coger el + o -, final
				if(cierto){
					cierto = false;
					pinto += data.getDatos().get(0)[0] + "	"; 
				}
				pinto += data.getHijoTipo() + "	";
				data = data.getPadre();
			}
			System.out.println(cont + "	" + pinto);
			cont++;
		}
	}
}
