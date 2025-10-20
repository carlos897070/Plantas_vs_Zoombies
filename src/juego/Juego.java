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
	
	Planta[] fuego;
	BolaFuego[] bolas;
	Planta plantaTomada = null;
	
	Nuez[] nueces;
	Nuez nuezTomada = null;
	
	Huracol[] huracoles;
	BolaAzul[] bolasAzul;
	Huracol huracolTomada = null;
	
	
	
	
	
	
	
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.ancho = 1200;
		this.alto = 600;
		this.entorno = new Entorno(this, "Proyecto para TP", ancho, alto);
		
		// Inicializar lo que haga falta para el juego
		// ...
		
		//Cargar Cuadricula
		this.c = new Cuadricula(160,178,entorno);
		//Info de coordenadas de cuadricula
		c.imprimirCoordenadasCeldas();
		
		//Cargar imagenes de fondo y banner superior
		this.fondoInterno = Herramientas.cargarImagen("Imagenes/casaFondo2.jpg");
		this.panelSuperior = Herramientas.cargarImagen("Imagenes/banner6.jpg");
		
		//Cargar regalos
		this.reg = new Regalo(160,178,entorno);
		
		//Plantas superPlanta
		this.huracoles = new Huracol[20];
		huracoles[0] = new Huracol(378, 84, entorno);
		
		//Plantas nueces
		this.nueces = new Nuez[20];
		nueces[0] = new Nuez(269.6, 88, entorno);
		
		//Plantas lanzaFuego
		this.fuego = new Planta[20];
		fuego[0] = new Planta(160, 68, entorno);
		
		//Cargar disparos de plantas
		this.bolas = new BolaFuego[500];
		this.bolasAzul = new BolaAzul[500];
		
		// Inicia el juego!
		this.entorno.iniciar();
		
		//Info de celdas
		double ancho = c.getAnchoCelda();
		double alto = c.getAltoCelda();
		System.out.println("Ancho de celda: " + ancho);
		System.out.println("Alto de celda: " + alto);
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
		
		//Dibujo el fondo interno, casa que se ve en el lateral izquierdo del entorno
		entorno.dibujarImagen(fondoInterno, ancho/2 -100, alto/2, 0, 1.2);
		//Dibujo panel superior
		entorno.dibujarImagen(panelSuperior, (ancho/2)+54, 60, 0, 0.57);
		//Dibujo la cuadricula
		c.dibujar();
		//Dibujo los regalos
		reg.dibujar();
		//Variable que me da el tiempo actual en segundos
		double tiempoActual = entorno.tiempo() / 1000.0; // tiempo en segundos
		
		
		// ----- Seccion huracoles -----
		
		//Dibujar huracoles
		for(Huracol h: huracoles)
		{
			if(h!=null)
			{
				h.dibujar();
			}
			
		}
		
		
		//Recorremos todas los Huracoles. Seleccionar, arrastrar y plantar una planta tipo huracol
		for (int h = 0; h < huracoles.length; h++) {
		    Huracol huracol = huracoles[h];

		    if (huracol != null)
		    {
		    	// seleccionar planta si se hace click sobre ella
			    if (entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO) && !huracol.plantada && huracolTomada == null) 
			    {

			        if (huracol.encima(entorno.mouseX(), entorno.mouseY())) 
			        {
			        	huracol.seleccion = true;
			            huracolTomada = huracol;
			        }
			    }

			    // Arrastrar planra mientras mantengo presionado el click
			    if (huracol.seleccion && entorno.estaPresionado(entorno.BOTON_IZQUIERDO)) {
			    	huracol.arrastrar(entorno.mouseX(), entorno.mouseY());
			    }

			    // Suelto planta cuando dejo de presionar el botón
			    if (huracol.seleccion && !entorno.estaPresionado(entorno.BOTON_IZQUIERDO)) {
			    	huracol.seleccion = false;

			        // Si la suelto sobre el panel (parte superior), vuelve a su lugar
			        if (entorno.mouseY() <= 20) {
			        	huracol.nuevaPosicion(378, 88);
			        	huracol.plantada = false;
		                huracolTomada = null;

			        } else {
			            // Si no, intento plantarla en la celda más cercana
			            double[] centro = c.obtenerCentroCeldaMasCercana(entorno.mouseX(), entorno.mouseY());

			            // Chequeo que la celda no esté ocupada
			            if (!c.celdaOcupadaNuez(centro[0], centro[1], nueces) && !c.celdaOcupada(centro[0], centro[1], fuego) && !c.celdaOcupadaHuracol(centro[0], centro[1], huracoles)) {
			            	huracol.nuevaPosicion(centro[0], centro[1]);
			            	huracol.plantada = true;
			             // Creo una nueva planta disponible en el panel
				            for (int i = 0; i < huracoles.length; i++) {
				                if (huracoles[i] == null) {
				                    huracoles[i] = new Huracol(378, 84, entorno);
				                    break;
				                }
				            }
			            } else {
			                // Si está ocupada, vuelve a su posición original
			            	huracol.nuevaPosicion(378, 88);
			            	huracol.plantada = false;
			                huracolTomada = null;
			            }

			            // Libero la planta tomada
			            huracolTomada = null;

			            
			        }
			    }
			}
		}
		
		
		// ----- Seccion nueces -----
		
		
		
		//Dibujar todas las nueces
		for(Nuez n: nueces)
		{
			if(n!=null)
			{
				n.dibujar();
			}
			
		}

		
		//Recorremos todas las nueces. Seleccionar, arrastrar y plantar una planta tipo nuez
		for (int n = 0; n < nueces.length; n++) {
		    Nuez nuez = nueces[n];

		    if (nuez != null)
		    {
		    	// seleccionar planta si se hace click sobre ella
			    if (entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO) && !nuez.plantada && nuezTomada == null) 
			    {

			        if (nuez.encima(entorno.mouseX(), entorno.mouseY())) 
			        {
			            nuez.seleccion = true;
			            nuezTomada = nuez;
			        }
			    }

			    // Arrastrar planra mientras mantengo presionado el click
			    if (nuez.seleccion && entorno.estaPresionado(entorno.BOTON_IZQUIERDO)) {
			        nuez.arrastrar(entorno.mouseX(), entorno.mouseY());
			    }

			    // Suelto planta cuando dejo de presionar el botón
			    if (nuez.seleccion && !entorno.estaPresionado(entorno.BOTON_IZQUIERDO)) {
			        nuez.seleccion = false;

			        // Si la suelto sobre el panel (parte superior), vuelve a su lugar
			        if (entorno.mouseY() <= c.y - c.alto) {
			            nuez.nuevaPosicion(265, 93);
			            nuez.plantada = false;
		                nuezTomada = null;

			        } else {
			            // Si no, intento plantarla en la celda más cercana
			            double[] centro = c.obtenerCentroCeldaMasCercana(entorno.mouseX(), entorno.mouseY());

			            // Chequeo que la celda no esté ocupada
			            if (!c.celdaOcupadaNuez(centro[0], centro[1], nueces) && !c.celdaOcupada(centro[0], centro[1], fuego) && !c.celdaOcupadaHuracol(centro[0], centro[1], huracoles)) {
			                nuez.nuevaPosicion(centro[0], centro[1]);
			                nuez.plantada = true;
			             // Creo una nueva planta disponible en el panel
				            for (int i = 0; i < nueces.length; i++) {
				                if (nueces[i] == null) {
				                    nueces[i] = new Nuez(269.6, 88, entorno);
				                    break;
				                }
				            }
			            } else {
			                // Si está ocupada, vuelve a su posición original
			                nuez.nuevaPosicion(265, 93);
			                nuez.plantada = false;
			                nuezTomada = null;
			            }

			            // Libero la planta tomada
			            nuezTomada = null;

			            
			        }
			    }
			}
		}
		
		
		// ----- Seccion de planta lanza fuego -----
		
		
		
		//Dibujar todas las plantas
		for(Planta p: fuego)
		{
			if(p!=null)
			{
				p.dibujar();
			}
			
		}
		
		//Recorremos todas las plantas. Seleccionar, arrastrar y plantar una planta tipo fuego
		for (int p = 0; p < fuego.length; p++) {
		    Planta planta = fuego[p];

		    if (planta != null)
		    {
		    	// seleccionar planta si se hace click sobre ella
			    if (entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO) && !planta.plantada && plantaTomada == null) 
			    {

			        if (planta.encima(entorno.mouseX(), entorno.mouseY())) 
			        {
			            planta.seleccion = true;
			            plantaTomada = planta;
			        }
			    }

			    // Arrastrar planta mientras mantengo presionado el click
			    if (planta.seleccion && entorno.estaPresionado(entorno.BOTON_IZQUIERDO)) {
			        planta.arrastrar(entorno.mouseX(), entorno.mouseY());
			    }

			    // Suelto planta cuando dejo de presionar el botón
			    if (planta.seleccion && !entorno.estaPresionado(entorno.BOTON_IZQUIERDO)) {
			       planta.seleccion = false;

			        // Si la suelto sobre el panel (parte superior), vuelve a su lugar
			        if (entorno.mouseY() <= c.y - c.alto) {
			            planta.nuevaPosicion(150, 88);
			            planta.plantada = false;
			            plantaTomada = null;
			        } 
			        else 
			        {
			            // Si no, intento plantarla en la celda más cercana
			            double[] centro = c.obtenerCentroCeldaMasCercana(entorno.mouseX(), entorno.mouseY());

			            // Chequeo que la celda no esté ocupada
			            if (!c.celdaOcupada(centro[0], centro[1], fuego) && !c.celdaOcupadaNuez(centro[0], centro[1], nueces) && !c.celdaOcupadaHuracol(centro[0], centro[1], huracoles)) {
			                planta.nuevaPosicion(centro[0], centro[1]);
			                planta.plantada = true;
			                
			              
			                
			                // Creo una nueva planta disponible en el panel
				            for (int i = 0; i < fuego.length; i++) 
				            {
				                if (fuego[i] == null) 
				                {
				                    fuego[i] = new Planta(160, 68, entorno);
				                    break;
				                }
				            }
			            } 
			            else 
			            {
			                // Si está ocupada, vuelve a su posición original
			                planta.nuevaPosicion(150, 88);
			            }

			            // Libero la planta tomada
			            plantaTomada = null;
			            
			        }
			    }
			}
		}
		
		
		// -----Seccion desplazamiento de las plantas con las teclas-------
		
		
		//Plantas de fuego
		for(int k = 0; k < fuego.length; k++)
			
		{
			Planta p = fuego[k];
			
			if(p != null && p.plantada)
			{
				double[] proximoArriba = c.obtenerCentroCeldaMasCercana(p.x, p.y - c.alto);
				double[] proximoDerecho = c.obtenerCentroCeldaMasCercana(p.x + c.ancho, p.y);
				double[] proximoAbajo = c.obtenerCentroCeldaMasCercana(p.x, p.y + c.alto);
				double[] proximoIzquiero = c.obtenerCentroCeldaMasCercana(p.x - c.ancho, p.y);
				
				if (!c.celdaOcupada(proximoArriba[0], proximoArriba[1], fuego) && !c.celdaOcupadaNuez(proximoArriba[0], proximoArriba[1], nueces) && !c.celdaOcupadaHuracol(proximoArriba[0], proximoArriba[1], huracoles))
				{
					p.desplazarArriba(proximoArriba[0], proximoArriba[1]);
				}
				
				if (!c.celdaOcupada(proximoDerecho[0], proximoDerecho[1], fuego) && !c.celdaOcupadaNuez(proximoDerecho[0], proximoDerecho[1], nueces) && !c.celdaOcupadaHuracol(proximoDerecho[0], proximoDerecho[1], huracoles))
				{
					p.desplazarDerecha(proximoDerecho[0], proximoDerecho[1]);
				}
				
				if (!c.celdaOcupada(proximoAbajo[0], proximoAbajo[1], fuego) && !c.celdaOcupadaNuez(proximoAbajo[0], proximoAbajo[1], nueces) && !c.celdaOcupadaHuracol(proximoAbajo[0], proximoAbajo[1], huracoles))
				{
					p.desplazarAbajo(proximoAbajo[0], proximoAbajo[1]);
				}
				
				if (!c.celdaOcupada(proximoIzquiero[0], proximoIzquiero[1], fuego) && !c.celdaOcupadaNuez(proximoIzquiero[0], proximoIzquiero[1], nueces) && !c.celdaOcupadaHuracol(proximoIzquiero[0], proximoIzquiero[1], huracoles))
				{
					p.desplazarIzquierda(proximoIzquiero[0], proximoIzquiero[1]);
				}
				
			
			}
		}
		
		//Plantas nueces
		for(int k = 0; k < nueces.length; k++)
					
		{
			Nuez n = nueces[k];
			
			if(n != null && n.plantada)
			{
				double[] proximoArriba = c.obtenerCentroCeldaMasCercana(n.x, n.y - c.alto);
				double[] proximoDerecho = c.obtenerCentroCeldaMasCercana(n.x + c.ancho, n.y);
				double[] proximoAbajo = c.obtenerCentroCeldaMasCercana(n.x, n.y + c.alto);
				double[] proximoIzquiero = c.obtenerCentroCeldaMasCercana(n.x - c.ancho, n.y);
				
				if (!c.celdaOcupada(proximoArriba[0], proximoArriba[1], fuego) && !c.celdaOcupadaNuez(proximoArriba[0], proximoArriba[1], nueces) && !c.celdaOcupadaHuracol(proximoArriba[0], proximoArriba[1], huracoles))
				{
					n.desplazarArriba(proximoArriba[0], proximoArriba[1]);
				}
				
				if (!c.celdaOcupada(proximoDerecho[0], proximoDerecho[1], fuego) && !c.celdaOcupadaNuez(proximoDerecho[0], proximoDerecho[1], nueces) && !c.celdaOcupadaHuracol(proximoDerecho[0], proximoDerecho[1], huracoles))
				{
					n.desplazarDerecha(proximoDerecho[0], proximoDerecho[1]);
				}
				
				if (!c.celdaOcupada(proximoAbajo[0], proximoAbajo[1], fuego) && !c.celdaOcupadaNuez(proximoAbajo[0], proximoAbajo[1], nueces) && !c.celdaOcupadaHuracol(proximoAbajo[0], proximoAbajo[1], huracoles))
				{
					n.desplazarAbajo(proximoAbajo[0], proximoAbajo[1]);
				}
				
				if (!c.celdaOcupada(proximoIzquiero[0], proximoIzquiero[1], fuego) && !c.celdaOcupadaNuez(proximoIzquiero[0], proximoIzquiero[1], nueces) && !c.celdaOcupadaHuracol(proximoIzquiero[0], proximoIzquiero[1], huracoles))
				{
					n.desplazarIzquierda(proximoIzquiero[0], proximoIzquiero[1]);
				}	
			}
		}
		
		//Plantas Huracoles
		for(int k = 0; k < huracoles.length; k++)
			
		{
			Huracol h = huracoles[k];
			
			if(h != null && h.plantada)
			{
				double[] proximoArriba = c.obtenerCentroCeldaMasCercana(h.x, h.y - c.alto);
				double[] proximoDerecho = c.obtenerCentroCeldaMasCercana(h.x + c.ancho, h.y);
				double[] proximoAbajo = c.obtenerCentroCeldaMasCercana(h.x, h.y + c.alto);
				double[] proximoIzquiero = c.obtenerCentroCeldaMasCercana(h.x - c.ancho, h.y);
				
				if (!c.celdaOcupada(proximoArriba[0], proximoArriba[1], fuego) && !c.celdaOcupadaNuez(proximoArriba[0], proximoArriba[1], nueces) && !c.celdaOcupadaHuracol(proximoArriba[0], proximoArriba[1], huracoles))
				{
					h.desplazarArriba(proximoArriba[0], proximoArriba[1]);
				}
				
				if (!c.celdaOcupada(proximoDerecho[0], proximoDerecho[1], fuego) && !c.celdaOcupadaNuez(proximoDerecho[0], proximoDerecho[1], nueces) && !c.celdaOcupadaHuracol(proximoDerecho[0], proximoDerecho[1], huracoles))
				{
					h.desplazarDerecha(proximoDerecho[0], proximoDerecho[1]);
				}
				
				if (!c.celdaOcupada(proximoAbajo[0], proximoAbajo[1], fuego) && !c.celdaOcupadaNuez(proximoAbajo[0], proximoAbajo[1], nueces) && !c.celdaOcupadaHuracol(proximoAbajo[0], proximoAbajo[1], huracoles))
				{
					h.desplazarAbajo(proximoAbajo[0], proximoAbajo[1]);
				}
				
				if (!c.celdaOcupada(proximoIzquiero[0], proximoIzquiero[1], fuego) && !c.celdaOcupadaNuez(proximoIzquiero[0], proximoIzquiero[1], nueces) && !c.celdaOcupadaHuracol(proximoIzquiero[0], proximoIzquiero[1], huracoles))
				{
					h.desplazarIzquierda(proximoIzquiero[0], proximoIzquiero[1]);
				}	
			}
		}
		
		    
		
		// ----- Seccion disparos de plantas
		
		
		//Bolas de fuego
		//Genera una bola de fuego en un determinado tiempo
		for (Planta p : fuego) {
	        if (p != null && p.plantada) {
	            if (tiempoActual - p.tiempoUltimoDisparo >= p.intervaloDisparo) {
	                for (int j = 0; j < bolas.length; j++) {
	                    if (bolas[j] == null) {
	                        bolas[j] = new BolaFuego(p.x, p.y+12, entorno);
	                        break;
	                    }
	                }
	                p.tiempoUltimoDisparo = tiempoActual;
	            }
	        }
	    }
			
		//Movimiento de disparo
		for(int k = 0; k < bolas.length; k++)
		{
			BolaFuego b = bolas[k];
			if(b != null)
			{
				b.mover();
				b.dibujar();
				 // Si sale del entorno, la eliminamos
		        if (b.x > ancho) {
		            bolas[k] = null;
		        }
			}
			
		}
		
		
		//Bola azul
		//Genera una bola azul en un determinado tiempo
		for (Huracol h : huracoles) {
	        if (h != null && h.plantada) {
	            if (tiempoActual - h.tiempoUltimoDisparo >= h.intervaloDisparo) {
	                for (int j = 0; j < bolasAzul.length; j++) {
	                    if (bolasAzul[j] == null) {
	                        bolasAzul[j] = new BolaAzul(h.x+30, h.y, entorno);
	                        break;
	                    }
	                }
	                h.tiempoUltimoDisparo = tiempoActual;
	            }
	        }
	    }
			
		//Movimiento de disparo
		for(int k = 0; k < bolasAzul.length; k++)
		{
			BolaAzul b = bolasAzul[k];
			if(b != null)
			{
				b.mover();
				b.dibujar();
				 // Si sale del entorno, la eliminamos
		        if (b.x > ancho) {
		            bolasAzul[k] = null;
		        }
			}
			
		}
		
		
		
	}
	
	
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
	
}
