package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Nuez {

	double x, y, escala, dxA;
	Image img, exp;
	Entorno e;
	boolean seleccion, plantada, seleccionadaParaMover;
	Color colorSeleccion;
	
	public Nuez(double x, double y, Entorno e) {
		this.x = x;
		this.y = y;
		this.e = e;
		this.img = Herramientas.cargarImagen("Imagenes/nuez.gif");
		this.exp = Herramientas.cargarImagen("Imagenes/explosion.gif");
		this.escala = 0.29;
		this.seleccion = false;
		this.plantada = false;
		this.colorSeleccion = new Color(200, 200, 20, 120);
		this.dxA = 0;
	}
	
	public double rotacionSeleccion()
	{
		return this.dxA += 0.01;
	}
	
	public void dibujar()
	{
		if(this.seleccion)
		{
			e.dibujarRectangulo(this.x-3, this.y+5, 109.616, 93.6, this.rotacionSeleccion() , colorSeleccion);
		}
		if (this.seleccionadaParaMover) 
		{
	        e.dibujarRectangulo(this.x-3, this.y+5, 109.616, 93.6, 0, colorSeleccion);
	    }
		e.dibujarImagen(img, this.x, this.y, 0, escala);
	}
	
	//Metodo para arrastrar la nuez
		void arrastrar(double x,double y) {
			this.x=x;
			this.y=y;
		
    }
		
	//Metodo para saber si el mause esta encima de una planta
	boolean encima(double mx, double my) {
	    double anchoMitad = (img.getWidth(null) * escala) / 2;
	    double altoMitad = (img.getHeight(null) * escala) / 2;

	    return (mx > x - anchoMitad && mx < x + anchoMitad &&
	            my > y - altoMitad && my < y + altoMitad);
	}
	
	public void nuevaPosicion(double nuevaX, double nuevaY) {
	    this.x = nuevaX +5;
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
