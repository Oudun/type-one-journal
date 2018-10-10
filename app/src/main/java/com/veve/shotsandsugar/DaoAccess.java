package com.veve.shotsandsugar;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import com.veve.shotsandsugar.model.Insulin;
import com.veve.shotsandsugar.model.InsulinShot;
import com.veve.shotsandsugar.model.SelectedInsulin;
import com.veve.shotsandsugar.model.SugarLevel;

@Dao
public interface DaoAccess {

    @Insert
    void insertSugarLevel (SugarLevel record);

    @Insert
    void insertSugarLevels (List<SugarLevel> records);

    @Query("SELECT * FROM SugarLevel WHERE Id = :id")
    SugarLevel fetchSugarLevel (int id);

    @Query("SELECT * FROM SugarLevel")
    List<SugarLevel> fetchSugarLevels ();

    @Update
    void updateMovie (SugarLevel record);

    @Delete
    void deleteSugarLevel(SugarLevel record);

    @Insert
    void insertSugarLevel (Insulin record);

    @Insert
    void insertInsulin(Insulin insulin);

    @Insert
    void insertSelectedInsulin(SelectedInsulin insulin);

    @Query("SELECT * FROM Insulin")
    List<Insulin> listInsulins();

    @Query("SELECT Insulin.* FROM Insulin, SelectedInsulin WHERE Insulin.id = SelectedInsulin.insulinId")
    List<Insulin> listSelectedInsulins();

    @Insert
    void insertShot(InsulinShot insulinShot);

}