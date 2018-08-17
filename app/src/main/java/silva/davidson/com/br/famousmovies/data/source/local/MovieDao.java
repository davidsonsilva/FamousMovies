package silva.davidson.com.br.famousmovies.data.source.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import silva.davidson.com.br.famousmovies.data.Movie;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM favorite_movies")
    LiveData<List<Movie>> getAllFavoriteMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavoriteMovie(Movie movie);

    @Delete
    void deleteFavoriteMovie(Movie movie);

    @Query("SELECT * FROM favorite_movies where id = :movieId")
    Movie verifyFavorite(int movieId);


}
