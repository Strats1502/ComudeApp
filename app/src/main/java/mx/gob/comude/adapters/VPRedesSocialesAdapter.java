package mx.gob.comude.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import mx.gob.comude.fragments.FacebookFragment;
import mx.gob.comude.fragments.TwitterFragment;


/**
 * Created by codigus on 19/5/2017.
 */

public class VPRedesSocialesAdapter extends FragmentPagerAdapter {

    public VPRedesSocialesAdapter(FragmentManager fm) {
        super(fm);
    }

    //Dependiendo de la position que le pases cambia de fragment
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new TwitterFragment();
                break;
            case 1:
                fragment = new FacebookFragment();
                break;
        }
        return fragment;
    }

    //Regresa el número de elementos en el tabLayout
    @Override
    public int getCount() {
        return 2;
    }


}
