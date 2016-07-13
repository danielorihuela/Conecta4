package com.example.dnaiel.conecta4ad;

import java.util.Random;

public class Game {
    static final int NFILAS = 6;
    static final int NCOLUMNAS = 7;
    public static final int VACIO = 0;
    public static final int MAQUINA = 1;
    private final String MAQUINAFULL = "1111";
    public static final int JUGADOR = 2;
    private final String JUGADORFULL = "2222";
    private boolean juego_activo = true;

    private int tablero[][];

    public Game() {
        tablero = new int[NFILAS][NCOLUMNAS];

        for (int i = 0; i < NFILAS; i++)
            for (int j = 0; j < NCOLUMNAS; j++)
                tablero[i][j] = VACIO;
    }

    public boolean estaVacio(int i, int j) {
        if (i>=NFILAS || i<0) return false;
        if (j>=NCOLUMNAS || j<0) return false;
        if(tablero[i][j] == VACIO){
        	return true;
        }
    	return false;
    }

    public boolean estaJugador(int i, int j) {
   	 if (i>=NFILAS || i<0) return false;
   	 if (j>=NCOLUMNAS || j<0) return false;
   	 if(tablero[i][j] == JUGADOR){
      		 return true;
       	}
      	 return false;
   	}
    	
    public boolean estaMaquina(int i, int j) {
        if (i>=NFILAS || i<0) return false;
        if (j>=NCOLUMNAS || j<0) return false;
        if(tablero[i][j] == MAQUINA){
            return true;
        }
        return false;
    }

    public void ponerJugador(int i, int j) {
        tablero[i][j] = JUGADOR;
        }
    public void ponerMaquina(int i, int j) {
        tablero[i][j] = MAQUINA;
    }

    public boolean tableroLleno() {
        for (int i=0; i<NFILAS; i++)
            for (int j=0; j<NCOLUMNAS; j++)
                if (tablero[i][j] == VACIO)
                    return false;

        return true;
    }

    public boolean columnaLlena(int columna) {
        for(int i = 0; i < NFILAS; i++) {
            if (tablero[i][columna] == VACIO) {
                return false;
            }
        }
        return true;
    }

    public int fila3() {
        int columna;
        for (int i = 0; i < NFILAS; i++)
            for (int j = 0; j < NCOLUMNAS; j++)
                if (estaJugador(i, j) && estaJugador(i, j + 1) && estaJugador(i, j + 2)
                        && estaVacio(i, j + 3) && !estaVacio(i - 1, j + 3)) {
                    columna = j + 3;
                    return columna;
                } else if (estaMaquina(i, j) && estaMaquina(i, j + 1) && estaMaquina(i, j + 2)
                        && estaVacio(i, j + 3) && !estaVacio(i - 1, j + 3)) {
                    columna = j + 3;
                    return columna;
                }  else if (estaJugador(i, j) && estaJugador(i, j + 1) && estaJugador(i, j + 2)
                        && estaVacio(i, j - 1) && !estaVacio(i - 1, j - 1)) {
                    columna = j - 1;
                    return columna;
                } else if (estaMaquina(i, j) && estaMaquina(i, j + 1) && estaMaquina(i, j + 2)
                        && estaVacio(i, j - 1) && !estaVacio(i - 1, j - 1)) {
                    columna = j - 1;
                    return columna;
                } else if(estaJugador(i, j) && estaJugador(i, j + 2) && estaJugador(i, j + 3)
                        && estaVacio(i, j + 1) && !estaVacio(i - 1, j + 1)){
                    columna = j + 1;
                    return columna;
                } else if(estaMaquina(i, j) && estaMaquina(i, j + 2) && estaMaquina(i, j + 3)
                        && estaVacio(i, j + 1) && !estaVacio(i - 1, j + 1)){
                    columna = j + 1;
                    return columna;
                } else if(estaJugador(i, j) && estaJugador(i, j + 1) && estaJugador(i, j + 3)
                        && estaVacio(i, j + 2) && !estaVacio(i - 1, j + 2)){
                    columna = j + 2;
                    return columna;
                } else if(estaMaquina(i, j) && estaMaquina(i, j + 1) && estaMaquina(i, j + 3)
                        && estaVacio(i, j + 2) && !estaVacio(i - 1, j + 2)){
                    columna = j + 2;
                    return columna;
                }
        columna = -1;
        return columna;
        }


