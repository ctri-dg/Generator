package com.example.dataprovider.controllers;
import com.example.dataprovider.exceptions.ResourceNotFoundException;
import com.example.dataprovider.models.*;
import com.example.dataprovider.repositories.*;
import com.example.dataprovider.requests.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/data-provider/v1/resource")
public class PersonController {

	@Autowired
	private PersonRepository resourceRepository;

	@GetMapping
		private List<Person> getAllBranches(){
			return resourceRepository.findAll();
}

	@PostMapping
		private Person createPerson(@RequestBody Person person){
			return resourceRepository.save(person);
}

	@PutMapping
		private Person updatePerson(@RequestBody Person person){
			return resourceRepository.save(person);
}

	@DeleteMapping("/{id}")
		private void deletePersonByid(@PathVariable long id){
			resourceRepository.deleteById(id);
}

	@GetMapping("/{id}")
		private Person getPersonById(@PathVariable long id){
			Optional<Person> person = resourceRepository.findById(id);

			if(person.isEmpty()){
				throw new ResourceNotFoundException("Invalid Branch Id");
}
			return person.get();
}
	@PostMapping("/name")
		private List<Person> getPersonesByName(@RequestBody NameRequest nameRequest){
			return resourceRepository.findByName(nameRequest.getName());
}
	@PostMapping("/family")
		private List<Person> getPersonesByFamily(@RequestBody FamilyRequest familyRequest){
			return resourceRepository.findByFamily(familyRequest.getFamily());
}
}
