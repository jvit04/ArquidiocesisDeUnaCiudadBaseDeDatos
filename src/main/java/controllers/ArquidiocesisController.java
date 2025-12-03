package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class ArquidiocesisController {
    @FXML
    private Rectangle rectanguloMargenInf;

    @FXML
    private TextField registrotextFieldDireccion;

    @FXML
    private AnchorPane ancorPane1;

    @FXML
    private Button botonDescargar;

    @FXML
    private Button botonExaminar;

    @FXML
    private Button botonIniciar;
    @FXML
    private Label labelArquidiocesis3;


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
    private ImageView gifCatedral;

    @FXML
    private ImageView imageCatedral;

    @FXML
    private Label labelArquidiocesis1;

    @FXML
    private Label labelArquidiocesis2;

    @FXML
    private Label labelDinamico;

    @FXML
    private Label labelSeleccione;

    @FXML
    private VBox menuBotones;

    @FXML
    private Rectangle rectanguloFondo;

    @FXML
    private Rectangle rectanguloFondo1;

    @FXML
    private Rectangle rectanguloInicio;

    @FXML
    private Rectangle rectanguloMargenDer;

    @FXML
    private Rectangle rectanguloMargenIzq;

    @FXML
    private Rectangle rectanguloMargenSup;

    @FXML
    private Rectangle rectanguloMenu;

    @FXML
    private Button registroBotonEnviar;

    @FXML
    private Label registroLabelCiudadPertenece;

    @FXML
    private DatePicker registroDatePickerFF;

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
    private TextField registroTextFieldVicaria;

    @FXML
    private TextField registroTxtFieldEmail;

    @FXML
    private TextField registroTxtFieldSitioWeb;

    @FXML
    private TextField registroTxtFieldTelefono;

    @FXML
    private TextField registroTextFieldParroco;

    @FXML
    private TableView<?> tablaReporte;

    @FXML
    private TextField textFieldRutaArchivo;

    @FXML
    private TextField txtFieldNombreParroquia;
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
        registroTextFieldVicaria.setVisible(false);
        registroLabelCiudadPertenece.setVisible(false);
        registroTextFieldCiudad.setVisible(false);
        registroLabelDireccionParroquia.setVisible(false);
        registrotextFieldDireccion.setVisible(false);
        registroLabelParroco.setVisible(false);
        registroTextFieldParroco.setVisible(false);
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
    void crearParroquia(ActionEvent event) {
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
        registroTextFieldVicaria.setVisible(true);
        registroLabelCiudadPertenece.setVisible(true);
        registroTextFieldCiudad.setVisible(true);
        registroLabelDireccionParroquia.setVisible(true);
        registrotextFieldDireccion.setVisible(true);
        registroLabelParroco.setVisible(true);
        registroTextFieldParroco.setVisible(true);
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
        registroTextFieldVicaria.setVisible(false);
        registroLabelCiudadPertenece.setVisible(false);
        registroTextFieldCiudad.setVisible(false);
        registroLabelDireccionParroquia.setVisible(false);
        registrotextFieldDireccion.setVisible(false);
        registroLabelParroco.setVisible(false);
        registroTextFieldParroco.setVisible(false);
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
        registroTextFieldVicaria.setVisible(false);
        registroLabelCiudadPertenece.setVisible(false);
        registroTextFieldCiudad.setVisible(false);
        registroLabelDireccionParroquia.setVisible(false);
        registrotextFieldDireccion.setVisible(false);
        registroLabelParroco.setVisible(false);
        registroTextFieldParroco.setVisible(false);
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







}

