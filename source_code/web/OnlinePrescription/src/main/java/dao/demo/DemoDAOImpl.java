package dao.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import domainModel.demo.Person;

public class DemoDAOImpl implements IDemoDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int insertPerson(Person person) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updatePerson(Person person) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deletePerson(int personID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Map<String, Object>> selectAllPerson() {
		String sql = "SELECT * FROM person";
		List<Map<String, Object>> listOfPerson = jdbcTemplate.queryForList(sql);
		return listOfPerson;
	}
	
	/**
	 * This is the data query
	 * @author Kunal Kanere
	 */
	@Override
	public List<Person> getAllPersonData(String wherePart, String orderByPart, String rowNumPart) {
		//System.out.println("demo data dao called........");
		List<Person> list = new ArrayList<Person>();
	//	String sql = "select * from ( SELECT  x.*, rownum as r FROM ( "
	//			+ "select * from person where 1=1 "+wherePart+orderByPart+" ) x ) "+rowNumPart;
		
		String sql2="select * from ( "
				+ "select p.*,  @rownum:=@rownum+1 as rownum from person p , (SELECT @rownum:=0) r where 1=1 "+
				wherePart+" "+orderByPart+ " ) x "+rowNumPart;
		try{
			SqlRowSet rs = jdbcTemplate.queryForRowSet(sql2);
			
			Person person;
			while(rs.next()){
				person = new Person();
				person.setFirstName(rs.getString("first_name"));
				person.setLastName(rs.getString("last_name"));
				person.setStreet(rs.getString("street_name"));
				person.setCity(rs.getString("city"));
				person.setState(rs.getString("state"));
				person.setCountry(rs.getString("country"));
				list.add(person);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * get count for datatable
	 * @param wherePart
	 * @return
	 * @author Kunal Kanere
	 */
	@Override
	public int getPersonCount(String wherePart){
		//System.out.println("demo data count dao called........");
		int count=0;
		String sql= "select count(*) totalCount from person p where 1=1 "+wherePart;
		try{
			count = jdbcTemplate.queryForInt(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
		return count;
	}
}
