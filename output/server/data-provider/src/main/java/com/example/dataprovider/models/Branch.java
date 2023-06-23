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
	private string city;
	private string area;
	private string manager;
	private int employees;

}
