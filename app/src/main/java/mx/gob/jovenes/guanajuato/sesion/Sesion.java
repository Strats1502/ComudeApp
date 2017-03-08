package mx.gob.jovenes.guanajuato.sesion;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import mx.gob.jovenes.guanajuato.model.Usuario;

/**
 * Created by code on 7/03/17.
 */

public class Sesion {

    private static SharedPreferences prefs;

    private static final String ID_USUARIO = "id_usuario";
    private static final String EMAIL  = "email";
    private static final String API_TOKEN = "api_token";
    private static final String NOMBRE = "nombre";
    private static final String ID_DATOS_USUARIO = "id_datos_usuario";
    private static final String ID_GENERO = "id_genero";
    private static final String FECHA_NACIMIENTO = "fecha_nacimiento";
    private static final String ID_OCUPACION = "id_ocupacion";
    private static final String CODIGO_POSTAL = "codigo_postal";
    private static final String TELEFONO = "telefono";
    private static final String CURP = "curp";
    private static final String ID_ESTADO = "id_estado";
    private static final String ID_MUNICIPIO = "id_municipio";
    private static final String RUTA_IMAGEN = "ruta_imagen";

    public Sesion(Context ctx){

        prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static int getIdUsuario(){
        return prefs.getInt(ID_USUARIO, 0);
    }

    public static String getEmail(){
        return prefs.getString(EMAIL, null);
    }

    public static String getApiToken(){
        return prefs.getString(API_TOKEN, null);
    }

    public static String getNombre(){
        return prefs.getString(NOMBRE, null);
    }

    public static int getIdDatosUsuario(){
        return prefs.getInt(ID_DATOS_USUARIO, 0);
    }

    public static int getIdGenero(){
        return prefs.getInt(ID_GENERO, 0);
    }

    public static String getFechaNacimiento(){
        return prefs.getString(FECHA_NACIMIENTO, null);
    }

    public static int getIdOcupacion(){
        return prefs.getInt(ID_OCUPACION, 0);
    }

    public static int getCodigoPostal(){
        return prefs.getInt(CODIGO_POSTAL, 0);
    }

    public static String getTelefono(){
        return prefs.getString(TELEFONO, null);
    }

    public static String getCurp(){
        return prefs.getString(CURP, null);
    }

    public static int getIdEstado(){
        return prefs.getInt(ID_ESTADO, 0);
    }
    public static int getIdMunicipio(){
        return prefs.getInt(ID_MUNICIPIO, 0);
    }
    public static String getRutaImagen (){
        return prefs.getString(RUTA_IMAGEN, null);
    }

    public static void cargarSesion(Usuario usuario) {
        prefs.edit().putInt(ID_USUARIO, usuario.getId_usuario()).apply();
        prefs.edit().putInt(ID_DATOS_USUARIO, usuario.getId_datos_usuario()).apply();
        prefs.edit().putInt(ID_ESTADO, usuario.getId_estado()).apply();
        prefs.edit().putInt(ID_MUNICIPIO, usuario.getId_municipio()).apply();
        prefs.edit().putInt(ID_GENERO, usuario.getId_genero()).apply();
        prefs.edit().putInt(ID_OCUPACION, usuario.getId_ocupacion()).apply();
        prefs.edit().putInt(CODIGO_POSTAL, usuario.getCodigo_postal()).apply();
        prefs.edit().putString(EMAIL, usuario.getEmail()).apply();
        prefs.edit().putString(API_TOKEN, usuario.getApi_token()).apply();
        prefs.edit().putString(NOMBRE, usuario.getNombre()).apply();
        if (usuario.getFecha_nacimiento() != null) {
            prefs.edit().putString(FECHA_NACIMIENTO, usuario.getFecha_nacimiento().toString()).apply();
        }
        prefs.edit().putString(TELEFONO, usuario.getTelefono()).apply();
        prefs.edit().putString(CURP, usuario.getCurp()).apply();
        prefs.edit().putString(RUTA_IMAGEN, usuario.getRuta_imagen()).apply();


    }

}