    public int columna3(){
        int columna;
        for (int i = 0; i < NFILAS; i++)
            for (int j = 0; j < NCOLUMNAS; j++)
                if(estaJugador(i, j) && estaJugador(i + 1,j) && estaJugador(i + 2, j) && estaVacio(i + 3, j)){
                    columna = j;
                    return  columna;
                } else if(estaMaquina(i, j) && estaMaquina(i + 1, j) && estaMaquina(i + 2, j) && estaVacio(i + 3, j)) {
                    columna = j;
                    return  columna;
                }
        columna = -1;
        return columna;
    }

    public int diagonalArriba3(){
        int columna;
        for (int i = 0; i < NFILAS; i++)
            for (int j = 0; j < NCOLUMNAS; j++)
                if(estaJugador(i, j) && estaJugador(i + 1,j - 1) && estaJugador(i + 2, j - 2)
                        && estaVacio(i + 3, j - 3) && !estaVacio(i + 2, j - 3)){
                    columna = j - 3;
                    return  columna;
                } else if(estaMaquina(i, j) && estaMaquina(i + 1,j - 1) && estaMaquina(i + 2, j - 2)
                        && estaVacio(i + 3, j - 3) && !estaVacio(i + 2, j - 3)){
                    columna = j - 3;
                    return  columna;
                } else if(estaJugador(i, j) && estaJugador(i + 1,j - 1) && estaJugador(i + 2, j - 2)
                        && estaVacio(i - 1, j + 1) && !estaVacio(i - 2, j + 1)){
                    columna = j + 1;
                    return  columna;
                } else if(estaMaquina(i, j) && estaMaquina(i + 1,j - 1) && estaMaquina(i + 2, j - 2)
                        && estaVacio(i - 1, j + 1) && !estaVacio(i - 2, j + 1)){
                    columna = j + 1;
                    return columna;
                } else if(estaJugador(i, j) && estaJugador(i + 1,j - 1) && estaJugador(i + 3, j - 3)
                        && estaVacio(i + 2, j - 2) && !estaVacio(i + 1, j - 2)){
                    columna = j - 2;
                    return  columna;
                }  else if(estaMaquina(i, j) && estaMaquina(i + 1, j - 1) && estaMaquina(i + 3, j - 3)
                        && estaVacio(i + 2, j - 2) && !estaVacio(i + 1, j - 2)){
                    columna = j - 2;
                    return  columna;
                } else if(estaJugador(i, j) && estaJugador(i + 2,j - 2) && estaJugador(i + 3, j - 3)
                        && estaVacio(i + 1, j - 1) && !estaVacio(i, j - 1)){
                    columna = j - 1;
                    return  columna;
                } else if(estaMaquina(i, j) && estaMaquina(i + 2,j - 2) && estaMaquina(i + 3, j - 3)
                        && estaVacio(i + 1, j - 1) && !estaVacio(i, j - 1)){
                    columna = j - 1;
                    return  columna;
                }
        columna = -1;
        return columna;
    }

    public int diagonalAbajo3(){
        int columna;
        for (int i = 0; i < NFILAS; i++)
            for (int j = 0; j < NCOLUMNAS; j++)
                if(estaJugador(i, j) && estaJugador(i + 1,j + 1) && estaJugador(i + 2, j + 2)
                        && estaVacio(i + 3, j + 3) && !estaVacio(i + 2, j + 3)){
                    columna = j + 3;
                    return  columna;
                } else if(estaMaquina(i, j) && estaMaquina(i + 1,j + 1) && estaMaquina(i + 2, j + 2)
                        && estaVacio(i + 3, j + 3) && !estaVacio(i + 2, j + 3)){
                    columna = j + 3;
                    return  columna;
                } else if(estaJugador(i, j) && estaJugador(i + 1,j + 1) && estaJugador(i + 2, j + 2)
                        && estaVacio(i - 1, j - 1) && !estaVacio(i - 2, j - 1)){
                    columna = j - 1;
                    return  columna;
                } else if(estaMaquina(i, j) && estaMaquina(i + 1,j + 1) && estaMaquina(i + 2, j + 2)
                        && estaVacio(i - 1, j - 1) && !estaVacio(i - 2, j - 1)) {
                    columna = j - 1;
                    return columna;
                } else if(estaJugador(i, j) && estaJugador(i + 1, j + 1) && estaJugador(i + 3, j + 3)
                        && estaVacio(i + 2, j + 2) && !estaVacio(i + 1, j + 2)){
                    columna = j + 2;
                    return  columna;
                } else if(estaMaquina(i, j) && estaMaquina(i + 1, j + 1) && estaMaquina(i + 3, j + 3)
                        && estaVacio(i + 2, j + 2) && !estaVacio(i + 1, j + 2)){
                    columna = j + 2;
                    return  columna;
                } else if(estaJugador(i, j) && estaJugador(i + 2, j + 2) && estaJugador(i + 3, j + 3)
                        && estaVacio(i + 1, j + 1) && !estaVacio(i, j + 1)){
                    columna = j + 1;
                    return  columna;
                } else if(estaMaquina(i, j) && estaMaquina(i + 2,j + 2) && estaMaquina(i + 3, j + 3)
                        && estaVacio(i + 1, j + 1) && !estaVacio(i, j + 1)){
                    columna = j + 1;
                    return  columna;
                }
        columna = generarColumna();
        return columna;
    }

