package mx.gob.comude.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.messaging.RemoteMessage;

import org.w3c.dom.Text;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import mx.gob.comude.R;
import mx.gob.comude.adapters.RVNotificacionAdapter;
import mx.gob.comude.application.MyApplication;
import mx.gob.comude.model.Notificacion;
import mx.gob.comude.notifications.FirebaseMessagingService;
import retrofit2.Retrofit;

/**
 * Created by code on 9/02/17.
 */

public class NotificacionesFragment extends CustomFragment {
    private RecyclerView rvNotificaciones;
    private TextView tvEmptyNotificaciones;
    private RVNotificacionAdapter adapter;
    private Retrofit retrofit;
    private Realm realm;
    private List<Notificacion> notificaciones;
    private SharedPreferences preferences;
    private AppCompatActivity activity;
    private Toolbar toolbar;
    private CollapsingToolbarLayout cToolbar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrofit = ((MyApplication) getActivity().getApplication()).getRetrofitInstance();
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplication());
        realm = MyApplication.getRealmInstance();

        activity = ((AppCompatActivity) getActivity());
        toolbar = (Toolbar) activity.findViewById(R.id.toolbar2);
        cToolbar = (CollapsingToolbarLayout) activity.findViewById(R.id.collapsing_toolbar);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notificaciones, container, false);
        rvNotificaciones = (RecyclerView) v.findViewById(R.id.rv_notificaciones);
        tvEmptyNotificaciones = (TextView) v.findViewById(R.id.tv_empty_notificacion);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rvNotificaciones.setLayoutManager(llm);

        updateList();

        return v;
    }


    private void updateList() {
        RealmResults<Notificacion> result = realm.where(Notificacion.class).findAll();
        notificaciones = realm.copyFromRealm(result);
        adapter = new RVNotificacionAdapter(getActivity(), notificaciones);
        rvNotificaciones.setAdapter(adapter);
    }


    @Override
    public void onStop() {
        super.onStop();
        getActivity().findViewById(R.id.toolbar).setVisibility(View.VISIBLE);
        cToolbar.setVisibility(View.GONE);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setHomeButtonEnabled(true);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        ImageView imagen = (ImageView)cToolbar.findViewById(R.id.image);

        getActivity().findViewById(R.id.toolbar).setVisibility(View.GONE);
        cToolbar.setVisibility(View.VISIBLE);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setHomeButtonEnabled(true);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setTitle("Notificaciones");
        cToolbar.setTitle("Notificaciones");
        imagen.setImageResource(R.drawable.comude_img_4);

    }
}
