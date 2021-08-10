package main;

import java.awt.AWTException;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

public class Main{

	public static String lasttext = "";
	public static String lasttext2 = "";
	static JTextField stid;
	static JTextField stid2;
	private static String steam_key = "";
	static String configfile = "";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main main = new Main();
		
		
		//Get config values
		configfile = System.getProperty("user.dir") + "\\config.txt";
		System.out.println(configfile);
		
		String[] config = null;
		try {
			FileInputStream in = new FileInputStream(configfile);
			int content;
			String s = "";
			while((content = in.read()) != -1) {
				s += ((char) content);
			}
			in.close();
			
			config = s.split("\r");
			
			//Parse all config objects
			for (String str : config) {
				//edit \n and \r away because it does not work elsewise. 
				str = str.replace("\n", "").replace("\r", "");
				//comment out with ##
				if(!str.startsWith("##")) {
					System.out.println(str);
					//Split into key and value
					String[] str2 = str.split(" : ");
					//Test if it is empty or default
					if(str2[0].equals("steamkey") && str2.length == 2 && !str2[1].equals("") && !str2[1].equals("ABCDEFGH")) {
						
						steam_key = str2[1];
						System.out.println(steam_key);
					}
				}
			}
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			main.createConfig();
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(config == null) {
			main.createConfig();
		}
		
		//Create the GUIne
		main.GUI();
		
		//Anti Textedit Thread! > Prevents users from editing the Textfield.
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					if(!stid.getText().equals(lasttext) || !stid2.getText().equals(lasttext2)) {
						stid.setText(lasttext);
						stid2.setText(lasttext2);
					}
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		});
		
		t.start();
		
		
		//Main Scanning Thread
		Thread t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				while(true) {
					try {
						Thread.sleep(250);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					try {
						//Take a Screenshot
						BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
						LuminanceSource source = new BufferedImageLuminanceSource(image);
						BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
						
						String text;
						try {
							//Scan for the QR code.
							Result result = new MultiFormatReader().decode(bitmap);
							text = result.getText().toString();
							//System.out.println(result.getText());
						} catch(NotFoundException e) {
							text = "";
						}

						
						//Test if the lasttext equals the new Text to prevent the programm from Looping.
						if(!text.equals(lasttext) && text != "") {
							//Test if the string from the QR code matches one of the formats
							//and sets them to the output field, copies to clipboard, plays a sound.
							
							//@steam
							if(text.contains("@steam")) {
								long l = Long.parseLong(text.split("@steam")[0]);
								stid.setText(text);
								lasttext = text;
								System.out.println("QR: " + text);
								main.Playsound();
								StringSelection selection = new StringSelection(text);
								Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
								clipboard.setContents(selection, selection);
								String name = main.getSteamName(l);
								lasttext2 = name;
								stid2.setText(name);
							//@discord
							} else if(text.contains("@discord")) {
								long l = Long.parseLong(text.split("@discord")[0]);
								stid.setText(text);
								lasttext = text;
								System.out.println("QR: " + text);
								main.Playsound();
								StringSelection selection = new StringSelection(text);
								Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
								clipboard.setContents(selection, selection);
								lasttext2 = "";
								stid2.setText("");
							//@northwood
							} else if(text.contains("@northwood")) {
								stid.setText(text);
								lasttext = text;
								System.out.println("QR: " + text);
								main.Playsound();
								StringSelection selection = new StringSelection(text);
								Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
								clipboard.setContents(selection, selection);
								lasttext2 = text.split("@")[0];
								stid2.setText(text.split("@")[0]);
							}
						}
						
					} catch (HeadlessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (AWTException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NumberFormatException e) {
						
					}
					
					
					
				}
				
			}
		});
		
		t2.start();
		
		
		
		
	}


	public void GUI() {
		
		//Create the GUI
		
		JFrame frame = new JFrame();
		
		JLabel label = new JLabel("Userid:");
		
		stid = new JTextField("");
		
		JLabel label2 = new JLabel("Name:");
		
		stid2 = new JTextField("");
		
		//I'd be mad if you'd remove that :(
		//The spacebars at the end are for formating reasons.
		JLabel author = new JLabel("made by Mortiz#5829                        ");
		
		
		
		
		
		
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
		panel.setLayout(new GridLayout(0, 1));
		
		//add all the stuff to the Panel
		panel.add(label);
		panel.add(stid);
		if(steam_key != "") {
			panel.add(label2);
			panel.add(stid2);
		}
		panel.add(author);
		
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("SCP reader beta");
		
		//I didn't wanted to ship the software with an extra picture file so i create the icon with code
		// **Image stuff**
		BufferedImage bufferedImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
		Graphics2D w = (Graphics2D) bufferedImage.getGraphics();
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		w.setRenderingHints(rh);
		AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f);
		w.setComposite(alpha);
		//Main Background
		Color c = new Color(0, 0, 0);
		w.setColor(c);
		w.fillRoundRect(0, 0, 32, 32, 32, 32);
		//Circle
		c = new Color(255, 255, 255);
		w.setColor(c);
		w.fillRoundRect(5, 5, 22, 22, 22, 22);
		Image img = bufferedImage.getScaledInstance(32, 32, 0);
		w.dispose();
		frame.setIconImage(img);
		
		//Disable resizeablity 
		frame.setResizable(false);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	public void Playsound() {
		
        try {
        	Clip clip = AudioSystem.getClip();
        	
        	//Load the "Speech On.wav" from windows to play it.
        	File file = new File("C:\\Windows\\Media\\Speech On.wav");
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);
            clip.open(inputStream);
            
			clip.start(); 
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	
	public String getSteamName(long id) {
		
		try {
			URL url = new URL("http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key=" + steam_key + "&steamids=" + id);
	        URLConnection urlc = url.openConnection();
	        BufferedReader in = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
	        
	        String inputLine;
	        String text = "";
	        
	        while ((inputLine = in.readLine()) != null) {
	        	//System.out.println(inputLine);
	        	text = inputLine;
	        	
	        }
	            
	        in.close();
	        
	        //System.out.println(text);
	        JSONObject jsonObject1 = new JSONObject(text);
	        JSONObject jsonObject2 = jsonObject1.getJSONObject("response");
	        JSONArray jsonArray1 = jsonObject2.getJSONArray("players");
	        if(jsonArray1.length() == 0) {
	        	return "player not found";
	        }
	        JSONObject jsonObject3 = jsonArray1.getJSONObject(0);
	        //System.out.println(jsonObject3.get("personaname"));
	        return jsonObject3.get("personaname").toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return "invalid token";
		}
	}
	
		
		
	public void createConfig() {
		
		try {
			PrintWriter writer = new PrintWriter(configfile, "UTF-8");
			writer.println("##steamkey can be found on https://steamcommunity.com/dev/apikey leave default or Empty to disable the name field.");
			writer.println("steamkey : ABCDEFGH");
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
