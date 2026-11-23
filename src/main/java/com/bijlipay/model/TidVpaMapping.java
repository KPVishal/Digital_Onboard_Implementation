package com.bijlipay.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tid_vpa_mapping")
@Setter
@Getter
public class TidVpaMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vpa_mapping_id", nullable = false)
    private long id;

    @Column(name = "vpa", nullable = true)
    private String vpa;

    @ManyToOne(optional = true)
    @JoinColumn(name = "mars_device", referencedColumnName = "mars_device_id")
    private MarsDeviceDetails marsDeviceDetails;


}
