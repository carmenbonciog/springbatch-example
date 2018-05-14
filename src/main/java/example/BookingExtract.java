package example;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class BookingExtract {
	
	private Integer id; 
	private String street;
	private String city;
	private Integer zip;
	private String state;
	private Integer beds;
	private Integer baths;
	private Integer sqft;
	private String accomodationType;
	private String holidayDate;
	private BigDecimal price;
	private BigDecimal latitude;
	private BigDecimal longitude;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	

	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	

	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	

	public Integer getZip() {
		return zip;
	}
	public void setZip(Integer zip) {
		this.zip = zip;
	}
	

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	

	public Integer getBeds() {
		return beds;
	}
	public void setBeds(Integer beds) {
		this.beds = beds;
	}
	

	public Integer getBaths() {
		return baths;
	}
	public void setBaths(Integer baths) {
		this.baths = baths;
	}
	

	public Integer getSqft() {
		return sqft;
	}
	public void setSqft(Integer sqft) {
		this.sqft = sqft;
	}
	

	public String getAccomodationType() {
		return accomodationType;
	}
	public void setAccomodationType(String accomodationType) {
		this.accomodationType = accomodationType;
	}
	

	public String getHolidayDate() {
		return holidayDate;
	}
	public void setHolidayDate(String holidayDate) {

		this.holidayDate = holidayDate;
	}
	

	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public BigDecimal getLatitude() {
		return latitude;
	}
	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}
	
	public BigDecimal getLongitude() {
		return longitude;
	}
	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
}