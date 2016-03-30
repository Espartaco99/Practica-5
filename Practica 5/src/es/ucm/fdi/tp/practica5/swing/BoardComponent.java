package es.ucm.fdi.tp.practica5.swing;

import java.awt.Color;

import javax.swing.JComponent;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public abstract class BoardComponent extends JComponent {
	
	private Board board;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//habra que hacer algo, seguramente inicializar board
	public BoardComponent(Board board){
		this.board = board;
	}
	
	public void redraw(Board b){
		
	}
	
	protected abstract Color getPieceColor(Piece p);
	protected abstract boolean isPlayerPiece(Piece p);
	protected abstract void mouseClicked(int row, int col, int mouseButton);
}
