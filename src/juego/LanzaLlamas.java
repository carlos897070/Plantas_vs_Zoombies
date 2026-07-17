package juego;

import java.awt.Color;

import entorno.Entorno;
import entorno.Herramientas;

public class LanzaLlamas extends Planta  {
	
	BolaFuego[] bolas;
	
	
	public LanzaLlamas(double x, double y, Entorno e) {
		
		this.x = x;
		this.y = y;
		this.e = e;
		this.escala	= 0.8;
		this.img = Herramientas.cargarImagen("Imagenes/Lanzafuego.gif");
		this.seleccion = false;
		this.plantada = false;
		this.tiempoUltimoDisparo = 0;
		this.intervaloDisparo = 1.5;
		this.seleccionadaParaMover = false;
		this.colorSeleccion = new Color(250, 0, 0, 120);
		this.dxA = 0;
		this.vida = 20;
		this.disparando = false;
		this.bolas = new BolaFuego[80];
	}
	
	
	//Metodo para dibujar la planta
	public void dibujar()
	{
		/*if(this.seleccion)
		{
			e.dibujarRectangulo(this.x-8, this.y+21, 109.616, 93.6, this.rotacionSeleccion() , colorSeleccion);
		}*/
		if (this.seleccionadaParaMover) 
		{
	        e.dibujarRectangulo(this.x-8, this.y+21, 109.616, 93.6, 0, colorSeleccion);
	    }
		e.dibujarImagen(img, this.x, this.y, 0, this.escala);
	}
	
	
	
	//Metodo para cambiar posicion de una planta
	public void nuevaPosicion(double nuevaX, double nuevaY) {
	    this.x = nuevaX +10;
	    this.y = nuevaY - 20;
	}
	
	public void disparar()
	{
		for (int j = 0; j < bolas.length; j++) {
            if (bolas[j] == null) {
                bolas[j] = new BolaFuego(this.x, this.y+12, e);
                break;
            }
            
        }
	}
	
	
	

}
