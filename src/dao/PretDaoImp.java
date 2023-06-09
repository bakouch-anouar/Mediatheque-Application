package dao;


import beans.Pret;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PretDaoImp implements PretDao{

    private String filePath;
    private Document document;
    private Element root;

    public PretDaoImp(String filePath){
        this.filePath = filePath;
        load();
    }

    private void load(){
        try {
            SAXBuilder sax = new SAXBuilder();
            document = sax.build(new File(filePath));
            root = document.getRootElement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void save(){
        try {
            XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
            out.output(document, new FileOutputStream(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ajouterPret(Pret pret) {

        Element e = new Element("pret");
        e.setAttribute("id", String.valueOf(pret.getIdAdherent()+"-"+pret.getIdOeuvre()));
        Element idOeuvre = new Element("idOeuvre");
        Element idAdherent = new Element("idAdherent");
        Element datePret = new Element("datePret");

        idOeuvre.addContent(String.valueOf(pret.getIdOeuvre()));
        idAdherent.addContent(String.valueOf(pret.getIdAdherent()));
        datePret.addContent(pret.getDatePret());

        e.addContent(idOeuvre);
        e.addContent(idAdherent);
        e.addContent(datePret);
        root.addContent(e);
        save();
    }

    @Override
    public void modifierPret(Pret pret) {

        List<Element> list = root.getChildren("pret");
        for (Element d : list) {
            if (Integer.parseInt(d.getChild("idOeuvre").getText()) == pret.getIdOeuvre()) {
                d.getChild("idAdherent").setText(String.valueOf(pret.getIdAdherent()));
                d.getChild("datePret").setText(pret.getDatePret());
                save();
            }
        }
    }

    @Override
    public void supprimerPret(Pret pret) {
            List<Element> list = root.getChildren("pret");
            for (Element d : list) {
                if (Integer.parseInt(d.getChild("idOeuvre").getText()) == pret.getIdOeuvre()) {
                    root.removeContent(d);
                    save();
                }
            }
    }

    @Override
    public Pret rechercherPret(int idOeuvre, int idAdherent) {
        List<Element> list = root.getChildren("pret");
        for (Element d : list) {
           String idPret = idOeuvre+"-"+idAdherent;
            if (d.getAttributeValue("id").equals(idPret)) {
                int idOeuvre1 = Integer.parseInt(d.getChild("idOeuvre").getText());
                int idAdherent1 = Integer.parseInt(d.getChild("idAdherent").getText());
                String datePret = String.valueOf(d.getChild("datePret").getText());
                return new Pret(idOeuvre1,idAdherent1,datePret);
            }
        }
        return null;
    }

    @Override
        public List<Pret> getPrets () {
            List<Pret> list = new ArrayList<Pret>();
            List<Element> elements = root.getChildren("pret");
            for (Element e : elements) {
                int idOeuvre = Integer.parseInt(e.getChild("idOeuvre").getText());
                int idAdherent = Integer.parseInt(e.getChild("idAdherent").getText());
                String datePret = e.getChild("datePret").getText();
                list.add(new Pret(idOeuvre,idAdherent,datePret));
            }
            return list;
        }
    }