package silva.davidson.com.br.famousmovies.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import silva.davidson.com.br.famousmovies.R;
import silva.davidson.com.br.famousmovies.ReviewsFragment;
import silva.davidson.com.br.famousmovies.TrailersFragment;

public class MoviePagerAdapter extends FragmentPagerAdapter {

    public MoviePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return ReviewsFragment.newInstance();
            case 1: return TrailersFragment.newInstance();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position){
        switch (position) {
            case 0 : return getItem(position).getString(R.string.title_tab_review);
            case 1 : return getItem(position).getString(R.string.title_tab_trailers);
            default:return "";
        }
    }
}
