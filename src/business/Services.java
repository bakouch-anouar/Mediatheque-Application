package business;

import beans.Adherent;
import beans.Auteur;
import beans.Oeuvre;
import beans.Pret;

import java.util.List;

public interface Services {
    public void ajouterAdherent(Adherent adherent);
    public void modifierAdherent(Adherent adherent);
    public void supprimerAdherent(Adherent adherent);
    public Adherent getAdherent(int id);

    public List<Adherent> getAdherents();

    public Adherent getAdherent(String nom);
    public void ajouterAuteur(Auteur auteur);
    public void supprimerAuteur(Auteur auteur);
    public void modifierAuteur(Auteur auteur);
    public Auteur rechercherAuteur(int id);
    public Auteur rechercherAuteur(String nom);
    public List<Auteur> getAuteurs();

    public void ajouterOeuvre(Oeuvre oeuvre);
    public void modifierOeuvre(Oeuvre oeuvre);
    public void supprimerOeuvre(Oeuvre oeuvre);
    public Oeuvre getOeuvre(int id);
    public List<Oeuvre> getOeuvres();
    public List<Oeuvre> getOeuvresParAuteur(int idAuteur);

    public void ajouterPret(Pret pret);
    public void modifierPret(Pret pret);
    public void supprimerPret(Pret pret);
    public Pret rechercherPret(int idOeuvre, int idAdherent);
    public List<Pret> getPrets();
}
