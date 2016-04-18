package es.ucm.fdi.tp.practica5.attt;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica5.swing.RectBoardSwingView;

public class AdvancedTTTSwingView extends RectBoardSwingView {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AdvancedTTTSwingPlayer player;
	
	public AdvancedTTTSwingView(Observable<GameObserver> g, Controller c, Piece localPiece, Player random, Player ai) {
		super(g, c, localPiece, random, ai);
		player = new AdvancedTTTSwingPlayer();
		
	}

	@Override
	protected void handleMouseClick(int rowOrigin, int colOrigin, int mouseButton) {
		//Allow the moves of the mouse get captured, used in case the state or phase of the game dont allow us make a move
		if (getMouseActive()){
			addMsg("Left Click on the destination position, right click to cancel\n");
			//If we click right mouse, we cancel the move
			if (mouseButton != 3){
				//Obtener la fila y columna destino desde el raton
				int rowDest = 0, colDest = 0;
				player.setMove(rowOrigin, colOrigin, rowDest, colDest);
				decideMakeManualMove(player);
			}
		}
		
		
	}
	@Override
	protected void activateBoard() {
	// - add corresponding message to the status messages indicating
	// what to do for making a move, etc.
		super.activateBoard();
		addMsg("Click on an empty cell\n");
	}
}
