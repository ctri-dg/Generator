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
public class %sController {
