package com.example.dataprovider.repositories;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dataprovider.models.*;

public interface BranchRepository extends JpaRepository<Branch, Long> {
	List<Branch> findByCity(String city);
	List<Branch> findByArea(String area);
	List<Branch> findByCityAndArea(String city, String area);

}
