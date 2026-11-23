package com.bijlipay.model;

import javax.persistence.*;

@Entity
@Table(name = "soundbox_language")
public class SoundboxLanguage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "language_id", nullable = false)
    private long id;
    @Column(name = "language_name", nullable = false)
    private String languageName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }


    @Override
    public String toString() {
        return "SoundboxLanguage{" +
                "id=" + id +
                ", languageName='" + languageName + '\'' +
                '}';
    }
}
