package utilities;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
//Clase con metodo que se encarga de guardar el archivo del reporte en el ordenador
public class guardarArchivoEnRuta {
   public static void guardarArchivo(File fileDestino) {
        try {
            InputStream is = guardarArchivoEnRuta.class.getResourceAsStream(Paths.REPORTES);

            if (is != null) {
                Files.copy(is, fileDestino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                is.close();
                System.out.println("Archivo guardado exitosamente en: " + fileDestino.getAbsolutePath());
            } else {
                System.err.println("No se encontró el archivo de origen.");
            }

        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
