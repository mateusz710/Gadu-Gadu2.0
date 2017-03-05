import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

import javax.swing.*;

public class Klient {
	
	JFrame ramkaG��wna;
	JPanel panelG��wny;
	JTextArea wiadomosci;
	JTextArea rozmowa;
	PrintWriter pisarz;
	BufferedReader buforWej�ciowy;
	
public static void main(String[] args)
{
	Klient klient = new Klient();
	klient.Po��czZSerwerem();
	klient.wy�wietlGUI();
	
}
	
	public void Po��czZSerwerem()
	{
		
		try
		{
		Socket gniazdo = new Socket("192.168.0.11", 6951);
		InputStreamReader strumienWej�ciowy = new InputStreamReader(gniazdo.getInputStream());
		buforWej�ciowy = new BufferedReader(strumienWej�ciowy);
		
		
		pisarz = new PrintWriter(gniazdo.getOutputStream());
		
		System.out.println("Po��czono z serwerem");
		
		Thread t = new Thread(new OdbiorcaKomunikat�w());
		t.start();
		}
			
		catch(IOException exception)
		{
			System.out.println("B��d z po��czeniem");
			exception.printStackTrace();
		}
		}


	
	public void wy�wietlGUI()
	{
			ramkaG��wna = new JFrame("Gadu-Gadu");
			panelG��wny = new JPanel();
		
			JPanel panelG�rny = new JPanel();
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
		
			
			
			JButton wyslij = new JButton ("Wy�lij");
			wyslij.addActionListener(new mojWyslijListener());
			
			panelG��wny.add(rozmowa);
			panelG��wny.add(wiadomosci);
			panelG��wny.add(wyslij);
			
			ramkaG��wna.getContentPane().add(BorderLayout.CENTER, panelG��wny);
			
			ramkaG��wna.setSize(700,500);
			ramkaG��wna.setVisible(true);
			
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
	
	class OdbiorcaKomunikat�w implements Runnable
	{

	
		public void run() {
		
			String wiadomosc;
			
			try {
				while((wiadomosc=buforWej�ciowy.readLine()) != null)
				{
					System.out.println("Otrzymalem wiadomosc zwrton�: " + wiadomosc);
					rozmowa.append(wiadomosc + "\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		}
		
	}


