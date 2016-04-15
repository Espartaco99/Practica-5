package es.ucm.fdi.tp.practica5.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.HashMap;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import es.ucm.fdi.tp.basecode.bgame.Utils;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.extra.jcolor.ColorChooser;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
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
	private JPanel ctrlPanel;
	private JPanel mainPanel;
	private JComboBox<Piece> listPieces;
	private JTextArea storyArea;
	private MyTableModel MyTable;
	
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
		this.playerTypes = new HashMap<>();
		this.pieceColors = new HashMap<>();
		this.listPieces = new JComboBox<>();
		initGUI();
		g.addObserver(this);
	}
	
	class MyTableModel extends DefaultTableModel {

		/**
		* 
		*/
		private static final long serialVersionUID = 1L;

		private String[] colNames;
		private List<String> modes;
		private List<Integer> numPieces;

		public MyTableModel() {
			this.colNames = new String[] { "Player", "Mode", "#Pieces" };
			this.modes = new ArrayList<>();
			this.numPieces = new ArrayList<>();
			
		}

		@Override
		public String getColumnName(int col) {
			return colNames[col];
		}

		@Override
		public int getColumnCount() {
			return colNames.length;
		}

		@Override
		public int getRowCount() {
			return modes != null ? modes.size() : 0;
		}
		/*
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			if (columnIndex == 0) {
				return rowIndex;
			} else {
				return modes.get(rowIndex);
			}
		}
		 */
		
		public void addNameMode(String name) {
			modes.add(name);
			//refresh();
		}

		public void refresh() {
			fireTableDataChanged();
		}

		public void addNumPieces(int pieceCount) {
			numPieces.add(pieceCount);
			
		}

	};
	
	
	
	/**
	 * Construct the view
	 */
	private void initGUI(){ 
		this.setSize(400, 200);
		this.setVisible(true);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		//Set the panel to be the one of the swingView
		mainPanel = new JPanel( new BorderLayout(5,5) );
		this.setContentPane(mainPanel);
		
		ctrlPanel = new JPanel();
		//Alignment to ensure all the components are in the Y axis
		ctrlPanel.setLayout(new BoxLayout(ctrlPanel, BoxLayout.Y_AXIS));
		
		//Hacer funciones para añadir cada componente, completar el codigo de estas funciones
		addStatusMessages();
		addPlayerInformation();
		addPieceColors();
		addPlayerModes();
		addAutomaticMoves();
		addQuitRestartButtons();
		mainPanel.add(ctrlPanel, BorderLayout.LINE_END);
		initBoardGui();
		
		this.pack();
		this.setVisible(true);
		
	}
	
	/**
	 * Add the random and intelligent button into ctrlPanel
	 */
	private void addAutomaticMoves() {
		JPanel movesPanel = new JPanel();
		//Random Button
		JButton random = new JButton("Random");
		movesPanel.add(random);
		//Random Button
		JButton intelligent = new JButton("Intelligent");
		movesPanel.add(intelligent);
		//Border
		Border b = BorderFactory.createLineBorder(Color.black, 2);
		movesPanel.setBorder(BorderFactory.createTitledBorder(b, "Automatic Modes"));
		//movesPanel.getAlignmentY();
		ctrlPanel.add(movesPanel);
	}
	
	/**
	 * Add a JPanel which contains the players pieces, the player modes and a set button to change between modes into ctrlPanel
	 */
	private void addPlayerModes() {
	//Falla porque no tenemos la lista de piezas pasada por ningun lado
			//Players ComboBox
			JPanel optionsPanel = new JPanel();
			//listPieces = new JComboBox<Piece>();
			optionsPanel.add(listPieces);
			//Modes ComboBox
			String nameModes[] = {"Manual", "Intelligent", "Random"};
			JComboBox<String> listModes = new JComboBox<>(nameModes);
			optionsPanel.add(listModes);
			//Set Button
			JButton set = new JButton("Set");
			set.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//All the items in the listPieces are of the Piece type, so the casting wont fail
					Piece p = (Piece)listPieces.getSelectedItem();
					listModes.getSelectedItem();
				}
			});
			optionsPanel.add(set);
			//Border
			Border b = BorderFactory.createLineBorder(Color.black, 2);
			optionsPanel.setBorder(BorderFactory.createTitledBorder(b, "Player Modes"));
			ctrlPanel.add(optionsPanel);
	}
	
	/**
	 * Add a JPanel which contains the players pieces and the chooseColor button into ctrlPanel
	 */
	private void addPieceColors() {
		//Players ComboBox
		JPanel optionsPanel = new JPanel();
		//listPieces = new JComboBox<Piece>();
		optionsPanel.add(listPieces);
		
		JButton chooseColor = new JButton("Choose Color");
		chooseColor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//All the items in the listPieces are of the Piece type, so the casting wont fail
				Piece p = (Piece)listPieces.getSelectedItem();
				ColorChooser c = new ColorChooser(new JFrame(), "Choose Line Color", pieceColors.get(p));
				if (c.getColor() != null) {
					pieceColors.put(p, c.getColor());
					repaint();
				}
			}
		});
		optionsPanel.add(chooseColor);
		//Border
		Border b = BorderFactory.createLineBorder(Color.black, 2);
		optionsPanel.setBorder(BorderFactory.createTitledBorder(b, "Piece Colors"));
		ctrlPanel.add(optionsPanel);
	}

	/**
	 * Add a ScrollPane which contains a table with the players, mode and number of pieces they have into ctrlPanel
	 */
	private void addPlayerInformation() {
		
		MyTable = new MyTableModel();
		MyTable.getRowCount();
		JTable table = new JTable(MyTable) {
			private static final long serialVersionUID = 1L;

			// THIS IS HOW WE CHANGE THE COLOR OF EACH ROW
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
				//NO SE PORQUE NO FUNCIONA
				Component comp = super.prepareRenderer(renderer, row, col);
				// the color of row 'row' is taken from the colors table, if
				// 'null' setBackground will use the parent component color.
				comp.setBackground(pieceColors.get(row));
				return comp;
			}
		};
		
		//Falta cambiar el color a la tabla
		Border b = BorderFactory.createLineBorder(Color.black, 2);
		JScrollPane scrollPane = new JScrollPane(table);
		//Used to resize the scrollPane because its too big by default
		scrollPane.setPreferredSize(new Dimension(300, 80));
		
		scrollPane.setBorder(BorderFactory.createTitledBorder(b, "Player Information"));
		ctrlPanel.add(scrollPane);
		
	}
	
	/**
	 * Adds the TextArea (usar JTextArea) where the messages are gonna be displayed
	 */
	private void addStatusMessages() {
		Border b = BorderFactory.createLineBorder(Color.black, 2);
		//String story = "I sexually Identify as an Attack Helicopter. Ever since I was a boy I dreamed of soaring over the oilfields dropping hot sticky loads on disgusting foreigners. People say to me that a person being a helicopter is Impossible and I’m fucking retarded but I don’t care, I’m beautiful. I’m having a plastic surgeon install rotary blades, 30 mm cannons and AMG-114 Hellfire missiles on my body. From now on I want you guys to call me “Apache” and respect my right to kill from above and kill needlessly. If you can’t accept me you’re a heliphobe and need to check your vehicle privilege. Thank you for being so understanding.";
		storyArea = new JTextArea();
		storyArea.setEditable(false);
		storyArea.setLineWrap(true);
		storyArea.setWrapStyleWord(true);
		JScrollPane area = new JScrollPane(storyArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		area.setPreferredSize(new Dimension(300, 100));
		area.setMinimumSize(new Dimension(200, 100));
		area.setBorder(BorderFactory.createTitledBorder(b, "Status Messages"));
		ctrlPanel.add(area);
		
	}
	
	//La dejo por si tengo que usarla en otro lado (probablamente en RectBoard), aqui sobra 
	private GridBagConstraints constraintsY(int i) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridy = i;
        gbc.gridx = 0;
		return gbc;
	}

	/**
	 * Add a JPanel which contains the quit and restart button into ctrlPanel
	 */
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
		ctrlPanel.add(p);
	}

	final protected Piece getTurn() { return turn; }
	final protected Board getBoard() { return board; }
	
	
	
	//Preguntar por que era board el tipo en vez de List<Piece>
	final protected List<Piece> getPieces() { return pieces; }
	
	
	
	
	final protected Color getPieceColor(Piece p) { return pieceColors.get(p); }
	final protected Color setPieceColor(Piece p, Color c) { return pieceColors.put(p,c); }
	final protected void setBoardArea(JComponent c) { 
		mainPanel.add(c,BorderLayout.CENTER);
		this.pack();
	}
	final protected void addMsg(String msg){ 
		storyArea.append(msg);
	}
	//Lo recibes de ConnectNSqingView
	final protected void decideMakeManualMove(Player manualPlayer) { 
		//De donde saco las GameRules
		GameRules rules;
		manualPlayer.requestMove(turn, board, pieces, rules.);
		
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
			//Se llama solo por el juego
			public void run() {
				handleGameStart(); 
			}
		});
	}
	
	
	/**
	 * Adds the main boxes and elements in the panel previously made by initGUI
	 */
	//NO SE PORQUE SE LLAMA DOS VECES A LA FUNCION
	private void handleGameStart() {
		//HashMap used for playerTypes		
		for( Piece p : pieces ) {
			listPieces.addItem(p);
			this.playerTypes.put(p, PlayerMode.MANUAL);
			//Put random colors at the start
			this.pieceColors.put(p, Utils.randomColor());
			
			MyTable.addNameMode(playerTypes.get(p).name());
			//NO FUNCIONA COMO DEBERIA, imprime lo mismo que 
			if (board.getPieceCount(p) != null){
				MyTable.addNumPieces(board.getPieceCount(p));
			}
		}
		MyTable.refresh();
		listPieces.setSelectedIndex(0);
		String story = "Turn for " + pieces.get(0).toString() + "\n";
		storyArea.append(story);
		super.setTitle("Board Games: " + gameDesc);
		//rellenar todo lo que se ha inicializado
		redrawBoard();
	}
	
	//No usar JOptionPane.showMessageDialog, meter estos mensajes en el text area de addStatusMessages()
	public void onGameOver(final Board board, final State state, final Piece winner) {
		this.board = board;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() { 
				//Mostrar mensaje de victoria o empate
				if (state == State.Draw){
					storyArea.append("You have draw, try it again!" + "\n");
				}
				//If someone has won and the piece is part of the player, show him the victory message
				else if (state == State.Won && turn == winner){
					storyArea.append("You have won, congratulations!" + "\n");
				}
				else {
					storyArea.append("You have lost, try it again!" + "\n");
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
				handleOnMoveStart(); 
			}

			private void handleOnMoveStart() {
				// TODO Auto-generated method stub
				
			}
		});
	}
	public void onMoveEnd(Board board, Piece turn, boolean success) {
		this.board = board;
		this.turn = turn;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() { 
				handleOnMoveEnd(); 
			}

			private void handleOnMoveEnd() {
				// TODO Auto-generated method stub
				
			}
		});
	}
	public void onChangeTurn(Board board, final Piece turn) {
		this.board = board;
		this.turn = turn;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() { 
				handleOnChangeTurn(); 
			}

			private void handleOnChangeTurn() {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void onError(String msg) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() { 
				handleOnError(); 
			}

			private void handleOnError() {
				JFrame frame = new JFrame();
				JOptionPane.showMessageDialog(frame, msg, "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		});
	}
	
}
