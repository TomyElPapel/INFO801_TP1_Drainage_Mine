public class Negotiation {
    private AppelOffre appelOffre;
    private Data date;
    private float prix;

    // Constructor
    public Negotiation(AppelOffre appelOffre, Data date, float prix) {
        this.appelOffre = appelOffre;
        this.date = date;
        this.prix = prix;
    }

    // Getter and Setter for appelOffre
    public AppelOffre getAppelOffre() {
        return appelOffre;
    }

    public void setAppelOffre(AppelOffre appelOffre) {
        this.appelOffre = appelOffre;
    }

    // Getter and Setter for date
    public Data getDate() {
        return date;
    }

    public void setDate(Data date) {
        this.date = date;
    }

    // Getter and Setter for prix
    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }
}
