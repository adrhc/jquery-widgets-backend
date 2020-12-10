package ro.go.adrhc.datarest.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Person {
	@Id
	@GeneratedValue
	private Integer id;
	private String firstName;
	private String lastName;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("name")
	private List<Cat> cats;
}
