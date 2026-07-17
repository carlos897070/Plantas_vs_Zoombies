package juego;

import java.awt.Image;

import entorno.Entorno;

public class Proyectil {
	
	double x, y, escala, dx, angulo;
	Image img, imgDaño;
	Entorno e;
	
	public void dibujar()
	{
		e.dibujarImagen(img, this.x, this.y, angulo, escala);
	}
	
	public void mover()
	{
		this.x += dx;
	}
	
	public boolean colision(ZombieEstandar z)
	{
		if(z != null)
    	{
    		double dist = Math.sqrt( Math.pow(this.x - z.x, 2) + Math.pow(this.y - z.y, 2) );
    		
    		if(dist < 20)
    		{
    			e.dibujarImagen(this.imgDaño, this.x, this.y, 0, 0.2);
    			return true;
    			
    		}
    	}
		return false;
	}
	
	public boolean colision(Tumba t)
	{
		if(t != null && t.enterrada)
    	{
    		double dist = Math.sqrt( Math.pow(this.x - t.x, 2) + Math.pow(this.y - t.y, 2) );
    		
    		if(dist < 40)
    		{
    			e.dibujarImagen(this.imgDaño, this.x, this.y, 0, 0.2);
    			return true;
    		}
    	}
		return false;
	}

}
