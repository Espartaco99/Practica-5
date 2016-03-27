package es.ucm.fdi.tp.practica5.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;


public abstract class SwingView extends JFrame implements GameObserver {
	
	private Controller ctrl;
	private Piece localPiece;
	private Piece turn;
	//De donde saco el tablero
	private Board board;
	//De donde saco la lista de piezas
	private List<Piece> pieces;
	//De donde saco toda la paleta de colores
	private Map<Piece, Color> pieceColors;
	//Manejar PlayerMode, no me gusta el enumerado
	private Map<Piece, PlayerMode> playerTypes;
	private String gameDesc;

	/**
	 * Player modes (manual, random, etc.)
	 * <p>
	 * Modos de juego.
	 */
	enum PlayerMode {
		MANUAL("m", "Manual"), RANDOM("r", "Random"), AI("a", "Automatics");

		private String id;
		private String desc;

		PlayerMode(String id, String desc) {
			this.id = id;
			this.desc = desc;
		}

		public String getId() {
			return id;
		}

		public String getDesc() {
			return desc;
		}

		@Override
		public String toString() {
			return id;
		}
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SwingView(){
		
	}
	
	public SwingView(Observable<GameObserver> g, Controller c, Piece localPiece, Player randPlayer, Player aiPlayer) {
		super("Board Games: ");
		this.ctrl = c;
		this.localPiece = localPiece;
		
		//this.playerTypes.put(localPiece, PlayerMode.MANUAL);
		initGUI();
		g.addObserver(this);
	}
	/**
	 * Construct the view
	 */
	private void initGUI(){ 
		this.setSize(400, 200);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Set the panel to be the one of the swingView
		JPanel mainPanel = new JPanel( new BorderLayout(5,5) );
		this.setContentPane(mainPanel);
		
		
		//initBoardGui(); 
	}
	final protected Piece getTurn() { return turn; }
	final protected Board getBoard() { return board; }
	final protected Board getPieces() { return (Board) pieces; }
	final protected Color getPieceColor(Piece p) { return pieceColors.get(p); }
	final protected Color setPieceColor(Piece p, Color c) { return pieceColors.put(p,c); }
	final protected void setBoardArea(JComponent c) { 
		
	}
	final protected void addMsg(String msg) { 
		
	}
	
	final protected void decideMakeManualMove(Player manualPlayer) { 
		
	}
	
	@SuppressWarnings("unused")
	private void decideMakeAutomaticMove() { 
		/*
		if (turn == //automatic and belongs to this view)
		ctrl.makeMove(); 
		*/
	}
	
	protected abstract void initBoardGui();
	protected abstract void activateBoard();
	protected abstract void deActivateBoard();
	protected abstract void redrawBoard();

	private void quit() {
		int n = JOptionPane.showOptionDialog(new JFrame(), "Are sure you want to quit?", "Quit",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
		if (n == 0) {
			// finalize your app
			System.exit(0);
		}
	}
	
	// GameObserver Methods
	public void onGameStart(final Board board, final String gameDesc, final List<Piece> pieces, final Piece turn) {
		this.pieces = pieces;
		this.board = board;
		this.turn = turn;
		this.gameDesc = gameDesc;
		SwingUtilities.invokeLater(new Runnable() {
			//No se que hacer con esto
			public void run() {
				
				handleGameStart(); 
			}

			
		});
	}
	
	
	/**
	 * Adds the main boxes and elements in the panel previously made by initGUI
	 */
	//No funciona la insercion de BorderLayout.Center ni BorderLayout.Line_End
	private void handleGameStart() {
		JPanel visualBoard = new JPanel();
		visualBoard.setPreferredSize(new Dimension(20,20));	
		visualBoard.setBackground(Color.GRAY);
		
		this.add(visualBoard, BorderLayout.CENTER);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		
		Border b = BorderFactory.createLineBorder(Color.black, 2);
		
		JPanel optionsPanel = new JPanel();
		
		optionsPanel.setPreferredSize(new Dimension(50,50));
		
		String story = "I sexually Identify as an Attack Helicopter. Ever since I was a boy I dreamed of soaring over the oilfields dropping hot sticky loads on disgusting foreigners. People say to me that a person being a helicopter is Impossible and I’m fucking retarded but I don’t care, I’m beautiful. I’m having a plastic surgeon install rotary blades, 30 mm cannons and AMG-114 Hellfire missiles on my body. From now on I want you guys to call me “Apache” and respect my right to kill from above and kill needlessly. If you can’t accept me you’re a heliphobe and need to check your vehicle privilege. Thank you for being so understanding.";
		JTextArea storyArea = new JTextArea(story);
		storyArea.setEditable(false);
		storyArea.setLineWrap(true);
		storyArea.setWrapStyleWord(true);
		JScrollPane area = new JScrollPane(storyArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		area.setPreferredSize(new Dimension(300, 200));
		area.setBorder(BorderFactory.createTitledBorder(b, "Status Messages"));
		optionsPanel.add(area);
		
		//Falla porque no tenemos la lista de piezas pasada por ningun lado
		String namePieces[] = new String[pieces.size()];
		for (int i =0; i < pieces.size(); i++){
			namePieces[i] = pieces.get(i).toString();
		}
		
		JComboBox<String> listPieces = new JComboBox<>(namePieces);
		listPieces.setSelectedIndex(0);
		optionsPanel.add(listPieces);
		String nameModes[] = {"Manual", "Intelligent", "Random"};
		JComboBox<String> listModes = new JComboBox<>(nameModes);
		optionsPanel.add(listModes);
		//Falta comboBox igual que listPieces
		
		JButton quit = new JButton("Quit");
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { quit(); }
		});
		optionsPanel.add(quit);
		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowListener() {
			public void windowClosing(WindowEvent e) { quit(); }

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		
		});
		
		this.add(optionsPanel, BorderLayout.LINE_END);
		this.pack();
		this.setVisible(true);
		
	}
	
	//Hacer algo con el estado 
	public void onGameOver(final Board board, final State state, final Piece winner) {
		this.board = board;
		this.turn = winner;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() { 
				//Mostrar mensaje de victoria o empate
				//handleOnGameOver(); 
			}
		});
	}
	public void onMoveStart(Board board, Piece turn) {
		this.board = board;
		this.turn = turn;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() { 
			//	handleOnMoveStart(); 
			}
		});
	}
	public void onMoveEnd(Board board, Piece turn, boolean success) {
		this.board = board;
		this.turn = turn;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() { 
				//handleOnMoveEnd(); 
			}
		});
	}
	public void onChangeTurn(Board board, final Piece turn) {
		this.board = board;
		this.turn = turn;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() { 
				//handleOnChangeTurn(); 
			}
		});
	}
	
	public void onError(String msg) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() { 
				JFrame frame = new JFrame();
				JOptionPane.showMessageDialog(frame, msg, "ERROR", JOptionPane.ERROR_MESSAGE);
				//handleOnError(); 
			}
		});
	}
	
}
