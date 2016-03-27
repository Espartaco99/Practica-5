package es.ucm.fdi.tp.practica5.swing;

import java.awt.Color;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public abstract class RectBoardSwingView extends SwingView {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BoardComponent boardComp;
	private boolean mouseActive = false;
	
	public RectBoardSwingView(Observable<GameObserver> g, Controller c, Piece localPiece, Player random, Player ai) {
		super(g, c, localPiece, random, ai);
	}
	@Override
	protected void initBoardGui() {
		boardComp = new BoardComponent() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			@Override
			protected void mouseClicked(int row, int col, int mouseButton) {
				handleMouseClick(row,col,mouseButton);
			}
			@Override
			protected Color getPieceColor(Piece p) {
				return null;
			// get the color from the colours table, and if not
			// available (e.g., for obstacles) set it to have a color
			};
			@Override
			protected boolean isPlayerPiece(Piece p) {
				return rootPaneCheckingEnabled;
			// return true if p is a player piece, false if not (e.g, an obstacle)
			}

		};
		
		setBoardArea(boardComp); // install the board in the view
	}
	@Override
	protected void redrawBoard() {
		// ask boardComp to redraw the board
		boardComp.redraw((Board) this);
	}
	
	protected abstract void handleMouseClick(int row, int col, int mouseButton);
	

	@Override
	protected void activateBoard() {
		//Añadir mensaje de que hacer con el juego, mirar como acceder a los elementos del super
		//super.
		mouseActive = true;
	}

	@Override
	protected void deActivateBoard() {
		mouseActive = false;
	}
	
	protected boolean getMouseActive(){
		return mouseActive;
	}

}
