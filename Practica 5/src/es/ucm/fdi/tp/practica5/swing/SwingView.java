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
import javax.swing.JTable;
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
import es.ucm.fdi.tp.practica5.Main.PlayerMode;


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
	private JPanel crtlPanel;
	private JPanel mainPanel;

	
	
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
		mainPanel = new JPanel( new BorderLayout(5,5) );
		this.setContentPane(mainPanel);
		
		
		crtlPanel = new JPanel(); // BOXLAYOUT
		mainPanel.add(crtlPanel, BorderLayout.LINE_END);
		
		//Hacer funciones para añadir cada componente, completar el codigo de estas funciones
		addQuitRestartButtons();
		addStatusMessages();
		addPlayerInformation();
		addPieceColors();
		addPlayerModes();
		addAutomaticMoves();
		initBoardGui();
		
		/*
		//Tabla
		String[] columnNames = { "Player", "Mode", "#Pieces" };
		Object[][] data = {
				{ "X", "Manual", ""},
				{ "O", "Inteligent", ""},
		};
		JTable table = new JTable(data,columnNames);
		//Falta cambiar el color a la tabla
		mainPanel.add(new JScrollPane(table));
		table.setFillsViewportHeight(true);
		*/
		
	}
	private void addAutomaticMoves() {
		// TODO Auto-generated method stub
		
	}

	private void addPlayerModes() {
		// TODO Auto-generated method stub
		
	}

	private void addPieceColors() {
		// TODO Auto-generated method stub
		
	}

	private void addPlayerInformation() {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Adds the TextArea (usar JTextArea) where the messages are gonna be displayed
	 */
	private void addStatusMessages() {
		// TODO Auto-generated method stub
		
	}

	private void addQuitRestartButtons() {
		JPanel p = new JPanel();

		JButton quit = new JButton("Quit");
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { quit(); }
		});
		//Dejar todas las funciones tal como estan
				this.addWindowListener(new WindowListener() {
					public void windowClosing(WindowEvent e) { quit(); }

					@Override
					public void windowActivated(WindowEvent e) {
					}

					@Override
					public void windowClosed(WindowEvent e) {
					}

					@Override
					public void windowDeactivated(WindowEvent e) {
					}

					@Override
					public void windowDeiconified(WindowEvent e) {
					}

					@Override
					public void windowIconified(WindowEvent e) {
					}

					@Override
					public void windowOpened(WindowEvent e) {
					}
				
				});
		p.add(quit);
		JButton restart = new JButton("Restart");
		p.add(restart);
		crtlPanel.add(p);
	}

	final protected Piece getTurn() { return turn; }
	final protected Board getBoard() { return board; }
	final protected Board getPieces() { return (Board) pieces; }
	final protected Color getPieceColor(Piece p) { return pieceColors.get(p); }
	final protected Color setPieceColor(Piece p, Color c) { return pieceColors.put(p,c); }
	final protected void setBoardArea(JComponent c) { 
		mainPanel.add(c,BorderLayout.CENTER);
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
	//Sobra todo el codigo de aqui, son funciones pequeñas de hacer
	private void handleGameStart() {
		JPanel visualBoard = new JPanel();
		visualBoard.setPreferredSize(new Dimension(20,20));	
		visualBoard.setBackground(Color.GRAY);
		
		this.add(visualBoard, BorderLayout.CENTER);
		//Cant resize
		//this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		
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
		
		
		
		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		
		
		this.add(optionsPanel, BorderLayout.LINE_END);
		this.pack();
		this.setVisible(true);
		
	}
	
	//No usar JOptionPane.showMessageDialog, meter estos mensajes en el text area de addStatusMessages()
	public void onGameOver(final Board board, final State state, final Piece winner) {
		this.board = board;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() { 
				//Mostrar mensaje de victoria o empate
				JFrame frame = new JFrame();
				
				//CAMBIAR
				if (state == State.Draw){
					JOptionPane.showMessageDialog(frame,
							"You have draw, try it again!");
				}
				//If someone has won and the piece is part of the player, show him the victory message
				else if (state == State.Won && turn == winner){
					JOptionPane.showMessageDialog(frame,
							"You have won, congratulations!");
				}
				else {
					JOptionPane.showMessageDialog(frame,
							"You have lost, try it again!");
				}
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
