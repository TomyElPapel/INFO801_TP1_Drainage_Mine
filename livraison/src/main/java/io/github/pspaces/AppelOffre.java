public class AppelOffre {
    private String nom;
    private String[] requirements;
    private float cout;
    private Date date;
    private int quantitée;

    // Constructor
    public AppelOffre(String nom, String[] requirements, float cout, Date date, int quantitée) {
        this.nom = nom;
        this.requirements = requirements;
        this.cout = cout;
        this.date = date;
        this.quantitée = quantitée;
    }

    // Getters and Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String[] getRequirements() {
        return requirements;
    }

    public void setRequirements(String[] requirements) {
        this.requirements = requirements;
    }

    public float getCout() {
        return cout;
    }

    public void setCout(float cout) {
        this.cout = cout;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getQuantitée() {
        return quantitée;
    }

    public void setQuantitée(int quantitée) {
        this.quantitée = quantitée;
    }
}
