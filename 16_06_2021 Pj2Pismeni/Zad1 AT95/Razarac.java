import java.util.Random;

public class Razarac extends VojnoPlovilo implements Radar, RaketniStit{
	
	public Razarac(){
		
	}
	
	public Razarac(/*int vrstaNaMapi,*/ boolean smjerKretanja){
		super(/*vrstaNaMapi,*/ smjerKretanja);
		Random random = new Random();
		this.naoruzanje = new Naoruzanje[2];
		this.naoruzanje[0] = new Raketa(random.nextDouble(11));
		this.naoruzanje[1] = new Raketa(random.nextDouble(11));
		
	}
}