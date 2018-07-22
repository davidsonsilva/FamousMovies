package silva.davidson.com.br.famousmovies.persistence;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import silva.davidson.com.br.famousmovies.model.Movie;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM favorite_movies LIMIT 10")
    LiveData<Movie> getFavoriteMovie();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavoriteMovie(Movie movie);

    @Query("DELETE FROM favorite_movies WHERE id = :movieId")
    void deleteFavoriteMovie(int movieId);

}
