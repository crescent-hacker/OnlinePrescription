package dao.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import domainModel.doctor.DoctorBO;
import domainModel.pharmacy.Pharmacy;

public class AdminDAOImpl implements AdminDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public int getDoctorCount(String wherePart, String statusPart) {
		int count=0;
		String sql= "select count(*) totalCount from doctor_info d where 1=1 "+wherePart + statusPart;
//		System.out.println("getDoctorCount sql = "+sql);
		try{
			count = jdbcTemplate.queryForInt(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
		return count;
		
	}

	@Override
	public List<DoctorBO> getDoctorList(String wherePart, String statusPart, String orderByPart, String rownumPart) {
		List<DoctorBO> list = new ArrayList<DoctorBO>();
		
		String sql="select * from ( "
				+ "select d.first_name, d.last_name, d.title, d.doctor_id, d.hospital, d.department, d.phone_num, d.status, "
				+ "date_format(d.registration_date,'%d-%m-%Y') reg_date ,  @rownum:=@rownum+1 as rownum from doctor_info d ,"
				+ " (SELECT @rownum:=0) r where 1=1 "+wherePart+statusPart+" "+orderByPart+ " ) x "+rownumPart;
//		System.out.println("getDoctorList sql = "+sql);

		try{
			
			SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
			
			DoctorBO doctor;
			while(rs.next()){
				doctor = new DoctorBO();
				doctor.setDocId(rs.getInt("doctor_id"));
				doctor.setTitle(rs.getString("title"));
				doctor.setFirstName(rs.getString("first_name"));
				doctor.setLastName(rs.getString("last_name"));
				doctor.setHospital(rs.getString("hospital"));
				doctor.setDepartment(rs.getString("department"));
				doctor.setRegistrationDate(rs.getString("reg_date"));
				doctor.setPhone(rs.getString("phone_num"));
				doctor.setStatus(rs.getString("status"));
				list.add(doctor);
			}
		//	System.out.println("doctor data size= "+list.size());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public DoctorBO getDoctorById(int id) {
		DoctorBO doctor = null;
		String sql = "select d.doctor_id, d.first_name, d.last_name, d.title, d.hospital, d.department,"
				+ " date_format(d.date_of_birth,'%d-%m-%Y') dob, date_format(d.registration_date,'%d-%m-%Y') reg_date, "
				+ " d.phone_num, d.status , date_format(d.action_date,'%d-%m-%Y') action_date, d.registration_number "
				+ " from doctor_info d where doctor_id = ?";
		try{
			SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, new Object[]{id});
			if(rs.next()){
				doctor = new DoctorBO();
				doctor.setDocId(rs.getInt("doctor_id"));
				doctor.setTitle(rs.getString("title"));
				doctor.setFirstName(rs.getString("first_name"));
				doctor.setLastName(rs.getString("last_name"));
				doctor.setHospital(rs.getString("hospital"));
				doctor.setDepartment(rs.getString("department"));
				doctor.setRegistrationDate(rs.getString("reg_date"));
				doctor.setDob(rs.getString("dob"));
				doctor.setStatus(rs.getString("status"));
				doctor.setPhone(rs.getString("phone_num"));
				doctor.setApprovalDate(rs.getString("action_date"));
				doctor.setRegistrationNumber(rs.getString("registration_number"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return doctor;
	}

	@Override
	public boolean approveRejectDoctorRequeust(int doctorId, String action) {
		String sql = "update doctor_info set status = '"+action+"',action_date=NOW() where doctor_id ="+doctorId;
		boolean update = false;
		try{
			if(jdbcTemplate.update(sql) == 1)
				update = true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return update;
	}

	@Override
	public int getPharmacyCount(String wherePart, String statusPart) {
		int count=0;
		String sql= "select count(*) totalCount from pharmacist_info d where 1=1 "+wherePart + statusPart;
//		System.out.println("getDoctorCount sql = "+sql);
		try{
			count = jdbcTemplate.queryForInt(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List<Pharmacy> getPharmacyList(String wherePart, String statusPart,
			String orderByPart, String rownumPart) {
		
		List<Pharmacy> list = new ArrayList<Pharmacy>();
		
		String sql="select * from ( "
				+ "select id, name, street, postcode, suburb, phone, status, "
				+ "date_format(registration_date,'%d-%m-%Y') reg_date ,  @rownum:=@rownum+1 as rownum from pharmacist_info  ,"
				+ " (SELECT @rownum:=0) r where 1=1 "+wherePart+statusPart+" "+orderByPart+ " ) x "+rownumPart;
//		System.out.println("getDoctorList sql = "+sql);

		try{
			
			SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
			
			Pharmacy pharm;
			while(rs.next()){
				pharm = new Pharmacy();
				pharm.setId(rs.getInt("id"));
				pharm.setStatus(rs.getString("status"));
				pharm.setName(rs.getString("name"));
				pharm.setStreet(rs.getString("street"));
				pharm.setPostcode(rs.getString("postcode"));
				pharm.setSuburb(rs.getString("suburb"));
				pharm.setRegistrationDate(rs.getString("reg_date"));
				pharm.setPhone(rs.getString("phone"));
				list.add(pharm);
			}
		//	System.out.println("doctor data size= "+list.size());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Pharmacy getPharmacyById(int id) {
		String sql = "select id, name, street, postcode, suburb, phone, "
				+ " date_format(registration_date,'%d-%m-%Y') reg_date, "
				+ " status, date_format(action_date,'%d-%m-%Y') action_date, registration_number "
				+ " from pharmacist_info  where id = ?";
		Pharmacy pharm = null;
		try{
			SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, new Object[]{id});
			if(rs.next()){
				pharm = new Pharmacy();
				pharm.setId(rs.getInt("id"));
				pharm.setStatus(rs.getString("status"));
				pharm.setName(rs.getString("name"));
				pharm.setStreet(rs.getString("street"));
				pharm.setPostcode(rs.getString("postcode"));
				pharm.setSuburb(rs.getString("suburb"));
				pharm.setRegistrationDate(rs.getString("reg_date"));
				pharm.setPhone(rs.getString("phone"));
				pharm.setApprovalDate(rs.getString("action_date"));
				pharm.setRegistrationNumber(rs.getString("registration_number"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return pharm;
	}

	@Override
	public boolean approveRejectPharmacyRequeust(int pharmId, String action) {
		String sql = "update pharmacist_info set status = '"+action+"',action_date=NOW() where id ="+pharmId;
		boolean update = false;
		try{
			if(jdbcTemplate.update(sql) == 1)
				update = true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return update;
	}

}
