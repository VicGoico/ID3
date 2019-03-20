package CapaPresentacion;

import java.awt.Image;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Mensaje extends JFrame{
	
	private JPanel centro;
	
	private JLabel texto;
	private JLabel dibujo;
	
	private JButton continuar;
	

	public Mensaje(boolean result){
		if(result){
			// Texto: Los ficheros se cargaron correctamente
			initOK();
		}
		else{
			// Texto: Los ficheros fallaron
			initKO();
		}
	}


	private void initKO() {
		
		
	}


	private void initOK() {
		// TODO Auto-generated method stub
		
	}
}
