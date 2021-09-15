package platform;

import javax.persistence.*;

@Entity
public class Snippet{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private int id;

    @Column
    private String code;

    @Column
    private String date;

    public Snippet() {

    }

    public Snippet(int id, String code, String date) {
        this.id = id;
        this.code = code;
        this.date = date;
    }

    public Snippet(String code, String date) {
        this.code = code;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
