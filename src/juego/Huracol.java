package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Huracol {

	double x, y, escala, tiempoUltimoDisparo, intervaloDisparo, dxA, vida;
	Image img;
	Entorno e;
	boolean seleccion, plantada, seleccionadaParaMover, disparando;
	Color colorSeleccion;
	
	public Huracol(double x, double y, Entorno e) {
		
		this.x = x;
		this.y = y;
		this.e = e;
		this.img = Herramientas.cargarImagen("Imagenes/plantaHuracol.gif");
		this.escala = 0.7;
		this.seleccion = false;
		this.plantada = false;
		this.tiempoUltimoDisparo = 0;
		this.intervaloDisparo = 1.5;
		this.colorSeleccion = new Color(0, 250, 0, 100);
		this.dxA = 0;
		this.vida = 20;
		this.disparando = false;
	}
	
	public double rotacionSeleccion()
	{
		return this.dxA += 0.01;
	}
	
	public void dibujar()
	{
		if(this.seleccion)
		{
			e.dibujarRectangulo(this.x+0.4, this.y+5.5, 109.616, 93.6, this.rotacionSeleccion() , colorSeleccion);
		}
		if (this.seleccionadaParaMover)
		{
	        e.dibujarRectangulo(this.x, this.y+5, 109.616, 93.6, 0, colorSeleccion);
	    }
		e.dibujarImagen(img, this.x, this.y, 0, escala);
	}
	
	//Metodo para arrastrar la nuez
	public void arrastrar(double x,double y) {
			this.x=x;
			this.y=y;
		
    }
		
	//Metodo para saber si el mause esta encima de una planta
	boolean encima(double mx, double my) {
		
		double dist = Math.sqrt(Math.pow(mx - this.x, 2) + Math.pow(my - this.y, 2));  

	    return dist < 30;
	}
	
	public void nuevaPosicion(double nuevaX, double nuevaY) {
	    this.x = nuevaX ;
	    this.y = nuevaY - 5;
	}
	
	public void desplazarArriba(double nx, double ny)
	{
		if(this.encima(e.mouseX(), e.mouseY()) && e.sePresionoBoton(e.BOTON_DERECHO))
		{
			this.seleccionadaParaMover = true;
			
		}
		if( ( !this.encima(e.mouseX(), e.mouseY()) ) && ( e.sePresionoBoton(e.BOTON_DERECHO) || e.sePresionoBoton(e.BOTON_IZQUIERDO) ) )
		{
			this.seleccionadaParaMover = false;
		}
		
		if(this.seleccionadaParaMover)
		{
			if(e.sePresiono('w') )
			{
				this.nuevaPosicion(nx, ny);
			}
		}
	}
	
	public void desplazarAbajo(double nx, double ny)
	{
		if(this.encima(e.mouseX(), e.mouseY()) && e.sePresionoBoton(e.BOTON_DERECHO))
		{
			this.seleccionadaParaMover = true;
			
		}
		if( ( !this.encima(e.mouseX(), e.mouseY()) ) && ( e.sePresionoBoton(e.BOTON_DERECHO) || e.sePresionoBoton(e.BOTON_IZQUIERDO) ) )
		{
			this.seleccionadaParaMover = false;
		}
		
		if(this.seleccionadaParaMover)
		{
			if(e.sePresiono('s') )
			{
				this.nuevaPosicion(nx, ny);
			}	
		}
	}
	
	public void desplazarDerecha(double nx, double ny)
	{
		if(this.encima(e.mouseX(), e.mouseY()) && e.sePresionoBoton(e.BOTON_DERECHO))
		{
			this.seleccionadaParaMover = true;
			
		}
		if( ( !this.encima(e.mouseX(), e.mouseY()) ) && ( e.sePresionoBoton(e.BOTON_DERECHO) || e.sePresionoBoton(e.BOTON_IZQUIERDO) ) )
		{
			this.seleccionadaParaMover = false;
		}
		
		if(this.seleccionadaParaMover)
		{
			if(e.sePresiono('d') )
			{
				this.nuevaPosicion(nx, ny);
			}	
		}
	}
	
	public void desplazarIzquierda(double nx, double ny)
	{
		if(this.encima(e.mouseX(), e.mouseY()) && e.sePresionoBoton(e.BOTON_DERECHO))
		{
			this.seleccionadaParaMover = true;
			
		}
		if( ( !this.encima(e.mouseX(), e.mouseY()) ) && ( e.sePresionoBoton(e.BOTON_DERECHO) || e.sePresionoBoton(e.BOTON_IZQUIERDO) ) )
		{
			this.seleccionadaParaMover = false;
		}
		
		if(this.seleccionadaParaMover)
		{
			if(e.sePresiono('a') )
			{
				this.nuevaPosicion(nx, ny);
			}	
		}
	}
	
	
}
