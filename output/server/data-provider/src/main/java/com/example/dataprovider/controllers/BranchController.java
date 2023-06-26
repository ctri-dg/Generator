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
@RequestMapping("/data-provider/v1/branch")
@CrossOrigin
public class BranchController {

	@Autowired
	private BranchRepository resourceRepository;

	@GetMapping
		private List<Branch> getAllBranches(){
			return resourceRepository.findAll();
}

	@PostMapping
		private Branch createBranch(@RequestBody Branch branch){
			return resourceRepository.save(branch);
}

	@PutMapping
		private Branch updateBranch(@RequestBody Branch branch){
			return resourceRepository.save(branch);
}

	@DeleteMapping("/{id}")
		private void deleteBranchByid(@PathVariable long id){
			resourceRepository.deleteById(id);
}

	@GetMapping("/{id}")
		private Branch getBranchById(@PathVariable long id){
			Optional<Branch> branch = resourceRepository.findById(id);

			if(branch.isEmpty()){
				throw new ResourceNotFoundException("Invalid Branch Id");
}
			return branch.get();
}
	@PostMapping("/{city}")
		private List<Branch> getBranchesByCity(@RequestBody CityRequest cityRequest){
			return resourceRepository.findByCity(cityRequest.getCity());
}
	@PostMapping("/{area}")
		private List<Branch> getBranchesByArea(@RequestBody AreaRequest areaRequest){
			return resourceRepository.findByArea(areaRequest.getArea());
}
	@PostMapping("/{cityarea}")
		private List<Branch> getBranchesByCityArea(@RequestBody CityAreaRequest cityareaRequest){
			return resourceRepository.findByCityAndArea(cityareaRequest.getCity() ,cityareaRequest.getArea());
}
}
