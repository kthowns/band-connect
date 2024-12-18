package entity;

public class MemberDetail {
	private Integer id;
	private Integer bandId;
	private String name;
	private Integer age;
	private String phone;
	private String position;

	@Override
	public String toString() {
		return position + ": " + name + "(" + age + ", " + phone + ")";
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getBandId() {
		return bandId;
	}
	public void setBandId(Integer bandId) {
		this.bandId = bandId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
}
