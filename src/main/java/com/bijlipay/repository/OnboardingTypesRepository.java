package com.bijlipay.repository;

import com.bijlipay.model.OnboardingTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OnboardingTypesRepository extends JpaRepository<OnboardingTypes, Integer> {

    @Query("select o from OnboardingTypes o where o.onboarding =?1")
    OnboardingTypes checkConfigOnboard(String onboarding);

    @Query("select o from OnboardingTypes o where o.id =?1")
    OnboardingTypes findByOnboardConfigId(int id);
}
