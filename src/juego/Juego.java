package juego;


import java.awt.Color;
import java.awt.Image;
import java.util.Arrays;

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
	Image fondoInterno, panelSuperior, cartelInfo, zombieSuperior1;
	int ancho, alto;
	
	Regalo reg;
	Explosion[] explosiones;
	
	Planta[] fuego;
	BolaFuego[] bolas;
	Planta plantaTomada = null;
	
	Nuez[] nueces;
	Nuez nuezTomada = null;
	
	Huracol[] huracoles;
	BolaAzul[] bolasAzul;
	Huracol huracolTomada = null;
	
	Zombie[] zombies;
	ZombieBart[] zombiesBart;
	ZombieCerebro[] zombiesCerebros;
	
	//Banderas temporales para creacion y eliminacion de zombies
	double tiempoUltimoCreado, intervaloCreacion;
	double tiempoUltimoCreadoBart, intervaloCreacionBart;
	double tiempoUltimoCreadoCerebro, intervaloCreacionCerebro;
	
	int zombiesEliminados;
	int zombiesRestantes;
	
	// ---- Control de tiempo del juego ----
	double tiempoJuego;           // Tiempo jugado real (en segundos)
	double tiempoUltimoTick;      // Guarda el tiempo del entorno en el último frame
	
	//Banderas booleanas de estado del juego
	boolean jugando = true;
	boolean pausado = false;
	boolean gameOver = false;
	boolean ganaste = false;
	
	
	
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.ancho = 1200;
		this.alto = 600;
		this.entorno = new Entorno(this, "Plantas vs Zombies - Versión Iturri 1.0.1", ancho, alto);
		
		// Inicializar lo que haga falta para el juego
		// ...
		
		//Cargar Cuadricula
		this.c = new Cuadricula(160,178,entorno);
		//Info de coordenadas de cuadricula
		c.imprimirCoordenadasCeldas();
		
		//Cargar imagenes de fondo y banner superior
		this.fondoInterno = Herramientas.cargarImagen("Imagenes/casaFondo2.jpg");
		this.panelSuperior = Herramientas.cargarImagen("Imagenes/banner6.jpg");
		this.cartelInfo = Herramientas.cargarImagen("Imagenes/imagen1.png");
		this.zombieSuperior1 = Herramientas.cargarImagen("Imagenes/zoombie0.png");
		
		//Cargar regalos
		this.reg = new Regalo(160,178,entorno);
		
		//Explosiones
		this.explosiones = new Explosion[50];
		
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
		this.bolas = new BolaFuego[50];
		this.bolasAzul = new BolaAzul[50];
		
		//Variables temporales para la creacion de zombies
		this.zombies = new Zombie[20];
		this.zombiesBart = new ZombieBart[20];
		this.zombiesCerebros = new ZombieCerebro[20];
		this.intervaloCreacion = 5;
		this.tiempoUltimoCreado = 0;
		this.intervaloCreacionBart = 7;
		this.tiempoUltimoCreadoBart = 0;
		this.intervaloCreacionCerebro = 17;
		this.tiempoUltimoCreadoCerebro = 0;
		this.zombiesEliminados = 0;
		this.zombiesRestantes = 50;
		
		this.tiempoJuego = 0;
		
		// Inicia el juego!
		this.entorno.iniciar();
		//Inicio el tiempo del ultimo tick usando el tiempo del entorno
		this.tiempoUltimoTick = entorno.tiempo() / 1000.0;
		
		//Info de celdas
		double ancho = c.getAnchoCelda();
		double alto = c.getAltoCelda();
		System.out.println("Ancho de celda: " + ancho);
		System.out.println("Alto de celda: " + alto);
		
		//Imprimo info de fuentes disponibles
		System.out.println(Arrays.toString(entorno.fontDisponibles));
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
		
		// --- Actualizar el tiempo del juego ---
		double tiempoActualEntorno = entorno.tiempo() / 1000.0;
		double deltaTiempo = tiempoActualEntorno - this.tiempoUltimoTick;

		// Solo avanza si el juego está en curso
		if (jugando) {
		    this.tiempoJuego += deltaTiempo;
		}

		// Registrar el tiempo actual para el próximo frame
		this.tiempoUltimoTick = tiempoActualEntorno;
		
		
		//Variable que me da el tiempo actual en segundos
		double tiempoActual = this.tiempoJuego; // tiempo en segundos
		double tiempoActualZombie = this.tiempoJuego; // tiempo en segundos
		int tiempoMostrado = (int) this.tiempoJuego;
		
		
		
		//Dibujo el fondo interno, casa que se ve en el lateral izquierdo del entorno
		entorno.dibujarImagen(fondoInterno, ancho/2 -70, alto/2, 0, 1.35);
		
		
		// --- CONTROL DE ESTADOS DEL JUEGO ---
				Color fondoEstado = new Color(0, 0, 0, 180);

				// --- PAUSA ---
				if (pausado) {
				    entorno.dibujarRectangulo(ancho / 2, alto / 2, ancho, alto, 0, fondoEstado);
				    entorno.cambiarFont("Arial Rounded MT Bold", 70, Color.YELLOW);
				    entorno.escribirTexto("PAUSA", ancho / 2 - 120, alto / 2);
				    entorno.cambiarFont("Bookman Old Style", 30, Color.LIGHT_GRAY);
				    entorno.escribirTexto("Presiona P para continuar", ancho / 2 - 180, alto / 2 + 80);

				    // Reanudar el juego
				    if (entorno.sePresiono('p')) {
				        pausado = false;
				        jugando = true;
				    }
				    return; // Detiene el resto del tick mientras está pausado
				}

				// --- GAME OVER ---
				if (gameOver) {
				    entorno.dibujarRectangulo(ancho / 2, alto / 2, ancho, alto, 0, fondoEstado);
				    entorno.cambiarFont("Arial Rounded MT Bold", 70, Color.WHITE);
				    entorno.escribirTexto("GAME OVER", ancho / 2 - 220, alto / 2);
				    entorno.cambiarFont("Bookman Old Style", 30, Color.LIGHT_GRAY);
				    entorno.escribirTexto("Presiona R para reiniciar", ancho / 2 - 190, alto / 2 + 80);

				    if (entorno.sePresiono('r')) {
				        reiniciarJuego();
				    }
				    return;
				}

				// --- GANASTE ---
				if (ganaste) {
				    entorno.dibujarRectangulo(ancho / 2, alto / 2, ancho, alto, 0, fondoEstado);
				    entorno.cambiarFont("Arial Rounded MT Bold", 70, Color.GREEN);
				    entorno.escribirTexto("¡GANASTE!", ancho / 2 - 200, alto / 2);
				    entorno.cambiarFont("Bookman Old Style", 30, Color.LIGHT_GRAY);
				    entorno.escribirTexto("Presiona R para jugar de nuevo", ancho / 2 - 230, alto / 2 + 80);

				    if (entorno.sePresiono('r')) {
				        reiniciarJuego();
				    }
				    return;
				}

				// --- ACTIVAR PAUSA DESDE EL JUEGO ---
				if (jugando && entorno.sePresiono('p')) {
				    pausado = true;
				    jugando = false;
				    return;
				}
		
		
		
		//Dibujo panel superior
		entorno.dibujarImagen(panelSuperior, (ancho/2)+54, 60, 0, 0.57);
		//Dibujo la cuadricula
		c.dibujar();
		//Dibujo los regalos
		reg.dibujar();
		//Dibujo cartel
		entorno.dibujarImagen(cartelInfo, ancho/2 +50, 65, 0, 0.36);
		entorno.cambiarFont("Bookman Old Style", 17, Color.WHITE, entorno.NEGRITA);
		entorno.escribirTexto("Tiempo de juego: " + tiempoMostrado, ancho/2 -55, 30);
		entorno.escribirTexto("Zombies Eliminados: " + this.zombiesEliminados, ancho/2 -55, 70);
		entorno.escribirTexto("Zombies restantes: " + this.zombiesRestantes, ancho/2 -55, 110);
		
		entorno.dibujarImagen(zombieSuperior1, (c.x + c.ancho*7)-10, 80, 0, 0.2);
		
		

		// --- Verificar si algún zombie toca la casa ---
		if (jugando) {
		    for (Zombie z : zombies) {
		        if (z != null && z.x <= reg.x + 30) {
		            gameOver = true;
		            jugando = false;
		        }
		    }
		    for (ZombieBart z : zombiesBart) {
		        if (z != null && z.x <= reg.x + 30) {
		            gameOver = true;
		            jugando = false;
		        }
		    }
		    for (ZombieCerebro z : zombiesCerebros) {
		        if (z != null && z.x <= reg.x + 30) {
		            gameOver = true;
		            jugando = false;
		        }
		    }
		}
		
		
		
		
		
		
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
		
		
		
		// ---------- Seccion explosion de nueces y colision con zombies----------------
		
		for (int k = 0; k < nueces.length; k++) 
		{
			 if (nueces[k] != null && nueces[k].plantada) 
			 {
				 	//Colision con zombies comunes
			        for (int j = 0; j < zombies.length; j++) 
			        {
			            if (zombies[j] != null) 
			            {
			                double dist = Math.sqrt(Math.pow(nueces[k].x - zombies[j].x, 2) + Math.pow(nueces[k].y - zombies[j].y, 2));

			                if (dist < 30) 
			                {
			                	double[] centro = c.obtenerCentroCeldaMasCercana(nueces[k].x, nueces[k].y);
			                	
			                	for (int e = 0; e < explosiones.length; e++) 
			                	{
			                	    if (explosiones[e] == null) 
			                	    {
			                	        explosiones[e] = new Explosion(centro[0], centro[1]-40, entorno);
			                	        break;
			                	    }
			                	}
			                	nueces[k] = null;
			                	zombies[j] = null;
			                	this.zombiesEliminados++;
			                	this.zombiesRestantes--;
			                	break;
			               }
			           }
			       }
			 }
		}
		
		for (int k = 0; k < nueces.length; k++) 
		{
			 if (nueces[k] != null && nueces[k].plantada) 
			 {
				 	//Colision con zombies comunes
			        for (int j = 0; j < zombiesBart.length; j++) 
			        {
			            if (zombiesBart[j] != null) 
			            {
			                double dist = Math.sqrt(Math.pow(nueces[k].x - zombiesBart[j].x, 2) + Math.pow(nueces[k].y - zombiesBart[j].y, 2));

			                if (dist < 30) 
			                {
			                	double[] centro = c.obtenerCentroCeldaMasCercana(nueces[k].x, nueces[k].y);
			                	
			                	for (int e = 0; e < explosiones.length; e++) 
			                	{
			                	    if (explosiones[e] == null) 
			                	    {
			                	        explosiones[e] = new Explosion(centro[0], centro[1]-40, entorno);
			                	        break;
			                	    }
			                	}
			                	nueces[k] = null;
			                	zombiesBart[j] = null;
			                	this.zombiesEliminados++;
			                	this.zombiesRestantes--;
			                	break;
			               }
			           }
			       }
			 }
		}
		
		
		for (int k = 0; k < nueces.length; k++) 
		{
			 if (nueces[k] != null && nueces[k].plantada) 
			 {
				 	//Colision con zombies comunes
			        for (int j = 0; j < zombiesCerebros.length; j++) 
			        {
			            if (zombiesCerebros[j] != null) 
			            {
			                double dist = Math.sqrt(Math.pow(nueces[k].x - zombiesCerebros[j].x, 2) + Math.pow(nueces[k].y - zombiesCerebros[j].y, 2));

			                if (dist < 30) 
			                {
			                	double[] centro = c.obtenerCentroCeldaMasCercana(nueces[k].x, nueces[k].y);
			                	
			                	for (int e = 0; e < explosiones.length; e++) 
			                	{
			                	    if (explosiones[e] == null) 
			                	    {
			                	        explosiones[e] = new Explosion(centro[0], centro[1]-40, entorno);
			                	        break;
			                	    }
			                	}
			                	nueces[k] = null;
			                	zombiesCerebros[j] = null;
			                	this.zombiesEliminados++;
			                	this.zombiesRestantes--;
			                	break;
			               }
			           }
			       }
			 }
		}
		
		
		for (int e = 0; e < explosiones.length; e++) {
		    if (explosiones[e] != null) {
		        explosiones[e].dibujar(entorno);
		        if (explosiones[e].expirada()) {
		            explosiones[e] = null;
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
		
		
		
		
		// ----- Seccion Zombies ----- 
		
		
		//Zombies comunes
		if(tiempoActualZombie - this.tiempoUltimoCreado >= this.intervaloCreacion)
			
		for(int j = 0; j < zombies.length; j++)
		{
			if(zombies[j] == null)
			{
				int fila = (int) (Math.random() * 5); 
	            double yZombie = c.y + fila * c.getAltoCelda() - 10;
				
				zombies[j] = new Zombie(1200, yZombie, entorno);
				this.tiempoUltimoCreado = tiempoActualZombie;
				break;
			}
		}
		
		// Mover y dibujar todos los zombies existentes
		for (int j = 0; j < zombies.length; j++) {
		    Zombie z = zombies[j];
		    if (z != null) {
		        z.mover();
		        z.dibujar();

		        // Si sale del entorno por la izquierda, lo eliminamos
		        if (z.x < 0) {
		            zombies[j] = null;
		        }
		    }
		}
		
		
		//Zombies Cerebros
		if(tiempoActualZombie - this.tiempoUltimoCreadoCerebro >= this.intervaloCreacionCerebro)
			
			for(int j = 0; j < zombiesCerebros.length; j++)
			{
				if(zombiesCerebros[j] == null)
				{
					int fila = (int) (Math.random() * 5); 
		            double yZombie = c.y + fila * c.getAltoCelda()-10;
					
					zombiesCerebros[j] = new ZombieCerebro(1200, yZombie, entorno);
					this.tiempoUltimoCreadoCerebro = tiempoActualZombie;
					break;
				}
			}
			
			// Mover y dibujar todos los zombies existentes
			for (int j = 0; j < zombiesCerebros.length; j++) {
			    ZombieCerebro z = zombiesCerebros[j];
			    if (z != null) {
			        z.mover();
			        z.dibujar();

			        // Si sale del entorno por la izquierda, lo eliminamos
			        if (z.x < 0) {
			            zombiesCerebros[j] = null;
			        }
			    }
			}
			
			
			//Zombies Bart
			if(tiempoActualZombie - this.tiempoUltimoCreadoBart >= this.intervaloCreacionBart)
				
				for(int j = 0; j < zombiesBart.length; j++)
				{
					if(zombiesBart[j] == null)
					{
						int fila = (int) (Math.random() * 5); 
			            double yZombie = c.y + fila * c.getAltoCelda() - 15;
						
						zombiesBart[j] = new ZombieBart(1200, yZombie, entorno);
						this.tiempoUltimoCreadoBart = tiempoActualZombie;
						break;
					}
				}
				
				// Mover y dibujar todos los zombies existentes
				for (int j = 0; j < zombiesBart.length; j++) {
				    ZombieBart z = zombiesBart[j];
				    if (z != null) {
				        z.mover();
				        z.dibujar();

				        // Si sale del entorno por la izquierda, lo eliminamos
				        if (z.x < 0) {
				            zombiesBart[j] = null;
				        }
				    }
				}

		
			    
		
		// ----- Seccion disparos de plantas ------
		
		
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
			
		//Movimiento de disparo bola de fuego
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
	        for(int j = 0; j < zombies.length; j++)
	        {
	        	if(zombies[j] != null)
	        	{
	        		double dist = Math.sqrt( Math.pow(b.x - zombies[j].x, 2) + Math.pow(b.y - zombies[j].y, 2) );
	        		if(dist < 20)
	        		{
	        			entorno.dibujarImagen(b.imgDaño, b.x, b.y, 0, 0.2);
	        			zombies[j].vida -=2;
	        			if(zombies[j].vida == 0)
	        			{
	        				zombies[j] = null;
	        				this.zombiesEliminados+=1;
	        				this.zombiesRestantes--;
	        			}
	        			bolas[k] = null;
	        			      		
	        		}
	        	}
		     }
	        for(int j = 0; j < zombiesBart.length; j++)
	        {
	        	if(zombiesBart[j] != null)
	        	{
	        		double dist = Math.sqrt( Math.pow(b.x - zombiesBart[j].x, 2) + Math.pow(b.y - zombiesBart[j].y, 2) );
	        		if(dist < 20)
	        		{
	        			entorno.dibujarImagen(b.imgDaño, b.x, b.y, 0, 0.2);
	        			zombiesBart[j].vida -=2;
	        			if(zombiesBart[j].vida == 0)
	        			{
	        				zombiesBart[j] = null;
	        				this.zombiesEliminados+=1;
	        				this.zombiesRestantes--;
	        			}
	        			bolas[k] = null;
	        			      		
	        		}
	        	}
		     }
	        for(int j = 0; j < zombiesCerebros.length; j++)
	        {
	        	if(zombiesCerebros[j] != null)
	        	{
	        		double dist = Math.sqrt( Math.pow(b.x - zombiesCerebros[j].x, 2) + Math.pow(b.y - zombiesCerebros[j].y, 2) );
	        		if(dist < 20)
	        		{
	        			entorno.dibujarImagen(b.imgDaño, b.x, b.y, 0, 0.2);
	        			zombiesCerebros[j].vida -=2;
	        			if(zombiesCerebros[j].vida == 0)
	        			{
	        				zombiesCerebros[j] = null;	
	        				this.zombiesEliminados+=1;
	        				this.zombiesRestantes--;
	        			}
	        			bolas[k] = null;
	        			      		
	        		}
	        	}
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
		        
	        for(int j = 0; j < zombies.length; j++)
	        {
	        	if(zombies[j] != null)
	        	{
	        		double dist = Math.sqrt( Math.pow(b.x - zombies[j].x, 2) + Math.pow(b.y - zombies[j].y, 2) );
	        		if(dist < 20)
	        		{
	        			entorno.dibujarImagen(b.imgDaño, b.x, b.y, 0, 0.2);
	        			zombies[j].vida -=2;
	        			if(zombies[j].vida == 0)
	        			{
	        				zombies[j] = null;	
	        				this.zombiesEliminados+=1;
	        				this.zombiesRestantes--;
	        			}
	        			bolasAzul[k] = null;      		
	        		}
	        	}
		     }
	        for(int j = 0; j < zombiesBart.length; j++)
	        {
	        	if(zombiesBart[j] != null)
	        	{
	        		double dist = Math.sqrt( Math.pow(b.x - zombiesBart[j].x, 2) + Math.pow(b.y - zombiesBart[j].y, 2) );
	        		if(dist < 20)
	        		{
	        			entorno.dibujarImagen(b.imgDaño, b.x, b.y, 0, 0.2);
	        			zombiesBart[j].vida -=2;
	        			if(zombiesBart[j].vida == 0)
	        			{
	        				zombiesBart[j] = null;
	        				this.zombiesEliminados+=1;
	        				this.zombiesRestantes--;
	        			}
	        			bolasAzul[k] = null;      		
	        		}
	        	}
		     } 
	        for(int j = 0; j < zombiesCerebros.length; j++)
	        {
	        	if(zombiesCerebros[j] != null)
	        	{
	        		double dist = Math.sqrt( Math.pow(b.x - zombiesCerebros[j].x, 2) + Math.pow(b.y - zombiesCerebros[j].y, 2) );
	        		if(dist < 20)
	        		{
	        			entorno.dibujarImagen(b.imgDaño, b.x, b.y, 0, 0.2);
	        			zombiesCerebros[j].vida -=2;
	        			if(zombiesCerebros[j].vida == 0)
	        			{
	        				zombiesCerebros[j] = null;
	        				this.zombiesEliminados+=1;
	        				this.zombiesRestantes--;
	        			}
	        			bolasAzul[k] = null;
	        			      		
	        		}
	        	}
		     }
	        
		}
		}
	
		//Condicion para ganar el juego
		if (jugando && (zombiesEliminados == 50)) {
		    ganaste = true;
		    jugando = false;
		}
		
	}
	
	
	public void reiniciarJuego() {
	    // Limpio plantas, zombies, disparos y explosiones
	    Arrays.fill(fuego, null);
	    Arrays.fill(nueces, null);
	    Arrays.fill(huracoles, null);
	    Arrays.fill(bolas, null);
	    Arrays.fill(bolasAzul, null);
	    Arrays.fill(zombies, null);
	    Arrays.fill(zombiesBart, null);
	    Arrays.fill(zombiesCerebros, null);
	    Arrays.fill(explosiones, null);

	    // Reaparezco las plantas base del panel
	    fuego[0] = new Planta(160, 68, entorno);
	    nueces[0] = new Nuez(269.6, 88, entorno);
	    huracoles[0] = new Huracol(378, 84, entorno);

	    // Reseteo variables
	    this.zombiesEliminados = 0;
	    this.tiempoUltimoCreado = 0;
	    this.tiempoUltimoCreadoBart = 0;
	    this.tiempoUltimoCreadoCerebro = 0;
	    this.zombiesRestantes = 50;
	    tiempoJuego = 0;
	    tiempoUltimoTick = entorno.tiempo() / 1000.0;

	 // Restauro banderas
	    jugando = true;
	    pausado = false;
	    gameOver = false;
	    ganaste = false;
	}
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
	
}
