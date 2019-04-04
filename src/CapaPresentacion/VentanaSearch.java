package CapaPresentacion;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import CapaNegocio.AlgoritmoLogica;

public class VentanaSearch extends JFrame{
	//private JLabel mensaje;
	//private JTextField informacion;
	private JLabel respuesta;
	
	// Faltan estos botones
	private JButton buscar;
	private JButton volverVentana;
	
	private AlgoritmoLogica logica;
	private VentanaPrinicpal prin;

	
	public VentanaSearch(AlgoritmoLogica logica, VentanaPrinicpal prin, String[]titulos){
		this.logica = logica;
		this.prin = prin;
		init(titulos);
	}
	private void init(String[]titulos){

		this.setTitle("Buscar rama");
		this.setLocationRelativeTo(null);
		// this.setResizable(false);
		
		this.setSize(600, 400);
		this.setLocation(400, 200);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		
		
		this.respuesta = new JLabel("");
			
			
		//panel.add(this.mensaje);
		//panel.add(this.informacion);
		ArrayList<JPanel> lista = generarPaneles();
		for(JPanel miniPanel: lista){
			panel2.add(miniPanel);
		}
		this.add(panel2, BorderLayout.CENTER);
		
		this.add(this.respuesta, BorderLayout.WEST);
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		
		this.buscar = new JButton("Buscar");
		this.buscar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String result = "";
				String [] aux = new String [logica.getTitulos().length-1];
				JPanel panelPrueba = (JPanel) panel2.getComponent(0);
				JComboBox opcion = (JComboBox) panelPrueba.getComponent(1);
				aux[0] = (String) opcion.getSelectedItem();
				System.out.println("Salio: "+aux[0]);
				
				/*if (logica.search(aux, result)) {
					respuesta.setText(result);
				} else {
					respuesta.setText("No se encontro ninguna rama con esas condiciones");
				}*/
			}
		});
		this.volverVentana = new JButton("Volver");
		this.volverVentana.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				prin.setVisible(true);
			}
		});
		panel.add(this.buscar);
		panel.add(this.volverVentana);
		
		this.add(panel, BorderLayout.SOUTH);
		
		this.setVisible(true);
	}
	// Para guardar todos los posibles valores
	private HashMap<String, ArrayList<String>> dameHashMap(){
		HashMap<String, ArrayList<String>> result = new HashMap<>();
		ArrayList<String> pequeno;
		HashMap<String, Integer> local;
		
		for(int j = 0; j < this.logica.getTitulos().length-1; j++){
			pequeno = new ArrayList<>();
			local = new HashMap<>();
			for(int i = 0; i < this.logica.getTablaDatos().size(); i++){
				if(!local.containsKey(this.logica.getTablaDatos().get(i)[j])){
					local.put(this.logica.getTablaDatos().get(i)[j], 1);
					pequeno.add(this.logica.getTablaDatos().get(i)[j]);
				}
			}
			result.put(this.logica.getTitulos()[j], pequeno);
		}
		
		
		return result;
	}
	
	private ArrayList<JPanel> generarPaneles(){
		HashMap<String, ArrayList<String>> listar = dameHashMap();
		ArrayList<JPanel> result = new ArrayList<>();
		JPanel panel;
		JLabel campo;
		JComboBox opciones;
		
		
		for(int i = 0; i < this.logica.getTitulos().length-1; i++){
			String clave = this.logica.getTitulos()[i];
			panel = new JPanel();
			panel.setLayout(new FlowLayout());
			
			campo = new JLabel(clave);
			opciones = new JComboBox<>();
			for(String palabra: listar.get(clave)){
				opciones.addItem(palabra);
			}
			panel.add(campo);
			panel.add(opciones);
			result.add(panel);
		}
		
		
		return result;
	}
}
