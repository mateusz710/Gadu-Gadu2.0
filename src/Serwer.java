
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class Serwer {
	
	BufferedReader buforWejsciowy;
	ArrayList<PrintWriter> strumieniewyjsciowe;
	
	
	public static void main(String[] args)
	{
		Serwer serwer = new Serwer();
		serwer.PostawSerwer();
		System.out.println("cos");
	}


	class zadanieDoWykonania implements Runnable{

	
		public zadanieDoWykonania(Socket gniazdo)
		{
			
			InputStreamReader czytacz;
			try {
				czytacz = new InputStreamReader(gniazdo.getInputStream());
				buforWejsciowy = new BufferedReader(czytacz);
				
				PrintWriter pisarz = new PrintWriter(gniazdo.getOutputStream());
				strumieniewyjsciowe.add(pisarz);
				
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}

		
	
	public void run() {
		String wiadomosc;
		
		try {
			while((wiadomosc=buforWejsciowy.readLine()) != null)
			{
				System.out.println("Dosta³em widomoœæ");
				rozeslijDoWSzystkich(wiadomosc);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	}
	
	public void rozeslijDoWSzystkich(String s)
	{
		
		Iterator<PrintWriter> i = strumieniewyjsciowe.iterator();
		
		while(i.hasNext())
		{
			try{
			PrintWriter pisarz = (PrintWriter) i.next();
			pisarz.println(s);
			pisarz.flush();
			
			
			}
			catch (NoSuchElementException exc)
			{
				exc.getStackTrace();
			}
		}
		
	}
	
	public void PostawSerwer()
	{
		
		
		try{
		ServerSocket socket = new ServerSocket(6951);
		System.out.println("Serwer Stoi");
		strumieniewyjsciowe = new ArrayList();
			while(true)
				{
					System.out.println("Wchodze i oczekuje");
					Socket gniazdo = socket.accept();
					System.out.println("Otrzyma³em zg³oszenie");
					
				
					Thread t = new Thread(new zadanieDoWykonania(gniazdo));
					t.start();
					
					
			
			
				}
		}
		catch(IOException exception)
		{
			exception.printStackTrace();
		}
		
	}

}

