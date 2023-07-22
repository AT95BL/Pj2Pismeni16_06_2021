import java.nio.file.*;
import java.io.IOException;

public class Main
{
	public static void main(String[]args)throws IOException
	{
		Path path=Paths.get("C:\\Users\\pc\\Desktop\\PJ2backup\\PJ2 zadaci novo");
		Path outPath=Paths.get("C:\\Users\\pc\\Desktop\\test");
		Finder finder=new Finder(".txt",outPath);
		Files.walkFileTree(path,finder);
		System.out.println(finder.getNumOfMatchedFiles());
		finder.copyAllFiles();
	}
}