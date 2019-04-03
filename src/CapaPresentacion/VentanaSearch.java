package CapaPresentacion;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import CapaNegocio.AlgoritmoLogica;

public class VentanaSearch extends JFrame{
	private JLabel mensaje;
	private JTextField informacion;
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
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		
		
		this.mensaje = new JLabel("Introduce los datos para la busqueda:");
		// Poner un ejemplo: condicion1:condicion2:condicion3
		this.informacion = new JTextField("Ejemplo: condicion1:condicion2:condicion3");
		// Borra el texto cuando hago click
		this.informacion.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
                informacion.setText("");
            }
		});
		
		this.respuesta = new JLabel("");
		
		
		this.informacion.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String cadena = informacion.getText();
				String [] aux = cadena.split(":");
				boolean bien = true;
				// String nombre de la condicion y Integer: numero de veces que aparece la condicion
				HashMap<String, Integer> comprobar= new HashMap<>();
				for(int i = 0; i < aux.length && bien; i++){
					if(!comprobar.containsKey(aux[i])){
						comprobar.put(aux[i], 1);
					}
					else{
						bien = false;
					}
				}
				
				
				if(bien){
					if(cadena.length() == titulos.length-1){
						String result = "";
						if(logica.search(aux, result)){
							respuesta.setText(result);
						}
						else{
							respuesta.setText("No se encontro ninguna rama con esas condiciones");
						}
					}
					else{
						String res = "Pusiste mas condiciones de las permitidas"+System.lineSeparator()+"Max: "+(titulos.length-1);
						JOptionPane.showMessageDialog(null, res);
					}
				}
				else{
					String res = "Repetiste 2 veces la misma condicion";
					JOptionPane.showMessageDialog(null, res);
				}
				
			}
		});
		panel.add(this.mensaje);
		panel.add(this.informacion);
		this.add(panel, BorderLayout.CENTER);
		
		this.add(this.respuesta, BorderLayout.SOUTH);
		this.setVisible(true);
	}
}
