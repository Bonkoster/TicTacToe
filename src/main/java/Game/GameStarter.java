package Game;

import javax.sound.midi.MidiUnavailableException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GameStarter {
	
	public static void main(String[] args) throws MidiUnavailableException{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("ApplicationContext.xml");
		GameField fir = (GameField) ctx.getBean("GameField");
		fir.showGUI();
	}
}
