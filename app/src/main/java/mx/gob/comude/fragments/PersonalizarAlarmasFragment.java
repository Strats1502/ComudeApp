package mx.gob.comude.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mx.gob.comude.R;


/**
 * Created by Uriel on 01/02/2016.
 */
public class PersonalizarAlarmasFragment extends CustomFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_personalizar_alarmas, parent, false);

        return v;
    }
}