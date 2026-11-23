package com.bijlipay.model;


import javax.persistence.*;

@Entity
@Table(name = "lang_soundbox_sou")
public class LanguageSoundbox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "sound_id")
    private Long soundId;
    @Column(name = "language")
    private String language;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "file_uri")
    private String fileUri;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSoundId() {
        return soundId;
    }

    public void setSoundId(Long soundId) {
        this.soundId = soundId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUri() {
        return fileUri;
    }

    public void setFileUri(String fileUri) {
        this.fileUri = fileUri;
    }
}
