package ru.mirea.khudyakovma.employeedb;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Hero {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String name;
    public String description;

    public Hero(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
