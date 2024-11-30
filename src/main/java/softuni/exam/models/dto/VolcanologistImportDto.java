package softuni.exam.models.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "volcanologist")
public class VolcanologistImportDto {

    @XmlElement(name = "first_name")
    @Length(min = 2, max = 30)
    private String firstName;
    @XmlElement(name = "last_name")
    @Length(min = 2, max = 30)
    private String lastName;
    @XmlElement
    @Min(value = 0)
    private double salary;
    @XmlElement
    @Min(value = 18)
    @Max(value = 80)
    private int age;
    @XmlElement(name = "exploring_from")
    private String exploringFrom;
    @XmlElement(name = "exploring_volcano_id")
    private int exploringVolcanoId;


    public VolcanologistImportDto() {}
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getExploringFrom() {
        return exploringFrom;
    }
    public void setExploringFrom(String exploringFrom) {
        this.exploringFrom = exploringFrom;
    }
    public int getExploringVolcanoId() {
        return exploringVolcanoId;

    }
    public void setExploringVolcanoId(int exploringVolcanoId) {
        this.exploringVolcanoId = exploringVolcanoId;

    }

}


/*/
<volcanologist>
        <first_name>John</first_name>
        <last_name>Doe</last_name>
        <salary>5000.00</salary>
        <age>55</age>
        <exploring_from>1987-01-15</exploring_from>
        <exploring_volcano_id>11</exploring_volcano_id>
    </volcanologist>
 */
