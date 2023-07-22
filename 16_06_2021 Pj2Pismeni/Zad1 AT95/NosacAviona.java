import java.util.Random;

public class NosacAviona extends VojnoPlovilo implements Radar, RaketniStit, TorpedoStit{
	
	public NosacAviona(){
		
	}
	
	public NosacAviona(/*int vrstaNaMapi,*/ boolean smjerKretanja){
		super(/*vrstaNaMapi,*/ smjerKretanja);
		Random random = new Random();
		this.naoruzanje = new Naoruzanje[2];
		this.naoruzanje[0] = new Raketa(random.nextDouble(11));
		this.naoruzanje[1] = new Raketa(random.nextDouble(11));
	}
}