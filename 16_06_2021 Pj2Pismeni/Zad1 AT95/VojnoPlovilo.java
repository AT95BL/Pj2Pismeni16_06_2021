import java.util.Date;

public class VojnoPlovilo extends Thread{		// Citajuci tekst zadatka shvatio sam da instance klasa nasljednica ove klase tj. vojna plovila ce se kretati duz mape. Krecu se konkurentno a ne jedan zavrsi pa drugi krece..) i zato je ova klasa VojnoPlovilo ekstenzija klase Thread.
	
	public static int glodbalID = 0;			//	Citajuci tekst zadatka, naisao sam na "Sva plovila imaju identifikator.."
	public int ID;								//  tako da code[5,6] sluzi upravo u tu svrhu..
	
	Naoruzanje[] naoruzanje;					//	Citajuci tekst zadatka "Razaraci i nosaci aviona od naoruzanja posjeduju torpeda i rakete, a podmornice posjeduju samo torpeda.." shvatio sam da neka vojna plovila imaju vise zaliha naoruzanja. Tako npr. razaraci imaju po nekoliko raketa e u tu svrhu Naoruzanje[] kao atribut..
	
	boolean smjerKretanja; 						// true->, false<-	i time cu programirati "Razaraci i nosaci aviona se krecu sa prve pozicije na mapi prema kraju, pravolinijski. Podmornice se krecu od posljednje pozicije na mapi prema pocetku, pravolinijski." kao sto je to sve slikovito objasnjeno sa strelicama..
	
	public int vrstaNaMapi;
	public int pozicijaNaMapi;
	
	public long startTime = 0;
	public long endTime = -1;
	
	public boolean napadnut = false;
	public boolean unisten = false;
	
	public VojnoPlovilo(){
		
	}
	
	/*Constructor*/
	public VojnoPlovilo(/*int vrstaNaMapi,*/ boolean smjerKretanja){
		this.ID = ++glodbalID;											//	Panaosob objektu dodjela ID-a,
		//this.vrstaNaMapi = vrstaNaMapi;
		
		this.smjerKretanja = smjerKretanja;								//	U zavisnosti da li se duz mape krece brod(Razarac ili NosacAviona) ili Podmornica kao argument Constructor-a salje se true/false vrijednost.
		
		if(smjerKretanja){					//	Ako se vojno plovilo krece s'lijeva u desno tj. ako je plovilo-brod onda
			this.pozicijaNaMapi = 0;		//	neka se ono krece od samog pocetka mape i neka konvergira prema kraju mape.
		}
		else{
			this.pozicijaNaMapi = Simulacija.brojKolona-1;	//	U suprotnom tj. ako se krece s'desna u lijevo tj. ako je u pitanju podmornica e onda neka se krece s'desna u lijevo. Znaci sve kao sto je eksplicitno naglaseno u tekstu zadatka "Podmornice se krecu od posljednje pozicije na mapi prema pocetku, pravolinijski." ..za brodove imamo suprotan smjer kretanja.
		}	
	}
	/*
		Rezime:
		-Vidim objekat koji je neka instance neke od klasa nasljednica klase VojnoPlovilo..vidim ga sa svim njegovim prethodno inicijalizovanim atributima i shvatam kako isti treba da se krece duz mape.
		-Atribute inicijalizujem na standardan/uobicajen nacin ali ono sto je ovde od znacaja jeste da se uoci to kako vidim kako ce se sa true/false programirati kretanje objekta duz mape onda kada se i simulacija pokrene!
		-NA PRETHODNU RECENICU DOBRO OBRATITI PAZNJU PA AKO TREBA TOKOM PROGRAMIRANJA VRATITI SE NA ISTU!
	*/
	
	@Override
	public String toString(){
		return "Vojno Plovilo: " + this.ID + " se nalazi na " + this.pozicijaNaMapi + " poziciji ";
	}
	
