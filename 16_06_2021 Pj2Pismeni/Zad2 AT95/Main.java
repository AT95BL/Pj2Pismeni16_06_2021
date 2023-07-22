import java.util.HashSet;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

public class Main
{
	public static HashSet<Knjiga> generateGroupOfBooks(int num)
	{
		HashSet<Knjiga> set=new HashSet<>();
		Zanr[]zanrovi=Zanr.values();
		for(int i=1;i<20;i++)
		{
			set.add(new Knjiga("Naslov"+num+i,"Pisac"+i+num,2000+i,zanrovi[i%4]));
		}
		set.add(new Knjiga("abc","pisac123",2020,Zanr.TRILER));
		set.add(new Knjiga("abc1213dfasdsadasdsa","pisac123das",2020,Zanr.TRILER));
		return set;
	}
	
	public static void main(String[]args)
	{
		HashSet<Knjiga> set1=generateGroupOfBooks(15);
		HashSet<Knjiga> set2=generateGroupOfBooks(20);
		set2.stream().forEach(s->
			{
				set1.add(s);
			});
		set2.clear();
		System.out.println("Broj knjiga u grupi:"+set1.size());
		set1.stream().map(Knjiga::getPisac).distinct().forEach(System.out::println);
		Map<Zanr,List<Knjiga>> mapa =set1.stream().collect(Collectors.groupingBy(Knjiga::getZanr));
		System.out.println("Kreirane grupe:");
		mapa.keySet().forEach((s)->{
			System.out.println(s+":");
			mapa.get(s).stream().forEach(System.out::println);
		});
		System.out.println("Knjige sortirane opadajuce prema godini:");
		set1.stream().sorted((o1,o2)->o2.godinaIzdavanja-o1.godinaIzdavanja).forEach(System.out::println);
		int sum=mapa.get(Zanr.PUTOPIS).stream().filter(s->s.godinaIzdavanja%3==0).mapToInt(Knjiga::getGodinaIzdavanja).sum();
		System.out.println("Suma godina:"+sum);
		Optional<Knjiga> optima1=set1.stream().min((a,b)->a.naslov.length()-b.naslov.length());
		System.out.println(optima1.get());
		Optional<Knjiga> optima2=set1.stream().max((a,b)->a.naslov.length()-b.naslov.length());
		System.out.println(optima2.get());
	}
}