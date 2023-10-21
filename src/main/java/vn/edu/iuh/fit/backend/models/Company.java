package vn.edu.iuh.fit.backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "company")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "com_id")
    private long id;

    @Column(name = "comp_name", nullable = false)
    private String name;

    @OneToOne
    @JoinColumn(name = "address", nullable = false)
    private Address address;

    @Column(name = "web_url")
    private String webURL;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "about", length = 2000)
    private String about;

    @OneToMany(mappedBy = "company",fetch = FetchType.LAZY)
    private List<Job> jobs;

    public Company(String name, Address address, String webURL, String phone, String email, String about) {
        this.name = name;
        this.address = address;
        this.webURL = webURL;
        this.phone = phone;
        this.email = email;
        this.about = about;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setWebURL(String webURL) {
        this.webURL = webURL;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }
}