	/*
		Na thread-ove gledaj kao na PODprograme glavnog main() programa(thread-a)
		koji se istovremeno(ali konkurentno) izvrsavaju sa tim glavnim programom(thread-om)!
		(Ponasanje tog podprograma definisem Override-om run() metode klase Thread, a to je metoda koja biva pozvana od strane start() metode.)

		Simulacija ce simulirati kretanje objekata(instanci klasa nasljednica klase VojnoPlovilo) tako da izvrsavanjem start() metode
		za svaki od tih objekata treba da je definisano ponasanje thread-a(podprograma) tako da u ovoj klasi VojnoPlovilo
		Override-ovanjem idem da definisem to ponasanje.

		Znaci u VojnoPlovilo definisem ponasanje objekata tokom simulacije, a u Simulator cu onda definisati samu simulaciju!
		-NA PRETHODNU RECENICU DOBRO OBRATITI PAZNJU I ISTO TAKO SHVATITI/RAZUMJETI ISTU!!!
	*/ 
	@Override
	public void run(){											// Znaci sada pa sve do kraja metode definisemo/opisujemo sta se desava dok prolazimo kroz potProgram!
		
		int pozicija;
		this.startTime = new Date().getTime();					// U samoj simulaciji za thread je pokrenuta metoda .start() tako da vrijeme nakon kojeg objekat pocinje da se krece duz mape, e to vrijeme je dokumentovano bas na ovoj liniji,
		
		while(true){											// Jako bitno, procitacu to ovako "Ja sam objekat(VojnoPlovilo) taj i taj i sve dok simulacija traje.." ..
																// sve dok simulacija traje ono sto se sa mnom desava ili ti se moze desiti opisano je code snippet-om unutar ove while petlje.		JAKO VAZNO ZA RAZUMIJEVANJE!!!
			if(Simulacija.pauza){								//	Ako je ovaj uslov zadovoljen, onda je simulacija trenutno pauzirana. DAKLE OVIM CODE SNIPPET-OM DEFINISEM STA SE DESAVA TOKOM SIMULACIJE KADA JE SELEKTOVANA OPCIJA PAUZIRANJA SAME SIMULACIJE. Ta opcija "WAIT -zaustavlja kretanje svih vozila na trenutnoj lokaciji.." sto se moze procitati iz samog teksta zadatka!
				synchronized(Simulacija.pauzaLock){				// method is declared as synchronized, which means that only one thread can execute it at a time
					this.endTime = new Date().getTime();		// Simulacija je trenutno prekinuta, da dokumentujemo vrijeme pocetka pauze..
					try{		
						Simulacija.pauzaLock.wait();
						/* 	
							In Java, wait is a keyword that is used to temporarily suspend the execution of a thread.
							The wait() method can be called on any object in Java, as long as it is called from within a synchronized block or method on the same object.
							This is because the wait() method releases the lock on the object it is called on, and waits for another thread to notify it by calling notify() or notifyAll() on the same object.
						*/
					}
					catch(InterruptedException ex){
						System.out.println("PREKID!");
					}
				}
			}
			/*
				Dakle tokom trajanja simulacije, korisnik programa se opredjelio za WAIT opciju  prethodni if definise ponasanje
				thread u takvoj situaciji. Atribut pauzaLock klase simulacija ce pokrenuti .wait() metodu i therad(brod/podmornica) ce biti pod uticajem istog!
			*/
			
			if(this.unisten){
				System.out.println(this + " je unisten!!! \n");
				break;
			}
			/*
			 * Prethodni if() block pokriva sljedeci scneario koji se moze desiti tokom izvodjenja simulacije:
			 * objekat sa kojim radi simulacija je prethodno unisten, recimo pogodio ga je neki torpedo,
			 * posto nema smisla ista raditi sa unistenim objektom, jednostavno samo ispisi informaciju o tome da je ovaj objekat unisten
			 * te preko 'break' prekini gornju while petlju!
			 * Dakle, ovim if block-om mi smo definisali slucaj da je objekat prethodno pogodjen i unisten. 
			   Dakle, tada se za taj objekat narusava ova while() petlja sto znaci da za taj objekat vise nema simulacije..
			 */
			
			try{
				sleep(1000);
			}catch(InterruptedException ex){
				System.out.println("PREKID!!! \n");
			}
			/*
				Elem, thread-ovi lete jako brzo pa bez da svaki od istih zaustavis bar na 1[s]
				to ti bez prethodnog try{} bloka nece poci za rukom.
				Narocito kod racunara sa procesorom visokih performansi.
			*/
			
			System.out.println(this);	//	Tokom simulacije, stanje, polozaj objekta ispisuje bas ova linija.
			
			/*
				Tokom trajanja simulacije moze se desiti scenario gdje podmornica napada brod(razarac ili nosac aviona).
				if(){} koji slijedi definise/opisuje trajanje i ishod takvog napada.
			*/
			if(this.napadnut){
				if(this instanceof TorpedoStit){								// brod se u tom slucaju moze odbraniti od napada..zatim on uzvraca kontranapadom..
					this.napadnut = false;
					System.out.println("Napad na " + this + " je odbijen.");
					for(var el : Simulacija.vojnaPlovila){									//	Petljom prolazimo kroz plovila, sve u cilju da nadjemo tu podmornicu koja je napala taj broj.
						if(el.vrstaNaMapi == this.vrstaNaMapi && (el.ID != this.ID)){		//	istu detektujemo tako sto se ona i napadnuti brod nalaze u istoj vrsti na mapi ali imaju razlicit ID!!!
							el.unisten = true;												//	Pronadjena je i brod je unistava
							break;															//	a svako unistenje treba evidentirati sto vrsimo azuriranjem..
						}
					}
				}
				else{
					System.out.println(this + " je unisten!!! \n");				
					break;
				}
			}
			/*	Stari komentar ali neka i njega..
				Prethodni if() block pokriva sljedeci scneario koji se moze desiti tokom izvodjenja simulacije:
				Tokom izvodjenja simulacije, moze se desiti to da tekuci objekat(brod u ovom slucaju) bude napadnut od strane nekog drugog objekta(podmornice).
				Ovim code-snippet-om definisemo(opisujemo) takav scenario.
				Dakle, tacno je da je objekat(brod) napadnut od strane nekog drugog objekta(podmornice) jer bez toga ne bi ni ulazili u ovaj if(){},
				unutrasnjim if()-om objekat ima torpedo-stit te uspjesno prezivljava torpedo-napad. A ako nema onda zna se, program skace na donji else{}.

				Medjutim, ako se brod uspjesno odbranio od napada e onda isti uzvraca kontranapadom!
				Iteratorska for petlja prolazi kroz sve objekte na mapi, prolazi kroz sve objekte nad kojima se vrsi simulacija.
				Ako naidje na objekat koji se nalazi u istoj vrsti kao i ovaj sto je napadnut && taj objekat ima id razlicit od tekuceg objekta
				to se tumaci tako sto je podmornica ispalila torpedo na brod koji ima torpedo-stit, isti ide u kontranapad te unistava tu podmornicu.

				Koliko je pametno raditi posao preko apstraktne klase pa po potrebi prepoznavati o kojoj vrsti instance njene klase nasljednice se radi!
			*/	
			
			if(this instanceof Sonar){
				synchronized(Simulacija.map){
					if(this.pozicijaNaMapi-3 > 0 && Simulacija.map[this.vrstaNaMapi][this.pozicijaNaMapi-3]!=null)
					{
						VojnoPlovilo plovilo = (VojnoPlovilo)Simulacija.map[this.vrstaNaMapi][this.pozicijaNaMapi-3];
						System.out.println("Podmornica " + this.ID + " napada plovilo " + plovilo.ID);
						plovilo.napadnut = true;
					}
				}
			}
			 /*
			 * Prethodni if() block pokriva "Kada podmornica nadje brod na udaljenosti od 3 pozicije tada ispaljuje torpedo."
			 * Prvo sto se desava jeste da se izvrsi prepoznavanje 'tekuci objekat je podmornica' sto se desava odma u samome if-u,
			 * zatim za synchronized i argument if()-a ispod njega sam vec iskormentarisao..tako da idemo u tijelo if(){},
			 * 
			 * Sa plovilo referenciram taj napadnuti objekat( brod(Razarac ili NosacAviona)). Mogao sam i duplirati code pa panaosob pisati scenario
			 * kako za Razacar tako i za NosacAviona ali nisam jer se sluzim metodama OOP-a pa napadnuti objekat referenciram njegovom apstraktnom-nadklasom!
			 * Dalji tok ne treba komentarisati..naredba za ispis i setter ispod nje..
			 */
			
			
			if(this.smjerKretanja && this.pozicijaNaMapi == Simulacija.brojKolona-1){ 
				break;
			}
			else if(!this.smjerKretanja && this.pozicijaNaMapi == 0){
				break;
			}
			/*
			 *	Prethodni if(){} i njemu sljedbenik else if(){} 	
			 *  definise detekcije:
			 *  1) Objekat koji se od svog prvog postavljanja na mapu kretao -> duz mape je stigao do poslednje celije mape,
			 *  2) a objekat koji se od svog prvog postavljanja na mapu kretao <- duz mape je stigao do prve celije u mapi.
			 *  Citajuci tekst zadatka, -> je kretanje predvidjeno za brodove, a <- je kretanje predvidjeno za podmornice..
			 */
			
			//	Jos je preostalo da definisemo proces kretanja objketa kroz mapu.
			
			if(this.smjerKretanja){														//	Ako se objekat(thread) krece s'lijeva u'desno
				this.pozicijaNaMapi++;													//  onda inkrementalno setnjom baj-po-bajt konvergiraj ka zavrsetku mape.
				if(this.pozicijaNaMapi > 0){
					Simulacija.map[this.vrstaNaMapi][this.pozicijaNaMapi-1]=null;		//	Prethodnu poziciju objekta(thread) SETuj na null,
				}
				Simulacija.map[this.vrstaNaMapi][this.pozicijaNaMapi]=this;				//	a novu mu azuriraj!
			}
			else{																			//	Ako se objekat(thread) krece s'desna u'lijevo
				this.pozicijaNaMapi--;														//	onda dekrementalno setas bajt-po-bajt s'tim da konvergiras ka pocetku mape.
				if(this.pozicijaNaMapi < Simulacija.brojKolona-1){			
					Simulacija.map[this.vrstaNaMapi][this.pozicijaNaMapi+1]=null;			//	Prethodnu poziciju objekta(thread) set-uj na null,
				}
				Simulacija.map[this.vrstaNaMapi][this.pozicijaNaMapi] = this;				//	a novu mu azuriraj!
			}
		}
		
		this.endTime = new Date().getTime();
	}
}