package db_lab.model;

import db_lab.data.Prodotti;
import db_lab.data.Turni;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface Model {

    public String findDipendente(String dipendenteCF, String password);

    public Map<String,Float> listGustiPopolari();

    public Map<String,Integer> listProdottiPopolari();

    public Map<String,Integer> listMesiPopolari();

    public Map<String, Integer> listFasceOrarie();

    public String calculateRicavoMensile();

    public List<Turni> listTurniDipendente(String dipendente);

    public List<String> listAllGusti();

    public void registraDose(String dipendente, String gusto, Float quantita);

    public void registraCliente(String dipendenteCF, String clienteCF, String nomeCliente, String cognomeCliente, String dataNascita,
    String email);

    public boolean clientePresente(String clienteCF);

    public boolean verificaSePuoiCancellareCliente(String clienteCF);

    public void cancellaCliente(String clienteCF);

    public List<Prodotti> listAllProdotti();

    public List<String> nuovoOrdine(String dipendente, String clienteCF, float nTessera);

    public void nuovaComposizione(String dipendente, String data, String orario, String codProdotto, Integer quantita);

    public void cambiaImportoTotale(String dipendente, String data, String orario, Float importoTotale);

    public float trovaUltimaTesseraCliente(String cliente);

    // Create a model that connects to a database using the given connection.
    //
    public static Model fromConnection(Connection connection) {
        return new DBModel(connection);
    }
}
