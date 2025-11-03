package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Regalo {

	double x, y, escala;
	Image img;
	Entorno e;
	
	
	public Regalo(double x, double y, Entorno e) {
		this.x = x;
		this.y = y;
		this.e = e;
		this.escala = 0.2;
		this.img = Herramientas.cargarImagen("Imagenes/regalo2.gif");
	}
		
		public void dibujar()
		{
			for (int k = 0; k < 5; k++) 
			{
	            double posY = this.y + k * 93.6;
	            e.dibujarImagen(img, this.x, posY, 0, escala);
			}
		}
	
}
