package juego;


import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	// Variables y métodos propios de cada grupo
	// ...
	
	private Entorno entorno;
	Cuadricula c;
	Image fondoInterno, panelSuperior;
	int ancho, alto;
	Regalo reg;
	
	
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.ancho = 1200;
		this.alto = 600;
		this.entorno = new Entorno(this, "Proyecto para TP", ancho, alto);
		
		// Inicializar lo que haga falta para el juego
		// ...
		this.c = new Cuadricula(160,178,entorno);
		this.fondoInterno = Herramientas.cargarImagen("Imagenes/casaFondo2.jpg");
		this.panelSuperior = Herramientas.cargarImagen("Imagenes/banner6.jpg");
		this.reg = new Regalo(160,178,entorno);

		// Inicia el juego!
		this.entorno.iniciar();
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick()
	{
		// Procesamiento de un instante de tiempo
		// ...
		entorno.dibujarImagen(fondoInterno, ancho/2 -100, alto/2, 0, 1.2);
		entorno.dibujarImagen(panelSuperior, (ancho/2)+54, 60, 0, 0.57);
		c.dibujar();
		//entorno.dibujarImagen(regalo, ancho/2, 300, 0, 0.3);
		reg.dibujar();
		
	}
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
