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
	
	Image fondoInterno, panelSuperior, cartelInfo, 
	zombieSuperior1, zombieSuperior2, zombieSuperior3, 
	recarga, cargandoHuracol, cargandoFuego, cargandoNuez, cargandoCarbonopodo;
	
	int ancho, alto;
	
	Regalo reg;
	Explosion[] explosiones;
	
	LanzaLlamas[] fuego;
	BolaFuego[] bolas;
	LanzaLlamas plantaTomada = null;
	
	Nuez[] nueces;
	Nuez nuezTomada = null;
	
	Huracol[] huracoles;
	BolaAzul[] bolasAzul;
	Huracol huracolTomada = null;
	
	Carbonopodo[] guisantes;
	BolaCarbon[] bolasCarbon;
	Carbonopodo guisanteTomado = null;
	
	Zombie[] zombies;
	ZombieBart[] zombiesBart;
	ZombieCerebro[] zombiesCerebros;
	ProyectilZombie[] portales;
	ZombieColosal colosal;
	
	
	Tumba [] tumbas;
	ProyectilColosal [] energia;
	
	//Banderas temporales para creacion y eliminacion de zombies
	double tiempoUltimoCreado, intervaloCreacion;
	double tiempoUltimoCreadoBart, intervaloCreacionBart;
	double tiempoUltimoCreadoCerebro, intervaloCreacionCerebro;
	double tiempoExplosionColosal, tiempoParaCrearColosal;
	
	int zombiesEliminados, zombiesRestantes, zombiesCreados;
	
	
	// ---- Control de tiempo del juego ----
	double tiempoJuego;           // Tiempo jugado real (en segundos)
	double tiempoUltimoTick;      // Guarda el tiempo del entorno en el último frame
	
	// Control de recarga de planta en panel
	double ultimaPlantadaHuracol, intervaloPlantarHuracol, 
	ultimaPlantadaPlanta, intervaloPlantarPlanta, 
	ultimaPlantadaNuez, intervaloPlantarNuez,
	ultimaPlantadaGuisante, intervaloPlantarGuisante;
	
	boolean esperandoRecargaHuracol, esperandoRecargaPlanta, esperandoRecargaNuez, esperandoRecargaGuisante;
	boolean colosalCreado, anuncioColosal;
	
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
		//c.imprimirCoordenadasCeldas();
		
		//Cargar imagenes de fondo y banner superior
		this.fondoInterno = Herramientas.cargarImagen("Imagenes/casaFondo2.jpg");
		this.panelSuperior = Herramientas.cargarImagen("Imagenes/banner6.jpg");
		this.cartelInfo = Herramientas.cargarImagen("Imagenes/imagen1.png");
		
		// Imagenes superiores de los zombies
		this.zombieSuperior1 = Herramientas.cargarImagen("Imagenes/zoombie0.png");
		this.zombieSuperior2 = Herramientas.cargarImagen("Imagenes/bartZombiePng.png");
		this.zombieSuperior3 = Herramientas.cargarImagen("Imagenes/cerebroZombiePng.png");
		
		// Imagen superior de spiner de carga
		this.recarga = Herramientas.cargarImagen("Imagenes/loading2.gif");
		// Imagenes superiores de las plantas en recarga
		this.cargandoHuracol = Herramientas.cargarImagen("Imagenes/huracolCargando.png");
		this.cargandoNuez = Herramientas.cargarImagen("Imagenes/nuezCargando.png");
		this.cargandoFuego = Herramientas.cargarImagen("Imagenes/fuegoCargando.png");
		this.cargandoCarbonopodo = Herramientas.cargarImagen("Imagenes/carbonopodoCargando.png");
		
		//Cargar regalos
		this.reg = new Regalo(160,178,entorno);
		
		//Explosiones
		this.explosiones = new Explosion[10];
		
		//Plantas superPlanta
		this.huracoles = new Huracol[20];
		huracoles[0] = new Huracol(378, 84, entorno);
		
		//Plantas nueces
		this.nueces = new Nuez[20];
		nueces[0] = new Nuez(269.6, 88, entorno);
		
		//Plantas lanzaFuego
		this.fuego = new LanzaLlamas[20];
		fuego[0] = new LanzaLlamas(170, 68, entorno);
		
		this.guisantes = new Carbonopodo[20];
		guisantes[0] = new Carbonopodo(491, 85, entorno);
		
		//Cargar disparos de plantas
		this.bolas = new BolaFuego[80];
		this.bolasAzul = new BolaAzul[80];
		this.bolasCarbon = new BolaCarbon[80];
		this.portales = new ProyectilZombie[30];
		
		
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
		this.zombiesCreados = 0;
		this.colosalCreado = false;
		this.tiempoExplosionColosal = 0;
		this.anuncioColosal = false;
		this.tiempoParaCrearColosal = 0;
		
		//this.colosal = new ZombieColosal(1400, alto/2+30, entorno);
		this.energia = new ProyectilColosal[10];
		
		// Arreglo de tumbas
		this.tumbas = new Tumba[20];
		
		// Variables de control para creacion de plantas
		this.ultimaPlantadaHuracol = 0;
		this.intervaloPlantarHuracol = 7;
		this.esperandoRecargaHuracol = false;
		
		this.ultimaPlantadaPlanta = 0;
		this.intervaloPlantarPlanta = 7;
		this.esperandoRecargaPlanta= false;
		
		this.ultimaPlantadaNuez = 0;
		this.intervaloPlantarNuez = 12;
		this.esperandoRecargaNuez = false;
		
		this.ultimaPlantadaGuisante = 0;
		this.intervaloPlantarGuisante = 15;
		this.esperandoRecargaGuisante = false;
		
		//Tiempo de juego transcurrido
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
		
		
		
		//Dibujo el fondo interno, casa que se ve en el lateral izquierdo del entorno y se muestra como fondo en los estados pausa, game over y ganaste
		
		entorno.dibujarImagen(fondoInterno, ancho/2 -70, alto/2, 0, 1.35);
		
		
		// ------------------ CONTROL DE ESTADOS DEL JUEGO ----------------------
		
		Color fondoEstado = new Color(0, 0, 0, 180);

		// --------------- PAUSA -----------------
		
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

		// ---------------- GAME OVER -----------------------
		
		if (gameOver) {
		    entorno.dibujarRectangulo(ancho / 2, alto / 2, ancho, alto, 0, fondoEstado);
		    entorno.cambiarFont("Arial Rounded MT Bold", 70, Color.MAGENTA);
		    entorno.escribirTexto("GAME OVER", ancho / 2 - 220, alto / 2);
		    entorno.cambiarFont("Bookman Old Style", 30, Color.LIGHT_GRAY);
		    entorno.escribirTexto("Presiona R para reiniciar", ancho / 2 - 190, alto / 2 + 80);

		    if (entorno.sePresiono('r')) {
		        reiniciarJuego();
		    }
		    return;
		}

		// ------------------- GANASTE ----------------------
		
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

		// ------------------ ACTIVAR PAUSA DESDE EL JUEGO ----------------
		
		if (jugando && entorno.sePresiono('p')) {
		    pausado = true;
		    jugando = false;
		    return;
		}
		
		
		// ------------------- Dibujar cesped, panel superior e imagenes superiores ---------------------
		
		//Dibujo panel superior
		entorno.dibujarImagen(panelSuperior, (ancho/2)+54, 60, 0, 0.57);
		//Dibujo la cuadricula
		c.dibujar();
		//Dibujo los regalos
		reg.dibujar();
		
		
		// Dibujar rectangulos contenedores de imagen superior de zombies
		Color recZombie = new Color(80, 20, 80, 200);
		Color recZombie1 = new Color(180, 50, 0, 200);
		Color recZombie2 = new Color(180, 60, 80, 200);
		
		entorno.dibujarRectangulo((c.x + c.ancho*7), 65, c.ancho, c.y-(c.alto/2), 0, recZombie);
		entorno.dibujarRectangulo((c.x + c.ancho*8)+0.2, 65, c.ancho, c.y-(c.alto/2), 0, recZombie1);
		entorno.dibujarRectangulo((c.x + c.ancho*9), 65, c.ancho, c.y-(c.alto/2), 0, recZombie2);
		
		// Dibujar imagenes superiores de zombies
		entorno.dibujarImagen(zombieSuperior1, (c.x + c.ancho*7)-10, 80, 0, 0.2);
		entorno.dibujarImagen(zombieSuperior2, (c.x + c.ancho*8)-5, 80, 0, 0.2);
		entorno.dibujarImagen(zombieSuperior3, (c.x + c.ancho*9), 85, 0, 0.3);
		
		// Dibujar rectangulos contenedores de imagen superior de plantas
		Color recFuego = new Color(180, 20, 30, 200);
		Color recNuez = new Color(180, 180, 30, 200);
		Color recHuracol = new Color(20, 160, 20, 200);
		Color recCarbon = new Color(240, 240, 240, 200);
		
		entorno.dibujarRectangulo((c.x), 65, c.ancho, c.y-(c.alto/2), 0, recFuego);
		entorno.dibujarRectangulo((c.x + c.ancho*1), 65, c.ancho, c.y-(c.alto/2), 0, recNuez);
		entorno.dibujarRectangulo((c.x + c.ancho*2), 65, c.ancho, c.y-(c.alto/2), 0, recHuracol);
		entorno.dibujarRectangulo((c.x + c.ancho*3), 65, c.ancho, c.y-(c.alto/2), 0, recCarbon);
		
		
		//Dibujo cartel de info de juego
		
		entorno.dibujarImagen(cartelInfo, ancho/2 +50, 65, 0, 0.36);
		entorno.cambiarFont("Arial Rounded MT Bold", 17, Color.YELLOW);
		entorno.escribirTexto("Tiempo de juego: " + tiempoMostrado, ancho/2 -50, 30);
		entorno.escribirTexto("Zombies Eliminados: " + this.zombiesEliminados, ancho/2 -50, 70);
		entorno.escribirTexto("Zombies restantes: " + this.zombiesRestantes, ancho/2 -50, 110);
				
		
		
		
		
		// ------------------- Anuncio del zombie Colosal -------------------------
		
		if(this.zombiesEliminados == 4)
		{
			this.tiempoParaCrearColosal = this.tiempoJuego;
		}
		
		
		if(this.zombiesEliminados >= 50 && !this.anuncioColosal && this.tiempoJuego - this.tiempoParaCrearColosal >= 5)
		{
			entorno.dibujarRectangulo(ancho / 2, alto / 2, ancho, alto, 0, fondoEstado);
		    entorno.cambiarFont("Arial Rounded MT Bold", 50, Color.BLUE);
		    entorno.escribirTexto("¡Lograste matar a todos los zombies!", 160, alto / 2);
		    entorno.cambiarFont("Bookman Old Style", 30, Color.LIGHT_GRAY);
		    entorno.escribirTexto("Es momento de enfrentar al zombie colosal", ancho / 2 - 280, alto / 2 + 60);
		    entorno.cambiarFont("Bookman Old Style", 25, Color.RED);
		    entorno.escribirTexto("Cuando estes listo, presioná C", ancho / 2 - 160, alto / 2 + 110);
		    
		    if(entorno.sePresiono('c'))
		    {
		    	anuncioColosal = true;
		    	this.tiempoParaCrearColosal = tiempoJuego;
		    }
		    return;
		}
		

		// ------------------ Verificar si algún zombie toca la casa --------------------
		
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
		    if(this.colosal != null && this.colosal.x < 320)
		    {
		    	gameOver = true;
	            jugando = false;
		    }
		}
		
		
		
		// --------------- Seccion de planta lanza fuego ------------------
		
		
		
		//Dibujar todas las plantas
		for(LanzaLlamas p: fuego)
		{
			if(p!=null)
			{
				p.dibujar();
			}
			
		}
		
		if(esperandoRecargaPlanta)
		{
			entorno.dibujarImagen(recarga, 170, 25, 0, 0.1);
			entorno.dibujarImagen(cargandoFuego, 170, 68, 0, 0.8);
		}
		
		//Recorremos todas las plantas. Seleccionar, arrastrar y plantar una planta tipo fuego
		for (int p = 0; p < fuego.length; p++) {
		    LanzaLlamas lanzaLlamas = fuego[p];

		    if (lanzaLlamas != null)
		    {
		    	// seleccionar planta si se hace click sobre ella
			    if (entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO) && !lanzaLlamas.plantada && plantaTomada == null) 
			    {

			        if (lanzaLlamas.encima(entorno.mouseX(), entorno.mouseY())) 
			        {
			            lanzaLlamas.seleccion = true;
			            plantaTomada = lanzaLlamas;
			        }
			    }

			    // Arrastrar planta mientras mantengo presionado el click
			    if (lanzaLlamas.seleccion && entorno.estaPresionado(entorno.BOTON_IZQUIERDO)) {
			        lanzaLlamas.arrastrar(entorno.mouseX(), entorno.mouseY());
			    }

			    // Suelto planta cuando dejo de presionar el botón
			    if (lanzaLlamas.seleccion && !entorno.estaPresionado(entorno.BOTON_IZQUIERDO)) {
			       lanzaLlamas.seleccion = false;

			        // Si la suelto sobre el panel (parte superior), vuelve a su lugar
			        if (entorno.mouseY() <= c.y - c.alto) {
			            lanzaLlamas.nuevaPosicion(150, 88);
			            lanzaLlamas.plantada = false;
			            plantaTomada = null;
			        } 
			        else 
			        {
			            // Si no, intento plantarla en la celda más cercana
			            double[] centro = c.obtenerCentroCeldaMasCercana(entorno.mouseX(), entorno.mouseY());

			            // Chequeo que la celda no esté ocupada
			            if (!celdaOcupada(centro[0], centro[1])) {
			                lanzaLlamas.nuevaPosicion(centro[0], centro[1]);
			                lanzaLlamas.plantada = true;
			                this.ultimaPlantadaPlanta = this.tiempoJuego;
			            	this.esperandoRecargaPlanta = true;
			                
			            } 
			            else 
			            {
			                // Si está ocupada, vuelve a su posición original
			                lanzaLlamas.nuevaPosicion(150, 88);
			            }

			            // Libero la planta tomada
			            plantaTomada = null;
			            
			        }
			    }
			}
		}
		
		// Se crea despues de 5 segundos otra planta Fuego en panel
		if(!hayPlantaFuegoPanel(fuego) && esperandoRecargaPlanta)
		{
			if(tiempoJuego - ultimaPlantadaPlanta >= intervaloPlantarPlanta)
			{
				for (int i = 0; i < fuego.length; i++) {
		            if (fuego[i] == null) {
		                fuego[i] = new LanzaLlamas(170, 68, entorno);
		                this.esperandoRecargaPlanta = false;
		                break;
		            }
		        }
			}
			
		}
		
		
		
		
		
		//  ---------------- Colision de plantas de fuego con zombies -------------------
		
		
		
		for (int k = 0; k < fuego.length; k++) 
		{
			 if (fuego[k] != null && fuego[k].plantada) 
			 {
				 	//Colision con zombies comunes
			        for (int j = 0; j < zombies.length; j++) 
			        {
			            if (zombies[j] != null) 
			            {
			                double dist = Math.sqrt(Math.pow(fuego[k].x - zombies[j].x, 2) + Math.pow(fuego[k].y - zombies[j].y, 2));
	
			                if (dist < 30) 
			                {
			                	
			                	fuego[k].vida -= 0.5;
			                	zombies[j].x += 5;
			                	
			                	if(fuego[k].vida == 0)
					               {
			                		
			                			fuego[k] = null;
			                			
					               }
			                	break;
			               }
			               
			           }
			       }
			 }
		}
		
		
		for (int k = 0; k < fuego.length; k++) 
		{
			 if (fuego[k] != null && fuego[k].plantada) 
			 {
				 	//Colision con zombies comunes
			        for (int j = 0; j < zombiesBart.length; j++) 
			        {
			            if (zombiesBart[j] != null) 
			            {
			                double dist = Math.sqrt(Math.pow(fuego[k].x - zombiesBart[j].x, 2) + Math.pow(fuego[k].y - zombiesBart[j].y, 2));
	
			                if (dist < 30) 
			                {
			                	
			                	fuego[k].vida -= 0.5;
			                	zombiesBart[j].x += 5;
			                	
			                	if(fuego[k].vida == 0)
					               {
			                		
			                			fuego[k] = null;
			                			
					               }
			                	break;
			               }
			               
			           }
			       }
			 }
		}
		
		
		for (int k = 0; k < fuego.length; k++) 
		{
			 if (fuego[k] != null && fuego[k].plantada) 
			 {
				 	//Colision con zombies comunes
			        for (int j = 0; j < zombiesCerebros.length; j++) 
			        {
			            if (zombiesCerebros[j] != null) 
			            {
			                double dist = Math.sqrt(Math.pow(fuego[k].x - zombiesCerebros[j].x, 2) + Math.pow(fuego[k].y - zombiesCerebros[j].y, 2));
	
			                if (dist < 30) 
			                {
			                	
			                	fuego[k].vida -= 0.5;
			                	zombiesCerebros[j].x += 5;
			                	
			                	if(fuego[k].vida == 0)
					               {
			                		
			                			fuego[k] = null;
			                			
					               }
			                	break;
			               }
			               
			           }
			       }
			 }
		}
			
		
		for (int k = 0; k < fuego.length; k++) 
		{
			 if (fuego[k] != null && fuego[k].plantada) 
			 {
				 if(colosal != null)
				 {
					 double dist = Math.sqrt(Math.pow(fuego[k].x - colosal.x, 2) + Math.pow(fuego[k].y - colosal.y, 2));
					 
					 if (dist < 300 )
		             {
						 
						 fuego[k] = null;
		            	 break;
		             }
				 }
			 }
		}
				
		
		
		
		
		
		
		
		
		
		
		// ------------------------------ Seccion plantas nueces ----------------------------------------------------
		
		
		
		
		
		//Dibujar todas las nueces
		for(Nuez n: nueces)
		{
			if(n!=null)
			{
				n.dibujar();
			}
			
		}
		
		
		if(esperandoRecargaNuez)
		{
			entorno.dibujarImagen(recarga, 269.6, 25, 0, 0.1);
			entorno.dibujarImagen(cargandoNuez, 269.6, 88, 0, 0.29);
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
			            if (!this.celdaOcupada(centro[0], centro[1])) {
			                nuez.nuevaPosicion(centro[0], centro[1]);
			                nuez.plantada = true;
			                this.ultimaPlantadaNuez = this.tiempoJuego;
			            	this.esperandoRecargaNuez = true;
			            	
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
		
		// Se crea despues de 5 segundos otra planta Nuez en panel
		if(!hayPlantaNuezPanel(nueces) && esperandoRecargaNuez)
		{
			if(tiempoJuego - ultimaPlantadaNuez >= intervaloPlantarNuez)
			{
				for (int i = 0; i < nueces.length; i++) {
		            if (nueces[i] == null) {
		                nueces[i] = new Nuez(269.6, 88, entorno);
		                this.esperandoRecargaNuez = false;
		                break;
		            }
		        }
			}
			
		}
		
		
		// ---------- Seccion explosion de nueces y colision con zombies ----------------
		
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
			                	        explosiones[e] = new Explosion(centro[0], centro[1]-40, entorno, 0.7);
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
			                	        explosiones[e] = new Explosion(centro[0], centro[1]-40, entorno, 0.7);
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
			                	        explosiones[e] = new Explosion(centro[0], centro[1]-40, entorno, 0.7);
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
		
		for (int k = 0; k < nueces.length; k++)
		{
			if (nueces[k] != null && nueces[k].plantada)
			{
				if(this.colosal != null)
		        {
		        	double dist = Math.sqrt( Math.pow(nueces[k].x - colosal.x, 2) + Math.pow(nueces[k].y - colosal.y, 2) );
		        	
		        	if(dist < 200)
		        	{
		        		double[] centro = c.obtenerCentroCeldaMasCercana(nueces[k].x, nueces[k].y);
	                	
	                	for (int e = 0; e < explosiones.length; e++) 
	                	{
	                	    if (explosiones[e] == null) 
	                	    {
	                	        explosiones[e] = new Explosion(centro[0], centro[1]-40, entorno, 0.7);
	                	        break;
	                	    }
	                	}
		        		colosal.vida -= 0.5;
		        		
		        		if(colosal.vida <= 0)
		        		{
		        			for (int e = 0; e < explosiones.length; e++) 
		                	{
		                	    if (explosiones[e] == null) 
		                	    {
		                	        explosiones[e] = new Explosion(colosal.x, colosal.y, entorno, 3);
		                	        break;
		                	    }
		                	}
			        		colosal = null;
			        		this.tiempoExplosionColosal = tiempoJuego;
		        		}
		        		nueces[k] = null;
		        		break;
		        	}
		        }
			}
		}
		
		
		// ---------------------- Colision con el radio de explosiones -------------------------
		
		for (int e = 0; e < explosiones.length; e++) {
		    if (explosiones[e] != null) {
		        explosiones[e].dibujar(entorno);
		        if (explosiones[e].expirada()) {
		            explosiones[e] = null;
		        }
		    }
		}
		
		
		for (int e = 0; e < explosiones.length; e++)
		{
			if(explosiones[e] != null)
			{
				for(int k = 0; k < zombies.length; k++)
			    {
			    	if(zombies[k] != null)
			    	{
			    		double dist = Math.sqrt(Math.pow(explosiones[e].x - zombies[k].x, 2) + Math.pow(explosiones[e].y - zombies[k].y, 2));
			    		
			    		if(dist < c.ancho+20)
			    		{
			    			zombies[k] = null;
			    			this.zombiesRestantes--;
			    			this.zombiesEliminados++;
			    		}
			    	}
			    }
				for(int k = 0; k < zombiesBart.length; k++)
			    {
			    	if(zombiesBart[k] != null)
			    	{
			    		double dist = Math.sqrt(Math.pow(explosiones[e].x - zombiesBart[k].x, 2) + Math.pow(explosiones[e].y - zombiesBart[k].y, 2));
			    		
			    		if(dist < c.ancho+20)
			    		{
			    			zombiesBart[k] = null;
			    			this.zombiesRestantes--;
			    			this.zombiesEliminados++;
			    		}
			    	}
			    }
				for(int k = 0; k < zombiesCerebros.length; k++)
			    {
			    	if(zombiesCerebros[k] != null)
			    	{
			    		double dist = Math.sqrt(Math.pow(explosiones[e].x - zombiesCerebros[k].x, 2) + Math.pow(explosiones[e].y - zombiesCerebros[k].y, 2));
			    		
			    		if(dist < c.ancho+20)
			    		{
			    			zombiesCerebros[k] = null;
			    			this.zombiesRestantes--;
			    			this.zombiesEliminados++;
			    		}
			    	}
			    }
			}
		}
		
		
	
		
		
		// ----------------------------- Seccion huracoles ----------------------------------
		
		
		
		//Dibujar huracoles
		for(Huracol h: huracoles)
		{
			if(h!=null)
			{
				h.dibujar();
			}
			
		}
		
		if(esperandoRecargaHuracol)
		{
			entorno.dibujarImagen(recarga, 378, 25, 0, 0.1);
			entorno.dibujarImagen(cargandoHuracol, 378, 84, 0, 0.7);
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
			            if ( !celdaOcupada(centro[0], centro[1]) ) {
			            	huracol.nuevaPosicion(centro[0], centro[1]);
			            	huracol.plantada = true;
			            	this.ultimaPlantadaHuracol = this.tiempoJuego;
			            	this.esperandoRecargaHuracol = true;
			            	
			            } else {
			                // Si está ocupada, vuelve a su posición original
			            	huracol.nuevaPosicion(378, 88);
			            	huracol.plantada = false;
			                //huracolTomada = null;
			            }

			            // Libero la planta tomada
			            huracolTomada = null;
			            
			        }
			    }
			}
		}
		
		// Se crea despues de 5 segundos otra planta Huracol en panel
		if(!hayPlantaHuracolPanel(huracoles) && esperandoRecargaHuracol)
		{
			if(tiempoJuego - ultimaPlantadaHuracol >= intervaloPlantarHuracol)
			{
				for (int i = 0; i < huracoles.length; i++) {
		            if (huracoles[i] == null) {
		                huracoles[i] = new Huracol(378, 84, entorno);
		                this.esperandoRecargaHuracol = false;
		                break;
		            }
		        }
			}
			
		}
		
		
		
		//  ---------------- Colision de huracoles con zombies -------------------
		
		
		
		for (int k = 0; k < huracoles.length; k++) 
		{
			 if (huracoles[k] != null && huracoles[k].plantada) 
			 {
				 	//Colision con zombies comunes
			        for (int j = 0; j < zombies.length; j++) 
			        {
			            if (zombies[j] != null) 
			            {
			                double dist = Math.sqrt(Math.pow(huracoles[k].x - zombies[j].x, 2) + Math.pow(huracoles[k].y - zombies[j].y, 2));

			                if (dist < 30) 
			                {
			                	
			                	huracoles[k].vida -= 0.5;
			                	zombies[j].x += 5;
			                	if(huracoles[k].vida == 0)
					               {
					            	   huracoles[k] = null;
					               }
			                	break;
			               }
			               
			           }
			       }
			 }
		}
		
		
		for (int k = 0; k < huracoles.length; k++) 
		{
			 if (huracoles[k] != null && huracoles[k].plantada) 
			 {
				 	//Colision con zombies bart
			        for (int j = 0; j < zombiesBart.length; j++) 
			        {
			            if (zombiesBart[j] != null) 
			            {
			                double dist = Math.sqrt(Math.pow(huracoles[k].x - zombiesBart[j].x, 2) + Math.pow(huracoles[k].y - zombiesBart[j].y, 2));

			                if (dist < 30) 
			                {
			                	
			                	huracoles[k].vida -= 0.5;
			                	zombiesBart[j].x += 5;
			                	if(huracoles[k].vida == 0)
					               {
					            	   huracoles[k] = null;
					               }
			                	break;
			               }
			               
			           }
			       }
			 }
		}
		
		
		for (int k = 0; k < huracoles.length; k++) 
		{
			 if (huracoles[k] != null && huracoles[k].plantada) 
			 {
				 	//Colision con zombies cerebros
			        for (int j = 0; j < zombiesCerebros.length; j++) 
			        {
			            if (zombiesCerebros[j] != null) 
			            {
			                double dist = Math.sqrt(Math.pow(huracoles[k].x - zombiesCerebros[j].x, 2) + Math.pow(huracoles[k].y - zombiesCerebros[j].y, 2));

			                if (dist < 30) 
			                {
			                	
			                	huracoles[k].vida -= 0.5;
			                	zombiesCerebros[j].x += 5;
			                	if(huracoles[k].vida == 0)
					               {
					            	   huracoles[k] = null;
					               }
			                	break;
			               }
			               
			           }
			       }
			 }
		}
		
		for (int k = 0; k < huracoles.length; k++) 
		{
			 if (huracoles[k] != null && huracoles[k].plantada) 
			 {
				 if(colosal != null)
				 {
					 double dist = Math.sqrt(Math.pow(huracoles[k].x - colosal.x, 2) + Math.pow(huracoles[k].y - colosal.y, 2));
					 
					 if (dist < 300 )
		             {
						 
						 huracoles[k] = null;
		            	 break;
		             }
				 }
			 }
		}
		
		
		
		
		
		// --------------------- Seccion guisantes -----------------------------
		
		
		
		//Dibujar guisantes
		
		for(Carbonopodo pv: guisantes)
		{
			if(pv!=null)
			{
				pv.dibujar();
			}
			
		}
		
		if(esperandoRecargaGuisante)
		{
			entorno.dibujarImagen(recarga, 482, 25, 0, 0.1);
			entorno.dibujarImagen(cargandoCarbonopodo, 488, 85, 0, 0.09);
		}
		
		
		//Recorremos todas los Huracoles. Seleccionar, arrastrar y plantar una planta tipo huracol
		for (int h = 0; h < guisantes.length; h++) {
		    Carbonopodo guisante = guisantes[h];

		    if (guisante != null)
		    {
		    	// seleccionar planta si se hace click sobre ella
			    if (entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO) && !guisante.plantada && guisanteTomado == null) 
			    {

			        if (guisante.encima(entorno.mouseX(), entorno.mouseY())) 
			        {
			        	guisante.seleccion = true;
			        	guisanteTomado = guisante;
			        }
			    }

			    // Arrastrar planra mientras mantengo presionado el click
			    if (guisante.seleccion && entorno.estaPresionado(entorno.BOTON_IZQUIERDO)) {
			    	guisante.arrastrar(entorno.mouseX(), entorno.mouseY());
			    }

			    // Suelto planta cuando dejo de presionar el botón
			    if (guisante.seleccion && !entorno.estaPresionado(entorno.BOTON_IZQUIERDO)) {
			    	guisante.seleccion = false;

			        // Si la suelto sobre el panel (parte superior), vuelve a su lugar
			        if (entorno.mouseY() <= 20) {
			        	guisante.nuevaPosicion(490, 85);
			        	guisante.plantada = false;
			        	guisanteTomado = null;

			        } else {
			            // Si no, intento plantarla en la celda más cercana
			            double[] centro = c.obtenerCentroCeldaMasCercana(entorno.mouseX(), entorno.mouseY());

			            // Chequeo que la celda no esté ocupada
			            if (!celdaOcupada(centro[0], centro[1])) {
			            	guisante.nuevaPosicion(centro[0], centro[1]);
			            	guisante.plantada = true;
			            	this.ultimaPlantadaGuisante = this.tiempoJuego;
			            	this.esperandoRecargaGuisante = true;
			            	
			            } else {
			                // Si está ocupada, vuelve a su posición original
			            	guisante.nuevaPosicion(490, 85);
			            	guisante.plantada = false;
			                //huracolTomada = null;
			            }

			            // Libero la planta tomada
			            guisanteTomado = null;
			            
			        }
			    }
			}
		}
		
		// Se crea despues de 5 segundos otra planta Huracol en panel
		if(!hayPlantaGuisantePanel(guisantes) && esperandoRecargaGuisante)
		{
			if(tiempoJuego - ultimaPlantadaGuisante >= intervaloPlantarGuisante)
			{
				for (int i = 0; i < guisantes.length; i++) {
		            if (guisantes[i] == null) {
		            	guisantes[i] = new Carbonopodo(490, 85, entorno);
		                this.esperandoRecargaGuisante = false;
		                break;
		            }
		        }
			}
			
		}
		
		
		
		//  ---------------- Colision de guisantes con zombies -------------------
		
		
		
		for (int k = 0; k < guisantes.length; k++) 
		{
			 if (guisantes[k] != null && guisantes[k].plantada) 
			 {
				 	//Colision con zombies comunes
			        for (int j = 0; j < zombies.length; j++) 
			        {
			            if (zombies[j] != null) 
			            {
			                double dist = Math.sqrt(Math.pow(guisantes[k].x - zombies[j].x, 2) + Math.pow(guisantes[k].y - zombies[j].y, 2));

			                if (dist < 30) 
			                {
			                	
			                	guisantes[k].vida -= 0.5;
			                	zombies[j].x += 5;
			                	if(guisantes[k].vida == 0)
					               {
			                		    guisantes[k] = null;
					               }
			                	break;
			               }
			               
			           }
			       }
			 }
		}
		
		
		for (int k = 0; k < guisantes.length; k++) 
		{
			 if (guisantes[k] != null && guisantes[k].plantada) 
			 {
				 	//Colision con zombies bart
			        for (int j = 0; j < zombiesBart.length; j++) 
			        {
			            if (zombiesBart[j] != null) 
			            {
			                double dist = Math.sqrt(Math.pow(guisantes[k].x - zombiesBart[j].x, 2) + Math.pow(guisantes[k].y - zombiesBart[j].y, 2));

			                if (dist < 30) 
			                {
			                	
			                	guisantes[k].vida -= 0.5;
			                	zombiesBart[j].x += 5;
			                	if(guisantes[k].vida == 0)
					               {
			                			guisantes[k] = null;
					               }
			                	break;
			               }
			               
			           }
			       }
			 }
		}
		
		
		for (int k = 0; k < guisantes.length; k++) 
		{
			 if (guisantes[k] != null && guisantes[k].plantada) 
			 {
				 	//Colision con zombies cerebros
			        for (int j = 0; j < zombiesCerebros.length; j++) 
			        {
			            if (zombiesCerebros[j] != null) 
			            {
			                double dist = Math.sqrt(Math.pow(guisantes[k].x - zombiesCerebros[j].x, 2) + Math.pow(guisantes[k].y - zombiesCerebros[j].y, 2));

			                if (dist < 30) 
			                {
			                	
			                	guisantes[k].vida -= 0.5;
			                	zombiesCerebros[j].x += 5;
			                	if(guisantes[k].vida == 0)
					               {
			                			guisantes[k] = null;
					               }
			                	break;
			               }
			               
			           }
			       }
			 }
		}
		
		for (int k = 0; k < guisantes.length; k++) 
		{
			 if (guisantes[k] != null && guisantes[k].plantada) 
			 {
				 if(colosal != null)
				 {
					 double dist = Math.sqrt(Math.pow(guisantes[k].x - colosal.x, 2) + Math.pow(guisantes[k].y - colosal.y, 2));
					 
					 if (dist < 300 )
		             {
						 
						 guisantes[k] = null;
		            	 break;
		             }
				 }
			 }
		}
		
		
		
		
		
		
		// --------------------------- Seccion desplazamiento de las plantas con las teclas ---------------------------------------
		
		
		//Plantas de fuego
		for(int k = 0; k < fuego.length; k++)
			
		{
			LanzaLlamas p = fuego[k];
			
			if(p != null && p.plantada)
			{
				double[] proximoArriba = c.obtenerCentroCeldaMasCercana(p.x, p.y - c.alto);
				double[] proximoDerecho = c.obtenerCentroCeldaMasCercana(p.x + c.ancho, p.y);
				double[] proximoAbajo = c.obtenerCentroCeldaMasCercana(p.x, p.y + c.alto);
				double[] proximoIzquierdo = c.obtenerCentroCeldaMasCercana(p.x - c.ancho, p.y);
				
				if (!celdaOcupada(proximoArriba[0], proximoArriba[1]))
				{
					p.desplazarArriba(proximoArriba[0], proximoArriba[1]);
				}
				
				if (!celdaOcupada(proximoDerecho[0], proximoDerecho[1]))
				{
					p.desplazarDerecha(proximoDerecho[0], proximoDerecho[1]);
				}
				
				if (!celdaOcupada(proximoAbajo[0], proximoAbajo[1]))
				{
					p.desplazarAbajo(proximoAbajo[0], proximoAbajo[1]);
				}
				
				if (!celdaOcupada(proximoIzquierdo[0], proximoIzquierdo[1]))
				{
					p.desplazarIzquierda(proximoIzquierdo[0], proximoIzquierdo[1]);
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
				double[] proximoIzquierdo = c.obtenerCentroCeldaMasCercana(n.x - c.ancho, n.y);
				
				if (!celdaOcupada(proximoArriba[0], proximoArriba[1]))
				{
					n.desplazarArriba(proximoArriba[0], proximoArriba[1]);
				}
				
				if (!celdaOcupada(proximoDerecho[0], proximoDerecho[1]))
				{
					n.desplazarDerecha(proximoDerecho[0], proximoDerecho[1]);
				}
				
				if (!celdaOcupada(proximoAbajo[0], proximoAbajo[1]))
				{
					n.desplazarAbajo(proximoAbajo[0], proximoAbajo[1]);
				}
				
				if (!celdaOcupada(proximoIzquierdo[0], proximoIzquierdo[1]))
				{
					n.desplazarIzquierda(proximoIzquierdo[0], proximoIzquierdo[1]);
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
				double[] proximoIzquierdo = c.obtenerCentroCeldaMasCercana(h.x - c.ancho, h.y);
				
				if (!celdaOcupada(proximoArriba[0], proximoArriba[1]))
				{
					h.desplazarArriba(proximoArriba[0], proximoArriba[1]);
				}
				
				if (!celdaOcupada(proximoDerecho[0], proximoDerecho[1]))
				{
					h.desplazarDerecha(proximoDerecho[0], proximoDerecho[1]);
				}
				
				if (!celdaOcupada(proximoAbajo[0], proximoAbajo[1]))
				{
					h.desplazarAbajo(proximoAbajo[0], proximoAbajo[1]);
				}
				
				if (!celdaOcupada(proximoIzquierdo[0], proximoIzquierdo[1]))
				{
					h.desplazarIzquierda(proximoIzquierdo[0], proximoIzquierdo[1]);
				}
			}
		}
		
		
		// Planta Carbonopodo
		for(int k = 0; k < guisantes.length; k++)
					
		{
			Carbonopodo g = guisantes[k];
			
			if(g != null && g.plantada)
			{
				double[] proximoArriba = c.obtenerCentroCeldaMasCercana(g.x, g.y - c.alto);
				double[] proximoDerecho = c.obtenerCentroCeldaMasCercana(g.x + c.ancho, g.y);
				double[] proximoAbajo = c.obtenerCentroCeldaMasCercana(g.x, g.y + c.alto);
				double[] proximoIzquierdo = c.obtenerCentroCeldaMasCercana(g.x - c.ancho, g.y);
				
				if (!celdaOcupada(proximoArriba[0], proximoArriba[1]))
				{
					g.desplazarArriba(proximoArriba[0], proximoArriba[1]);
				}
				
				if (!celdaOcupada(proximoDerecho[0], proximoDerecho[1]))
				{
					g.desplazarDerecha(proximoDerecho[0], proximoDerecho[1]);
				}
				
				if (!celdaOcupada(proximoAbajo[0], proximoAbajo[1]))
				{
					g.desplazarAbajo(proximoAbajo[0], proximoAbajo[1]);
				}
				
				if (!celdaOcupada(proximoIzquierdo[0], proximoIzquierdo[1]))
				{
					g.desplazarIzquierda(proximoIzquierdo[0], proximoIzquierdo[1]);
				}
			}
		}
		
		
		
		
		// --------------------------- Eliminar plantas al seleccionarlas y presionar la tecla 'e' ----------------------------------
		
		for(int k = 0; k < fuego.length; k++)
		{
			if( fuego[k] != null && fuego[k].plantada && fuego[k].seleccionadaParaMover)
			{
				if(entorno.sePresiono('e'))
				{
					fuego[k] = null;
				}
			}
		}
		
		
		for(int k = 0; k < huracoles.length; k++)
		{
			if( huracoles[k] != null && huracoles[k].plantada && huracoles[k].seleccionadaParaMover)
			{
				if(entorno.sePresiono('e'))
				{
					huracoles[k] = null;
				}
			}
		}
		
		
		for(int k = 0; k < nueces.length; k++)
		{
			if( nueces[k] != null && nueces[k].plantada && nueces[k].seleccionadaParaMover)
			{
				if(entorno.sePresiono('e'))
				{
					nueces[k] = null;
				}
			}
		}
		
		for(int k = 0; k < guisantes.length; k++)
		{
			if( guisantes[k] != null && guisantes[k].plantada && guisantes[k].seleccionadaParaMover)
			{
				if(entorno.sePresiono('e'))
				{
					guisantes[k] = null;
				}
			}
		}
		
		
		
		
		// ----------------------------- Seccion Zombies ------------------------------------- 
		
		
		//Zombies comunes
		if(tiempoActualZombie - this.tiempoUltimoCreado >= this.intervaloCreacion)
		{
			for(int j = 0; j < zombies.length; j++)
			{
				if(zombies[j] == null)
				{
					int fila = (int) (Math.random() * 5); 
		            double yZombie = c.y + fila * c.getAltoCelda() - 10;
		            
		            if(this.zombiesCreados < 50)
		    		{
		            	zombies[j] = new Zombie(1200, yZombie, entorno);
						this.zombiesCreados++;
						break;
		    		}
				}
			}
			this.tiempoUltimoCreado = tiempoActualZombie;
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
		{
			for(int j = 0; j < zombiesCerebros.length; j++)
			{
				if(zombiesCerebros[j] == null)
				{
					int fila = (int) (Math.random() * 5); 
		            double yZombie = c.y + fila * c.getAltoCelda()-10;
					
		            if(this.zombiesCreados < 50)
		    		{
		            	zombiesCerebros[j] = new ZombieCerebro(1200, yZombie, entorno);
						this.zombiesCreados++;
						break;
		    		}
				}
			}
			this.tiempoUltimoCreadoCerebro = tiempoActualZombie;
		}
			
			
			
		// Mover y dibujar todos los zombies cerebros existentes
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
		{
			for(int j = 0; j < zombiesBart.length; j++)
			{
				if(zombiesBart[j] == null)
				{
					int fila = (int) (Math.random() * 5); 
		            double yZombie = c.y + fila * c.getAltoCelda() - 15;
					
		            if(this.zombiesCreados < 50)
		    		{
		            	zombiesBart[j] = new ZombieBart(1200, yZombie, entorno);
						this.zombiesCreados++;
						break;
		    		}
				}
			}
			this.tiempoUltimoCreadoBart = tiempoActualZombie;
		}
		
		// Mover y dibujar todos los zombies bart existentes
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
		
		// ------------------------ Creacion del zombie colosal ---------------------------------------
				
		if(this.zombiesEliminados >= 50 && !this.colosalCreado && this.anuncioColosal && this.tiempoJuego - this.tiempoParaCrearColosal >= 5)
		{
			this.colosal = new ZombieColosal(1400, alto/2+30, entorno);
			this.colosalCreado = true;
		}
		
		if(this.colosal != null)
		{
			colosal.dibujar();
			colosal.mover();
		}
		
		
		// ------------------------- Disparos del zomibie Colosal -----------------------------------
		
		if(this.colosal != null)
		{
			if (tiempoActual - this.colosal.ultimoTiempoDisparo >= this.colosal.intervaloDisparo && colosal.x < c.x + c.ancho*9)
			{
				for(int k = 0; k < energia.length; k++)
				{
					if(energia[k] == null)
					{
						for(int t=0; t<5; t++)
						{
							energia[k + t] = new ProyectilColosal(colosal.x-100, colosal.y-160 + c.alto*t, 0.4, entorno);
						}
						break;
					}
				}
				this.colosal.ultimoTiempoDisparo = tiempoActual;
			}
		}
		
		for(int k = 0; k < energia.length; k++)
		{
			if(energia[k] != null)
			{
				energia[k].mover();
				energia[k].dibujar();
				
				if(energia[k].x < c.x + c.ancho/2)
				{
					energia[k] = null;
				}
				
			}
		}
		
		
		// ------------------------- Colision de disparos colosal con plantas y disparos de plantas ---------------------------------
		
		
		
		
		for(int k = 0; k < energia.length; k++)
		{
			if(energia[k] != null)
			{
				for(int j = 0; j < this.bolas.length; j++)
				{
					if(bolas[j] != null)
					{
						
						double dist = Math.sqrt(Math.pow(energia[k].x - bolas[j].x, 2) + Math.pow(energia[k].y - bolas[j].y, 2));
						
						if(dist < 20 )
						{
							bolas[j] = null;
							break;
						}
					}
				}
				
				for(int j = 0; j < this.bolasAzul.length; j++)
				{
					if(bolasAzul[j] != null)
					{
						
						double dist = Math.sqrt(Math.pow(energia[k].x - bolasAzul[j].x, 2) + Math.pow(energia[k].y - bolasAzul[j].y, 2));
						
						if(dist < 20 )
						{
							bolasAzul[j] = null;
							break;
						}
					}
				}
				
				for(int j = 0; j < this.bolasCarbon.length; j++)
				{
					if(bolasCarbon[j] != null)
					{
						
						double dist = Math.sqrt(Math.pow(energia[k].x - bolasCarbon[j].x, 2) + Math.pow(energia[k].y - bolasCarbon[j].y, 2));
						
						if(dist < 20 )
						{
							bolasCarbon[j] = null;
							break;
						}
					}
				}
				
			}
		
		}
		
		for(int k = 0; k < energia.length; k++)
		{
			if(energia[k] != null)
			{
				
				for(int j = 0; j < this.huracoles.length; j++)
				{
					if(huracoles[j] != null && huracoles[j].plantada)
					{
						
						double dist = Math.sqrt(Math.pow(energia[k].x - huracoles[j].x, 2) + Math.pow(energia[k].y - huracoles[j].y, 2));
						
						if(dist < 20 )
						{
							huracoles[j] = null;
							energia[k] = null;
							break;
						}
					}
				}
			}		
		}
		
		for(int k = 0; k < energia.length; k++)
		{
			if(energia[k] != null)
			{
				
				for(int j = 0; j < this.fuego.length; j++)
				{
					if(fuego[j] != null && fuego[j].plantada)
					{
						
						double dist = Math.sqrt(Math.pow(energia[k].x - fuego[j].x, 2) + Math.pow(energia[k].y - fuego[j].y, 2));
						
						if(dist < 20 )
						{
							fuego[j] = null;
							energia[k] = null;
							break;
						}
					}
				}
			}		
		}
		
		for(int k = 0; k < energia.length; k++)
		{
			if(energia[k] != null)
			{
				
				for(int j = 0; j < this.nueces.length; j++)
				{
					if(nueces[j] != null && nueces[j].plantada)
					{
						
						double dist = Math.sqrt(Math.pow(energia[k].x - nueces[j].x, 2) + Math.pow(energia[k].y - nueces[j].y, 2));
						
						if(dist < 20 )
						{
							nueces[j] = null;
							energia[k] = null;
							break;
						}
					}
				}
			}		
		}
		
		for(int k = 0; k < energia.length; k++)
		{
			if(energia[k] != null)
			{
				
				for(int j = 0; j < this.guisantes.length; j++)
				{
					if(guisantes[j] != null && guisantes[j].plantada)
					{
						
						double dist = Math.sqrt(Math.pow(energia[k].x - guisantes[j].x, 2) + Math.pow(energia[k].y - guisantes[j].y, 2));
						
						if(dist < 20 )
						{
							guisantes[j] = null;
							energia[k] = null;
							break;
						}
					}
				}
			}		
		}
		
		
		
		
		
		
		
		
		
		// --------------------------- Seccion disparos de plantas ------------------------------
		
		
		// ----------------------------- Bolas de fuego ----------------------------------------
		
		//Genera una bola de fuego en un determinado tiempo
		for (LanzaLlamas p : fuego) {
	        if (p != null && p.plantada && !p.disparando && this.hayZombiesEnCesped()) {
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
		        		double [] centro = c.obtenerCentroCeldaMasCercana(zombies[j].x, zombies[j].y);
		        		double numRandom = (int) (Math.random() * 10);
		        		
		        		if(dist < 20)
		        		{
		        			entorno.dibujarImagen(b.imgDaño, b.x, b.y, 0, 0.2);
		        			zombies[j].vida -= 2;
		        			
		        			if(zombies[j].vida == 0)
		        			{
		        				zombies[j] = null;
		        				this.zombiesEliminados+=1;
		        				this.zombiesRestantes--;
		        				
		        				if(numRandom <= 4)
		        				{
		        					for(int t = 0; t < tumbas.length; t++)
		        					{
		        						if(tumbas[t] == null && !celdaOcupada(centro[0], centro[1]))
		        							
		        						{
		        							tumbas[t] = new Tumba(centro[0], centro[1], entorno);
		        							tumbas[t].enterrada = true;
		        							break;
		        						}
		        					}
		        				}
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
		        		double [] centro = c.obtenerCentroCeldaMasCercana(zombiesBart[j].x, zombiesBart[j].y);
		        		double numRandom = (int) (Math.random() * 10);
		        		
		        		if(dist < 20)
		        		{
		        			entorno.dibujarImagen(b.imgDaño, b.x, b.y, 0, 0.2);
		        			zombiesBart[j].vida -=2;
		        			if(zombiesBart[j].vida == 0)
		        			{
		        				zombiesBart[j] = null;
		        				this.zombiesEliminados+=1;
		        				this.zombiesRestantes--;
		        				
		        				if(numRandom <= 4)
		        				{
		        					for(int t = 0; t < tumbas.length; t++)
		        					{
		        						if(tumbas[t] == null && !celdaOcupada(centro[0], centro[1]))
		        							
		        						{
		        							tumbas[t] = new Tumba(centro[0], centro[1], entorno);
		        							tumbas[t].enterrada = true;
		        							break;
		        						}
		        					}
		        				}
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
		        		double [] centro = c.obtenerCentroCeldaMasCercana(zombiesCerebros[j].x, zombiesCerebros[j].y);
		        		double numRandom = (int) (Math.random() * 10);
		        		
		        		if(dist < 20)
		        		{
		        			entorno.dibujarImagen(b.imgDaño, b.x, b.y, 0, 0.2);
		        			zombiesCerebros[j].vida -=2;
		        			if(zombiesCerebros[j].vida == 0)
		        			{
		        				zombiesCerebros[j] = null;	
		        				this.zombiesEliminados+=1;
		        				this.zombiesRestantes--;
		        				
		        				if(numRandom <= 4)
		        				{
		        					for(int t = 0; t < tumbas.length; t++)
		        					{
		        						if(tumbas[t] == null && !celdaOcupada(centro[0], centro[1]))
		        						{
		        							tumbas[t] = new Tumba(centro[0], centro[1], entorno);
		        							tumbas[t].enterrada = true;
		        							break;
		        						}
		        					}
		        				}
		        			}
		        			bolas[k] = null;
		        			      		
		        		}
		        	}
			     }
		        for(int j = 0; j < tumbas.length; j++)
		        {
		        	if(tumbas[j] != null && tumbas[j].enterrada)
		        	{
		        		double dist = Math.sqrt( Math.pow(b.x - tumbas[j].x, 2) + Math.pow(b.y - tumbas[j].y, 2) );
		        		
		        		if(dist < 40)
		        		{
		        			entorno.dibujarImagen(b.imgDaño, b.x, b.y, 0, 0.2);
		        			tumbas[j].vida -= 2;
		        			
		        			if(tumbas[j].vida == 0)
		        			{
		        				tumbas[j].enterrada = false;
		        				tumbas[j] = null;
		        			}
		        			bolas[k] = null; 
		        		}
		        	}
		        }
		        if(this.colosal != null)
		        {
		        	double dist = Math.sqrt( Math.pow(b.x - colosal.x, 2) + Math.pow(b.y - colosal.y, 2) );
		        	
		        	if(dist < 230)
		        	{
		        		entorno.dibujarImagen(b.imgDaño, b.x, b.y, 0, 0.2);
		        		colosal.vida -= 0.5;
		        		if(colosal.vida <= 0)
		        		{
		        	    	
		        	    	for (int e = 0; e < explosiones.length; e++) 
		        	    	{
		        	    	    if (explosiones[e] == null) 
		        	    	    {
		        	    	        explosiones[e] = new Explosion(colosal.x, colosal.y, entorno, 3);
		        	    	        break;
		        	    	    }
		        	    	}
		        			colosal = null;
		        			this.tiempoExplosionColosal = tiempoJuego;
		        		}
		        		bolas[k] = null;
		        	}
		        }
		     }
			
		}
		
		
		
		
		// ------------------------------ Bola azul ------------------------------------------
		
		//Genera una bola azul en un determinado tiempo
		
		for (Huracol h : huracoles) {
	        if (h != null && h.plantada && !h.disparando && this.hayZombiesEnCesped()) {
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
		
			
		// ------------------ Movimiento de disparos y colisiones de bolas azul con zombies, tumbas y colosal ----------------------------
		
		
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
		        		double [] centro = c.obtenerCentroCeldaMasCercana(zombies[j].x, zombies[j].y);
		        		double numRandom = (int) (Math.random() * 10);
		        		
		        		if(dist < 20)
		        		{
		        			entorno.dibujarImagen(b.imgDaño, b.x, b.y, 0, 0.2);
		        			zombies[j].vida -= 1;
		        			zombies[j].velocidad -= 0.02;
		        			
		        			if(zombies[j].vida <= 0)
		        			{
		        				zombies[j] = null;
		        				this.zombiesEliminados+=1;
		        				this.zombiesRestantes--;
		        				
		        				if(numRandom <= 4)
		        				{
		        					for(int t = 0; t < tumbas.length; t++)
		        					{
		        						if(tumbas[t] == null && !celdaOcupada(centro[0], centro[1]))
		        						{
		        							tumbas[t] = new Tumba(centro[0], centro[1], entorno);
		        							tumbas[t].enterrada = true;
		        							break;
		        						}
		        					}
		        				}
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
		        		double [] centro = c.obtenerCentroCeldaMasCercana(zombiesBart[j].x, zombiesBart[j].y);
		        		double numRandom = (int) (Math.random() * 10);
		        		
		        		if(dist < 20)
		        		{
		        			entorno.dibujarImagen(b.imgDaño, b.x, b.y, 0, 0.2);
		        			
		        			zombiesBart[j].vida -= 1;
		        			zombiesBart[j].velocidad -= 0.02;
		        			
		        			if(zombiesBart[j].vida <= 0)
		        			{
		        				zombiesBart[j] = null;
		        				this.zombiesEliminados+=1;
		        				this.zombiesRestantes--;
		        				
		        				if(numRandom <= 4)
		        				{
		        					for(int t = 0; t < tumbas.length; t++)
		        					{
		        						if(tumbas[t] == null && !celdaOcupada(centro[0], centro[1]))
		        						{
		        							tumbas[t] = new Tumba(centro[0], centro[1], entorno);
		        							tumbas[t].enterrada = true;
		        							break;
		        						}
		        					}
		        				}
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
		        		double [] centro = c.obtenerCentroCeldaMasCercana(zombiesCerebros[j].x, zombiesCerebros[j].y);
		        		double numRandom = (int) (Math.random() * 10);
		        		
		        		if(dist < 20)
		        		{
		        			entorno.dibujarImagen(b.imgDaño, b.x, b.y, 0, 0.2);
		        			zombiesCerebros[j].vida -= 1;
		        			zombiesCerebros[j].velocidad -= 0.02;
		        			
		        			if(zombiesCerebros[j].vida <= 0)
		        			{
		        				zombiesCerebros[j] = null;
		        				this.zombiesEliminados+=1;
		        				this.zombiesRestantes--;
		        				
		        				if(numRandom <= 4)
		        				{
		        					for(int t = 0; t < tumbas.length; t++)
		        					{
		        						if(tumbas[t] == null && !celdaOcupada(centro[0], centro[1]))
		        						{
		        							tumbas[t] = new Tumba(centro[0], centro[1], entorno);
		        							tumbas[t].enterrada = true;
		        							break;
		        						}
		        					}
		        				}
		        			}
		        			bolasAzul[k] = null;      		
		        		}
		        	}
			     }
		        
		        for(int j = 0; j < tumbas.length; j++)
		        {
		        	if(tumbas[j] != null && tumbas[j].enterrada)
		        	{
		        		double dist = Math.sqrt( Math.pow(b.x - tumbas[j].x, 2) + Math.pow(b.y - tumbas[j].y, 2) );
		        		
		        		if(dist < 40)
		        		{
		        			entorno.dibujarImagen(b.imgDaño, b.x, b.y, 0, 0.2);
		        			tumbas[j].vida -= 2;
		        			
		        			if(tumbas[j].vida == 0)
		        			{
		        				tumbas[j].enterrada = false;
		        				tumbas[j] = null;
		        			}
		        			bolasAzul[k] = null; 
		        		}
		        	}
		        }
		        if(this.colosal != null)
		        {
		        	double dist = Math.sqrt( Math.pow(b.x - colosal.x, 2) + Math.pow(b.y - colosal.y, 2) );
		        	
		        	if(dist < 230)
		        	{
		        		entorno.dibujarImagen(b.imgDaño, b.x, b.y, 0, 0.2);
		        		colosal.vida -= 0.5;
		        		if(colosal.vida <= 0)
		        		{
		        			for (int e = 0; e < explosiones.length; e++) 
		        	    	{
		        	    	    if (explosiones[e] == null) 
		        	    	    {
		        	    	        explosiones[e] = new Explosion(colosal.x, colosal.y, entorno, 3);
		        	    	        break;
		        	    	    }
		        	    	}
		        			colosal = null;
		        			this.tiempoExplosionColosal = tiempoJuego;
		        		}
		        		bolasAzul[k] = null;
		        	}
		        }
			}
		}
		
		
		
		// ---------------------- Bolas Carbon -------------------------------
		
		//Genera una bola carbon en un determinado tiempo
		
		for (Carbonopodo g : guisantes) {
	        if (g != null && g.plantada && !g.disparando && this.hayZombiesEnCesped()) {
	            if (tiempoActual - g.tiempoUltimoDisparo >= g.intervaloDisparo) {
	                for (int j = 0; j < bolasCarbon.length; j++) {
	                    if (bolasCarbon[j] == null) {
	                    	bolasCarbon[j] = new BolaCarbon(g.x+30, g.y-5, entorno);
	                        break;
	                    }
	                }
	                g.tiempoUltimoDisparo = tiempoActual;
	            }
	        }
	    }
		
		
		for(int k = 0; k < bolasCarbon.length; k++)
		{
			
			
			BolaCarbon b = bolasCarbon[k];
			if(b != null)
			{
				b.mover();
				b.dibujar();
				 // Si sale del entorno, la eliminamos
		        if (b.x > ancho) {
		            bolasCarbon[k] = null;
		        }
		        
		        for(int j = 0; j < zombies.length; j++)
		        {
		        	if(zombies[j] != null)
		        	{
		        		double dist = Math.sqrt( Math.pow(b.x - zombies[j].x, 2) + Math.pow(b.y - zombies[j].y, 2) );
		        		double [] centro = c.obtenerCentroCeldaMasCercana(zombies[j].x, zombies[j].y);
		        		double numRandom = (int) (Math.random() * 10);
		        		
		        		if(dist < 20)
		        		{
		        			entorno.dibujarImagen(b.imgDaño, b.x, b.y, 0, 0.2);
		        			zombies[j].vida -= 2;
		        			
		        			if(zombies[j].vida <= 0)
		        			{
		        				zombies[j] = null;
		        				this.zombiesEliminados+=1;
		        				this.zombiesRestantes--;
		        				
		        				if(numRandom <= 4)
		        				{
		        					for(int t = 0; t < tumbas.length; t++)
		        					{
		        						if(tumbas[t] == null && !celdaOcupada(centro[0], centro[1]))
		        						{
		        							tumbas[t] = new Tumba(centro[0], centro[1], entorno);
		        							tumbas[t].enterrada = true;
		        							break;
		        						}
		        					}
		        				}
		        			}
		        			bolasCarbon[k] = null;      		
		        		}
		        		
		        	}
			     }
		        
		        for(int j = 0; j < zombiesBart.length; j++)
		        {
		        	if(zombiesBart[j] != null)
		        	{
		        		double dist = Math.sqrt( Math.pow(b.x - zombiesBart[j].x, 2) + Math.pow(b.y - zombiesBart[j].y, 2) );
		        		double [] centro = c.obtenerCentroCeldaMasCercana(zombiesBart[j].x, zombiesBart[j].y);
		        		double numRandom = (int) (Math.random() * 10);
		        		
		        		if(dist < 20)
		        		{
		        			entorno.dibujarImagen(b.imgDaño, b.x, b.y, 0, 0.2);
		        			
		        			zombiesBart[j].vida -= 2;
		        			
		        			if(zombiesBart[j].vida <= 0)
		        			{
		        				zombiesBart[j] = null;
		        				this.zombiesEliminados+=1;
		        				this.zombiesRestantes--;
		        				
		        				if(numRandom <= 4)
		        				{
		        					for(int t = 0; t < tumbas.length; t++)
		        					{
		        						if(tumbas[t] == null && !celdaOcupada(centro[0], centro[1]))
		        						{
		        							tumbas[t] = new Tumba(centro[0], centro[1], entorno);
		        							tumbas[t].enterrada = true;
		        							break;
		        						}
		        					}
		        				}
		        			}
		        			bolasCarbon[k] = null;      		
		        		}
		        	}
			     }
		        
		        for(int j = 0; j < zombiesCerebros.length; j++)
		        {
		        	if(zombiesCerebros[j] != null)
		        	{
		        		double dist = Math.sqrt( Math.pow(b.x - zombiesCerebros[j].x, 2) + Math.pow(b.y - zombiesCerebros[j].y, 2) );
		        		double [] centro = c.obtenerCentroCeldaMasCercana(zombiesCerebros[j].x, zombiesCerebros[j].y);
		        		double numRandom = (int) (Math.random() * 10);
		        		
		        		if(dist < 20)
		        		{
		        			entorno.dibujarImagen(b.imgDaño, b.x, b.y, 0, 0.2);
		        			zombiesCerebros[j].vida -= 2;
		        			
		        			if(zombiesCerebros[j].vida <= 0)
		        			{
		        				zombiesCerebros[j] = null;
		        				this.zombiesEliminados+=1;
		        				this.zombiesRestantes--;
		        				
		        				if(numRandom <= 4)
		        				{
		        					for(int t = 0; t < tumbas.length; t++)
		        					{
		        						if(tumbas[t] == null && !celdaOcupada(centro[0], centro[1]))
		        						{
		        							tumbas[t] = new Tumba(centro[0], centro[1], entorno);
		        							tumbas[t].enterrada = true;
		        							break;
		        						}
		        					}
		        				}
		        			}
		        			bolasCarbon[k] = null;      		
		        		}
		        	}
			     }
		        
		        for(int j = 0; j < tumbas.length; j++)
		        {
		        	if(tumbas[j] != null && tumbas[j].enterrada)
		        	{
		        		double dist = Math.sqrt( Math.pow(b.x - tumbas[j].x, 2) + Math.pow(b.y - tumbas[j].y, 2) );
		        		
		        		if(dist < 40)
		        		{
		        			entorno.dibujarImagen(b.imgDaño, b.x, b.y, 0, 0.2);
		        			tumbas[j].vida -= 2;
		        			
		        			if(tumbas[j].vida == 0)
		        			{
		        				tumbas[j].enterrada = false;
		        				tumbas[j] = null;
		        			}
		        			bolasCarbon[k] = null; 
		        		}
		        	}
		        }
		        if(this.colosal != null)
		        {
		        	double dist = Math.sqrt( Math.pow(b.x - colosal.x, 2) + Math.pow(b.y - colosal.y, 2) );
		        	
		        	if(dist < 230)
		        	{
		        		entorno.dibujarImagen(b.imgDaño, b.x, b.y, 0, 0.2);
		        		colosal.vida -= 1;
		        		if(colosal.vida <= 0)
		        		{
		        			for (int e = 0; e < explosiones.length; e++) 
		        	    	{
		        	    	    if (explosiones[e] == null) 
		        	    	    {
		        	    	        explosiones[e] = new Explosion(colosal.x, colosal.y, entorno, 3);
		        	    	        break;
		        	    	    }
		        	    	}
		        			colosal = null;
		        			this.tiempoExplosionColosal = tiempoJuego;
		        		}
		        		bolasCarbon[k] = null;
		        	}
		        }
			}
		}
		
		
		
		
		
		// ------------------------ Seccion disparos de zombies -------------------------------------
		
		
		
		// Se generan disparos para zombies comunes y zombies bart
		
		for(int k = 0; k < zombies.length; k++)
		{
			if(zombies[k] != null)
			{
				if(tiempoJuego - zombies[k].tiempoUltimoDisparo >= zombies[k].intervaloDisparo)
				{
					for(int j = 0; j < portales.length; j++)
					{
						if(portales[j] == null)
						{
							portales[j] = new ProyectilZombie(zombies[k].x, zombies[k].y, entorno);
							break;
						}
					}
					zombies[k].tiempoUltimoDisparo = this.tiempoJuego;
				}
			}
		}
		
		
		
		for(int k = 0; k < zombiesBart.length; k++)
		{
			if(zombiesBart[k] != null)
			{
				if(tiempoJuego - zombiesBart[k].tiempoUltimoDisparo >= zombiesBart[k].intervaloDisparo)
				{
					for(int j = 0; j < portales.length; j++)
					{
						if(portales[j] == null)
						{
							portales[j] = new ProyectilZombie(zombiesBart[k].x, zombiesBart[k].y, entorno);
							break;
						}
					}
					zombiesBart[k].tiempoUltimoDisparo = this.tiempoJuego;
				}
			}
		}
		
		
		
		// -------------- Se dibujan y colisionan disparos de zombies comunes con disparos de plantas y plantas ---------------------
		
		for(int k = 0; k < portales.length; k++)
		{
			if(portales[k] != null)
			{
				portales[k].mover();
				portales[k].dibujar();
				
				if(portales[k].x < c.x + c.ancho/2)
				{
					portales[k] = null;
				}
			}
		}
		
		
		
		
		for(int k = 0; k < portales.length; k++)
		{
			if(portales[k] != null)
			{
				for(int j = 0; j < this.bolas.length; j++)
				{
					if(bolas[j] != null)
					{
						
						double dist = Math.sqrt(Math.pow(portales[k].x - bolas[j].x, 2) + Math.pow(portales[k].y - bolas[j].y, 2));
						
						if(dist < 20 )
						{
							bolas[j] = null;
							portales[k] = null;
							break;
						}
					}
				}
			}
		}
		
		
		
		for(int k = 0; k < portales.length; k++)
		{
			if(portales[k] != null)
			{
				for(int j = 0; j < this.bolasAzul.length; j++)
				{
					if(bolasAzul[j] != null)
					{
						
						double dist = Math.sqrt(Math.pow(portales[k].x - bolasAzul[j].x, 2) + Math.pow(portales[k].y - bolasAzul[j].y, 2));
						
						if(dist < 20 )
						{
							bolasAzul[j] = null;
							portales[k] = null;
							break;
						}
					}
				}
			}
		}
		
		
		for(int k = 0; k < portales.length; k++)
		{
			if(portales[k] != null)
			{
				for(int j = 0; j < this.bolasCarbon.length; j++)
				{
					if(bolasCarbon[j] != null)
					{
						
						double dist = Math.sqrt(Math.pow(portales[k].x - bolasCarbon[j].x, 2) + Math.pow(portales[k].y - bolasCarbon[j].y, 2));
						
						if(dist < 20 )
						{
							bolasCarbon[j] = null;
							portales[k] = null;
							break;
						}
					}
				}
			}
		}
		
		
		for(int k = 0; k < portales.length; k++)
		{
			if(portales[k] != null)
			{
				for(int j = 0; j < this.fuego.length; j++)
				{
					if(fuego[j] != null && fuego[j].plantada)
					{
						
						double dist = Math.sqrt(Math.pow(portales[k].x - fuego[j].x, 2) + Math.pow(portales[k].y - fuego[j].y, 2));
						
						if(dist < 20 )
						{
							entorno.dibujarImagen(portales[k].imgDaño, portales[k].x, portales[k].y, 0, 0.2);
							fuego[j].vida -= 2;
							
							if(fuego[j].vida <= 0)
							{
								fuego[j] = null;
							}
							
							portales[k] = null;
							break;
						}
					}
				}
			}
		}
		
		for(int k = 0; k < portales.length; k++)
		{
			if(portales[k] != null)
			{
				for(int j = 0; j < this.huracoles.length; j++)
				{
					if(huracoles[j] != null && huracoles[j].plantada)
					{
						
						double dist = Math.sqrt(Math.pow(portales[k].x - huracoles[j].x, 2) + Math.pow(portales[k].y - huracoles[j].y, 2));
						
						if(dist < 20 )
						{
							entorno.dibujarImagen(portales[k].imgDaño, portales[k].x, portales[k].y, 0, 0.2);
							huracoles[j].vida -= 2;
							
							if(huracoles[j].vida <= 0)
							{
								huracoles[j] = null;
								
							}
							
							portales[k] = null;
							break;
						}
					}
				}
			}
		}
		
		for(int k = 0; k < portales.length; k++)
		{
			if(portales[k] != null)
			{
				for(int j = 0; j < this.nueces.length; j++)
				{
					if(nueces[j] != null && nueces[j].plantada)
					{
						
						double dist = Math.sqrt(Math.pow(portales[k].x - nueces[j].x, 2) + Math.pow(portales[k].y - nueces[j].y, 2));
						
						if(dist < 20 )
						{
							entorno.dibujarImagen(portales[k].imgDaño, portales[k].x, portales[k].y, 0, 0.2);
							nueces[j].vida -= 2;
							if(nueces[j].vida <= 0)
							{
								for(int e = 0; e < explosiones.length; e++)
								{
									if(explosiones[e] == null)
									{
										explosiones[e] = new Explosion(nueces[j].x, nueces[j].y-40, entorno, 0.7);
										break;
									}
								}
								nueces[j] = null;
							}
							portales[k] = null;
							break;
						}
					}
				}
			}
		}
		
		
		
		for(int k = 0; k < portales.length; k++)
		{
			if(portales[k] != null)
			{
				for(int j = 0; j < this.guisantes.length; j++)
				{
					if(guisantes[j] != null && guisantes[j].plantada)
					{
						
						double dist = Math.sqrt(Math.pow(portales[k].x - guisantes[j].x, 2) + Math.pow(portales[k].y - guisantes[j].y, 2));
						
						if(dist < 20 )
						{
							entorno.dibujarImagen(portales[k].imgDaño, portales[k].x, portales[k].y, 0, 0.2);
							guisantes[j].vida -= 2;
							
							if(guisantes[j].vida <= 0)
							{
								guisantes[j] = null;
								
							}
							
							portales[k] = null;
							break;
						}
					}
				}
			}
		}
		
		
		
		
		
	
		// ------------------- Dibujar tumbas -----------------------
		
		for (int k = 0; k < tumbas.length; k++)
		{
			if(tumbas[k] != null && tumbas[k].enterrada)
			{
				tumbas[k].dibujar();
			}
		}
		
		
		
		
		
		
		
		
		// ----------------- Condicion para ganar el juego --------------------
		
		if (jugando && this.colosal == null && this.colosalCreado)
		{
			// Cuando el colosal muere las plantas dejan de disparar
			
			for(int k = 0; k < fuego.length; k++)
			{
				if(fuego[k] != null && fuego[k].plantada)
				{
					fuego[k].disparando = true;
				}
				
			}
			for(int k = 0; k < huracoles.length; k++)
			{
				if(huracoles[k] != null && huracoles[k].plantada)
				{
					huracoles[k].disparando = true;
				}
				
			}
			
			for(int k = 0; k < guisantes.length; k++)
			{
				if(guisantes[k] != null && guisantes[k].plantada)
				{
					guisantes[k].disparando = true;
				}
			}
			
			if(tiempoJuego - this.tiempoExplosionColosal >= 5)
			{
				ganaste = true;
			    jugando = false;
			}
		    
		}
		
		
	}
	
	
	public void reiniciarJuego() {
		
	    // Limpio plantas, zombies, disparos y explosiones
	    Arrays.fill(fuego, null);
	    Arrays.fill(nueces, null);
	    Arrays.fill(huracoles, null);
	    Arrays.fill(guisantes, null);
	    Arrays.fill(bolasCarbon, null);
	    Arrays.fill(bolas, null);
	    Arrays.fill(bolasAzul, null);
	    Arrays.fill(zombies, null);
	    Arrays.fill(zombiesBart, null);
	    Arrays.fill(zombiesCerebros, null);
	    Arrays.fill(explosiones, null);
	    Arrays.fill(energia, null);
	    Arrays.fill(tumbas, null);
	    Arrays.fill(portales, null);

	    // Reaparezco las plantas base del panel
	    fuego[0] = new LanzaLlamas(170, 68, entorno);
	    nueces[0] = new Nuez(269.6, 88, entorno);
	    huracoles[0] = new Huracol(378, 84, entorno);
	    guisantes[0] = new Carbonopodo(491, 85, entorno);
	    this.colosal = null;

	    // Reseteo variables
	    this.zombiesEliminados = 0;
	    this.tiempoUltimoCreado = 0;
	    this.tiempoUltimoCreadoBart = 0;
	    this.tiempoUltimoCreadoCerebro = 0;
	    this.zombiesRestantes = 50;
	    this.zombiesCreados = 0;
	    this.ultimaPlantadaHuracol = 0;
	    this.ultimaPlantadaPlanta = 0;
	    this.ultimaPlantadaNuez = 0;
	    this.ultimaPlantadaGuisante = 0;
	    this.tiempoJuego = 0;
	    this.tiempoExplosionColosal = 0;
	    this.tiempoParaCrearColosal = 0;
	    this.esperandoRecargaHuracol = false;
	    this.esperandoRecargaNuez = false;
	    this.esperandoRecargaPlanta = false;
	    this.esperandoRecargaGuisante = false;
	    this.colosalCreado = false;
	    this.anuncioColosal = false;
	    
	    this.tiempoUltimoTick = entorno.tiempo() / 1000.0;

	 // Restauro banderas
	    jugando = true;
	    pausado = false;
	    gameOver = false;
	    ganaste = false;
	}
	
	
	
	// ----------------- Metodos de la clase juego ---------------------------
	

	// Metodo que devuelve true si hay planta en panel
	public boolean hayPlantaHuracolPanel(Huracol[] h)
	{
		for(int k = 0; k < h.length; k++)
		{
			if(h[k] != null && !h[k].plantada && h[k].y < (c.y - c.alto/2) )
			{
				return true;
			}
		}
		return false;
	}
	
	// Metodo que devuelve true si hay planta en panel
	public boolean hayPlantaNuezPanel(Nuez[] n)
	{
		for(int k = 0; k < n.length; k++)
		{
			if(n[k] != null && n[k].y < (c.y - c.alto/2) )
			{
				return true;
			}
		}
		return false;
	}
	// Metodo que devuelve true si hay planta en panel
	public boolean hayPlantaFuegoPanel(LanzaLlamas [] f)
	{
		for(int k = 0; k < f.length; k++)
		{
			if(f[k] != null && !f[k].plantada && f[k].y < (c.y - c.alto/2) )
			{
				return true;
			}
		}
		return false;
	}
	
	// Metodo que devuelve true si hay planta en panel
	public boolean hayPlantaGuisantePanel(Carbonopodo [] g)
	{
		for(int k = 0; k < g.length; k++)
		{
			if(g[k] != null && !g[k].plantada && g[k].y < (c.y - c.alto/2) )
			{
				return true;
			}
		}
		return false;
	}
	
	// Metodo booleano que devuelve true si hay zombies, tumbas o colosal en cesped
	public boolean hayZombiesEnCesped()
	{
		for(int k = 0; k < zombies.length; k++)
		{
			if(zombies[k] != null)
			{
				if(zombies[k].x < this.ancho)
				{
					return true;
				}
			}
		}
		for(int k = 0; k < zombiesBart.length; k++)
		{
			if(zombiesBart[k] != null)
			{
				if(zombiesBart[k].x < this.ancho)
				{
					return true;
				}
			}
		}
		for(int k = 0; k < zombiesCerebros.length; k++)
		{
			if(zombiesCerebros[k] != null)
			{
				if(zombiesCerebros[k].x < this.ancho)
				{
					return true;
				}
			}
		}
		
		for(int k = 0; k < tumbas.length; k++)
		{
			if(tumbas[k] != null && tumbas[k].enterrada)
			{
				return true;
			}
		}
		
		if(this.colosalCreado) {
			return true;
		}
		return false;
	}
	
	public boolean celdaOcupada(double x, double y)
	{
		
	//Aca digo que siempre la columna de los regalos va a estar ocupada
		
		if(x < 160 + c.ancho/2)
		{
			return true;
		}
		
	    for (LanzaLlamas p : fuego) {
	        if (p != null && p.plantada) {
	            // Si la distancia entre el centro de la celda y la planta es muy chica,
	            // consideramos que está ocupada
	            double dist = Math.sqrt(Math.pow(p.x - x, 2) + Math.pow(p.y - y, 2));
	            if (dist < c.alto / 2) {
	                return true;
	            }
	        }
	    }
	    
	    for (Nuez n : nueces) {
	        if (n != null && n.plantada) {
	            // Si la distancia entre el centro de la celda y la planta es muy chica,
	            // consideramos que está ocupada
	            double dist = Math.sqrt(Math.pow(n.x - x, 2) + Math.pow(n.y - y, 2));
	            if (dist < c.alto / 2) {
	                return true;
	            }
	        }
	    }
	    
	    for (Huracol h : huracoles) {
	        if (h != null && h.plantada) {
	            // Si la distancia entre el centro de la celda y la planta es muy chica,
	            // consideramos que está ocupada
	            double dist = Math.sqrt(Math.pow(h.x - x, 2) + Math.pow(h.y - y, 2));
	            if (dist < c.alto / 2) {
	                return true;
	            }
	        }
	    }
	    
	    for (Carbonopodo g : guisantes) {
	        if (g != null && g.plantada) {
	            // Si la distancia entre el centro de la celda y la planta es muy chica,
	            // consideramos que está ocupada
	            double dist = Math.sqrt(Math.pow(g.x - x, 2) + Math.pow(g.y - y, 2));
	            
	            if (dist < c.alto / 2) {
	                return true;
	            }
	        }
	    }
	    
	    for (Tumba t : tumbas) {
	        if (t != null && t.enterrada) {
	            // Si la distancia entre el centro de la celda y la planta es muy chica,
	            // consideramos que está ocupada
	            double dist = Math.sqrt(Math.pow(t.x - x, 2) + Math.pow(t.y - y, 2));
	            
	            if (dist < c.alto / 2) {
	                return true;
	            }
	        }
	    }
	    
	    return false;
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
	
}
