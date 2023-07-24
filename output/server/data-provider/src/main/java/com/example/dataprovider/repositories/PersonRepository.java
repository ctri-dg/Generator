package com.example.dataprovider.repositories;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dataprovider.models.*;

public interface PersonRepository extends JpaRepository<Person, Long> {
	List<Person> findByName(String name);
	List<Person> findByFamily(String family);

}
