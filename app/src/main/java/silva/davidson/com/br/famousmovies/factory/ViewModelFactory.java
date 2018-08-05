package silva.davidson.com.br.famousmovies.factory;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import silva.davidson.com.br.famousmovies.data.source.local.MovieDatabase;
import silva.davidson.com.br.famousmovies.viewmodel.MovieViewModel;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final MovieDatabase mdb;
    private final Application mApplication;
    private static volatile ViewModelFactory INSTANCE;


    public ViewModelFactory(MovieDatabase db, Application application) {
        this.mdb = db;
        this.mApplication = application;
    }

    public static ViewModelFactory getInstance(Application application) {

        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewModelFactory(MovieDatabase.getInstance(application
                            .getApplicationContext()),
                            application);
                }
            }
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MovieViewModel.class)) {
            return (T) new MovieViewModel(mApplication, mdb);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
