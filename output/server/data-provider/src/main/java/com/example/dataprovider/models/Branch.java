package com.example.dataprovider.models;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@Table(name = "dataprovider.branch")
public class Branch {
	@Id
	@GeneratedValue
	private long id;
	private String city;
	private String area;
	private String manager;
	private int employees;

}
