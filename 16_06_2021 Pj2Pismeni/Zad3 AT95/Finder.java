import java.nio.file.*;
import java.nio.file.attribute.*;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;

public class Finder implements FileVisitor<Path>
{
	private ArrayList<Path> paths=new ArrayList<>();
	private String ekstenzija;
	private Path copyPath;
	public Finder(String ekstenzija,Path copyPath){
		this.ekstenzija=ekstenzija;
		this.copyPath=copyPath;
	}
	
	private void find(Path path){
		if(path.getFileName().toString().endsWith(this.ekstenzija))
			paths.add(path);
	}
	
	public int getNumOfMatchedFiles(){
		return this.paths.size();
	}
	
	@Override
	public FileVisitResult preVisitDirectory(Path dir,BasicFileAttributes attrs)throws IOException{
		return FileVisitResult.CONTINUE;
	}
	
	@Override
	public FileVisitResult visitFile(Path file,BasicFileAttributes attrs)throws IOException{
		find(file);
		return FileVisitResult.CONTINUE;
	}
	
	@Override
	public FileVisitResult visitFileFailed(Path file,IOException exc)throws IOException{
		return FileVisitResult.CONTINUE;
	}
	
	@Override
	public FileVisitResult postVisitDirectory(Path dir,IOException exc)throws IOException{
		return FileVisitResult.CONTINUE;
	}
	
	public void copyAllFiles(){
		
			for(var path:this.paths){
				try{	
					Path dest=Paths.get(this.copyPath.toString(),path.getFileName().toString());
					Files.copy(path,dest);
				}
				catch(IOException e)
				{
					System.out.println("Greska prilikom kopiranja fajla:"+path.getFileName().toString());
				}
			}	
	}
}