package dao;

import beans.Auteur;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AuteurDaoImp implements AuteurDao{

    private Document doc;
    private Element racine;
    private String nomFichier;

    public AuteurDaoImp(String nomFichier) {
        this.nomFichier = nomFichier;
        load();
    }

    private void load(){
        try {
            SAXBuilder sax = new SAXBuilder();
            doc = sax.build(new File(nomFichier));
            racine = doc.getRootElement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void save(){
        try {
            XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
            out.output(doc, new FileOutputStream(nomFichier));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ajouterAuteur(Auteur auteur) {

        Element e = new Element("auteur");
        Element nom = new Element("nom");
        Element prenom = new Element("prenom");
        Element dateNaissance = new Element("dateNaissance");

        nom.addContent(auteur.getNom());
        prenom.addContent(auteur.getPrenom());
        dateNaissance.addContent(auteur.getDateNaissance());

        e.addContent(nom);
        e.addContent(prenom);
        e.addContent(dateNaissance);

        racine.addContent(e);
        save();
    }

    @Override
    public void supprimerAuteur(Auteur auteur) {}

    @Override
    public void modifierAuteur(Auteur auteur) {}

    @Override
    public Auteur rechercherAuteur(int id) {
        List<Element> list = racine.getChildren("auteur");
        for (Element d : list) {
            if (Integer.parseInt(d.getAttributeValue("id")) == id ) {
                return new Auteur(id,d.getChildText("nom"), d.getChildText("prenom"), d.getChildText("dateNaissance"));
            }
        }
        return null;
    }

    @Override
    public List<Auteur> getAuteurs() {
        List<Auteur> auteurs = new ArrayList<Auteur>();
        List<Element> list = racine.getChildren("auteur");
        for (Element d : list) {
            auteurs.add(new Auteur(Integer.parseInt(d.getAttributeValue("id")),d.getChildText("nom"), d.getChildText("prenom"), d.getChildText("dateNaissance")));
        }
        return auteurs;
    }
}
