package com.example.D0031N.EPOK;

public class ModuleDto {
    private String kod;
    private String namn;
    private Double hp;
    private boolean aktiv;

    public ModuleDto() {}
    public ModuleDto(String kod, String namn, Double hp, boolean aktiv) {
        this.kod = kod; this.namn = namn; this.hp = hp; this.aktiv = aktiv;
    }

    public String getKod() { return kod; }
    public String getNamn() { return namn; }
    public Double getHp() { return hp; }
    public boolean isAktiv() { return aktiv; }

    public void setKod(String v) { this.kod=v; }
    public void setNamn(String v) { this.namn=v; }
    public void setHp(Double v) { this.hp=v; }
    public void setAktiv(boolean v) { this.aktiv=v; }
}