package com.transilvania.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.transilvania.dao.CategoryDao;
import com.transilvania.dao.ParkingAdminDao;
import com.transilvania.dao.ParkingDao;
import com.transilvania.dao.ReservationDao;
import com.transilvania.dao.UserDao;
import com.transilvania.dto.AddUserDTO;
import com.transilvania.dto.CategoryDTO;
import com.transilvania.dto.LoginDTO;
import com.transilvania.dto.ParkingAdminDTO;
import com.transilvania.dto.ParkingDTO;
import com.transilvania.dto.ReserveDTO;
import com.transilvania.entities.Category;
import com.transilvania.entities.Parking;
import com.transilvania.entities.ParkingAdmin;
import com.transilvania.entities.Reservation;
import com.transilvania.entities.User;


@Controller
public class HomeController {
	@Autowired
	ParkingAdminDao parkingAdminDAO;
	
	@Autowired
	ParkingDao parkingDAO;
	
	@Autowired
	CategoryDao categoryDAO;
	
	@Autowired
	ReservationDao reservartionDao;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "home";
	}
	
	
	/*
	 * Return a json with all parkings
	 */
	@RequestMapping(value="/parkings",method=RequestMethod.GET)
	public @ResponseBody List<ParkingDTO> getParkings(){
		ParkingDao parkingsDao = new ParkingDao();
		
		//ArrayList for getting all the parkings
		ArrayList<Parking> parkings;
		parkings=(ArrayList<Parking>) parkingsDao.getAllParks();
		
		//ArrayList with dto for parkings
		ArrayList<ParkingDTO> parkingsDTO = new ArrayList<ParkingDTO>();
		
		//Transfering parkings to Dto
		for(int i=0;i<parkings.size();i++)
		{
			//Temporal DTO object for storing data into the list
			ParkingDTO temporalParkingDto = new ParkingDTO();
			
			List<CategoryDTO> categoryDTO =new ArrayList<CategoryDTO>();
			for ( Category c : parkings.get(i).getCategories()) {
				CategoryDTO catDTO=new CategoryDTO();
				catDTO.setBusy_places(c.getBusy_places());
				catDTO.setCovered(c.getCovered());
				catDTO.setName(c.getName());
				catDTO.setId_category(c.getId_category());
				catDTO.setMax_height(c.getMax_height());
				catDTO.setPrice(c.getPrice());
				catDTO.setTotal_places(c.getTotal_places());
				categoryDTO.add(catDTO);
			}
			temporalParkingDto.setCategories(categoryDTO);
			temporalParkingDto.setDescription(parkings.get(i).getDescription());
			temporalParkingDto.setId_parking(i);
			temporalParkingDto.setLat(parkings.get(i).getLat());
			temporalParkingDto.setLng(parkings.get(i).getLon());
			temporalParkingDto.setName(parkings.get(i).getName());
			temporalParkingDto.setImage(parkings.get(i).getImage());
			temporalParkingDto.setCreditCard(parkings.get(i).getCredit_card());
			//temporalParkingDto.setParkingAdmin(parkings.get(i).getParkingAdmin());   // ERROR 
			//System.out.println("pk:"+ parkings.get(i).getParkingAdmin());
			int totalPlaces=0;
			int busyPlaces=0;
			for(CategoryDTO c : temporalParkingDto.getCategories())
			{
				totalPlaces+= c.getTotal_places();
				busyPlaces+=c.getBusy_places();

			}
			temporalParkingDto.setTotalPlaces(totalPlaces);
			temporalParkingDto.setBusyPlaces(busyPlaces);
			
			
			parkingsDTO.add(temporalParkingDto);
		}
		
		return  parkingsDTO;

	}
	
	
	/*
	 * Function for adding new user to the database
	 */
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	@ResponseBody
	public Integer register(@RequestParam(name="userData")String userData)
	{
		Integer check=1; // for checking status. 1-> user saved, 2-> error, 3->user already registered
		UserDao userdao = new UserDao();
		User user = new User();
		AddUserDTO userDTO = new AddUserDTO();
		
		ObjectMapper om=new ObjectMapper();
		//saving the user
		try
		{
			userDTO=om.readValue(userData, AddUserDTO.class);		
			//check if the user is already registered
			for(User u : userdao.getAllUsers())
			{
				if(u.getEmail().compareTo(userDTO.getMail())==0)
				{
					
					check=3;
					break;
				}
			}
			if(check==1){
			user.setEmail(userDTO.getMail());
			user.setName(userDTO.getName());
			user.setPassword(userDTO.getPassword());
			user.setSurname(userDTO.getSurname());		
			userdao.persist(user);
			}
		}
		catch(Exception e)
		{
			
			check=2;
		}	
		return check;
	}
	
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	@ResponseBody
	public User login(@RequestParam(name="loginData")String loginData)
	{
		
		UserDao userDao = new UserDao();
		LoginDTO loginDTO = new LoginDTO();
		ObjectMapper om=new ObjectMapper();
		
	try{
			loginDTO=om.readValue(loginData, LoginDTO.class);			
			//Checking the user in database
			for(User u : userDao.getAllUsers())
			{
				if(u.getEmail().compareTo(loginDTO.getMail())==0)
				{
					if(u.getPassword().compareTo(loginDTO.getPassword())==0)
					{
						
						u.setReservations(null);
						return u;
					}
				}
			}	
		}
		catch(Exception e)
		{
			
		}
	return null;
	}
	

	@RequestMapping(value="createParking",method=RequestMethod.GET)
	@ResponseBody
	public Boolean createParking(@RequestParam(name="newParking")String newParking){
		
		
		ParkingDTO parkingDTO;
		ObjectMapper om=new ObjectMapper();
		try {
			parkingDTO=om.readValue(newParking, ParkingDTO.class);
			
			Parking parking=new Parking();
			parking.setArrive_time(parkingDTO.getArriveTime());
			parking.setCredit_card(parkingDTO.getCreditCard());
			parking.setImage(parkingDTO.getImage());
			parking.setLat(parkingDTO.getLat());
			parking.setLon(parkingDTO.getLng());
			parking.setName(parkingDTO.getName());
			
			ParkingAdmin parkingAdmin=parkingAdminDAO.getParkingById(1);
			parking.setParkingAdmin(parkingAdmin);
			parking.setDescription(parkingDTO.getDescription());

			parkingDAO.persist(parking);
			
			
			for (CategoryDTO c : parkingDTO.getCategories()) {
				Category category=new Category();
				category.setBusy_places(0);
				category.setCovered(c.getCovered());
				category.setMax_height(c.getMax_height());
				category.setPrice(c.getPrice());
				category.setTotal_places(c.getTotal_places());
				category.setPark(parking);
				category.setName(c.getName());
				categoryDAO.persist(category);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	
	@RequestMapping(value = "/reserveCheck", method = RequestMethod.GET)
	@ResponseBody
	public Integer reserveCheck(@RequestParam(name="x")Integer userId)
	{
		int check;
		UserDao userDao = new UserDao();
		if(userDao.getUserById(userId).getReservations() == null || userDao.getUserById(userId).getReservations().isEmpty())
		{
			System.out.println("Nu a mai facut rez");
		check=1;			
		}
	else
	{
		System.out.println("A mai facut rez");
		check=0;
	}	
		

		
	
		return check;
	}
	
	@RequestMapping(value = "/cancelReserve", method = RequestMethod.GET)
	@ResponseBody
	public Integer cancelReserve(@RequestParam(name="x")Integer userId)
	{
		ReservationDao r = new ReservationDao();
		CategoryDao categoryDao = new CategoryDao();
		UserDao userDao = new UserDao();
		CategoryDTO categoryDTO = new CategoryDTO();

		try{
		categoryDao.cancelReserve(userDao.getUserById(userId).getReservations().get(0).getCategory().getId_category());
		System.out.println("util"+userDao.getUserById(userId).getReservations().get(0).getId_reservation());
		reservartionDao.delete(userDao.getUserById(userId).getReservations().get(0).getId_reservation());	

		}
		catch(Exception e)
		{
			
		}
		return 1;
	}
	

	
	/*
	 * Funtion for reservation
	 * selectedCategory -> category where the user want to reserve
	 *          x       -> Id(pk) of the user that wants to reserve
	 */
	@RequestMapping(value = "/reserve", method = RequestMethod.GET)
	@ResponseBody
	public ReserveDTO reserve(@RequestParam(name="selectedCategory")String category,@RequestParam(name="x")Integer userId)
	{
		System.out.println(category);
		System.out.println(userId);
		
		ReserveDTO reserveDTO = new ReserveDTO();
		String reservationCode="";   //later generate it...
		CategoryDao categoryDao = new CategoryDao();
		UserDao userDao = new UserDao();
		
		Reservation reservation = new Reservation();
		CategoryDTO categoryDTO = new CategoryDTO();
		ObjectMapper om=new ObjectMapper();
		Category category1 = new Category();
		ParkingDao parkDao = new ParkingDao();
		

		///create reservation, update category
		try {
			categoryDTO=om.readValue(category, CategoryDTO.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(categoryDTO.getBusy_places() >= categoryDTO.getTotal_places() )
		{
			reserveDTO.setUniqueCode("");
		}
		else{
		
		
		//Making the reservation in database
		reservation.setReservation_date(new Timestamp(System.currentTimeMillis()));
		reservation.setUser(userDao.getUserById(userId));
		reservation.setCategory(categoryDao.getCategoryById(categoryDTO.getId_category()));
		reservationCode=reservation.getReservation_date().hashCode()+Integer.toString(Math.abs(reservation.getUser().getEmail().hashCode()));
		reservationCode=reservationCode.substring(1, reservationCode.length());
		reservation.setReservation_code(reservationCode);

		reservartionDao.persist(reservation);
		
		
		categoryDao.updateBusyPlaces(categoryDTO.getId_category());
		
		//Building data that returns to frontend
		reserveDTO.setUniqueCode(reservationCode);
		reserveDTO.setArriveTime(categoryDao.getCategoryById(categoryDTO.getId_category()).getPark().getArrive_time());
		
		}
		return reserveDTO;

	}
	
	@RequestMapping(value = "/getParkingsForAdmin", method = RequestMethod.GET)
	@ResponseBody
	public List<ParkingDTO> getParkingsForAdmin(@RequestParam("adminId")Integer adminId){
		
		List<ParkingDTO> response=new ArrayList<ParkingDTO>();

		ParkingAdmin admin;
		try {
			admin=parkingAdminDAO.getParkingById(1);
			System.out.println(admin.getParkings());
			for (Parking p : admin.getParkings()) {
				ParkingDTO parkingDTO=new ParkingDTO();
				parkingDTO.setArriveTime(p.getArrive_time());
				parkingDTO.setCreditCard(p.getCredit_card());
				parkingDTO.setDescription(p.getDescription());
				parkingDTO.setId_parking(p.getId_parking());
				parkingDTO.setImage(p.getImage());
				parkingDTO.setLat(p.getLat());
				parkingDTO.setLng(p.getLon());
				parkingDTO.setName(p.getName());
				List<CategoryDTO> categoryDTO=new ArrayList<CategoryDTO>();
				Integer totalPlaces=0;
				Integer ocupiedPlaces=0;
				Integer totalReservations=0;
				for (Category c : p.getCategories()) {
					CategoryDTO cat=new CategoryDTO();
					cat.setBusy_places(c.getBusy_places());
					cat.setCovered(c.getCovered());
					cat.setName(c.getName());
					cat.setId_category(c.getId_category());
					cat.setMax_height(c.getMax_height());
					cat.setPrice(c.getPrice());
					cat.setTotal_places(c.getTotal_places());
					ocupiedPlaces+=c.getBusy_places();
					totalPlaces+=c.getTotal_places();
					totalReservations+=c.getReservations().size();
					cat.setReservations(c.getReservations().size());
					categoryDTO.add(cat);
					//add rezervari
				}
				parkingDTO.setCategories(categoryDTO);
				parkingDTO.setBusyPlaces(ocupiedPlaces);
				parkingDTO.setTotalPlaces(totalPlaces);
				parkingDTO.setTotalReservations(totalReservations);
				response.add(parkingDTO);
				//add total rezervari
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return response;
	}
	@RequestMapping(value = "/updateCategory", method = RequestMethod.GET)
	@ResponseBody
	public boolean updateCategory(@RequestParam(name="category")String category){
		
		System.out.println(category);
		
		ObjectMapper om=new ObjectMapper();
		CategoryDTO catDTO;
		Category cat=new Category();
		try {
			catDTO=om.readValue(category,CategoryDTO.class);
			cat.setCovered(catDTO.getCovered());
			cat.setId_category(catDTO.getId_category());
			cat.setMax_height(catDTO.getMax_height());
			cat.setName(catDTO.getName());
			cat.setPrice(catDTO.getPrice());
			cat.setTotal_places(catDTO.getTotal_places());
			categoryDAO.adminUpdateCategory(cat);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	@RequestMapping(value = "/deleteCategory", method = RequestMethod.GET)
	@ResponseBody
	public Boolean deleteCategory(@RequestParam(name="categoryId")Integer id){
		
		try {
			categoryDAO.delete(id);;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	@RequestMapping(value = "/adminLogin", method = RequestMethod.GET)
	@ResponseBody
	public ParkingAdminDTO adminLogin(@RequestParam(name="email")String email,@RequestParam(name="password")String password){
			System.out.println(email);
		ParkingAdmin admin; 
		admin=parkingAdminDAO.getParkingAdminByEmail(email);
		if(admin==null){
			System.out.println("0");
			return null;
		
		}
		else {
			if(admin.getEmail().equals(password)){
				System.out.println(1);
				ParkingAdminDTO response=new ParkingAdminDTO();
				response.setEmail(email);
				response.setName(admin.getName());
				response.setId(admin.getId_parkingadmin());
				response.setSurname(admin.getSurname());
				response.setPassword(password);
				response.setImg(admin.getImg());
				System.out.println(2);
				return response;
			}
			else {
				System.out.println("3");
				return null;
			}
		}
	}
	@RequestMapping(value = "/deleteParking", method = RequestMethod.GET)
	@ResponseBody
	public boolean deleteParking(@RequestParam(name="idParking")Integer idParking){
		System.out.println(idParking);
		try {
			parkingDAO.delete(idParking);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return true;
	}
	
	@RequestMapping(value = "/addCategory", method = RequestMethod.GET)
	@ResponseBody
	public boolean addCategory(@RequestParam(name="parkId")Integer parkId,@RequestParam(name="category")String category){
		
		ObjectMapper om=new ObjectMapper();
		CategoryDTO catDTO;
		try {
			catDTO=om.readValue(category, CategoryDTO.class);
			Parking park=parkingDAO.getParkById(parkId);
			Category categ=new Category();
			categ.setCovered(catDTO.getCovered());
			categ.setMax_height(catDTO.getMax_height());
			categ.setName(catDTO.getName());
			categ.setPrice(catDTO.getPrice());
			categ.setTotal_places(catDTO.getTotal_places());
			categ.setBusy_places(0);
			categ.setPark(park);
			
			categoryDAO.persist(categ);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
			// TODO: handle exception
		}
		
		return true;
		
	}
	
	//**************************SIMULATOR*********************************
		@RequestMapping(value = "/simulator/parkEnter", method = RequestMethod.GET)
		@ResponseBody
		public boolean parkEnter(@RequestParam(name="category")String category){
			ObjectMapper om=new ObjectMapper();
			CategoryDao catDao = new CategoryDao();
			CategoryDTO catDTO = new CategoryDTO();
			try {
				catDTO=om.readValue(category, CategoryDTO.class);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(catDTO.getBusy_places()==catDTO.getTotal_places())
			{
				//full
			}
			else	
			catDao.updateBusyPlaces(catDTO.getId_category());
			
			return true;
			
		}
		
		
		
		@RequestMapping(value = "/simulator/parkExit", method = RequestMethod.GET)
		@ResponseBody
		public boolean parkExit(@RequestParam(name="category")String category){
			ObjectMapper om=new ObjectMapper();
			CategoryDao catDao = new CategoryDao();
			CategoryDTO catDTO = new CategoryDTO();
			try {
				catDTO=om.readValue(category, CategoryDTO.class);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(catDTO.getBusy_places()==0)
			{
				//empty parking
			}
			else
			catDao.updateBusyPlacesExit(catDTO.getId_category());

			return true;
		}
		
		
		@RequestMapping(value = "/simulator/getReservations", method = RequestMethod.GET)
		@ResponseBody
		public List<ReserveDTO> getReservations(){
			ReservationDao resDao = new ReservationDao();
			List<Reservation> r = resDao.getAllReservations();
			List<ReserveDTO> rDTO = new ArrayList<ReserveDTO>();
			for(Reservation res : r)
			{
				ReserveDTO temporal = new ReserveDTO();
				temporal.setUniqueCode(res.getReservation_code());
				rDTO.add(temporal);
			}

			return rDTO;
		}
		
		@RequestMapping(value = "/simulator/userArrived", method = RequestMethod.GET)
		@ResponseBody
		public Integer userArrived(@RequestParam(name="code")String code) throws JsonParseException, JsonMappingException, IOException{
			System.out.println(code);
			
			CategoryDao categoryDao = new CategoryDao();
			UserDao userDao = new UserDao();
			CategoryDTO categoryDTO = new CategoryDTO();
			ReservationDao rDao = new ReservationDao();
			List<Reservation> res = rDao.getAllReservations();
			Integer userId = null;
			ObjectMapper om=new ObjectMapper();
			ReserveDTO rDTO = new ReserveDTO();
			rDTO.setUniqueCode(code);

			System.out.println(res);
			
			for(Reservation r : res)
			{
				System.out.println(r.getReservation_code());
				if(r.getReservation_code().equals(rDTO.getUniqueCode())){
					userId=r.getUser().getId_normaluser();
				}
		
					
			}
			
			if(userId==null){
				return 0;
			}
			System.out.println(userId);

			
			reservartionDao.delete(userDao.getUserById(userId).getReservations().get(0).getId_reservation());		
			

			return 1;
		}
		
		@RequestMapping(value = "/simulator/checkReservations", method = RequestMethod.GET)
		@ResponseBody
		public Integer checkReservations(){
			ReservationDao  rDao = new ReservationDao();
	        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	        CategoryDao categoryDao = new CategoryDao();


			for(Reservation r : rDao.getAllReservations())
			{
				long minutes = timestamp.getMinutes() - r.getReservation_date().getMinutes();
						
				if(minutes >= r.getCategory().getPark().getArrive_time())
				{

						categoryDao.cancelReserve(r.getCategory().getId_category());
						reservartionDao.delete(r.getId_reservation());
											
				}

			}
		
			

			return 1;
		}
		
		
		@RequestMapping(value = "/editParking", method = RequestMethod.GET)
		@ResponseBody
		public boolean editParking(@RequestParam(name="parkingData")String parkingData){
			Parking parking;
			ParkingDTO parkingDTO;
			
			ObjectMapper om=new ObjectMapper();
			try {
				parkingDTO=om.readValue(parkingData, ParkingDTO.class);
				parking=parkingDAO.getParkById(parkingDTO.getId_parking());
				parking.setArrive_time(parkingDTO.getArriveTime());
				parking.setCredit_card(parkingDTO.getCreditCard());
				parking.setDescription(parkingDTO.getDescription());
				parking.setImage(parkingDTO.getImage());
				parking.setName(parkingDTO.getName());
				
				parkingDAO.updatePark(parking);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			
			return true;
		
		}
		
		
		@RequestMapping(value = "/getTotalReservations", method = RequestMethod.GET)
		@ResponseBody
		public Integer getTotalReservations(@RequestParam(name="id")int id){
			
			int totalReservations=0;
			ParkingDao parkingDAO = new ParkingDao();	
			ArrayList<Parking> parkings=(ArrayList<Parking>) parkingDAO.getAllParks();
			
			for(Parking p : parkings)
			{
				if(p.getId_parking()==id)
				{
					for(Category c : p.getCategories())
					{
						totalReservations+= c.getReservations().size();
					}
				}
			
			}
			
			return totalReservations;
			
		}
		
		@RequestMapping(value = "/getFreePlaces", method = RequestMethod.GET)
		@ResponseBody
		public Integer getFreePlaces(@RequestParam(name="id")int id){			
			int freePlaces=0;
			ParkingDao parkingDAO = new ParkingDao();	
			ArrayList<Parking> parkings=(ArrayList<Parking>) parkingDAO.getAllParks();
			
			for(Parking p : parkings)
			{
				if(p.getId_parking()==id)
				{
					for(Category c : p.getCategories())
					{
						freePlaces+=c.getTotal_places()-c.getBusy_places();
					}
				}
			
			}
			
			return freePlaces;		
		}
		
		@RequestMapping(value = "/setFreePlaces", method = RequestMethod.GET)
		@ResponseBody
		public Integer setFreePlaces(@RequestParam(name="places")int places,@RequestParam(name="idPark")int idPark,@RequestParam(name="idCategory") int idCategory){	
			CategoryDao c1 = new CategoryDao();;
			Category category = new Category();
			ArrayList<Parking> parkings=(ArrayList<Parking>) parkingDAO.getAllParks();
			
			for(Parking p : parkings)
			{
				if(p.getId_parking()==idPark)
				{
					for(Category c : p.getCategories())
					{
						if(c.getId_category()==idCategory)
						{
							c1.update(c.getId_category(), places);							
						}
						
					}
				}
			}	
			return places;
		}

}
