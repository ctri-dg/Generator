package com.example.dataprovider;

import com.example.dataprovider.exceptions.ResourceNotFoundException;
import com.example.dataprovider.models.Branch;
import com.example.dataprovider.repositories.BranchRepository;
import com.example.dataprovider.requests.AreaRequest;
import com.example.dataprovider.requests.CityAreaRequest;
import com.example.dataprovider.requests.CityRequest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class DataProviderApplicationTests {

	private MockMvc mockMvc;
	@Mock
	private BranchRepository branchRepository;

	@InjectMocks
	private BranchController branchController;
	@BeforeEach
	void setUp()
	{
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(myController).build();
	}
	@Test
	void testGetAllBranches() {
		List<Branch> branchList = new ArrayList<>();
		Branch branch1 = new Branch("Hyderabad","Malkajgiri","Manjunathan",10);
		Branch branch2 = new Branch("Nizamabad","Namdevwada","Basava",10);
		branchList.add(branch2);
		branchList.add(branch1);
		when(branchRepository.findAll()).thenReturn(branchList);
		mockMvc.perform(get("/data-provider/v1/resource"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].city").value("Hyderabad"))
				.andExpect(jsonPath("$[1].city").value("Nizamabad"));
	}
	private String asJsonString(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}
	@Test
	public void testCreateBranch() throws Exception {
		Branch newBranch = new Branch("Hyderabad","Malkajgiri","Manjunathan",10);


		when(branchRepository.save(any(Branch.class))).thenReturn(newBranch);

		mockMvc.perform(post("/data-provider/v1/resource")
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(newBranch)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.city").value("Hyderabad"))
				.andExpect(jsonPath("$.area").value("Malkajgiri"));

		verify(branchRepository, times(1)).save(any(Branch.class));
		verifyNoMoreInteractions(branchRepository);
	}
	@Test
	public void testUpdateBranch() throws Exception {
		Branch branch1 = new Branch("Hyderabad","Malkajgiri","Manjunathan",10);
		Branch branch2 = new Branch("Nizamabad","Namdevwada","Basava",10);

		when(branchRepository.save(any(Branch.class))).thenReturn(branch1);
		when(branchRepository.findById(1L)).thenReturn(Optional.of(branch2));

		mockMvc.perform(put("/data-provider/v1/resource")
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(branch1)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.city").value("Hyderabad"))
				.andExpect(jsonPath("$.area").value("Malkajgiri));

						verify(branchRepository, times(1)).save(any(Branch.class));
		verify(branchRepository, times(1)).findById(1L);
		verifyNoMoreInteractions(branchRepository);
	}
	@Test
	public void testDeleteBranchById() throws Exception {
		long branchIdToDelete = 1L;

		mockMvc.perform(delete("/data-provider/v1/resource/{id}", branchIdToDelete))
				.andExpect(status().isOk());

		verify(branchRepository, times(1)).deleteById(branchIdToDelete);
		verifyNoMoreInteractions(branchRepository);
	}
	@Test
	public void testGetBranchById() throws Exception {
		long branchId = 1L;
		Branch branch1 = new Branch(branchId,"Hyderabad","Malkajgiri","Manjunathan",10);

		when(branchRepository.findById(branchId)).thenReturn(Optional.of(branch));

		mockMvc.perform(get("/data-provider/v1/resource/{id}", branchId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.city").value("Hyderabad"))
				.andExpect(jsonPath("$.area").value("Malkajgiri"))
				.andExpect(jsonPath("$.manager").value("Manjunathan"))
				.andExpect(jsonPath("$.employees").value(10));

		verify(branchRepository, times(1)).findById(branchId);
		verifyNoMoreInteractions(branchRepository);
	}
	@Test
	public void testGetBranchesByCity() throws Exception {
		String city = "Hyderabad";
		List<Branch> branches = new ArrayList<>();
		Branch branch1 = new Branch("Hyderabad","Malkajgiri","Manjunathan",10);
		Branch branch2 = new Branch("Hyderabad","Namdevwada","Basava",10);
		branches.add(branch1);
		branches.add(branch2);

		when(branchRepository.findByCity(city)).thenReturn(branches);

		CityRequest cityRequest = new CityRequest();
		cityRequest.setCity(city);

		mockMvc.perform(post("/data-provider/v1/resource/city")
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(cityRequest)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].city").value("Hyderabad"))
				.andExpect(jsonPath("$[1].area").value("Namdevwada"));

		verify(branchRepository, times(1)).findByCity(city);
		verifyNoMoreInteractions(branchRepository);
	}
	@Test
	public void testGetBranchesByArea() throws Exception {
		String area = "Malkajgiri";
		List<Branch> branches = new ArrayList<>();
		Branch branch1 = new Branch("Hyderabad","Malkajgiri","Manjunathan",10);
		Branch branch2 = new Branch("Hyderabad","Namdevwada","Basava",10);
		branches.add(branch1);
		branches.add(branch2);

		when(branchRepository.findByArea(area)).thenReturn(branches);

		AreaRequest areaRequest = new AreaRequest();
		areaRequest.setArea(area);

		mockMvc.perform(post("/data-provider/v1/resource/area")
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(areaRequest)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].city").value("Hyderabad"))
				.andExpect(jsonPath("$[1].city").value("Hyderabad"));

		verify(branchRepository, times(1)).findByArea(area);
		verifyNoMoreInteractions(branchRepository);
	}
	@Test
	public void testGetBranchesByCityArea() throws Exception {
		String city = "Hyderabad";
		String area = "Malkajgiri";
		List<Branch> branches = new ArrayList<>();
		Branch branch1 = new Branch("Hyderabad","Malkajgiri","Manjunathan",10);
		Branch branch2 = new Branch("Hyderabad","Namdevwada","Basava",10);
		branches.add(branch1);
		branches.add(branch2);

		when(branchRepository.findByCityAndArea(city, area)).thenReturn(branches);

		CityAreaRequest cityAreaRequest = new CityAreaRequest();
		cityAreaRequest.setCity(city);
		cityAreaRequest.setArea(area);

		mockMvc.perform(post("/data-provider/v1/resource/cityarea")
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(cityAreaRequest)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].city").value("Hyderabad"))
				.andExpect(jsonPath("$[1].city").value("Hyderabad"));

		verify(branchRepository, times(1)).findByCityAndArea(city, area);
		verifyNoMoreInteractions(branchRepository);
	}


}

