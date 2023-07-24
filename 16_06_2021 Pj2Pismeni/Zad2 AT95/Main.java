import java.util.HashSet;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

public class Main
{
	public static HashSet<Knjiga> generateGroupOfBooks(int num)
	{
		HashSet<Knjiga> set=new HashSet<>();												//	A HashSet is a collection that does not allow duplicate elements.
		/*
		 * Obratiti paznju na sliku imena
		 * relathionshipBetween_hashCode()_and_Hash
		 * koju dostavljam u sklopu rjesenja zadatka!!!
		 */
		Zanr[]zanrovi=Zanr.values();
		for(int i=1;i<20;i++)
		{
			set.add(new Knjiga("Naslov"+num+i,"Pisac"+i+num,2000+i,zanrovi[i%4]));			//	Primjeti hesiranje [i%4]!!!
		}
		/*
		 * Prethodna for-petlja obavlja posao koji je eksplicitno naglasen u samom tekstu zadatka.
		 * Elem, receno je da se HashSet popuni sa minimalno 20 knjiga tako da se podatci generisu na slucajan nacin..
		*/

		set.add(new Knjiga("abc","pisac123",2020,Zanr.TRILER));
		set.add(new Knjiga("abc1213dfasdsadasdsa","pisac123das",2020,Zanr.TRILER));
		return set;
	}
	
	public static void main(String[]args)
	{
		HashSet<Knjiga> set1=generateGroupOfBooks(15);				//	Znaci sada ti set1 referencira HashSet<Knjiga>(15) ili ti 15 objekata-Knjiga tako da svakom objektu je pridruzen neki HashCode..
		HashSet<Knjiga> set2=generateGroupOfBooks(20);				//	Analogno prethodnom komentaru..
		set2.stream().forEach(s->
			{
				set1.add(s);
			});
		set2.clear();
		System.out.println("Broj knjiga u grupi:"+set1.size());		
		set1.stream().map(Knjiga::getPisac).distinct().forEach(System.out::println);	//	map(Knjiga::getPisac): This is an intermediate operation that transforms each Knjiga object in the stream into its corresponding Pisac (author) using the getPisac() method of the Knjiga class. The result is a stream of Pisac objects.
		/*
		 * Putting it all together, the code line takes the set1 HashSet, converts it into a stream of Knjiga objects, 
		 * then maps each Knjiga object to its corresponding Pisac using the getPisac() method. 
		 * It then removes any duplicate Pisac objects and finally prints each unique Pisac object to the console.
		   To better understand the code's behavior, 
		   let's assume the Knjiga class has a method called getPisac() that returns the Pisac (author) of the book as an object of type Pisac. 
		   The code will print the names of the unique authors of the books contained in the set1 HashSet. 
		   If there are multiple books written by the same author, the distinct() operation ensures that the author's name is printed only once.

		   Bukvalno za svaki od problema sa kojima se susrecem/o na ispitu iz Pj2 u Java programskom jeziku postoji built-in feature
		   ili ti neko drugi i pametniji je vec implementirao alat koji rjesava posao.
		   Moj posao jeste da u glavi rijesim problem, pa onda da odaberem najadekvatniju Java metodu, klasu, feature
		   i realizujem taj posao.
		   -Znaci shvatis/rijesis problem,
		   -Odaberes najbolje od Java progamskog jezika,
		   -primjenis i zadatak je time gotov! 
		 */
		Map<Zanr,List<Knjiga>> mapa =set1.stream().collect(Collectors.groupingBy(Knjiga::getZanr));
		/*
			Map<Zanr, List<Knjiga>> mapa: This line declares a variable named mapa of type Map. 
			The Map is a data structure in Java that stores key-value pairs. In this case, the keys are of type Zanr (probably an enum representing genres of books), 
			and the values are lists of Knjiga (books).

			set1.stream(): This converts the set1 HashSet into a stream of Knjiga objects. It allows us to perform streaming operations on the elements of the set.

			Collectors.groupingBy(Knjiga::getZanr): This is a terminal operation using the Collectors class. 
			It collects the elements of the stream into a Map, grouping them based on a specific criterion. 
			In this case, the grouping criterion is the result of the getZanr() method of the Knjiga class.

			Sva moc HashMap-e i Map-e ogleda se bas ovdje, komentar [68,70].

			Knjiga::getZanr: This is a method reference used as a classifier function for the grouping. 
			It represents the getZanr() method of the Knjiga class. This means that the books will be grouped based on their genres (Zanr).

			The resulting Map will have Zanr objects as keys, and each key will be associated with a list of Knjiga objects that belong to that specific genre.

			To understand the output of this code, let's assume that set1 contains various Knjiga objects with different genres. 
			The code will group these books by their genres and store them in the mapa variable as follows:

			The keys of mapa will be the different genres (Zanr) present in the set1.
			The values of mapa will be lists of Knjiga objects belonging to each respective genre.
			You can then access the books of a particular genre by retrieving the value corresponding to the respective genre key from the mapa map.
		*/		
		System.out.println("Kreirane grupe:");
		mapa.keySet().forEach((s)->{
			System.out.println(s+":");
			mapa.get(s).stream().forEach(System.out::println);
		});
		System.out.println("Knjige sortirane opadajuce prema godini:");
		set1.stream().sorted((o1,o2)->o2.godinaIzdavanja-o1.godinaIzdavanja).forEach(System.out::println);
		int sum=mapa.get(Zanr.PUTOPIS).stream().filter(s->s.godinaIzdavanja%3==0).mapToInt(Knjiga::getGodinaIzdavanja).sum();
		System.out.println("Suma godina:"+sum);
		//	Optional class allows you to work with potentially null values safely
		Optional<Knjiga> optima1=set1.stream().min((a,b)->a.naslov.length()-b.naslov.length());
		System.out.println(optima1.get());
		Optional<Knjiga> optima2=set1.stream().max((a,b)->a.naslov.length()-b.naslov.length());
		System.out.println(optima2.get());
		/*
		 * In summary, this code snippet finds the Knjiga object with the minimum title length in the set1 collection and prints it. 
		 * Then, it finds the Knjiga object with the maximum title length in the same collection and prints it as well.
		 */
	}
}