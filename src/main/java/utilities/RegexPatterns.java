package utilities;

public class RegexPatterns {
    public static final String NOMBRE_REGEX = "^[A-Za-zÁÉÍÓÚáéíóúñÑüÜ\\s]+$";
    public static final String DIRECCION_REGEX = "^[a-zA-ZÁÉÍÓÚáéíóúñÑüÜ0-9\\\\s.,#\\\\-\\\\/°()'\\\\u0022]+$";
    public static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    public static final String TELEFONO_REGEX = "^[0-9]{8}$";
    public static final String WEB_REGEX = "^(https?:\\/\\/)?(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)$";

}
