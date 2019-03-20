import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


public class Descarrega extends Thread {
	private URL html ;
	boolean isAscii;
	
	public Descarrega(String link, boolean ascii) throws IOException  {
		html = new URL(link);
		isAscii = ascii;
	}
	
	public void run() {
		File fileComprobar = null;
        FileOutputStream foutput = null;
        URLConnection conexio = null;
  		InputStream lectura = null;
  		String nombreFitxero = null;
  		String p1 = null;
  		String p2 = null;
  		int count = 1;
  		
  		
			try {
				conexio = html.openConnection();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// si no puede obtener conexion con el link
				e.printStackTrace();
			}

			String fitxer = html.getFile();
			if (fitxer.equals("")){
				System.out.println("File : No hi ha fitxer" );
				
				nombreFitxero = new String("index.php");
			
			}
			else {
				while(fitxer.indexOf("/")!=(-1))
					fitxer = fitxer.substring(fitxer.indexOf("/")+1);
					
				System.out.println("File :"+ fitxer );
				nombreFitxero = new String(fitxer);
			}
				
			try {
				lectura = conexio.getInputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// si no puede obtener los bytes de conexio
				e.printStackTrace();
			}
			fileComprobar = new File(nombreFitxero);
			if(fileComprobar.exists())
				while(fileComprobar.exists()) {
					p1 = new String(nombreFitxero.substring(0, nombreFitxero.indexOf(".")));
					p2 = new String(nombreFitxero.substring(nombreFitxero.indexOf("."),nombreFitxero.length()));
					fileComprobar = new File(p1+count+p2) ;
					count = count + 1;
				}
			
			try {
				foutput = new FileOutputStream(fileComprobar);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				// archivo no encontrado
				e.printStackTrace();
			}
			
			
			
					
			try {

				while((lectura.available()) > 0) {
					
					if(isAscii) {
						Html2AsciiInputStream htmlFilter = new Html2AsciiInputStream(lectura);
						htmlFilter.read();
					}
					
		
					foutput.write(lectura.read());
				}
			
				
				} catch (IOException e) {
				// TODO Auto-generated catch block
				// si no puede leer el archivo
				e.printStackTrace();
			}
			try {
				foutput.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// si no puede cerrar el archivo
				e.printStackTrace();
			}
			System.out.println("Done !");

	}
}
