package org.mf.langchain.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_AIRLINE")
public class Airline {

    @Id
    private int id;
    private String name;
    private String iata;
    private String icao;

    public Airline(int id, String name, String iata, String icao) {
        this.id = id;
        this.name = name;
        this.iata = iata;
        this.icao = icao;
    }

    public Airline() {
    }

    public static AirlineBuilder builder() {
        return new AirlineBuilder();
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getIata() {
        return this.iata;
    }

    public String getIcao() {
        return this.icao;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public void setIcao(String icao) {
        this.icao = icao;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Airline)) return false;
        final Airline other = (Airline) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getId() != other.getId()) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$iata = this.getIata();
        final Object other$iata = other.getIata();
        if (this$iata == null ? other$iata != null : !this$iata.equals(other$iata)) return false;
        final Object this$icao = this.getIcao();
        final Object other$icao = other.getIcao();
        if (this$icao == null ? other$icao != null : !this$icao.equals(other$icao)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Airline;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getId();
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $iata = this.getIata();
        result = result * PRIME + ($iata == null ? 43 : $iata.hashCode());
        final Object $icao = this.getIcao();
        result = result * PRIME + ($icao == null ? 43 : $icao.hashCode());
        return result;
    }

    public String toString() {
        return "Airline(id=" + this.getId() + ", name=" + this.getName() + ", iata=" + this.getIata() + ", icao=" + this.getIcao() + ")";
    }

    public static class AirlineBuilder {
        private int id;
        private String name;
        private String iata;
        private String icao;

        AirlineBuilder() {
        }

        public AirlineBuilder id(int id) {
            this.id = id;
            return this;
        }

        public AirlineBuilder name(String name) {
            this.name = name;
            return this;
        }

        public AirlineBuilder iata(String iata) {
            this.iata = iata;
            return this;
        }

        public AirlineBuilder icao(String icao) {
            this.icao = icao;
            return this;
        }

        public Airline build() {
            return new Airline(this.id, this.name, this.iata, this.icao);
        }

        public String toString() {
            return "Airline.AirlineBuilder(id=" + this.id + ", name=" + this.name + ", iata=" + this.iata + ", icao=" + this.icao + ")";
        }
    }
}
