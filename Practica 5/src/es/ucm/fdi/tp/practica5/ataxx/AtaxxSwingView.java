package es.ucm.fdi.tp.practica5.ataxx;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica5.swing.RectBoardSwingView;

public class AtaxxSwingView extends RectBoardSwingView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AtaxxSwingPlayer player;
	
	public AtaxxSwingView(Observable<GameObserver> g, Controller c, Piece localPiece, Player random, Player ai) {
		super(g, c, localPiece, random, ai);
		player = new AtaxxSwingPlayer();
		
	}

	@Override
	protected void handleMouseClick(int rowOrigin, int colOrigin, int mouseButton) {
		//Allow the moves of the mouse get captured, used in case the state or phase of the game dont allow us make a move
		if (getMouseActive()){
			//Obtener la fila y columna destino desde el raton
			int rowDest = 0, colDest = 0;
			player.setMove(rowOrigin, colOrigin, rowDest, colDest);
			decideMakeManualMove(player);
		}
		
		
	}
	@Override
	protected void activateBoard() {
	// - declare the board active, so handleMouseClick accepts moves
	// - add corresponding message to the status messages indicating
	// what to do for making a move, etc.
	}
	
	@Override
	protected void deActivateBoard() {
	// declare the board inactive, so handleMouseClick rejects moves
	}
}
