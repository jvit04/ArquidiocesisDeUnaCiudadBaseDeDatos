package controllers;
import java.io.File;
import java.sql.*;

import application.*;
import javafx.scene.control.ComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utilities.RegexPatterns;
import javafx.scene.control.ChoiceBox;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class ArquidiocesisController implements cargarClerigos, guardarParroquiaSQL, cargarVicarias, importarParroquiasCSV  {
    @FXML
    private AnchorPane ancorPane1;

    @FXML
    private Button botonDescargar;

    @FXML
    private Button botonExaminar;

    @FXML
    private Button botonIniciar;

    @FXML
    private Button botonMenuProceso;

    @FXML
    private Button botonMenuReporte;

    @FXML
    private Button botonMenuSubirArchivo;

    @FXML
    private Button botonRegresoInicio;

    @FXML
    private Button botonSubir;

    @FXML
    private TableColumn<Parroquia, String> ciudad;

    @FXML
    private TableColumn<Parroquia, String> direccion;

    @FXML
    private TableColumn<Parroquia, String> email;

    @FXML
    private TableColumn<Parroquia, String> fecha_ereccion;

    @FXML
    private ImageView gifCatedral;

    @FXML
    private TableColumn<Parroquia, Integer> idParroquia;

    @FXML
    private TableColumn<Parroquia, Integer> id_vicaria;

    @FXML
    private ImageView imageCatedral;

    @FXML
    private Label labelArquidiocesis1;

    @FXML
    private Label labelArquidiocesis2;

    @FXML
    private Label labelArquidiocesis3;

    @FXML
    private Label labelDinamico;

    @FXML
    private Label labelSeleccione;

    @FXML
    private VBox menuBotones;

    @FXML
    private TableColumn<Parroquia, String> nombre_parroquia;

    @FXML
    private Rectangle rectanguloFondo;

    @FXML
    private Rectangle rectanguloFondo1;

    @FXML
    private Rectangle rectanguloInicio;

    @FXML
    private Rectangle rectanguloMenu;

    @FXML
    private Button registroBotonEnviar;

    @FXML
    private DatePicker registroDatePickerFF;

    @FXML
    private Label registroLabelCiudadPertenece;

    @FXML
    private Label registroLabelDescripcion;

    @FXML
    private Label registroLabelDireccionParroquia;

    @FXML
    private Label registroLabelEmail;

    @FXML
    private Label registroLabelFechaF;

    @FXML
    private Label registroLabelNombreParroquia;

    @FXML
    private Label registroLabelParroco;

    @FXML
    private Label registroLabelSitioWeb;

    @FXML
    private Label registroLabelTelefono;

    @FXML
    private Label registroLabelVicaria;

    @FXML
    private TextField registroTextFieldCiudad;

    @FXML
    private ComboBox<Clerigo> registroComboBoxParroco;

    @FXML
    private ChoiceBox<Vicaria> registroChoiceBoxVicaria;

    @FXML
    private TextField registroTxtFieldEmail;

    @FXML
    private TextField registroTxtFieldSitioWeb;

    @FXML
    private TextField registroTxtFieldTelefono;

    @FXML
    private TextField registrotextFieldDireccion;

    @FXML
    private TableColumn<Parroquia, String> sitio_web;

    @FXML
    private TableView<Parroquia> tablaReporte;

    @FXML
    private TableColumn<Parroquia, String> telefono;

    @FXML
    private TextField textFieldRutaArchivo;

    @FXML
    private TextField txtFieldNombreParroquia;



    private File archivoSeleccionado;


    void setRegistroComboBoxParroco(){
        registroComboBoxParroco.getItems().addAll(cargarClerigos.cargarClerigos());
    }




    @FXML
    void mostrarMenu(ActionEvent event) {
        labelArquidiocesis2.setVisible(true);
        labelArquidiocesis1.setVisible(false);
        gifCatedral.setVisible(false);
        rectanguloFondo.setVisible(false);
        rectanguloFondo1.setVisible(true);
        rectanguloInicio.setVisible(false);
        rectanguloMenu.setVisible(true);
        menuBotones.setVisible(true);
        botonIniciar.setVisible(false);
        labelSeleccione.setVisible(true);
        imageCatedral.setVisible(true);

    }
    @FXML
    void regresarInicio(ActionEvent event) {
        initialize();
    }

    @FXML
    void initialize(){
        registroDatePickerFF.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                // Deshabilitar fechas futuras
                setDisable(empty || date.compareTo(LocalDate.now()) > 0);
            }
        });
        setRegistroComboBoxParroco();
        labelArquidiocesis2.setVisible(false);
        labelArquidiocesis1.setVisible(true);
        gifCatedral.setVisible(true);
        rectanguloFondo.setVisible(true);
        rectanguloFondo1.setVisible(false);
        rectanguloInicio.setVisible(true);
        rectanguloMenu.setVisible(false);
        menuBotones.setVisible(false);
        labelSeleccione.setVisible(false);
        botonIniciar.setVisible(true);
        labelArquidiocesis2.setLayoutY(40);
        labelArquidiocesis2.setLayoutX(245);
        labelDinamico.setVisible(false);
        textFieldRutaArchivo.setVisible(false);
        botonExaminar.setVisible(false);
        botonSubir.setVisible(false);
        textFieldRutaArchivo.setText("");
        imageCatedral.setVisible(false);
        labelSeleccione.setLayoutX(173);
        labelSeleccione.setText("Seleccione en el menú lo que desee realizar");
        tablaReporte.setVisible(false);
        botonDescargar.setVisible(false);
        //Objetos de Registro
        labelArquidiocesis3.setVisible(false);
        registroLabelDescripcion.setVisible(false);
        registroLabelNombreParroquia.setVisible(false);
        txtFieldNombreParroquia.setVisible(false);
        registroLabelVicaria.setVisible(false);
        registroChoiceBoxVicaria.setVisible(false);
        registroLabelCiudadPertenece.setVisible(false);
        registroTextFieldCiudad.setVisible(false);
        registroLabelDireccionParroquia.setVisible(false);
        registrotextFieldDireccion.setVisible(false);
        registroLabelParroco.setVisible(false);
        registroComboBoxParroco.setVisible(false);
        registroLabelTelefono.setVisible(false);
        registroTxtFieldTelefono.setVisible(false);
        registroLabelSitioWeb.setVisible(false);
        registroTxtFieldSitioWeb.setVisible(false);
        registroLabelEmail.setVisible(false);
        registroTxtFieldEmail.setVisible(false);
        registroLabelFechaF.setVisible(false);
        registroDatePickerFF.setVisible(false);
        registroBotonEnviar.setVisible(false);

        registroChoiceBoxVicaria.getItems().addAll(cargarVicarias.cargarVicarias());
        registroChoiceBoxVicaria.getSelectionModel().selectFirst();
        txtFieldNombreParroquia.setText("");
        registroTextFieldCiudad.setText("");
        registroComboBoxParroco.setValue(null);
        registrotextFieldDireccion.setText("");
        registroTxtFieldTelefono.setText("");
        registroTxtFieldSitioWeb.setText("");
        registroTxtFieldEmail.setText("");
        registroDatePickerFF.setValue(null);

