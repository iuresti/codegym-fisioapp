package codegym.tequila.fisioapp.model;

import jakarta.persistence.*;

@Embeddable
public class FamiliarRecords {

    @Column(name = "hereditary_diseases")
    private String hereditaryDiseases;
    @Column(name = "genetical_predispositions")
    private String geneticalPredispositions;

    public String getHereditaryDiseases() {
        return hereditaryDiseases;
    }

    public void setHereditaryDiseases(String hereditaryDiseases) {
        this.hereditaryDiseases = hereditaryDiseases;
    }

    public String getGeneticalPredispositions() {
        return geneticalPredispositions;
    }

    public void setGeneticalPredispositions(String geneticalPredispositions) {
        this.geneticalPredispositions = geneticalPredispositions;
    }
}
