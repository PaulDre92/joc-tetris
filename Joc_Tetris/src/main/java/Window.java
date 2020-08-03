import javax.sound.sampled.Clip;
import javax.swing.JFrame;

public class Window {
	// 413
	public static final int WIDTH = 445, HEIGHT = 635;

	private Board board;
	private Title title;
	private Results results;
	private JFrame window;
    private Clip music;

	public Window() {

		window = new JFrame("Tetris");
		window.setSize(WIDTH, HEIGHT);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);
		window.setResizable(false);

		board = new Board(this);
		title = new Title(this);
		results = new Results(this);
		window.addMouseMotionListener(title);
		window.addMouseListener(title);

		window.add(title);

		window.setVisible(true);
		music = ImageLoader.LoadSound("music.wav");
	    music.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public void startTetris() {
		window.remove(title);
		window.removeMouseListener(title);
		window.removeMouseMotionListener(title);
		window.remove(results);
		window.removeMouseListener(results);
		window.removeMouseMotionListener(results);
		window.removeKeyListener(results);
		board = new Board(this);
		window.requestFocusInWindow();
		window.revalidate();
		window.repaint();
		window.addMouseMotionListener(board);
		window.addMouseListener(board);
		window.addKeyListener(board);
		window.add(board);
		board.startGame();
		window.revalidate();
	}

	public void showResults(int score) {
		window.remove(board);		
		window.removeKeyListener(board);
		window.removeMouseListener(board);
		window.removeMouseMotionListener(board);
		results = new Results(this);
		results.score=score;
		window.addMouseMotionListener(results);
		window.addKeyListener(results);
		window.addMouseListener(results);
		window.add(results);
		window.revalidate();
	}

	public static void main(String[] args) {
		new Window();
	}

}
