package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Planta {

	double x, y, escala, tiempoUltimoDisparo, intervaloDisparo, dxA, vida;
	Entorno e;
	Image img;
	boolean seleccion, plantada, seleccionadaParaMover, disparando;
	Color colorSeleccion;
	
	public double rotacionSeleccion()
	{
		return this.dxA += 0.01;
	}
	
	//Metodo para arrastrar la planta
	void arrastrar(double x,double y) {
		this.x=x;
		this.y=y;
	
	}
	
	//Metodo para saber si el mause esta encima de una planta
	public boolean encima(double mx, double my) {
	    
	    double dist = Math.sqrt(Math.pow(mx - this.x, 2) + Math.pow(my - this.y, 2));  

	    return dist < 30;
	    
	}
	
	//Metodo para cambiar posicion de una planta
	public void nuevaPosicion(double nuevaX, double nuevaY) {
	    this.x = nuevaX;
	    this.y = nuevaY;
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
		if( this.encima(e.mouseX(), e.mouseY()) && e.sePresionoBoton(e.BOTON_DERECHO) )
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
