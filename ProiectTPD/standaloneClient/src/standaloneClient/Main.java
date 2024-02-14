package standaloneClient;
 
import javax.naming.InitialContext;
import javax.naming.NamingException;
 
import com.example.ProiectStatelessEjbRemote;
 
public class Main {
 
	public static void main(String[] args) throws NamingException{
		InitialContext context = new InitialContext();
		ProiectStatelessEjbRemote firstEjb = (ProiectStatelessEjbRemote) context
				.lookup("java:global/proiectTPDEar/proiectTPDEjb/ProiectStatelessEjb!com.example.ProiectStatelessEjbRemote");
		firstEjb.insert("Crina Anghelus");
 
	}
 
}