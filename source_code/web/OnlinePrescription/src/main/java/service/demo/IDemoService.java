package service.demo;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import domainModel.demo.Person;

public interface IDemoService {
	
	int insertPerson(Person person);

	int updatePerson(Person person);

	void deletePerson(int personID);

	List<Map<String, Object>> selectAllPerson();

	JSONObject getAllPersonJsonData(HttpServletRequest request);

	String downloadExcelDemo(HttpServletRequest request);

}
