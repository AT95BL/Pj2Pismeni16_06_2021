import java.nio.file.*;
import java.nio.file.attribute.*;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;

public class Finder implements FileVisitor<Path>{				//	JAVA API FileVisitor Interface, ono sto slijedi jeste njegova implementacija:
	
	private ArrayList<Path> paths = new ArrayList<>();
	private String ekstenzija;
	private Path copyPath;										//	https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/nio/file/Path.html An object that may be used to locate a file in a file system. It will typically represent a system dependent file path.
	
	public Finder(String ekstenzija, Path copyPath){
		this.ekstenzija = ekstenzija;
		this.copyPath = copyPath;
	}
	/*
		Poziv ovog Constructor-a rezultira tako da nakon izvrsenja istog imamo objekat klase Finder koji kao atribute posjeduje
		-inicijalizovan Path copyPath odnosno copyPath = apsolutna putanja do tekuce/g datoteke/direktorijuma
		-inicijalizovan String ekstenzija odnosno ekstenzija = ekstenzija datoteke
	*/
	
	private void find(Path path){
		if(path.getFileName().toString().endsWith(this.ekstenzija)){		//	https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/nio/file/Path.html#getFileName()
			paths.add(path);
		}
	}
	/*
		In summary, the find method is used to find files or directories with a specific file extension (ekstenzija) and add their corresponding Path objects to the paths collection. 
		The method assumes that the ekstenzija instance variable is set properly before calling the find method.
	*/
	
	public int getNumOfMatchedFiles(){
		return this.paths.size();				//	Vracamo duzino od ArrayList<Path> paths kolekcije..
	}
	
	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attributes) throws IOException{
		return FileVisitResult.CONTINUE;
	}
	
	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attribute) throws IOException{
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
	/*
		preVisitDirectory(Path dir, BasicFileAttributes attributes):

		This method is invoked before visiting the entries in a directory (i.e., before entering the directory).
		The dir parameter represents the Path of the directory to be visited, and attributes provide basic file attributes of the directory (like creation time, last modified time, etc.), but they are not used in this specific implementation.
		The method returns a FileVisitResult, which is an enum that indicates how the traversal should proceed. In this case, FileVisitResult.CONTINUE is returned, indicating that the traversal should continue without taking any specific actions at this point.
		visitFile(Path file, BasicFileAttributes attribute):

		This method is invoked when a file is visited during the traversal.
		The file parameter represents the Path of the file being visited, and attributes provide basic file attributes of the file (like size, creation time, etc.), but they are not used in this specific implementation.
		Inside this method, the find(file) method is called, presumably to find and process files that match a certain condition (such as having a specific file extension).
		The method returns FileVisitResult.CONTINUE, indicating that the traversal should continue without stopping.
		visitFileFailed(Path file, IOException exc):

		This method is invoked if there is an error accessing a file during the traversal.
		The file parameter represents the Path of the file that triggered the error, and the exc parameter holds the IOException that occurred.
		In this implementation, the method simply returns FileVisitResult.CONTINUE, indicating that the traversal should continue without taking any specific actions related to the failed file.
		postVisitDirectory(Path dir, IOException exc):

		This method is invoked after all the entries in a directory have been visited (i.e., after exiting the directory).
		The dir parameter represents the Path of the directory that has been visited, and the exc parameter holds any IOException that occurred during the traversal (if any).
		In this implementation, the method returns FileVisitResult.CONTINUE, indicating that the traversal should continue without taking any specific actions related to the visited directory.
	*/
	
	public void copyAllFiles(){
		
		for(var path : this.paths){		//	Using var allows you to declare a local variable without explicitly specifying its type. 
			try{
				Path dest = Paths.get(this.copyPath.toString(), path.getFileName().toString());
				Files.copy(path,dest);
			}
			catch(IOException ex){
				System.out.println("Greska prilikom kopiranja fajla: " + path.getFileName().toString());
			}
		}
	}
}