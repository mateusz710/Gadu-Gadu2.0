import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

import javax.swing.*;

public class Klient {
	
	JFrame ramkaG³ówna;
	JPanel panelG³ówny;
	JTextArea wiadomosci;
	JTextArea rozmowa;
	PrintWriter pisarz;
	BufferedReader buforWejœciowy;
	
public static void main(String[] args)
{
	Klient klient = new Klient();
	klient.Po³¹czZSerwerem();
	klient.wyœwietlGUI();
	
}
	
	public void Po³¹czZSerwerem()
	{
		
		try
		{
		Socket gniazdo = new Socket("192.168.0.11", 6951);
		InputStreamReader strumienWejœciowy = new InputStreamReader(gniazdo.getInputStream());
		buforWejœciowy = new BufferedReader(strumienWejœciowy);
		
		
		pisarz = new PrintWriter(gniazdo.getOutputStream());
		
		System.out.println("Po³¹czono z serwerem");
		
		Thread t = new Thread(new OdbiorcaKomunikatów());
		t.start();
		}
			
		catch(IOException exception)
		{
			System.out.println("B³¹d z po³¹czeniem");
			exception.printStackTrace();
		}
		}


	
	public void wyœwietlGUI()
	{
			ramkaG³ówna = new JFrame("Gadu-Gadu");
			panelG³ówny = new JPanel();
		
			JPanel panelGórny = new JPanel();
			JPanel panelDolny = new JPanel();
			JPanel panelBoczny = new JPanel();
			
			rozmowa = new JTextArea(15,50);
			rozmowa.setLineWrap(true);
			rozmowa.setWrapStyleWord(true);
			rozmowa.setEditable(false);
			
			JScrollPane przewijanie = new JScrollPane(rozmowa);
			przewijanie.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			przewijanie.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
			wiadomosci = new JTextArea(10,45);
			wiadomosci.setLineWrap(true);
			wiadomosci.setWrapStyleWord(true);
			wiadomosci.setEditable(true);
			
			JScrollPane przewijanie2 = new JScrollPane(wiadomosci);
			przewijanie2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			przewijanie2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
			
			
			JButton wyslij = new JButton ("Wyœlij");
			wyslij.addActionListener(new mojWyslijListener());
			
			panelG³ówny.add(rozmowa);
			panelG³ówny.add(wiadomosci);
			panelG³ówny.add(wyslij);
			
			ramkaG³ówna.getContentPane().add(BorderLayout.CENTER, panelG³ówny);
			
			ramkaG³ówna.setSize(700,500);
			ramkaG³ówna.setVisible(true);
			
	}
	
	
	class mojWyslijListener implements ActionListener
	{
		
	
		public void actionPerformed(ActionEvent event) {
			
			String tekst = wiadomosci.getText();
			wiadomosci.setText("");
			pisarz.println(tekst);
			pisarz.flush();
			
			
		}
		
		
	}
	
	class OdbiorcaKomunikatów implements Runnable
	{

	
		public void run() {
		
			String wiadomosc;
			
			try {
				while((wiadomosc=buforWejœciowy.readLine()) != null)
				{
					System.out.println("Otrzymalem wiadomosc zwrton¹: " + wiadomosc);
					rozmowa.append(wiadomosc + "\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		}
		
	}


