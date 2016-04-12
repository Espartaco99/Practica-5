package es.ucm.fdi.tp.practica5.attt;

import java.util.List;
import es.ucm.fdi.tp.basecode.attt.AdvancedTTTMove;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;


public class AdvancedTTTSwingPlayer extends Player {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int rowOrigin;
		private int colOrigin;
		private int rowDest;
		private int colDest;

		public void setMove(int rowOrigin, int colOrigin, int rowDest, int colDest) {
			this.rowOrigin = rowOrigin;
			this.colOrigin = colOrigin;
			this.rowDest = rowDest;
			this.colDest = colDest;
		}

		@Override
		public GameMove requestMove(Piece p, Board board, List<Piece> pieces,
				GameRules rules) {
			GameMove move = new AdvancedTTTMove(rowOrigin,colOrigin,rowDest, colDest, p);
			
			return move;
		}

}
