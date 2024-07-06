package codegym.tequila.fisioapp.dto;


import java.util.Objects;

public class FamiliarRecordsDto {

    private String hereditaryDiseases;

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

    @Override
    public String toString() {
        return "FamiliarRecordsDto{" +
                "hereditaryDiseases='" + hereditaryDiseases + '\'' +
                ", geneticalPredispositions='" + geneticalPredispositions + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FamiliarRecordsDto that = (FamiliarRecordsDto) o;
        return Objects.equals(hereditaryDiseases, that.hereditaryDiseases) && Objects.equals(geneticalPredispositions, that.geneticalPredispositions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hereditaryDiseases, geneticalPredispositions);
    }
}
