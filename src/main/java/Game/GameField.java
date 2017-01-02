package Game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameField {
	
	Color col1 = new Color(100,0,0);
	Color col2 = new Color(0, 0, 100);
	
	Synthesizer synth;
	MidiChannel[] channels;
	
	private JFrame mainFrame;
	private JPanel fieldForGame;
	private JPanel bottom;
	
	private boolean tu = true;
			
	private BorderLayout Borlay;
	private GridLayout GriLay;
	private FlowLayout FloLay;
	
	private MyJButton but1;
	private MyJButton but2;
	private MyJButton but3;
	private MyJButton but4;
	private MyJButton but5;
	private MyJButton but6;
	private MyJButton but7;
	private MyJButton but8;
	private MyJButton but9;
	
	private ArrayList<MyJButton> buttons;
	
	private JButton restart;
	
	private JLabel turn;
	
	public GameField(){
		
	}
	
	public void showGUI() throws MidiUnavailableException{
		
		synth = MidiSystem.getSynthesizer();
		synth.open();
		channels = synth.getChannels();
		
		mainFrame = new JFrame("TicTacToe");
		fieldForGame = new JPanel();
		bottom = new JPanel();
		buttons = new ArrayList<>();
		turn = new JLabel("Turn: Player 1");
		restart = new JButton("Restart");
		
		Borlay = new BorderLayout();
		GriLay = new GridLayout(3, 3);
		FloLay = new FlowLayout();
		
		mainFrame.setSize(300, 300);
		mainFrame.setLayout(Borlay);
		mainFrame.setResizable(false);
		fieldForGame.setLayout(GriLay);
		bottom.setLayout(FloLay);
		bottom.add(turn);
		bottom.add(restart);
		
		mainFrame.add(fieldForGame,BorderLayout.CENTER);
		mainFrame.add(bottom, BorderLayout.SOUTH);
		addToCollection();
		addButtons();
		mainFrame.setLocationRelativeTo(null);
		restart.setEnabled(false);
		restart.addActionListener(new RestartListener());
		mainFrame.setVisible(true);
		
	}
	
	public void playSound() throws InterruptedException{
		int instrument = 5 + (int) (Math.random() * 50);
		int note = 5 + (int) (Math.random() * 50);
		channels[0].programChange(instrument);
		channels[0].noteOn(note, 50);
		Thread.sleep(200);
		channels[0].noteOff(note);
	}
	
	public void addToCollection(){
		
		but1 = new MyJButton();
		but2 = new MyJButton();
		but3 = new MyJButton();
		but4 = new MyJButton();
		but5 = new MyJButton();
		but6 = new MyJButton();
		but7 = new MyJButton();
		but8 = new MyJButton();
		but9 = new MyJButton();
		
		buttons.add(but1);
		buttons.add(but2);
		buttons.add(but3);
		buttons.add(but4);
		buttons.add(but5);
		buttons.add(but6);
		buttons.add(but7);
		buttons.add(but8);
		buttons.add(but9);
	}
	
	public void addButtons(){
		
		String[] comm = {"but1","but2","but3","but4","but5","but6","but7","but8","but9"};
		int i = 0;
		for(MyJButton e : buttons){
			e.addActionListener(new MyJButtonListener());
			e.setActionCommand(comm[i]);
			i++;
			fieldForGame.add(e);
		}
		
	}

	public void winOfPlayer(){
		String pl1 = "X";
		String pl2 = "O";
		
		setColorPlayer1(but1, but2, but3, pl1);
		setColorPlayer1(but1, but5, but9, pl1);
		setColorPlayer1(but4, but5, but6, pl1);
		setColorPlayer1(but7, but8, but9, pl1);
		setColorPlayer1(but3, but5, but7, pl1);
		setColorPlayer1(but1, but4, but7, pl1);
		setColorPlayer1(but2, but5, but8, pl1);
		setColorPlayer1(but3, but6, but9, pl1);
		
		setColorPlayer2(but1, but2, but3, pl2);
		setColorPlayer2(but1, but5, but9, pl2);
		setColorPlayer2(but4, but5, but6, pl2);
		setColorPlayer2(but7, but8, but9, pl2);
		setColorPlayer2(but3, but5, but7, pl2);
		setColorPlayer2(but1, but4, but7, pl2);
		setColorPlayer2(but2, but5, but8, pl2);
		setColorPlayer2(but3, but6, but9, pl2);
		
		draw();
	}
	
	public void disableAllButtons(){
		for(MyJButton e : buttons){
			e.setEnabled(false);
		}
	}
	
	public void draw(){
		if(!but1.isEnabled()&&!but2.isEnabled()&&!but3.isEnabled()&&!but4.isEnabled()&&!but5.isEnabled()
				&&!but6.isEnabled()&&!but7.isEnabled()&&!but8.isEnabled()&&!but9.isEnabled()){
			restart.setEnabled(true);
		}
	}
	
	public void setColorPlayer1(MyJButton but1,MyJButton but2,MyJButton but3,String pl){
		
		String sc = pl;
		if((but1.getText().equals(sc)&&but2.getText().equals(sc)&&but3.getText().equals(sc))){
		but1.setBackground(col1);
		but2.setBackground(col1);
		but3.setBackground(col1);
		disableAllButtons();
		turn.setText("Player 1 is Winner!");
		restart.setEnabled(true);
		}
	}
	
	public void setColorPlayer2(MyJButton but1,MyJButton but2,MyJButton but3,String pl){
		
		String sc = pl;
		
		if(but1.getText().equals(sc)&&but2.getText().equals(sc)&&but3.getText().equals(sc)){
			but1.setBackground(col2);
			but2.setBackground(col2);
			but3.setBackground(col2);
			disableAllButtons();
			turn.setText("Player 2 is Winner!");
			restart.setEnabled(true);
			}	
	}
	
	public class RestartListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			for(MyJButton ea : buttons){
				ea.setEnabled(true);
				ea.setText("");
				ea.setBackground(null);
			}
			restart.setEnabled(false);
			
		}
		
	}
	
	public class MyJButton extends JButton {

		/**
		 * 
		 */
		private static final long serialVersionUID = -3292370560228698060L;
		Font font = new Font("Arial", Font.PLAIN, 40 );
		
		
		
		public MyJButton() {
			super.setSize(30, 30);
			super.setFont(font);
		}
		
		public void setMark() throws InterruptedException{
			if(tu == true){
				super.setText("X");
				super.setEnabled(false);
				tu = false;
				turn.setText("Turn: Player 2");
				playSound();
				winOfPlayer();
			} else {
				super.setText("O");
				super.setEnabled(false);
				tu = true;
				turn.setText("Turn: Player 1");
				playSound();
				winOfPlayer();
			}
		}
	}
		
	public class MyJButtonListener implements ActionListener {

		
		
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			try {	
			switch (command) {
			case "but1": buttons.get(0).setMark();
			break;
				
			case "but2": buttons.get(1).setMark();				
			break;
			
			case "but3": buttons.get(2).setMark();				
			break;
			
			case "but4": buttons.get(3).setMark();				
			break;
			
			case "but5": buttons.get(4).setMark();				
			break;
			
			case "but6": buttons.get(5).setMark();				
			break;
			
			case "but7": buttons.get(6).setMark();				
			break;
			
			case "but8": buttons.get(7).setMark();				
			break;
			
			case "but9": buttons.get(8).setMark();				
			break;

			default: turn.setText("Something Wrong!!!");
				break;
			}} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}
}



