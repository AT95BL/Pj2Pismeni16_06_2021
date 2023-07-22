import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Simulacija{
	
	public static int brojVrsta = 3;
	public static int brojKolona = 0;
	
	public static Object[][] map;
	/*
		map je cesto referenca bas na Object[][] iz razloga sto onda tako na istu mogu stavljati referencirane objekte bilo kojih klasa.. 
		Dakle, ako ces praviti map-u (za igricu recimo..) onda neka ta mapa bude instanca Object[][] klase.
	*/
	
	public static ArrayList<VojnoPlovilo> vojnaPlovila = new ArrayList<>();

	private void inicijalizacijaArrayListVojnihPlovila(){
		
		Random random = new Random();
		
		VojnoPlovilo razarac = new Razarac(/*random.nextInt(3) ,*/ true);
		VojnoPlovilo nosacAviona = new NosacAviona(/*random.nextInt(3),*/ true);
		VojnoPlovilo podmornica1 = new Podmornica(/*random.nextInt(3),*/ false);
		VojnoPlovilo podmornica2 = new Podmornica(/*random.nextInt(3),*/ false);
		
		vojnaPlovila.add(razarac);
		vojnaPlovila.add(nosacAviona);
		vojnaPlovila.add(podmornica1);
		vojnaPlovila.add(podmornica2);
		
		Simulacija.map[razarac.vrstaNaMapi = random.nextInt(Simulacija.brojVrsta)][razarac.pozicijaNaMapi = random.nextInt(Simulacija.brojKolona)] = razarac;
		Simulacija.map[nosacAviona.vrstaNaMapi = random.nextInt(Simulacija.brojVrsta)][nosacAviona.pozicijaNaMapi = random.nextInt(Simulacija.brojKolona)] = nosacAviona;
		Simulacija.map[podmornica1.vrstaNaMapi = random.nextInt(Simulacija.brojVrsta)][podmornica1.pozicijaNaMapi = random.nextInt(Simulacija.brojKolona)] = podmornica1;
		Simulacija.map[podmornica2.vrstaNaMapi = random.nextInt(Simulacija.brojVrsta)][podmornica2.pozicijaNaMapi = random.nextInt(Simulacija.brojKolona)] = podmornica2;	
	}
	
	public static boolean pauza=false;					//	boolean vrijednost kojom namjeravam programirati/kontrolisati pause/unpause simulacije
	public static Object pauzaLock = new Object();		//	jednostavno napravljen i referenciran objekat kako bi se preko istog pozivali 'wait, notfyAll..'
	
	/* Constructor */
	public Simulacija(int brojKolona){
		Simulacija.brojKolona = brojKolona;
		Simulacija.map = new Object[brojVrsta][brojKolona];
		inicijalizacijaArrayListVojnihPlovila();
	}
	
	public void startajSimulaciju(){
		for(var el : vojnaPlovila){
			el.start();
			/*
			 * plovila je referenca na niz objekata, a svaki od tih objekata jeste instanca child klase VojnoPlovilo, a ta klasa je
			 * child klasa klase Thread pa otuda joj i ova metoda start()	..
			 * Kako program petljom prolazi kroz kolekciju tako i metoda start() pali pozadinski(backgorund) process..
			 * Chat GPT 	pa		 'Java programming language. Can you explain me the start() method from Thread class ?'
			 * 
			 * Znaj isto tako da ce ova petlja bas brzo proletjeti kroz sve referencirane objekte!
			 * Kao rezultat, plovila ce se kretati duz mape ali  konkurentno
			 * (program -> proces -> nit) ..explanation..to be continued..
			 */
		}
	}
	
	public void pokreniPauziranjeSimulacije(){
		this.pauza = true;
	}
	
	public void ponistiPauziranjeSimulacije(){
		this.pauza = false;
		Simulacija.pauzaLock.notifyAll();
	}
	
	public static void main(String[] args){
		Simulacija simulacija = new Simulacija(20);			
		simulacija.startajSimulaciju();							//	Simulacija krece odavde..
		Scanner scanner = new Scanner(System.in);
		String line = scanner.nextLine();
		
		while(!"END".equals(line)){								// dalje, na meni je da isprogramiram scenario za unos WAIT, NOTIFY, INFO and TIME ..
			
			try{
				
				if("WAIT".equals(line)){
					simulacija.pokreniPauziranjeSimulacije();
				}
				
				else if("NOTIFY".equals(line)){
					simulacija.ponistiPauziranjeSimulacije();
				}
				
				else if(line.startsWith("INFO")){
					
					if(!Simulacija.pauza){
						throw new CommandNotValidException();
					}
					
					String[] tmp = line.split(" ");
					try{
						int id = Integer.parseInt(tmp[1]);
						for(var el : vojnaPlovila){
							if(el.ID == id){
								System.out.println(el);
								break;
							}
						}
					}
					catch(NumberFormatException | IndexOutOfBoundsException ex){
						System.out.println("ID nije validan!!!");
					}
				}
				
				else if(line.startsWith("TIME")){
					
					if(!Simulacija.pauza){
						throw new CommandNotValidException();
					}
					
					String[] tmp = line.split(" ");
					try{
						int id = Integer.parseInt(tmp[1]);
						for(var el : vojnaPlovila){
							if(el.ID == id){
								System.out.println(el + "Vrijeme kretanja: " + (el.endTime - el.startTime) + "[ms] \n");
								break;
							}
						}
					}
					catch(NumberFormatException | IndexOutOfBoundsException ex){
						System.out.println("ID nije validan!!!");
					}
				}
			}
			catch(CommandNotValidException ex){
				System.out.println(ex.getMessage());
			}
			
			line = scanner.nextLine();
		}
		
		scanner.close(); // Inace jako opasna djelatnost..zatvara ceo System.in ili ti vezu sa tastaturom pa ako u programu imamo vise od jedne instance klase Scanner moze doci do ozbiljne stete!
	}	
}