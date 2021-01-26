package ro.go.adrhc.datarest.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
@Getter
@Setter
@ToString
public class BaseEntity implements Serializable {
	@Id
	@GeneratedValue
	protected Integer id;
}
