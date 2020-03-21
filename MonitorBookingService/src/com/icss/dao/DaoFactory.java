package com.icss.dao;

public class DaoFactory {
    private static AreaDao areaDao;
    private static BanCiDao bancicDao;
    private static TicketDao ticketDao;
    private static TrainDao trainDao;
    private static UserDao userDao;
    private static StationDao staDao;
    private static SeatDao seatDao;

    static {
        if(areaDao==null){
            areaDao = new AreaDao();
            try {
                areaDao.OpenConncetion();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(staDao==null){
            staDao = new StationDao();
            try {
                staDao.OpenConncetion();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(bancicDao==null){
            bancicDao = new BanCiDao();
            try {
                bancicDao.OpenConncetion();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(ticketDao==null){
            ticketDao = new TicketDao();
            try {
                ticketDao.OpenConncetion();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(trainDao==null){
            trainDao = new TrainDao();
            try {
                trainDao.OpenConncetion();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(userDao==null){
            userDao = new UserDao();
            try {
                userDao.OpenConncetion();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(seatDao==null){
            seatDao = new SeatDao();
            try {
                seatDao.OpenConncetion();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static SeatDao getSeatDao() {
        return seatDao;
    }

    public static StationDao getStaDao() {
        return DaoFactory.staDao;
    }

    public static AreaDao getAreaDao() {
        return DaoFactory.areaDao;
    }

    public static BanCiDao getBancicDao() {
        return DaoFactory.bancicDao;
    }

    public static TicketDao getTicketDao() {
        return DaoFactory.ticketDao;
    }

    public static TrainDao getTrainDao() {
        return DaoFactory.trainDao;
    }

    public static UserDao getUserDao() {
        return DaoFactory.userDao;
    }
}