//
//        idParroquia.setCellValueFactory(new PropertyValueFactory<>("idParroquia"));
//        nombre_parroquia.setCellValueFactory(new PropertyValueFactory<>("nombreParroquia"));
//        direccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
//        ciudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
//        telefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
//        email.setCellValueFactory(new PropertyValueFactory<>("email"));
//        sitio_web.setCellValueFactory(new PropertyValueFactory<>("sitioWeb"));
//        fecha_ereccion.setCellValueFactory(new PropertyValueFactory<>("fechaEreccion"));
//        id_vicaria.setCellValueFactory(new PropertyValueFactory<>("idVicaria"));


        try {
            cargarTabla();
        } catch (SQLException e) {
            e.printStackTrace(); // Imprime el error si falla la conexión
        }
    }

    @FXML
    void crearParroquia(ActionEvent event) {
        registroComboBoxParroco.getSelectionModel().selectFirst();
        labelSeleccione.setVisible(false);
        labelArquidiocesis2.setLayoutY(20);
        labelDinamico.setVisible(false);
        textFieldRutaArchivo.setVisible(false);
        botonExaminar.setVisible(false);
        botonSubir.setVisible(false);
        textFieldRutaArchivo.setText("");
        imageCatedral.setVisible(false);
        tablaReporte.setVisible(false);
        botonDescargar.setVisible(false);
        //Objetos de Registro
        labelArquidiocesis2.setVisible(false);
        labelArquidiocesis3.setVisible(true);
        registroLabelDescripcion.setVisible(true);
        registroLabelNombreParroquia.setVisible(true);
        txtFieldNombreParroquia.setVisible(true);
        registroLabelVicaria.setVisible(true);
        registroChoiceBoxVicaria.setVisible(true);
        registroLabelCiudadPertenece.setVisible(true);
        registroTextFieldCiudad.setVisible(true);
        registroLabelDireccionParroquia.setVisible(true);
        registrotextFieldDireccion.setVisible(true);
        registroLabelParroco.setVisible(true);
        registroComboBoxParroco.setVisible(true);
        registroLabelTelefono.setVisible(true);
        registroTxtFieldTelefono.setVisible(true);
        registroLabelSitioWeb.setVisible(true);
        registroTxtFieldSitioWeb.setVisible(true);
        registroLabelEmail.setVisible(true);
        registroTxtFieldEmail.setVisible(true);
        registroLabelFechaF.setVisible(true);
        registroDatePickerFF.setVisible(true);
        registroBotonEnviar.setVisible(true);
    }

    @FXML
    void EnviarDatosParroquia(ActionEvent event) {
     //Validación de campos
        String nombreParroquia = txtFieldNombreParroquia.getText();
        int vicaria = registroChoiceBoxVicaria.getValue().getIdVicaria();
        String ciudad = registroTextFieldCiudad.getText();
        String direccion = registrotextFieldDireccion.getText();
        int parroco = registroComboBoxParroco.getValue().getId_clerigo();
        String telefono = registroTxtFieldTelefono.getText();
        String sitioWeb = registroTxtFieldSitioWeb.getText();
        String email = registroTxtFieldEmail.getText();
        LocalDate fechaFundacion = registroDatePickerFF.getValue();


                 if (nombreParroquia == null || nombreParroquia.trim().isEmpty()
                         || ciudad == null || ciudad.trim().isEmpty()
                         || direccion == null || direccion.trim().isEmpty()
                         || fechaFundacion == null
                 ) {
                    mostrarAlerta("Error","Rellene los campos necesarios.");
                     return;
                }
                 if (telefono.trim().isEmpty()){
                     telefono =null;
                 }
                 if (sitioWeb.trim().isEmpty()){
                     sitioWeb=null;
                 }
                 if (email.trim().isEmpty()){
                     email=null;
                 }


                //Se va a validar los campos con formatos REGEX
                if (!Pattern.matches(RegexPatterns.NOMBRE_REGEX,nombreParroquia) ) {
                    mostrarAlerta("Error","Formato invalido en campo Nombre de la Parroquia.");

                    return;
                }

            if (!Pattern.matches(RegexPatterns.NOMBRE_REGEX,ciudad) ) {
                mostrarAlerta("Error","Formato invalido en campo Ciudad.");

                return;
            }
        if (!Pattern.matches(RegexPatterns.DIRECCION_REGEX,direccion) ) {
            mostrarAlerta("Error","Formato invalido en campo Dirección.");

            return;
        }


        if (telefono !=null && !Pattern.matches(RegexPatterns.TELEFONO_REGEX,telefono) ) {
            mostrarAlerta("Error","Formato invalido en campo Telefono. Deben ser 10 dígitos");
            return;
        }


        if (sitioWeb != null && !Pattern.matches(RegexPatterns.WEB_REGEX,sitioWeb) ) {
            mostrarAlerta("Error","Formato invalido en campo Sitio Web. Ej: www.iglesia.com");
            return;
        }



            if (email != null && !Pattern.matches(RegexPatterns.EMAIL_REGEX,email) ) {
                    mostrarAlerta("Error","Formato invalido en campo Email. Ej: arquidiocesis@gmail.com");
                    return;
            }

        registroBotonEnviar.setDisable(true);
        try {
            Parroquia parroquia = new Parroquia(nombreParroquia, vicaria, ciudad, direccion, fechaFundacion, parroco, telefono, email, sitioWeb);
            guardarParroquiaSQL.guardarEnSQL(parroquia);
            mostrarAlerta("Éxito", "La parroquia ha sido registrada correctamente.");
            refrescarCrearParroquia();

        } catch (Exception e) {
            mostrarAlerta("Error al guardar", "No se pudo registrar: " + e.getMessage());

        } finally {
            registroBotonEnviar.setDisable(false);
        }
    }
    void refrescarCrearParroquia(){
        registroComboBoxParroco.getItems().clear();
        setRegistroComboBoxParroco();
        registroComboBoxParroco.getSelectionModel().selectFirst();
        txtFieldNombreParroquia.setText("");
        registroChoiceBoxVicaria.getSelectionModel().selectFirst();;
        registroTextFieldCiudad.setText("");
         registrotextFieldDireccion.setText("");
       registroTxtFieldTelefono.setText("");
        registroTxtFieldSitioWeb.setText("");
       registroTxtFieldEmail.setText("");
       registroDatePickerFF.setValue(null);

    }

    @FXML
    void generarReporte(ActionEvent event) {
        labelSeleccione.setVisible(false);
        labelArquidiocesis2.setLayoutY(24);
        labelDinamico.setVisible(false);
        textFieldRutaArchivo.setVisible(false);
        botonExaminar.setVisible(false);
        botonSubir.setVisible(false);
        textFieldRutaArchivo.setText("");
        imageCatedral.setVisible(false);
        tablaReporte.setVisible(true);
        botonDescargar.setVisible(true);
        //Objetos de Registro
        labelArquidiocesis2.setVisible(true);
        labelArquidiocesis3.setVisible(false);
        registroLabelDescripcion.setVisible(false);
        registroLabelNombreParroquia.setVisible(false);
        txtFieldNombreParroquia.setVisible(false);
        registroLabelVicaria.setVisible(false);
        registroChoiceBoxVicaria.setVisible(false);
        registroLabelCiudadPertenece.setVisible(false);
        registroTextFieldCiudad.setVisible(false);
        registroLabelDireccionParroquia.setVisible(false);
        registrotextFieldDireccion.setVisible(false);
        registroLabelParroco.setVisible(false);
        registroComboBoxParroco.setVisible(false);
        registroLabelTelefono.setVisible(false);
        registroTxtFieldTelefono.setVisible(false);
        registroLabelSitioWeb.setVisible(false);
        registroTxtFieldSitioWeb.setVisible(false);
        registroLabelEmail.setVisible(false);
        registroTxtFieldEmail.setVisible(false);
        registroLabelFechaF.setVisible(false);
        registroDatePickerFF.setVisible(false);
        registroBotonEnviar.setVisible(false);

    }
    @FXML
    void subirArchivosMenu(ActionEvent event) {
        labelSeleccione.setVisible(false);
        labelArquidiocesis2.setLayoutY(101);
        labelArquidiocesis2.setLayoutX(245);
        labelDinamico.setVisible(true);
        textFieldRutaArchivo.setVisible(true);
        botonExaminar.setVisible(true);
        botonSubir.setVisible(true);
        imageCatedral.setVisible(false);
        tablaReporte.setVisible(false);
        botonDescargar.setVisible(false);
//Objetos de Registro
        labelArquidiocesis2.setVisible(true);
        labelArquidiocesis3.setVisible(false);
        registroLabelDescripcion.setVisible(false);
        registroLabelNombreParroquia.setVisible(false);
        txtFieldNombreParroquia.setVisible(false);
        registroLabelVicaria.setVisible(false);
        registroChoiceBoxVicaria.setVisible(false);
        registroLabelCiudadPertenece.setVisible(false);
        registroTextFieldCiudad.setVisible(false);
        registroLabelDireccionParroquia.setVisible(false);
        registrotextFieldDireccion.setVisible(false);
        registroLabelParroco.setVisible(false);
        registroComboBoxParroco.setVisible(false);
        registroLabelTelefono.setVisible(false);
        registroTxtFieldTelefono.setVisible(false);
        registroLabelSitioWeb.setVisible(false);
        registroTxtFieldSitioWeb.setVisible(false);
        registroLabelEmail.setVisible(false);
        registroTxtFieldEmail.setVisible(false);
        registroLabelFechaF.setVisible(false);
        registroDatePickerFF.setVisible(false);
        registroBotonEnviar.setVisible(false);

    }
    @FXML
    void cambiarLabelExaminar(MouseEvent event) {
        labelDinamico.setText("Elegir ruta de archivo.");
        labelDinamico.setLayoutX(280);
    }
    @FXML
    void regresarLabelExaminar(MouseEvent event) {
        labelDinamico.setText("Elija el archivo a subir.");
        labelDinamico.setLayoutX(282);

    }

    @FXML
    void cambiarLabelSubir(MouseEvent event) {
        labelDinamico.setText("Subir archivos.");
        labelDinamico.setLayoutX(302);

    }

    @FXML
    void regresarLabelSubir(MouseEvent event) {
        labelDinamico.setText("Elija el archivo a subir.");
        labelDinamico.setLayoutX(282);

    }

    private void cargarTabla() throws SQLException{
            // lista observable donde se guardan los datos
            ObservableList<Parroquia> listaParroquias = FXCollections.observableArrayList();


//
//            try (Connection connection = ConexionBD.conectar();
//                    Statement stmt = connection.createStatement();
//                 ResultSet resultSet = stmt.executeQuery("SELECT * FROM parroquias")) {
//
//                while (resultSet.next()) {
//                    Parroquia p = new Parroquia(
//                            resultSet.getInt("idParroquia"),
//                            resultSet.getString("nombre_parroquia"),
//                            resultSet.getString("direccion_parroquia"),
//                            resultSet.getString("ciudad_parroquia"),
//                            resultSet.getString("telefono_parroquia"),
//                            resultSet.getString("email_parroquia"),
//                            resultSet.getString("sitio_web"),
//                            resultSet.getDate("fecha_ereccion"),
//                            resultSet.getInt("id_vicaria")
//                    );
//
//                    listaParroquias.add(p);
//                }
//            } catch (SQLException e) {
//                throw e; //Excepción para verla en consola
//            }
//
//            // 4. Asignar la lista llena a la Tabla
//            tablaReporte.setItems(listaParroquias);
        }
        private File archivoTemporal;
    @FXML
    void examinarRuta(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar archivo de Arquidiócesis");
        new FileChooser.ExtensionFilter("Archivos CSV (*.csv)",       "*.csv"); //solo se necesita csv
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File archivoTemporal = fileChooser.showOpenDialog(stage);

        if (archivoTemporal != null) {
            String nombreArchivo = archivoTemporal.getName().toLowerCase();

            // 2. Verificamos si termina en .csv
            if (!nombreArchivo.endsWith(".csv")) {
                mostrarAlerta("Formato Incorrecto", "El archivo seleccionado no es un CSV (.csv). Por favor, elige el archivo correcto.");


                archivoSeleccionado = null;
                textFieldRutaArchivo.clear();
                return;
            }


            archivoSeleccionado = archivoTemporal;
            textFieldRutaArchivo.setText(archivoSeleccionado.getAbsolutePath());
        }
    }

    @FXML
    void subirArchivo(ActionEvent event) {
        if (archivoSeleccionado == null) {
            mostrarAlerta("Error", "No hay archivo seleccionado");
            return;
        }

        String nombreArchivo = archivoSeleccionado.getName().toLowerCase().replace(".csv", "");

        try {
            switch (nombreArchivo) {
                case "parroqua":
                    //importarFeligreses(archivoSeleccionado);
                    break;
                case "parroquias":
                    importarParroquiasCSV.importarParroquias(archivoSeleccionado);
                    break;
                case "sacerdotes":
                    //importarSacerdotes(archivoSeleccionado);
                    break;
                default:
                    mostrarAlerta("Archivo Desconocido",
                            "El nombre del archivo (" + nombreArchivo + ") no coincide con ninguna tabla registrada.");
                    return;
            }


            mostrarAlerta("Éxito", "Los datos de " + nombreArchivo + " se importaron correctamente.");
            textFieldRutaArchivo.clear();
            archivoSeleccionado = null;

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error de Base de Datos", "Falló la importación: " + e.getMessage());
        }
    }




    // Método auxiliar para mostrar alertas en JavaFX
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }


}










