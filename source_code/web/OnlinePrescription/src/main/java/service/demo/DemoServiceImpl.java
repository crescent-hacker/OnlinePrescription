package service.demo;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domainModel.demo.Person;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import dao.demo.IDemoDAO;

public class DemoServiceImpl implements IDemoService {
	
	@Autowired
	IDemoDAO demoDAO;

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
		List<Map<String,Object>> demo =  demoDAO.selectAllPerson();
		//TODO, data process
		return demo;
	}
	
	/**
	 * @author Kunal
	 * Logic for datatable
	 */
	@Override
	public JSONObject getAllPersonJsonData(HttpServletRequest request) {
		//System.out.println("demo data service called........");
		JSONObject obj=new JSONObject();
		
		//String[] columns = {"p.first_name", "p.last_name","p.street_name","p.city","p.state", "p.country"};
		
		String[] columnSearch = {"p.first_name", "p.last_name","p.street_name","p.city","p.state", "p.country"};
		
	    String[] columnSort = {"p.first_name", "p.last_name","p.street_name","p.city","p.state", "p.country"};
	    
		String wherePart="", orderByPart="", rowNumPart="";
		
		//for datatable internal
		String startStr = request.getParameter("iDisplayStart");
		int start = -1;
		if(startStr != null)
			start = Integer.parseInt(startStr);
		
		int noOfRecords = Integer.parseInt(request.getParameter("iDisplayLength"));
		
		if(startStr != null && noOfRecords != -1)
		{
			rowNumPart = "where rownum between "+(start + 1)+" and "+(start+noOfRecords);
		}
		//where rownum between ? and ? (int start, int end)
		
		// sorting part
		if(request.getParameter("iSortCol_0") != null)
		{
			orderByPart = " ORDER BY ";
			//System.out.println("no of sortable columns are "+request.getParameter("iSortingCols"));
			for(int i = 0;i< Integer.parseInt(request.getParameter("iSortingCols"));i++)
			{
				
				if(request.getParameter("bSortable_"+Integer.parseInt(request.getParameter("iSortCol_"+i))).equals("true") )
				{
					orderByPart+= columnSort[Integer.parseInt(request.getParameter("iSortCol_"+i))]+" "+request.getParameter("sSortDir_"+i)+" "; 
                }
	        }
		}
		
		//search part
		if(!request.getParameter("sSearch").equals(""))
		{
			wherePart += " and ( ";
			for(int i = 0;i<columnSearch.length;i++)
			{
				wherePart+=" lower("+columnSearch[i]+") LIKE '%"+request.getParameter("sSearch").toLowerCase()+"%' OR ";
			}
			wherePart = wherePart.substring(0,wherePart.length()-3);
			wherePart+=") ";
		}
		
		//total records
		int echo = Integer.parseInt(request.getParameter("sEcho"));
		int totalRecords = 0;
		totalRecords = demoDAO.getPersonCount("");
		obj.put("sEcho",new Integer(echo));
		obj.put("iTotalRecords",new Integer(totalRecords));
		
		JSONArray parentObj = new JSONArray();
		JSONArray childList = null;
				
		//display data
		List<Person> list = demoDAO.getAllPersonData(wherePart, orderByPart, rowNumPart);
		for(int i=0; i<list.size(); i++){
			childList = new JSONArray();
			childList.put(list.get(i).getFirstName());
			childList.put(list.get(i).getLastName());
			childList.put(list.get(i).getStreet());
			childList.put(list.get(i).getCity());
			childList.put(list.get(i).getState());
			childList.put(list.get(i).getCountry());
			parentObj.put(childList);
		}
		
		obj.put("aaData", parentObj);
		
		//filtered records
		int totalFilteredRecords = 0;
		if(!wherePart.equals("") ){
			totalFilteredRecords = demoDAO.getPersonCount(wherePart);
		}else{
			totalFilteredRecords = totalRecords;
		}
		obj.put("iTotalDisplayRecords",new Integer(totalFilteredRecords));
		
		return obj;
	}
	
	@Override
	public String downloadExcelDemo(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<Person> list = demoDAO.getAllPersonData("", "", "");
	
		map.put("personList", list);
	//	map.put("fileName", "PersonExport"+".csv");
		String output="";
		try{
			InputStream isStream = null ;
	        isStream = this.getClass().getClassLoader().getResourceAsStream("../../static/vm/allPersonData.vm");
	        Velocity.init();
	        VelocityContext context = new VelocityContext(map);
			StringWriter writer = new StringWriter();
			Velocity.evaluate(context, writer, "", isStream);
			output = writer.getBuffer().toString();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return output;
	}
}