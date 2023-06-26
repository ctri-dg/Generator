package com.example.dataprovider.controllers;


import com.example.dataprovider.exceptions.ResourceNotFoundException;
import com.example.dataprovider.models.Branch;
import com.example.dataprovider.repositories.Repository;
import com.example.dataprovider.requests.Request;
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
    private Repository branchRepository;

    @GetMapping
    private List<Branch> getAllBranches(){
        return branchRepository.findAll();
    }

    @PostMapping
    private Branch createBranch(@RequestBody Branch branch){
        return branchRepository.save(branch);
    }

    @PutMapping
    private Branch updateBranch(@RequestBody Branch branch){
        return branchRepository.save(branch);
    }

    @DeleteMapping("/{id}")
    private void deleteBranchById(@PathVariable long id){
        branchRepository.deleteById(id);
    }

    @GetMapping("/{id}")
    private Branch getBranchById(@PathVariable long id){
        Optional<Branch> branch  = branchRepository.findById(id);
        if(branch.isEmpty()){
            throw new ResourceNotFoundException("Invalid branch id");
        }
        return branch.get();
    }

    @PostMapping("/id")
    private List<Branch> getBranchesById(@RequestBody Request dataRequest){
        List<Branch> ret = new ArrayList<Branch>();
        Optional<Branch> branch = branchRepository.findById(Long.parseLong(dataRequest.getParameter()));
        branch.ifPresent(ret::add);
        return ret;
    }
    @PostMapping("/city")
    private List<Branch> getBranchesByCity(@RequestBody Request dataRequest){
        return branchRepository.findByCity(dataRequest.getParameter());
    }

    @PostMapping("/area")
    private List<Branch> getBranchesByArea(@RequestBody Request dataRequest){
        return branchRepository.findByArea(dataRequest.getParameter());
    }

    @PostMapping("/manager")
    private List<Branch> getBranchesByManager(@RequestBody Request dataRequest){
        return branchRepository.findByManager(dataRequest.getParameter());
    }

}
