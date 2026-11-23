package com.bijlipay.model;
import javax.persistence.*;
@Entity
@Table(name = "kit_types")
public class KitType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "kit_id", nullable = false)
    private long id;
    @Column(name = "kit_type_name", nullable = false)
    private String kitTypeName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKitTypeName() {
        return kitTypeName;
    }

    public void setKitTypeName(String kitTypeName) {
        this.kitTypeName = kitTypeName;
    }


    @Override
    public String toString() {
        return "KitType{" +
                "id=" + id +
                ", kitTypeName='" + kitTypeName + '\'' +
                '}';
    }
}
