package com.bijlipay.model;

import javax.persistence.*;

@Entity
@Table(name = "census_data")
public class LocationData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name = "state")
    private String state;
    @Column(name = "district")
    private String district;
    @Column(name = "sub_district")
    private String subDistrict;
    @Column(name = "town_village")
    private String townVillage;
    @Column(name = "level")
    private String level;
    @Column(name = "state_name")
    private String stateName;
    @Column(name = "district_name")
    private String districtName;
    @Column(name = "sub_district_name")
    private String subDistrictName;
    @Column(name = "town_village_name")
    private String townVillageName;
    @Column(name = "total_households")
    private String totalHouseholds;
    @Column(name = "total_population")
    private String totalPopulation;
    @Column(name = "estimated_population_in_2020")
    private String estimated_population_in_2020;
    @Column(name = "tier")
    private String tier;

    @Override
    public String toString() {
        return "MerchantOnBoarding{" +
                "id=" + id +
                ", state='" + state + '\'' +
                ", district='" + district + '\'' +
                ", subDistrict='" + subDistrict + '\'' +
                ", townVillage='" + townVillage + '\'' +
                ", level='" + level + '\'' +
                ", stateName='" + stateName + '\'' +
                ", districtName='" + districtName + '\'' +
                ", subDistrictName='" + subDistrictName + '\'' +
                ", townVillageName='" + townVillageName + '\'' +
                ", totalHouseholds='" + totalHouseholds + '\'' +
                ", totalPopulation='" + totalPopulation + '\'' +
                ", estimated_population_in_2020='" + estimated_population_in_2020 + '\'' +
                ", tier='" + tier + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSubDistrict() {
        return subDistrict;
    }

    public void setSubDistrict(String subDistrict) {
        this.subDistrict = subDistrict;
    }

    public String getTownVillage() {
        return townVillage;
    }

    public void setTownVillage(String townVillage) {
        this.townVillage = townVillage;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getSubDistrictName() {
        return subDistrictName;
    }

    public void setSubDistrictName(String subDistrictName) {
        this.subDistrictName = subDistrictName;
    }

    public String getTownVillageName() {
        return townVillageName;
    }

    public void setTownVillageName(String townVillageName) {
        this.townVillageName = townVillageName;
    }

    public String getTotalHouseholds() {
        return totalHouseholds;
    }

    public void setTotalHouseholds(String totalHouseholds) {
        this.totalHouseholds = totalHouseholds;
    }

    public String getTotalPopulation() {
        return totalPopulation;
    }

    public void setTotalPopulation(String totalPopulation) {
        this.totalPopulation = totalPopulation;
    }

    public String getEstimated_population_in_2020() {
        return estimated_population_in_2020;
    }

    public void setEstimated_population_in_2020(String estimated_population_in_2020) {
        this.estimated_population_in_2020 = estimated_population_in_2020;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }


}
