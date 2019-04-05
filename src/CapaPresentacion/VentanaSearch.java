package CapaPresentacion;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import CapaNegocio.AlgoritmoLogica;

public class VentanaSearch extends JFrame{
	private JLabel respuesta;
	private JLabel icono;
	
	private JButton buscar;
	private JButton volverVentana;
	
	private AlgoritmoLogica logica;
	private VentanaPrinicpal prin;
	private ArrayList<JPanel> todosLosPaneles;

	
	public VentanaSearch(AlgoritmoLogica logica, VentanaPrinicpal prin){
		this.logica = logica;
		this.prin = prin;
		init();
	}
	private void init(){

		this.setTitle("Buscar rama");
		this.setLocationRelativeTo(null);
		
		this.setSize(800, 400);
		this.setLocation(400, 200);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());

		// Genera todos los JComboBox y JLabel dentro de un JPanel necesarios para la eleccion de los datos
		this.todosLosPaneles = generarPaneles();
		for(JPanel miniPanel: this.todosLosPaneles){
			panel2.add(miniPanel);
		}
		this.add(panel2, BorderLayout.NORTH);
		JPanel panel3 = new JPanel();
		panel3.setLayout(new FlowLayout());
		
		this.respuesta = new JLabel("");
		this.icono = new JLabel("");
		panel3.add(this.icono);
		panel3.add(this.respuesta);
		this.add(panel3, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		
		this.buscar = new JButton("Buscar");
		this.buscar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				HashMap<String, Integer> aux = new HashMap<>();
				// Hago este HashMap, para posteriormente hacer una busqueda sobre él
				for(int i = 0; i < todosLosPaneles.size(); i++){
					JPanel panelPrueba = (JPanel) panel2.getComponent(i);
					JComboBox opcion = (JComboBox) panelPrueba.getComponent(1);
					aux.put((String) opcion.getSelectedItem(), 1);
				}
				
				// Llamada para ver si la rama existe con esos datos
				if (logica.search(aux)) {				
					ImageIcon icon = new ImageIcon("images/resultado.png");
					icono.setIcon(icon);
					
					respuesta.setText(logica.getRespuesta());	
					respuesta.setFont(new Font("arial",Font.PLAIN, 30));
				} else {
					ImageIcon icon = new ImageIcon("images/mal.png");
					icono.setIcon(icon);
					respuesta.setText("No se encontro ninguna rama con esas condiciones");
				}
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
	// Para guardar todos los posibles valores del arbol
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
	
	// Metodo que genera todos los JComboBox y JLabel 
	// dentro de un JPanel necesarios para la eleccion de los datos
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
			// Meto los datos en el JComboBox
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
