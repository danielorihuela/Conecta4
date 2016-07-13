package com.example.dnaiel.conecta4ad;

public class GamePvP {
    static final int NFILAS = 6;
    static final int NCOLUMNAS = 7;
    static final int VACIO = 0;
    static final int JUGADOR = 1;
    private final String JUGADORFULL = "1111";
    static final int JUGADOR2 = 2;
    private final String JUGADOR2FULL = "2222";
    private boolean juego_activo = true;
	private int turno = JUGADOR2;
    
    private int tablero[][];

    public GamePvP() {
        tablero = new int[NFILAS][NCOLUMNAS];

        for (int i = 0; i < NFILAS; i++)
            for (int j = 0; j < NCOLUMNAS; j++)
                tablero[i][j] = VACIO;
    }
    
    
    public boolean estaVacio(int i, int j) {
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
    
    public boolean estaJugador2(int i, int j) {
    	 if (i>=NFILAS || i<0) return false;
       	 if (j>=NCOLUMNAS || j<0) return false;
       	 if(tablero[i][j] == JUGADOR2){
          		 return true;
           	}
          	 return false;
       	}

    public void ponerJugador(int i, int j) {
    	if(turno == JUGADOR)
        tablero[i][j] = JUGADOR;
    	
    	if(turno == JUGADOR2)
            tablero[i][j] = JUGADOR2;
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

	public int generarFila(int columna) {
		int i;
		int fila = +1;
		for(i = 0; i > -1; i++)
			if(tablero[i][columna] == VACIO){
				fila = i;
				break;
			}

		return fila;
	}
    
    boolean comprobarFila(int turno) {
		String cadena = turno == JUGADOR ? JUGADORFULL : JUGADOR2FULL;
		
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
		String cadena = turno == JUGADOR ? JUGADORFULL : JUGADOR2FULL;
		
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
    estaJugador(i,j)&& 
    estaJugador(i-1,j+1)&& 
    estaJugador(i-2,j+2)&&
    estaJugador(i-3,j+3)
    );
    }
    
    boolean comprobarDiagonalAbajo(int i,int j) {
    	return (
    estaJugador(i,j)&& 
    estaJugador(i+1,j+1)&& 
    estaJugador(i+2,j+2)&&
    estaJugador(i+3,j+3)
    );
    }
    
    boolean comprobarDiagonalArriba2(int i,int j) {
    	return (
    estaJugador2(i,j)&& 
    estaJugador2(i-1,j+1)&& 
    estaJugador2(i-2,j+2)&&
    estaJugador2(i-3,j+3)
    );
    }
    
    boolean comprobarDiagonalAbajo2(int i,int j) {
    	return (
    estaJugador2(i,j)&& 
    estaJugador2(i+1,j+1)&& 
    estaJugador2(i+2,j+2)&&
    estaJugador2(i+3,j+3)
    );
    }

    
    boolean comprobarDiagonales(int turno) {
		for(int i = 0; i<NFILAS ; i++)
    		for(int j = 0; j<NCOLUMNAS; j++){
    			if(estaJugador(i,j) || estaJugador2(i,j)) {
    				if (comprobarDiagonalArriba(i,j) || comprobarDiagonalAbajo(i,j) ||
    						comprobarDiagonalArriba2(i,j) || comprobarDiagonalAbajo2(i,j)) 
    					return true;
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
    
    public void cambiarTurno() {
    	if(turno == JUGADOR){
    		turno = JUGADOR2;
    	} else {
    		turno = JUGADOR;
    	}
    }

	public int quienJuega() {
		if(turno == JUGADOR){
			turno = JUGADOR;
		}
		
		if(turno == JUGADOR2){
			turno = JUGADOR2;
		}
		
		return turno;		
	}

}