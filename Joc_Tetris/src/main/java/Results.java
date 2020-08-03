import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Results extends JPanel implements KeyListener, MouseListener, MouseMotionListener{
	
	
	private static final long serialVersionUID = 1L;
	private int mouseX, mouseY;
	private Rectangle bounds;
	private boolean leftClick = false;
	private BufferedImage play;
	private Window window;
	private BufferedImage[] playButton = new BufferedImage[2];
	private Timer timer;
	int score;
	String username;
	JTable table;
	JTextField field;
	public Results(Window window){
		try {
			play = ImageIO.read(Board.class.getResource("play.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		timer = new Timer(1000/60, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
			
		});
		timer.start();
		mouseX = 0;
		mouseY = 0;
		playButton[0] = play.getSubimage(0, 0, 100, 80);
		playButton[1] = play.getSubimage(100, 0, 100, 80);
		
		bounds = new Rectangle(Window.WIDTH/2 - 50, Window.HEIGHT - 120, 100, 80);
		this.window=window;
		field =new JTextField("Insert Username");
		field.setBounds(Window.WIDTH/2 - 50, 100, 200, 50);
		field.addKeyListener(this);
		this.add(field);
		
	}
    public void saveScore() {
        JSONArray scorelist = new JSONArray();
        username = field.getText();
        field.disable();
        try (FileReader reader = new FileReader("data.json")) {
            JSONParser pars = new JSONParser();
            Object obj = pars.parse(reader);
            scorelist = (JSONArray) obj;
            System.out.println(scorelist);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject j = new JSONObject();
        j.put("username", username);
        j.put("score", score);
        scorelist.add(j);
        scorelist.sort(new ScoreComparator());
        String[] columnNames = {"Username","Score"};

        
        DefaultTableModel dataModel = new DefaultTableModel(columnNames,0);
        table = new JTable(dataModel);
        for(int i=0; i<10; i++) {
        	if(i<scorelist.size()) {
        		 Object[] row = {((JSONObject) scorelist.get(i)).get("username"), ((JSONObject) scorelist.get(i)).get("score")};
        		dataModel.addRow(row);
        	}
        }
        table.setBounds(Window.WIDTH/2 - 80, 150, 150, 160);
        this.add(table);
        try (FileWriter file = new FileWriter("data.json")) {

			file.write(scorelist.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		if(leftClick && bounds.contains(mouseX, mouseY))
			window.startTetris();
			
		g.setColor(Color.lightGray);
		
		g.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);
		
		
		
		if(bounds.contains(mouseX, mouseY))
			g.drawImage(playButton[0], Window.WIDTH/2 - 50, Window.HEIGHT - 120, null);
		else
			g.drawImage(playButton[1], Window.WIDTH/2 - 50, Window.HEIGHT - 120, null);
		
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {	
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
			leftClick = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
			leftClick = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_ENTER){

            this.saveScore();
            
        }
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}	
}