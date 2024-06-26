package db_lab.data;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public final class Ordini {

    public final String CF;
    public final String data;
    public final String orario;
    public final float importoTotale;
    public final String con_CF;
    public final Optional<Float> con_numeroTessera; 

    public Ordini(String CF, String data, String orario, float importoTotale, 
                  String con_CF, Optional<Float> con_numeroTessera) {
        this.CF = CF;
        this.data = data;
        this.orario = orario;
        this.importoTotale = importoTotale;
        this.con_CF = con_CF == null ? "" : con_CF;
        this.con_numeroTessera = con_numeroTessera == null ? Optional.empty() : con_numeroTessera;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other == null) {
            return false;
        } else if (other instanceof Ordini) {
            var o = (Ordini) other;
            return o.CF.equals(this.CF) &&
                   o.data.equals(this.data) &&
                   o.orario.equals(this.orario) &&
                   o.importoTotale == this.importoTotale &&
                   o.con_CF.equals(this.con_CF) &&
                   o.con_numeroTessera.equals(this.con_numeroTessera);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.CF, this.data, this.orario, this.importoTotale, 
                            this.con_CF, this.con_numeroTessera);
    }

    @Override
    public String toString() {
        return Printer.stringify(
            "Ordini",
            List.of(
                Printer.field("CF", this.CF),
                Printer.field("data", this.data),
                Printer.field("orario", this.orario),
                Printer.field("importoTotale", this.importoTotale),
                Printer.field("con_CF", this.con_CF),
                Printer.field("con_numeroTessera", this.con_numeroTessera.orElse(null))
            )
        );
    }

    public final class DAO {
        public static final Map<String, Integer> listMesiPopolari(Connection connection) {
            try (
                var statement = connection.prepareStatement(Queries.MESE_POPOLARE); //uso prepareStatement e non il metodo di Utility prepare
                var resultSet = statement.executeQuery();                           //perchè non ho dei parametri nella query
                
            ) {
                var mesiPopolari = new HashMap<String, Integer>();
                while (resultSet.next()) {
                    var mese = resultSet.getString("mese");
                    var numeroOrdini = resultSet.getInt("numeroOrdini");
                    mesiPopolari.put(mese,numeroOrdini);
                }
                return mesiPopolari;
            } catch(Exception e) {
                throw new DAOException(e);
            }
        
        }

        public static final String calculateRicavoMensile(Connection connection) {
            try (
                var statement = connection.prepareStatement(Queries.RICAVO_MENSILE); //uso prepareStatement e non il metodo di Utility prepare
                var resultSet = statement.executeQuery();                           //perchè non ho dei parametri nella query
                
            ) {
                String ricavoMensile = "Ancora nessun ordine fatto in questo mese";
                if (resultSet.next()) {
                    var mese = resultSet.getString("mese");
                    var ricavo = resultSet.getFloat("ricavoMensile");
                    ricavoMensile = mese + " " + ricavo;
                }
                return ricavoMensile;
            } catch(Exception e) {
                throw new DAOException(e);
            }
        
        }

        public static List<String> nuovoOrdine(Connection connection, String dipendenteCF, String clienteCF, float nTessera) {
            LocalDate currentDate = LocalDate.now();
            String formattedDate = currentDate.toString(); // Già nel formato aaaa-mm-gg
            LocalTime currentTime = LocalTime.now();
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String formattedTime = currentTime.format(timeFormatter); 
            try (
                    var statement = clienteCF.isEmpty() ?
                    DAOUtils.prepare(connection, Queries.NUOVO_ORDINE_SENZA_TESSERA, dipendenteCF, 
                        formattedDate, formattedTime, null, null) :
                            DAOUtils.prepare(connection, Queries.NUOVO_ORDINE_SENZA_TESSERA, dipendenteCF,
                                    formattedDate, formattedTime, clienteCF, nTessera);

                    ) {
                    statement.executeUpdate();
                    statement.close();
                    return List.of(formattedDate, formattedTime);
                
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        public static void cambiaImportoTotale(Connection connection, String dipendenteCF, String data,
        String orario, Float importoTotale) {
            try (
                    var statement = DAOUtils.prepare(connection, Queries.CAMBIA_IMPORTO_TOTALE, importoTotale, dipendenteCF,
                            data, orario);) {

                statement.executeUpdate();
                statement.close();

            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        public static final Map<String,Integer> listFasceOrarie(Connection connection) {
            try (
                var statement = connection.prepareStatement(Queries.FASCIA_AFFOLLATA); //uso prepareStatement e non il metodo di Utility prepare
                var resultSet = statement.executeQuery();                           //perchè non ho dei parametri nella query
                
            ) {
                var fasceOrarie = new HashMap<String,Integer>();
                while (resultSet.next()) {
                    var fasciaOraria = resultSet.getString("fasciaOraria");
                    var numeroOrdini= resultSet.getInt("numeroOrdini");
                    
                    fasceOrarie.put(fasciaOraria,numeroOrdini);
                }
                return fasceOrarie;
            } catch(Exception e) {
                throw new DAOException(e);
            }
        
        }
    }

    
}
