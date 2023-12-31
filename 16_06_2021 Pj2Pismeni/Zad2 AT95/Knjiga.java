public class Knjiga{
	
	public String naslov;
	public String pisac;
	public int godinaIzdavanja;
	public Zanr zanr;
	
	public Knjiga(){
		this.naslov = "";
		this.pisac = "";
		this.godinaIzdavanja = 0;
		this.zanr = null;
	}
	
	public Knjiga(String naslov, String pisac, int godinaIzdavanja, Zanr zanr){
		this.naslov = naslov;
		this.pisac = pisac;
		this.godinaIzdavanja = godinaIzdavanja;
		this.zanr = zanr;
	}
	
	public String getNaslov(){ return this.naslov; }
	public String getPisac(){ return this.pisac; }
	public int getGodinaIzdavanja(){ return this.godinaIzdavanja; }
	public Zanr getZanr(){return this.zanr; }
	
	@Override
	public String toString(){
		return this.naslov + " " + this.pisac + " " + this.godinaIzdavanja + " " + this.zanr.toString();	//	Naglasavam this.zanr.toString() !!!
	}
	
	@Override
	public boolean equals(Object object){
		if(this == object){																					//	Provjeri jesu li this i object reference na isti objekat
			return true;																					//	te ako jesu..
		}
		
		if(object == null || this.getClass() != object.getClass()){											//	Ako nisu, provjeri je li mozda object referenca na null ili da mozda object ne referncira instancu neke druge klase?
			return false;
		}
		
		Knjiga knjiga = (Knjiga)object;																		//	(Eksplicitna konverzija)
		return (this.naslov.equals(knjiga.naslov)) && (this.godinaIzdavanja == knjiga.godinaIzdavanja);		//	poredjenje..
	}
	
	@Override
	public int hashCode(){
		int hash = 3;
		hash = 7 * hash + this.godinaIzdavanja;
		hash = 7 * hash + this.naslov.hashCode();
		return hash;
	}
	/*
		Tekuci objekat poziva metodu hashCode() i sta se desi?
		Izracuna se jedinstven broj koji odgovara tom objektu kao i svakom drugom objektu za koji this.equals(object) vraca true.
	*/
}