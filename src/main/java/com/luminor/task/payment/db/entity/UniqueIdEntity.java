package com.luminor.task.payment.db.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "unique_id", schema = "payments")
public class UniqueIdEntity {
    private int id;
    private String hashValue;
    private Timestamp generationTime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "hash_value")
    public String getHashValue() {
        return hashValue;
    }

    public void setHashValue(String hashValue) {
        this.hashValue = hashValue;
    }

    @Basic
    @Column(name = "generation_time")
    public Timestamp getGenerationTime() {
        return generationTime;
    }

    public void setGenerationTime(Timestamp generationTime) {
        this.generationTime = generationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UniqueIdEntity that = (UniqueIdEntity) o;
        return id == that.id &&
            Objects.equals(hashValue, that.hashValue) &&
            Objects.equals(generationTime, that.generationTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hashValue, generationTime);
    }
}
