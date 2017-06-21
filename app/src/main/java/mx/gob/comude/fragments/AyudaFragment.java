package mx.gob.comude.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import mx.gob.comude.R;

/**
 * Created by code on 26/10/16.
 */
public class AyudaFragment extends Fragment {
    private static int FIRST_SLIDE = 1;
    private static int LAST_SLIDE = 4;
    private int slide;
    private int nextSlide;

    private ImageView imageView;
    private TextView textView;
    private Button continuarBtn;
    private View backgroundView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ayuda, container, false);
        imageView = (ImageView) v.findViewById(R.id.img_tutorial);
        textView = (TextView) v.findViewById(R.id.tv_texto_tutorial);
        continuarBtn = (Button) v.findViewById(R.id.btn_continuar);
        backgroundView = v.findViewById(R.id.background);

        slide = getArguments().getInt("slide");
        nextSlide = slide + 1;
        switch (slide){
            case 1:
                backgroundView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                imageView.setImageResource(R.drawable.slide1);
                textView.setText(R.string.slide1);
                break;
            case 2:
                imageView.setImageResource(R.drawable.slide2);
                textView.setText(R.string.slide1);
                backgroundView.setBackgroundColor(getResources().getColor(R.color.colorAccentDark));
                break;
            case 3:
                imageView.setImageResource(R.drawable.slide3);
                textView.setText(R.string.slide1);
                backgroundView.setBackgroundColor(getResources().getColor(R.color.colorMagenta));
                break;
            case 4:
                imageView.setImageResource(R.drawable.slide4);
                textView.setText(R.string.slide1);
                backgroundView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                break;
        }

        continuarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nextSlide <= LAST_SLIDE) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    AyudaFragment a = AyudaFragment.newInstance(nextSlide);
                    ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left).replace(R.id.fragment_container, a).addToBackStack(null).commit();
                }else{
                    getActivity().finish();
                }
            }
        });
        return v;
    }

    public static AyudaFragment newInstance(int slide){
        AyudaFragment a = new AyudaFragment();
        Bundle args = new Bundle();
        args.putInt("slide", slide);
        a.setArguments(args);
        return a;
    }
}
