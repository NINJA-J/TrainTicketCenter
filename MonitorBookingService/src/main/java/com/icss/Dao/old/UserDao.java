package com.icss.Dao.old;

import com.icss.dto.JMSDto;
import com.icss.Entity.old.Seat;
import com.icss.Entity.old.Station;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDao extends BaseDao{

	
	/**
	 * 根据订单编号，获取座位号.
	 * @param orderno
	 * @return
	 * @throws Exception
	 */
	public String getBanciSeatno(String orderno) throws Exception{
		String seatno = null;
		String sql = "select seatno from ticket t where orderno=?";
		this.OpenConncetion();
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, orderno);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			seatno = rs.getString("seatno");
			break;
		}
		
		return seatno;
	}


	/**
	 *
	 * @param jms
	 * @return
	 * @throws Exception
	 */
	public String updateTicketState(JMSDto jms) throws Exception{
		Station sStat = DaoFactory.getStaDao().getStation(DaoFactory.getTrainDao().getTnoByBno(jms.getBno()),jms.getsArea());
		Station eStat = DaoFactory.getStaDao().getStation(DaoFactory.getTrainDao().getTnoByBno(jms.getBno()),jms.geteArea());
		int jmsOccupation = generateStationCover(sStat.getAreaIndex(),eStat.getAreaIndex());

		System.out.println("sStation ind = " + sStat.getAreaIndex() + ", eStation ind = " + eStat.getAreaIndex());
		Seat seat = DaoFactory.getSeatDao().getValidSeat(
				jms.getBno(),
				jms.getType(),
				jmsOccupation);
		if(seat==null){
			System.out.println("seat == null");
			return null;
		}

		OpenConncetion();

		String sqlIst = "insert into TICKET(ORDER_NO, TICKET, OWNER, STYPE,BNO,S_AREA,E_AREA,STATE) values (?,?,?,?,?,?,?,?)";
		PreparedStatement ps;
		ps = connection.prepareStatement(sqlIst);
		ps.setString(1,jms.getOrderno());
		ps.setString(2,seat.getSeatNo());
		ps.setString(3,jms.getUname());
		ps.setString(4,jms.getType());
		ps.setInt(5,jms.getBno());
		ps.setInt(6,jms.getsArea());
		ps.setInt(7,jms.geteArea());
		ps.setInt(8,1);
		ps.executeUpdate();

		String sqlUpd = "update SEAT set OCCUPATION=? where SEATNO=? and BNO=? ";
		ps = connection.prepareStatement(sqlUpd);
		ps.setInt(1,jmsOccupation|seat.getOccupation());
		ps.setString(2,seat.getSeatNo());
		ps.setInt(3,jms.getBno());
		ps.executeUpdate();
		connection.commit();

		return seat.getSeatNo();
	}
}