    public int posicion(){
        int columna;
        if(fila3() < 0){
            columna = columna3();
            if(columna3() < 0){
                columna = diagonalArriba3();
                if(diagonalArriba3() < 0){
                    columna = diagonalAbajo3();
                    return columna;
                }
                return columna;
            }
            return columna;
        }
        columna = fila3();
        return columna;
    }

    public int generarColumna() {
        int columna;
        Random r = new Random();
        columna = r.nextInt(NCOLUMNAS);

        return columna;
    }

    public int generarFila(int columna) {
        int i;
        int fila = +1;
        for(i = 0; i < NFILAS; i++)
            if(tablero[i][columna] == VACIO){
                fila = i;
                break;
            }

        return fila;
    }

    
    boolean comprobarFila(int turno) {
		String cadena = turno == MAQUINA ? MAQUINAFULL : JUGADORFULL;
		
		for (int i = 0; i < NFILAS; i++) {
			String str = "";
			for (int j = 0; j < NCOLUMNAS; j++)
				str += Integer.toString(tablero[i][j]);
			if (str.contains(cadena))
				return true;
		}

		return false;
	}
    
    
    boolean comprobarColumna(int turno) {
		String cadena = turno == MAQUINA ? MAQUINAFULL : JUGADORFULL;
		
		for(int j = 0;j<NCOLUMNAS;j++) {
			String str="";
			for(int i = 0;i<NFILAS;i++)
				str += Integer.toString(tablero[i][j]);
				if(str.contains(cadena)){
					return true;
				}
		}
		
		return false;
	}
    
    boolean comprobarDiagonalArriba(int i,int j) {
    	return (
            estaJugador(i, j) &&
            estaJugador(i - 1, j + 1) &&
            estaJugador(i - 2, j + 2) &&
            estaJugador(i - 3, j + 3));
    }

    boolean comprobarDiagonalAbajo(int i,int j) {
    	return (
            estaJugador(i, j)&&
            estaJugador(i + 1, j + 1)&&
            estaJugador(i + 2, j + 2)&&
            estaJugador(i + 3, j + 3));
    }
    
    boolean comprobarDiagonalArriba2(int i,int j) {
    	return (
            estaMaquina(i, j)&&
            estaMaquina(i - 1, j + 1)&&
            estaMaquina(i - 2, j + 2)&&
            estaMaquina(i - 3, j + 3));
    }
    
    boolean comprobarDiagonalAbajo2(int i,int j) {
    	return (
            estaMaquina(i, j)&&
            estaMaquina(i + 1, j + 1)&&
            estaMaquina(i + 2, j + 2)&&
            estaMaquina(i + 3, j + 3));
    }

    
    boolean comprobarDiagonales(int turno) {
		for(int i = 0; i<NFILAS ; i++)
    		for(int j = 0; j<NCOLUMNAS; j++){
    			if(estaJugador(i,j)) {
    				if (comprobarDiagonalArriba(i,j) || comprobarDiagonalAbajo(i,j)){
                        return true;
                    }
    			} else {
                    if(comprobarDiagonalArriba2(i,j) || comprobarDiagonalAbajo2(i,j)){
                        return  true;
                    }
                }
    		}
		return false;
	}
    
    boolean comprobarCuatro(int turno){
    	if(comprobarDiagonales(turno) || comprobarColumna(turno) || comprobarFila(turno)){
    		juego_activo=false;
    		return true;
    	}
		return false;
    }
    
    public void restart() {
    	for(int i = 0; i<NFILAS; i++)
    		for(int j = 0; j<NCOLUMNAS; j++)
                tablero[i][j] = VACIO;
    	
    	juego_activo = true;
    }
    
    public boolean fin() {
    	if(tableroLleno() || !juego_activo){
    		return true;
    	}
    	return false;
    }
}