package com.ensemble.sample.moviesapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name="movies")
public class Movie implements Serializable {
    @Serial
    private static final long serialVersionUID = -8078105744010761127L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    private String title;

    @NotNull
    @Size(min=3, max=250)
    private String about;

    /*@Column
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date date;
*/

    private String copyright;

    @Column(name = "duration")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime duration;
    private Integer count_like;
    private Integer count_dislike;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    public Integer getCount_like() {
        return count_like;
    }

    public void setCount_like(Integer count_like) {
        this.count_like = count_like;
    }

    public Integer getCount_dislike() {
        return count_dislike;
    }

    public void setCount_dislike(Integer count_dislike) {
        this.count_dislike = count_dislike;
    }

    public void setLike(){ this.count_like++; }

    public void setDislike(){ this.count_dislike++; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movies = (Movie) o;
        return id.equals(movies.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
