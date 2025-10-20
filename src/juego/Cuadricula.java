package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Cuadricula {
	
	double x, y, ancho, alto, escala;
	Image im1, im2;
	Entorno e;
	
	
	public Cuadricula(double x, double y, Entorno e) {
		this.x = x;
		this.y = y;
		this.e = e;
		this.im1 = Herramientas.cargarImagen("Imagenes/pasto3.png");
		this.im2 = Herramientas.cargarImagen("Imagenes/pasto4.png");
		this.escala = 0.208;
		this.ancho = this.im1.getWidth(null)*escala;
		this.alto = this.im2.getHeight(null)*escala;
	}
	
	public void dibujar()
	{
		 for (int i = 0; i < 10; i++) {
		        for (int k = 0; k < 5; k++) {
		            double posX = this.x + i * ancho;
		            double posY = this.y + k * alto;

		            if ( (i + k) % 2 == 0 )
		                e.dibujarImagen(im1, posX, posY, 0, escala);
		            else
		                e.dibujarImagen(im2, posX, posY, 0, escala);
		        }
		    }
	
    }
	public double[] obtenerCentroCeldaMasCercana(double mouseX, double mouseY) {
	    double[] centroMasCercano = new double[2];

	    for (int i = 0; i < 10; i++) {
	        for (int k = 0; k < 5; k++) {
	            // centro de cada celda
	            double cx = x + i * ancho;
	            double cy = y + k * alto;

	            double dist = Math.sqrt(Math.pow(mouseX - cx, 2) + Math.pow(mouseY - cy, 2));

	            if (dist < this.alto/2) {
	                centroMasCercano[0] = cx;
	                centroMasCercano[1] = cy;
	            }
	        }
	    }
	    return centroMasCercano;
	}
	
	public double getAnchoCelda() {
	    return ancho;
	}

	public double getAltoCelda() {
	    return alto;
	}
	
	public void imprimirCoordenadasCeldas() {
	    int columnas = 10;
	    int filas = 5;

	    for (int fila = 0; fila < filas; fila++) {
	        for (int col = 0; col < columnas; col++) {
	            double centroX = x + col * ancho;
	            double centroY = y + fila * alto;
	            System.out.println("Celda [" + fila + "," + col + "]: X=" + centroX + " Y=" + centroY);
	        }
	    }
	}
	
	public boolean celdaOcupada(double cx, double cy, Planta[] plantas) {
		//Aca digo que siempre la columna de los regalos va a estar ocupada
		if(cx < 160 + this.ancho/2)
		{
			return true;
		}
	    for (Planta p : plantas) {
	        if (p != null && p.plantada) {
	            // Si la distancia entre el centro de la celda y la planta es muy chica,
	            // consideramos que está ocupada
	            double dist = Math.sqrt(Math.pow(p.x - cx, 2) + Math.pow(p.y - cy, 2));
	            if (dist < this.alto / 2) {
	                return true;
	            }
	        }
	    }
	    return false;
	}
	
	public boolean celdaOcupadaNuez(double cx, double cy, Nuez[] nueces) {
		//Aca digo que siempre la columna de los regalos va a estar ocupada
		if(cx < 160 + this.ancho/2)
		{
			return true;
		}
	    for (Nuez n : nueces) {
	        if (n != null && n.plantada) {
	            // Si la distancia entre el centro de la celda y la planta es muy chica,
	            // consideramos que está ocupada
	            double dist = Math.sqrt(Math.pow(n.x - cx, 2) + Math.pow(n.y - cy, 2));
	            if (dist < this.alto / 2) {
	                return true;
	            }
	        }
	    }
	    return false;
	}
	
	public boolean celdaOcupadaHuracol(double cx, double cy, Huracol[] huracoles) {
		//Aca digo que siempre la columna de los regalos va a estar ocupada
		if(cx < 160 + this.ancho/2)
		{
			return true;
		}
	    for (Huracol h : huracoles) {
	        if (h != null && h.plantada) {
	            // Si la distancia entre el centro de la celda y la planta es muy chica,
	            // consideramos que está ocupada
	            double dist = Math.sqrt(Math.pow(h.x - cx, 2) + Math.pow(h.y - cy, 2));
	            if (dist < this.alto / 2) {
	                return true;
	            }
	        }
	    }
	    return false;
	}
	
	
}
