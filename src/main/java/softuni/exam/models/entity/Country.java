package softuni.exam.models.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "countries")
public class Country extends BaseEntity{

    @OneToMany(mappedBy = "country", fetch = FetchType.EAGER)
    List<Volcano> volcanoes;

    @Column(nullable = false, unique = true)
    private String name;
    @Column
    private String capital;


    public Country() {}


    public List<Volcano> getVolcanoes() {
        return volcanoes;
    }

    public void setVolcanoes(List<Volcano> volcanoes) {
        this.volcanoes = volcanoes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }
}
