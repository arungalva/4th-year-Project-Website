package com.eggplant.emoji.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "announcement")
public class Announcement {
    public static final String DEFAULT_ANNOUNCEMENT = "DEFAULT ANNOUNCEMENT";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String description;

    public Announcement(){ this(DEFAULT_ANNOUNCEMENT); }

    public Announcement(String description){ this.description = description; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }
}
