package com.bijlipay.repository;

import com.bijlipay.model.LanguageSoundbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LanguageSoundboxRepository extends JpaRepository<LanguageSoundbox,Long> {

    @Query("SELECT COUNT(u) FROM LanguageSoundbox u WHERE u.soundId = :id")
    long countExistingLanguage(@Param("id") Long id);
    @Query("SELECT l FROM LanguageSoundbox l WHERE l.language=?1 AND l.soundId=?2")
    LanguageSoundbox findLanguage(String language, Long soundId);

    @Query("SELECT l FROM LanguageSoundbox l WHERE l.language=?1")
    List<LanguageSoundbox> findSOundId(String Language);

    @Query("SELECT l FROM LanguageSoundbox l WHERE l.soundId=?1")
    LanguageSoundbox getById1(long id);
}
