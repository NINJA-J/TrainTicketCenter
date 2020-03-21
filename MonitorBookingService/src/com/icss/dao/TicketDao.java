package com.icss.dao;

import com.icss.entity.Seat;
import com.icss.entity.Ticket;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketDao extends BaseDao {
    public int generateStationCover(int s, int e){
        int t=0;
        for(int i = s; i < e; i++) t = t * 2 + 1;
        for(int i = 0; i < s; i++) t *= 2;
        return t;
    }

    /**
     * 获得指定班次banCi下行驶区间stationCover中各种票的数量
     * @param banCi
     * @param cusOccupation
     * @return
     * @throws Exception
     */
    public Map<Integer, List<Seat>> getTicketsByBanCi(int banCi, int cusOccupation) throws Exception {
        List<Seat> seats = new ArrayList<Seat>();
        Map<Integer,List<Seat>> maps = new HashMap<Integer, List<Seat>>();

        String sqlSeat = "select SEAT.SEATNO sno, SEAT.OCCUPATION occu, SEAT.STYPE type" +
                "  from SEAT " +
                "where SEAT.BNO = ?";
        OpenConncetion();

        PreparedStatement ps = connection.prepareStatement(sqlSeat);
        ps.setInt(1,banCi);

        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            if((rs.getInt("occu")&cusOccupation)==0){
                Seat seat = new Seat();
                seat.setBno(banCi);
                seat.setOccupation(rs.getInt("occu"));
                seat.setSeatNo(rs.getString("sno"));
                seat.setType(Integer.valueOf(rs.getString("type")));

                if (!maps.containsKey(seat.getType()))
                    maps.put(seat.getType(), new ArrayList<Seat>());
                maps.get(seat.getType()).add(seat);
            }
        }
        closeConnection();

        return maps;
    }

    /**
     *
     * @param orders
     * @return
     * @throws Exception
     */
    public List<Ticket> getTicketsByOrder(List<String> orders) throws Exception {
        OpenConncetion();
        String sql = "select * from ticket where orderno=?";
        List<Ticket> tickets = new ArrayList<Ticket>();

        for( String s:orders){
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,s);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setOrderNo(s);
                ticket.setOwner(rs.getString("owner"));

                ticket.setBno(rs.getInt("bno"));
                ticket.setSeatNo(rs.getString("seatno"));
                ticket.setStype(rs.getString("stype"));
                ticket.setsAreaIndex(rs.getInt("s_area_index"));
                ticket.seteAreaIndex(rs.getInt("e_area_index"));

                ticket.setState(rs.getInt("state"));
                tickets.add(ticket);
            }
        }
        return tickets;
    }

    public void refoundTicket(Ticket t) throws Exception {
        OpenConncetion();
        String sqlUpdTicket="update TICKET set STATE=3 where ORDER_NO='" + t.getOrderNo()+"'";
        String sqlSeat="select SEAT.OCCUPATION from SEAT where SEATNO='" + t.getSeatNo() + "'";
        String sqlSeatUpdate="update SEAT set OCCUPATION=? where BNO=? and SEATNO=?";

        Statement st = connection.createStatement();
        int rtn = st.executeUpdate(sqlUpdTicket);

        ResultSet rs = st.executeQuery(sqlSeat);
        int occupation = rs.getInt("occupation");

        PreparedStatement ps = connection.prepareStatement(sqlSeatUpdate);
        ps.setInt(1,occupation & ~generateStationCover(t.getsAreaIndex(),t.geteAreaIndex()));
        ps.setInt(2,t.getBno());
        ps.setString(3,t.getSeatNo());
        rtn = ps.executeUpdate();

        connection.commit();
        closeConnection();
    }
}
