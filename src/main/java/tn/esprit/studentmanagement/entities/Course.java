package tn.esprit.studentmanagement.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCourse;
    private String name;
    private String code;           // exemple : CS101
    private int credit;            // nombre de crédits
    private String description;

    @OneToMany(mappedBy = "course")
    private List<Enrollment> enrollments;
    public Course() {}
    public Course(Long idCourse, String name, String code, int credit, String description) {
        this.idCourse = idCourse;
        this.name = name;
        this.code = code;
        this.credit = credit;
        this.description = description;
    }


    public Long getIdCourse() { return idCourse; }
    public void setIdCourse(Long idCourse) { this.idCourse = idCourse; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public int getCredit() { return credit; }
    public void setCredit(int credit) { this.credit = credit; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
