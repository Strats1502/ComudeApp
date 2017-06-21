package mx.gob.comude.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import fr.ganfra.materialspinner.MaterialSpinner;
import mx.gob.comude.R;
import mx.gob.comude.activities.LoginActivity;
import mx.gob.comude.api.RegistroRequest;
import mx.gob.comude.api.Response;
import mx.gob.comude.api.UsuarioAPI;
import mx.gob.comude.application.MyApplication;
import mx.gob.comude.model.Usuario;
import mx.gob.comude.sesion.Sesion;
import mx.gob.comude.utils.EditTextValidations;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

import static android.app.Activity.RESULT_OK;

/**
 * Autor: Uriel Infante
 * Fragment de datos complementarios del usuario, esta interfaz solicita información obligatoria
 * al usuario, y es llamada cuando se va a crear un nuevo usuario, sin importar su forma de logueo.
 * Fecha: 02/05/2016
 */
public class EditarDatosFragment extends CustomFragment implements View.OnClickListener{
    private static final String EMAIL = "email";
    private static final String NOMBRE = "nombre";
    private static final String AP_PATERNO = "ap_paterno";
    private static final String RUTA_IMAGEN = "ruta_imagen";

    private Usuario usuario; //instancia que se usará para cargar datos que vengan de la interfaz.
    private Button btnContinuar;
    private ImageView imgPerfil;
    //private MaterialSpinner spnEstadoCivil;

    private MaterialSpinner spnNivelEstudios;
    private MaterialSpinner spnConfirmProgramaGobierno;
    private MaterialSpinner spnProgramaGobierno;
    private MaterialSpinner spnTrabaja;
    private MaterialSpinner spnConfirmPuebloIndigena;
    private MaterialSpinner spnPuebloIndigena;
    private MaterialSpinner spnConfirmCapacidadDiferente;
    private MaterialSpinner spnCapacidadDiferente;
    private MaterialSpinner spnConfirmPremios;
    private EditText etPremios;
    private MaterialSpinner spnConfirmProyectosSociales;
    private EditText etProyectosSociales;
    private MaterialSpinner spnSueldoProyectosSociales;
    private EditText etIdiomasAdicionales;
    private ProgressDialog progressDialog;
    private ImageButton btnBack;

    private UsuarioAPI usuarioAPI;

