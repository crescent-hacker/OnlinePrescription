package dao.demo;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import domainModel.demo.Person;

public interface IDemoDAO {
	
	int insertPerson(Person person);

	int updatePerson(Person person);

	void deletePerson(int personID);

	List<Map<String, Object>> selectAllPerson();

	List<Person> getAllPersonData(String wherePart, String orderByPart, String rowNumPart);

	int getPersonCount(String wherePart);

}
