package com.example.meetup.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "pictures")
public class PictureEntity extends BaseEntity{

    @Column
    private String title;

    @Column(nullable = false)
    private String url;

    public PictureEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public PictureEntity setUrl(String url) {
        this.url = url;
        return this;
    }
}
