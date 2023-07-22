/*
public enum Pravac{
	
	LIJEVO, DESNO
}
*/

public class Torpedo extends Naoruzanje{
	
	public Pravac pravacKretanja;
	
	public Torpedo(double jacina){
		super(jacina);
	}

	public void setPravacKretanja(Pravac pravac){
		this.pravacKretanja = pravac;
	}
}