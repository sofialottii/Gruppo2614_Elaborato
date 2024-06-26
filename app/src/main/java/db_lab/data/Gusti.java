package db_lab.data;

import java.sql.Connection;
import java.util.List;
import java.util.Objects;
import java.util.ArrayList;

public final class Gusti {

    public final String nomeGusto;
    public final String ricetta;
    public final float calorieTotali;
    public final String tipoGusto;
    public final String disponibilita;
    public final String periodoDisponibilita;

    public Gusti(String nomeGusto, String ricetta, float calorieTotali, String tipoGusto, 
                 String disponibilita, String periodoDisponibilita) {
        this.nomeGusto = nomeGusto;
        this.ricetta = ricetta;
        this.calorieTotali = calorieTotali;
        this.tipoGusto = tipoGusto;
        this.disponibilita = disponibilita == null ? "" : disponibilita;
        this.periodoDisponibilita = periodoDisponibilita == null ? "" : periodoDisponibilita;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other == null) {
            return false;
        } else if (other instanceof Gusti) {
            var g = (Gusti) other;
            return g.nomeGusto.equals(this.nomeGusto) &&
                   g.ricetta.equals(this.ricetta) &&
                   g.calorieTotali == this.calorieTotali &&
                   g.tipoGusto.equals(this.tipoGusto) &&
                   g.disponibilita.equals(this.disponibilita) &&
                   g.periodoDisponibilita.equals(this.periodoDisponibilita);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.nomeGusto, this.ricetta, this.calorieTotali, this.tipoGusto, 
                            this.disponibilita, this.periodoDisponibilita);
    }

    @Override
    public String toString() {
        return Printer.stringify(
            "Gusti",
            List.of(
                Printer.field("nomeGusto", this.nomeGusto),
                Printer.field("ricetta", this.ricetta),
                Printer.field("calorieTotali", this.calorieTotali),
                Printer.field("tipoGusto", this.tipoGusto),
                Printer.field("disponibilita", this.disponibilita),
                Printer.field("periodoDisponibilita", this.periodoDisponibilita)
            )
        );
    }

    public final class DAO {
        public static List<String> listAllGusti(Connection connection) {
            try (
                    var statement = connection.prepareStatement(Queries.VISUALIZZA_ALLGUSTI); //uso prepareStatement e non il metodo di Utility prepare
                var resultSet = statement.executeQuery();                           //perchè non ho dei parametri nella query
            )
            {
                List<String> gusti = new ArrayList<>();
                while (resultSet.next()) {
                    var nomeGusto = resultSet.getString("nomeGusto");
                    gusti.add(nomeGusto);
                }
                return gusti;

            } catch (Exception e) {
                throw new DAOException(e);
            }
        }
    }

    
}
