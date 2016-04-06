package es.ucm.fdi.tp.practica5.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public abstract class BoardComponent extends JComponent {
	private int _CELL_HEIGHT = 50;
	private int _CELL_WIDTH = 50;
	private int rows;
	private int cols;
	private Color[][] boardColor;
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
		this.board = b;
		repaint();
	}
	
	private void initBoard(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		this.boardColor = new Color[rows][cols];
		
	}
	
	private void initGUI() {
		addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("Mouse Released");
			}
			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("Mouse Pressed");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				System.out.println("Mouse Exited Component");
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				System.out.println("Mouse Entered Component");
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Button" + e.getButton() + " Clicked at" +"(" + e.getX() + ","+ e.getY() + ")");
			}
		});
		//....
	}
	
	public void setBoardSize(int rows, int cols) {
		initBoard(rows, cols);
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		_CELL_WIDTH = this.getWidth() / cols;
		_CELL_HEIGHT = this.getHeight() / rows;
	}
	
	private void drawCell(int row, int col, Graphics g) {
		int x = col * _CELL_WIDTH;
		int y = row * _CELL_HEIGHT;
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x + 2, y + 2, _CELL_WIDTH - 4, _CELL_HEIGHT - 4);
		g.setColor(boardColor[row][col]);
		g.fillOval(x + 4, y + 4, _CELL_WIDTH - 8, _CELL_HEIGHT - 8);
		g.setColor(Color.black);
		g.drawOval(x + 4, y + 4, _CELL_WIDTH - 8, _CELL_HEIGHT - 8);
	}
	
	protected abstract Color getPieceColor(Piece p);
	protected abstract boolean isPlayerPiece(Piece p);
	protected abstract void mouseClicked(int row, int col, int mouseButton);
}
