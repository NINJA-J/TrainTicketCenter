package com.icss.dao;

import com.icss.entity.BanCi;
import com.icss.entity.Seat;
import com.icss.entity.Station;
import com.icss.entity.Train;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class TrainDao extends BaseDao {
	/*
	* 经过s,e出发时间start
	* 查询经过这两站的train
	* 查询符合出发时间的banci
	* 查询banci中在s,e之间的座位情况
	* */
	
	
	/**
	 * 
	 * @param tno
	 * @return
	 * @throws Exception
	 */
	public Train getTrain(String tno) throws Exception{
		Train train = null;
		
		String sql = "select * from train where tno=?";
		this.OpenConncetion();
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, tno);
		ResultSet rs = ps.executeQuery();
		if(rs!=null){
			while(rs.next()){
		       train = new Train();
		       //train.setTname(rs.getString("tname"));   //提取数据库中的字段值，填充到Train对象中
		       train.setTno(rs.getString("tno"));
		       train.setsArea(rs.getString("tstart"));
		       train.seteArea(rs.getString("tend"));
		       train.setsDepTime(rs.getDate("starttime"));
		       train.setLongTime(rs.getString("longtime"));
		       train.setCusDepArea(rs.getInt("departure"));
			   train.setCusDepArea(rs.getInt("destination"));

			   train.setBans(new ArrayList<BanCi>());
			   break;
			}			
		}		
		this.closeConnection();
		
		return train;
	}

	/**
	 * 获取火车经停站的序号
	 * @param tno
	 * @param area
	 * @return
	 * @throws Exception
	 */
	public int getTrainStationIndex(int tno, int area) throws Exception {
		OpenConncetion();
		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery(
				"select TRANSITION.IND from TRANSITION where TNO="+tno+" and AREA="+area);
		int index = rs.getInt("ind");
		closeConnection();
		return index;
	}

	/**
	 *	获得经过起点站sArea和终点站eArea的火车（非班次）集合
	 * @param sArea
	 * @param eArea
	 * @return
	 */
	public List<Train> getTrainBySection(int sArea,int eArea) throws Exception {
		List<Train> tas = new ArrayList<Train>();

		String sql = "select TRAIN.TNO tno, TRAIN.TSTART tstart, TRAIN.TEND tend, TRAIN.starttime stime," +
				"TRAIN.LONGTIME ltime, t1.seq sIndex, t2.seq eIndex, t1.area sArea, t2.area eArea from" +
				"  ( select TRAIN.TNO tno, TRANSITION.IND seq, TRANSITION.AREA area  from TRAIN,TRANSITION " +
				"  where TRAIN.TNO = TRANSITION.TNO and TRANSITION.AREA = ?) t1," +
				"  ( select TRAIN.TNO tno, TRANSITION.IND seq, TRANSITION.AREA area  from TRAIN,TRANSITION " +
				"  where TRAIN.TNO = TRANSITION.TNO and TRANSITION.AREA = ?) t2, TRAIN " +
				"where t1.tno = t2.tno and t2.seq > t1.seq" +
				" and t1.tno = TRAIN.tno";
		OpenConncetion();
		//
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setInt(1,sArea);
		ps.setInt(2,eArea);
		ResultSet rs = ps.executeQuery();

		while(rs.next()){
			Train train = new Train();
			train.setTno(rs.getString("tno"));
			train.setsArea(rs.getString("tstart"));
			train.seteArea(rs.getString("tend"));
			train.setsDepTime(rs.getDate("stime"));
			train.setLongTime(rs.getString("ltime"));
			train.setCusDepArea(rs.getInt("sArea"));
			train.setCusDepAreaIndex(rs.getInt("sIndex"));
			train.setCusDepArea(rs.getInt("eArea"));
			train.setCusDepAreaIndex(rs.getInt("eIndex"));
			train.setCusOccupation(generateStationCover(
					rs.getInt("sIndex"),
					rs.getInt("eIndex")
			));

			train.setBans(new ArrayList<BanCi>());
			tas.add(train);
		}

		closeConnection();

		return tas;
	}

	public String getTnoByBno(int bno) throws Exception {
		OpenConncetion();
		Statement st = connection.createStatement();
		ResultSet rs =st.executeQuery("select TNO from TBANCI where BNO=" + bno);
		String tno = null;
		if(rs.next()) {
			tno = rs.getString("TNO");
		}
		closeConnection();
		return tno;
	}


	public Train getTrainByBno(int bno,int sArea, int eArea) throws Exception {
		String tno = getTnoByBno(bno);
		Station sStat = DaoFactory.getStaDao().getStation(tno,sArea);
		Station eStat = DaoFactory.getStaDao().getStation(tno,eArea);

		Train train = getTrain(tno);
		BanCi bc = DaoFactory.getBancicDao().getBanCiByBno(bno,sStat,eStat);
		train.getBans().add(bc);
		return train;
	}
//	public




	/**
	 *
	 * @param sArea
	 * @param startDate
	 * @param startDate
	 * @return
	 * @throws Exception
	 */
	public List<Train> getInfosByCustom(int sArea, int eArea, Date startDate) throws Exception {
		List<Train> trains = getTrainBySection(sArea,eArea);
		for(Train t:trains){
			List<BanCi> bans = DaoFactory.getBancicDao().getBanCiByDate(t,startDate,sArea);
			for(BanCi bc:bans){
				bc.setSeatInfo(DaoFactory.getTicketDao().getTicketsByBanCi(bc.getBno(),t.getCusOccupation()));
			}
			t.setBans(bans);
		}
		return trains;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Train> getAllTrains() throws Exception{
		
		List<Train> tas = null;                                   

		String sql = "select * from train ";
		this.OpenConncetion();
		//
		PreparedStatement ps = this.connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		if(rs != null) {
			tas = new ArrayList<Train>();                         
			while(rs.next()) {
				Train train = new Train();                    
				train.setTno(rs.getString("tno"));
				train.setsArea(rs.getString("tstart"));
				train.seteArea(rs.getString("tend"));
				train.setsDepTime(rs.getDate("starttime"));
				train.setLongTime(rs.getString("longtime"));
				train.setCusDepArea(rs.getInt("departure"));
				train.setCusDesArea(rs.getInt("destination"));
				tas.add(train);                                  
			}
			
		}
		this.closeConnection();
		
		return tas;
	}

	/**
	 *
	 * @param bno
	 * @param sArea
	 * @param eArea
	 * @return
	 * @throws Exception
	 */
	public int getOccupation( int bno, int sArea, int eArea ) throws Exception {
		OpenConncetion();
		int occu = -1;

		Statement st = connection.createStatement();

		ResultSet rs;
		rs = st.executeQuery( "select TRANSITION.IND from TRANSITION,TBANCI where TRANSITION.TNO=TBANCI.TNO and TBANCI.BNO=" + bno + " and area=" + sArea);
		int sIndex = rs.getInt("ind");
		rs = st.executeQuery( "select TRANSITION.IND from TRANSITION,TBANCI where TRANSITION.TNO=TBANCI.TNO and TBANCI.BNO=" + bno + " and area=" + eArea);
		int eIndex = rs.getInt("ind");

		occu = generateStationCover(sIndex,eIndex);
		closeConnection();

		return occu;
	}
}
