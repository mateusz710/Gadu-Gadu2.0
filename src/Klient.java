import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

import javax.swing.*;

public class Klient {
	
	JFrame ramkaRozmowy;
	JFrame ramkaNazwyU�ytkownika;
	JFrame ramkaWyboruRozm�wcy;
	JPanel panelRozmowy;
	JPanel panelNazwyU�ytkownika;
	JPanel panelWyboruRozm�wcy;
	JTextArea wiadomosci;
	JTextArea rozmowa;
	PrintWriter pisarz;
	BufferedReader buforWej�ciowy;
	String[] listaOpcji;
	
public static void main(String[] args)
{
	Klient klient = new Klient();
	//klient.Po��czZSerwerem();
	klient.Wy�wietlGUINazwyU�ytkownika();
	
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

	public void Wy�wietlGUINazwyU�ytkownika()
	{
		ramkaNazwyU�ytkownika = new JFrame("Gadu-Gadu");
		panelNazwyU�ytkownika = new JPanel();
		
		JLabel nazwa = new JLabel("Podaj nazwe u�ytkownika");
		JTextField poleNazwy = new JTextField(15);
		JButton rejestracja = new JButton("Rejestracja U�ytkownika");
		
		panelNazwyU�ytkownika.add(nazwa);
		panelNazwyU�ytkownika.add(poleNazwy);
		panelNazwyU�ytkownika.add(rejestracja);
		
		ramkaNazwyU�ytkownika.getContentPane().add(BorderLayout.CENTER, panelNazwyU�ytkownika);
		
		ramkaNazwyU�ytkownika.setSize(400,100);
		ramkaNazwyU�ytkownika.setVisible(true);
		
	}

	public void Wy�wietlGUIWyboruRozm�wcy()
	{
		listaOpcji[0] = "JAN";
		listaOpcji[1] = "KAZIMIERZ";
		
		JFrame ramkaWyboruRozm�wcy= new JFrame("Gadu-Gadu");
		JPanel panelRozmowy  = new JPanel();

		JLabel nazwa = new JLabel("Wybierz U�ytkownika do rozmowy");
		JList lista = new JList(listaOpcji);
		
		JScrollPane przewijanie = new JScrollPane(lista);
		przewijanie.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		przewijanie.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		lista.setVisibleRowCount(10);
		lista.setSelectionMode(1);
		
		
		JButton rozmawiaj = new JButton("Rozmawiaj");
		
		panelNazwyU�ytkownika.add(nazwa);
		panelNazwyU�ytkownika.add(poleNazwy);
		panelNazwyU�ytkownika.add(rejestracja);
		
		ramkaNazwyU�ytkownika.getContentPane().add(BorderLayout.CENTER, panelNazwyU�ytkownika);
		
		ramkaNazwyU�ytkownika.setSize(400,100);
		ramkaNazwyU�ytkownika.setVisible(true);
		
	}
	public void Wy�wietlGUIRozmowy()
	{
			ramkaRozmowy = new JFrame("Gadu-Gadu");
			panelRozmowy = new JPanel();
		
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
			
			panelRozmowy.add(rozmowa);
			panelRozmowy.add(wiadomosci);
			panelRozmowy.add(wyslij);
			
			ramkaRozmowy.getContentPane().add(BorderLayout.CENTER, panelRozmowy);
			
			ramkaRozmowy.setSize(700,500);
			ramkaRozmowy.setVisible(true);
			
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


