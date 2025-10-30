package com.example.D0031N;

import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Skriver ut till konsolen när applikationen startar
 * för att bekräfta att varje databas fungerar.
 */
@Component
public class DatabaseConnectionChecker implements CommandLineRunner {

    private final Jdbi epokJdbi;
    private final Jdbi studentitsJdbi;
    private final Jdbi ladokJdbi;

    public DatabaseConnectionChecker(
            @Qualifier("epokJdbi") Jdbi epokJdbi,
            @Qualifier("studentJdbi") Jdbi studentitsJdbi,
            @Qualifier("ladokJdbi") Jdbi ladokJdbi
    ) {
        this.epokJdbi = epokJdbi;
        this.studentitsJdbi = studentitsJdbi;
        this.ladokJdbi = ladokJdbi;
    }

    @Override
    public void run(String... args) {
        System.out.println("🔍 Kontrollerar databasanslutningar...");

        try {
            String epokVersion = epokJdbi.withHandle(h ->
                    h.createQuery("SELECT version()").mapTo(String.class).one());
            System.out.println("✅ EPOK är ansluten! " + epokVersion);
        } catch (Exception e) {
            System.err.println("❌ EPOK kunde inte ansluta: " + e.getMessage());
        }

        try {
            String studentVersion = studentitsJdbi.withHandle(h ->
                    h.createQuery("SELECT version()").mapTo(String.class).one());
            System.out.println("✅ STUDENTITS är ansluten! " + studentVersion);
        } catch (Exception e) {
            System.err.println("❌ STUDENTITS kunde inte ansluta: " + e.getMessage());
        }

        try {
            String ladokVersion = ladokJdbi.withHandle(h ->
                    h.createQuery("SELECT version()").mapTo(String.class).one());
            System.out.println("✅ LADOK är ansluten! " + ladokVersion);
        } catch (Exception e) {
            System.err.println("❌ LADOK kunde inte ansluta: " + e.getMessage());
        }

        System.out.println("--------------------------------------------------");
        System.out.println("🚀 Systemen är igång på http://localhost:8080");
        System.out.println("   → /epok/courses/D0031N/modules");
        System.out.println("   → /studentits/users/sveedz-4/personnummer");
        System.out.println("   → /ladok/results (POST)");
        System.out.println("--------------------------------------------------");
    }
}