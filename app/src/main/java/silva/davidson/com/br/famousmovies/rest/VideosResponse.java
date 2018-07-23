package silva.davidson.com.br.famousmovies.rest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

import silva.davidson.com.br.famousmovies.model.Videos;

public class VideosResponse {

    @SerializedName("id")
    @Expose

    private Integer id;
    @SerializedName("results")
    @Expose
    private List<Videos> videos = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setResults(List<Videos> results) {
        this.videos = results;
    }

    public List<Videos> getVideos() {
        if (videos == null)
            return Collections.emptyList();
        return videos;
    }

}
