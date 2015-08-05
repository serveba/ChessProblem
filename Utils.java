import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class Utils {
	public static Properties getResourceProps(String res, boolean externo, Class clase)
	  {
	    String sErr = null;
	    Properties props = new Properties();
	    try
	    {
	      InputStream in;	      
	      if (externo) {
	        in = new FileInputStream(new File(res));
	      } else {
	        in = clase.getResourceAsStream(res);
	      }
	      props.load(in);
	      in.close();
	      

	      return props;
	    }
	    catch (FileNotFoundException ex)
	    {
	      sErr = "Error buscando fichero: " + ex.getMessage();
	    }
	    catch (IOException ex)
	    {
	      sErr = "Error leyendo fichero: " + ex.getMessage();
	    }
	    catch (Exception ex)
	    {
	      sErr = "Error desconocido en cargaResource: " + ex.getMessage();
	    }
	    if (sErr != null) {
	      System.err.println(sErr);
	    }
	    return null;
	  }
	  
	  public static byte[] getBytesFromFile(File file)
	    throws IOException
	  {
	    InputStream is = new FileInputStream(file);
	    

	    long length = file.length();
	    if (length > 2147483647L) {
	      throw new IOException("File is too large");
	    }
	    byte[] bytes = new byte[(int)length];
	    

	    int offset = 0;
	    int numRead = 0;
	    while ((offset < bytes.length) && ((numRead = is.read(bytes, offset, bytes.length - offset)) >= 0)) {
	      offset += numRead;
	    }
	    if (offset < bytes.length) {
	      throw new IOException("Could not completely read file " + file.getName());
	    }
	    is.close();
	    return bytes;
	  }
	  
	  public static String obtenerExtension(String nombreFichero)
	  {
	    int posicionPunto = nombreFichero.lastIndexOf(".");
	    if (posicionPunto > -1) {
	      return nombreFichero.substring(posicionPunto + 1);
	    }
	    return "";
	  }
	  
	  public static String obtenerExtensionConPunto(String nombreFichero)
	  {
	    int posicionPunto = nombreFichero.lastIndexOf(".");
	    if (posicionPunto > -1) {
	      return nombreFichero.substring(posicionPunto);
	    }
	    return "";
	  }
	  
	  public static String obtenerNombreSinExtension(String nombreFichero)
	  {
	    String extensionConPunto = obtenerExtensionConPunto(nombreFichero);
	    

	    int lenExtensionConPunto = extensionConPunto.length();
	    

	    int posicionFinal = nombreFichero.length() - lenExtensionConPunto;
	    
	    return nombreFichero.substring(0, posicionFinal);
	  }
	  
	  public static String truncarNombreFisico(String nombreFisico, int nuevaLen)
	  {
	    if ((nombreFisico == null) || (nuevaLen <= 0)) {
	      return nombreFisico;
	    }
	    if (nombreFisico.length() <= nuevaLen) {
	      return nombreFisico;
	    }
	    String nombreParaDevolver = "";
	    
	    String extensionConPunto = obtenerExtensionConPunto(nombreFisico);
	    
	    int lenExtensionConPunto = 0;
	    if (extensionConPunto != null)
	    {
	      lenExtensionConPunto = extensionConPunto.length();
	      if (lenExtensionConPunto == nuevaLen) {
	        nuevaLen++;
	      }
	    }
	    String nombreSinExtension = obtenerNombreSinExtension(nombreFisico);
	    if (nombreSinExtension != null) {
	      nombreSinExtension = nombreSinExtension.trim();
	    }
	    int lenParaProcesar = nuevaLen - lenExtensionConPunto;
	    if (lenParaProcesar >= 0)
	    {
	      nombreParaDevolver = nombreSinExtension.substring(0, lenParaProcesar);
	      if (extensionConPunto != null) {
	        return nombreParaDevolver.trim() + extensionConPunto;
	      }
	      return nombreParaDevolver.trim();
	    }
	    return nombreFisico;
	  }
	  
	  public static String getSysdateFormateado()
	  {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
	    String fechaFormateada = sdf.format(new Date());
	    
	    return fechaFormateada;
	  }
	  
	  public static void main(String[] args)
	  {
	    String x = "abcdef";
	    String y = "  s";
	    String z = "bla BLA.pdf.doc";
	    
	    System.out.println(truncarNombreFisico(x, 1));
	    System.out.println(truncarNombreFisico(x, 0));
	    System.out.println(truncarNombreFisico(y, 1));
	    System.out.println(truncarNombreFisico(z, 13));
	    System.out.println(truncarNombreFisico(z, 4));
	  }
}