    //private String[] estadosCiviles = {"Soltero/a", "Comprometido/a", "Casado/a", "Divorciado/a", "Casado/a"};
    private String[] siNo = {"Sí", "No"};
    private String[] nivelesEstudio = {"Primaria", "Secundaria", "Preparatoria", "TSU",  "Universidad", "Maestría", "Doctorado", "Otro"};
    private String[] programasGobierno = {"Municipal", "Estatal", "Federal", "Internacional"};
    private String[] pueblosIndigenas = {"Otomí", "Chichimeca-Jonaz", "Náhuatl", "Mazahua", "Otra"};
    private String[] capacidadesDiferentes = {"Física", "Sensorial", "Auditiva", "Visual", "Psíquica", "Intelectual", "Mental"};



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Retrofit retrofit = ((MyApplication)getActivity().getApplication()).getRetrofitInstance();
        usuarioAPI = retrofit.create(UsuarioAPI.class);

    }

    /**
     * Método del ciclo de vida que se ejecuta después de que la vista ha sido generada
     * @param inflater
     * @param parent
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_editar_datos, parent, false);

        btnContinuar = (Button) v.findViewById(R.id.btn_continuar);
        spnNivelEstudios = (MaterialSpinner) v.findViewById(R.id.spn_nivel_estudios);
        spnConfirmProgramaGobierno = (MaterialSpinner) v.findViewById(R.id.spn_confirm_programa_gobierno);
        spnProgramaGobierno = (MaterialSpinner) v.findViewById(R.id.spn_programa_gobierno);
        spnTrabaja = (MaterialSpinner) v.findViewById(R.id.spn_trabaja);
        spnConfirmPuebloIndigena = (MaterialSpinner) v.findViewById(R.id.spn_confirm_pueblo_indigena);
        spnPuebloIndigena = (MaterialSpinner) v.findViewById(R.id.spn_pueblo_indigena);
        spnConfirmCapacidadDiferente = (MaterialSpinner) v.findViewById(R.id.spn_confirm_capacidad_diferente);
        spnCapacidadDiferente = (MaterialSpinner) v.findViewById(R.id.spn_capacidad_diferente);
        spnConfirmPremios = (MaterialSpinner) v.findViewById(R.id.spn_confirm_premios);
        spnConfirmProyectosSociales = (MaterialSpinner) v.findViewById(R.id.spn_confirm_proyectos_sociales);
        spnSueldoProyectosSociales = (MaterialSpinner) v.findViewById(R.id.spn_sueldo_proyectos_sociales);
        etIdiomasAdicionales = (EditText) v.findViewById(R.id.et_idiomas_adicionales);
        etPremios = (EditText) v.findViewById(R.id.et_premios);
        etProyectosSociales = (EditText) v.findViewById(R.id.et_proyecto_social);


        imgPerfil = (ImageView) v.findViewById(R.id.img_profile);
        ArrayAdapter<String> siNoAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, siNo);
        ArrayAdapter<String> nivelEstudioAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, nivelesEstudio);
        ArrayAdapter<String> programaGobiernoAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, programasGobierno);
        ArrayAdapter<String> pueblosIndigenasAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, pueblosIndigenas);
        ArrayAdapter<String> capacidadesDiferentesAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, capacidadesDiferentes);



        siNoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nivelEstudioAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        programaGobiernoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pueblosIndigenasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        capacidadesDiferentesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnNivelEstudios.setAdapter(nivelEstudioAdapter);
        spnProgramaGobierno.setAdapter(programaGobiernoAdapter);
        spnPuebloIndigena.setAdapter(pueblosIndigenasAdapter);
        spnCapacidadDiferente.setAdapter(capacidadesDiferentesAdapter);
        spnConfirmProgramaGobierno.setAdapter(siNoAdapter);
        spnTrabaja.setAdapter(siNoAdapter);
        spnConfirmPuebloIndigena.setAdapter(siNoAdapter);
        spnConfirmCapacidadDiferente.setAdapter(siNoAdapter);
        spnConfirmPremios.setAdapter(siNoAdapter);
        spnConfirmProyectosSociales.setAdapter(siNoAdapter);
        spnSueldoProyectosSociales.setAdapter(siNoAdapter);


        EditTextValidations.dependencySpinners(spnConfirmProgramaGobierno, new View[]{spnProgramaGobierno});
        EditTextValidations.dependencySpinners(spnConfirmPuebloIndigena, new View[] {spnPuebloIndigena});
        EditTextValidations.dependencySpinners(spnConfirmCapacidadDiferente, new View[] {spnCapacidadDiferente});
        EditTextValidations.dependencySpinners(spnConfirmPremios, new View[] {etPremios });
        EditTextValidations.dependencySpinners(spnConfirmProyectosSociales, new View [] {etProyectosSociales, spnSueldoProyectosSociales});

        btnContinuar.setOnClickListener(this);

        //Picasso.with(getActivity()).load(usuario.getRutaImagen()).into(imgPerfil);
        return v;
    }



    /**
     * Funcionalidad de la interfaz aplicada al Fragment para el clic
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_continuar:
                continuar();
                break;
        }
    }

    /**
     * onStop: Ciclo de vida de fragment, se ejecuta cuando se detiene el fragment.
     */
    @Override
    public void onStop() {
        super.onStop();
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    /**
     * Ejecución de lo que se hará al presionar el botón de contninuar, se realizan las validaciones
     * para pasar a la interfaz de Datos complementarios.
     */
    public void continuar(){
        //Verifica que los campos no estén vacíos
            progressDialog = ProgressDialog.show(getActivity(), "Registrando", "Espere un momento mientras se completa el registro", true);


        /*
            Call<Response<Usuario>> callRegistrar = usuarioAPI.registrar(
                    new RegistroRequest(
                            usuario.getEmail(),
                            "_",
                            "_",
                            etApPaterno.getText().toString(),
                            etApMaterno.getText().toString(),
                            etNombre.getText().toString(),
                            spnGenero.getSelectedItemPosition() == 1? "H" m: "M",
                            etFechaNacimiento.getText().toString(),
                            etCodigoPostal.getText().toString(),
                            estadosValueArray[spnEstado.getSelectedItemPosition() - 1],
                            "data:image/jpeg;base64," + getBase64(imgPerfil),
                            usuario.getIdGoogle(),
                            usuario.getIdFacebook()
                    )
            );


            callRegistrar.enqueue(new Callback<Response<Usuario>>() {
                @Override
                public void onResponse(Call<Response<Usuario>> call, retrofit2.Response<Response<Usuario>> response) {
                    if(progressDialog != null){
                        progressDialog.dismiss();
                    }
                    Response<Usuario> body = response.body();
                    if (body.success) {
                        Sesion.cargarSesion(body.data);
                        ((LoginActivity) getActivity()).startHomeActivity();

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage(body.errors[0])
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //do things
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();                    }
                }

                @Override
                public void onFailure(Call<Response<Usuario>> call, Throwable t) {
                    if(progressDialog != null){
                        progressDialog.dismiss();
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Error al conectar con el servidor, intente más adelante")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //do things
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();

                }
            });


        }
        */
    }





}
