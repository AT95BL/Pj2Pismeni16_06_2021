public class Raketa extends Naoruzanje{
	
	public int koordinataCiljaX;
	public int koordinataCiljaY;
	
	public Raketa(double jacina){
		super(jacina);
		this.koordinataCiljaX = 0;
		this.koordinataCiljaY = 0;
	}
	
	public void setKoordinateCIlja(int koordinataCiljaX, int koordinataCiljaY){
		this.koordinataCiljaX = koordinataCiljaX;
		this.koordinataCiljaY = koordinataCiljaY;
	}
}