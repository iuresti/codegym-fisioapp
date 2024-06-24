package codegym.tequila.fisioapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "familiar_records", schema = "fisioapp")
public class FamiliarRecords {

    @Id
    private String id;
    @Column(name = "hereditary_diseases")
    private String hereditaryDiseases;
    @Column(name = "genetical_predispositions")
    private String geneticalPredispositions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
