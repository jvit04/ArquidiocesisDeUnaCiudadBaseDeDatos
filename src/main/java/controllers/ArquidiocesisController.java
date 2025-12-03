package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.TableView;
import javafx.scene.text.Font;

public class ArquidiocesisController {
    @FXML
    private TableView<?> tablaReporte;
    @FXML
    private Button botonDescargar;

    @FXML
    private Rectangle RectanguloMargenInf;

    @FXML
    private AnchorPane ancorPane1;

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
    private ImageView gifCatedral;

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
    private ImageView imageCatedral;

    @FXML
    private TextField textFieldRutaArchivo;
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
        labelDinamico.setFont(new Font(25));
    }

    @FXML
    void generarReporte(ActionEvent event) {
        labelSeleccione.setLayoutX(182);
        labelSeleccione.setText("El reporte ha sido generado exitosamente");
        labelSeleccione.setVisible(true);
        labelArquidiocesis2.setLayoutY(40);
        labelDinamico.setVisible(false);
        textFieldRutaArchivo.setVisible(false);
        botonExaminar.setVisible(false);
        botonSubir.setVisible(false);
        textFieldRutaArchivo.setText("");
        imageCatedral.setVisible(false);
        tablaReporte.setVisible(true);
        botonDescargar.setVisible(true);
        labelArquidiocesis2.setFont(new Font(33));

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
        labelDinamico.setFont(new Font(33));


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

