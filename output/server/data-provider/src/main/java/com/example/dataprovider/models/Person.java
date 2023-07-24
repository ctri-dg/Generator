package com.example.dataprovider.models;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dataprovider.person")
public class Person {
	@Id
	@GeneratedValue
	private long id;
	private String name;
	private int age;
	private String family;

}
