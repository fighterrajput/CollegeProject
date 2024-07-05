package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.BankBean;
import com.rays.pro4.Bean.UPIBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Util.JDBCDataSource;

public class BankModel {
	public int nextPK() throws DatabaseException {

		String sql = "SELECT MAX(ID) FROM st_bank";
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {

			throw new DatabaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk + 1;

	}

	public long add(BankBean bean) throws ApplicationException, DuplicateRecordException {

		String sql = "INSERT INTO st_bank VALUES(?,?,?,?,?)";

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();

			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getBankName());
			pstmt.setLong(3, bean.getAccountNumber());
			pstmt.setDate(4, new java.sql.Date(bean.getOpenDate().getTime()));
			pstmt.setString(5, bean.getAccountType());

			// date of birth caste by sql date

			int a = pstmt.executeUpdate();
			System.out.println(a);
			conn.commit();
			pstmt.close();

		} catch (Exception e) {

			try {
				e.printStackTrace();
				conn.rollback();

			} catch (Exception e2) {
				e2.printStackTrace();
				// application exception
				throw new ApplicationException("Exception : add rollback exceptionn" + e2.getMessage());
			}
		}

		finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;

	}

	public void delete(BankBean bean) throws ApplicationException {

		String sql = "DELETE FROM st_bank WHERE ID=?";
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception: Delete rollback Exception" + e2.getMessage());
			}
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	public BankBean findByPK(long pk) throws ApplicationException {

		String sql = "SELECT * FROM st_bank WHERE ID=?";
		BankBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new BankBean();
				bean.setId(rs.getLong(1));
				bean.setBankName(rs.getString(2));
				bean.setAccountNumber(rs.getLong(3));
				bean.setOpenDate(rs.getDate(4));
				bean.setAccountType(rs.getString(5));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();

			throw new ApplicationException("Exception : Exception in getting User by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public void update(BankBean bean) throws ApplicationException, DuplicateRecordException {

		String sql = "UPDATE st_bank SET bankName=?,accountNumber=?, Date=?, accountType=? WHERE ID=?";
		Connection conn = null;

		try {
			
		
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, bean.getBankName());
			pstmt.setLong(2, bean.getAccountNumber());
			pstmt.setDate(3, new java.sql.Date(bean.getOpenDate().getTime()));
			pstmt.setString(4, bean.getAccountType());
			pstmt.setLong(5, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
	} catch (Exception e) {
		e.printStackTrace();

		try {
			conn.rollback();
		} catch (Exception e2) {
			e2.printStackTrace();
			throw new ApplicationException("Exception : Update Rollback Exception " + e2.getMessage());
		}
	} finally {
		JDBCDataSource.closeConnection(conn);
	}

}


	public List search(BankBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	public List search(BankBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM st_bank where 1=1");
		if (bean != null) {
			if (bean.getBankName() != null && bean.getBankName().length() > 0) {
				sql.append(" AND bankName like '" + bean.getBankName() + "%'");
			}

			if (bean.getOpenDate() != null && bean.getOpenDate().getTime() > 0) {
				Date d = new java.sql.Date(bean.getOpenDate().getTime());
				sql.append(" AND openDate like '" + d + "%'");
			}

			if (bean.getAccountType() != null && bean.getAccountType().length() > 0) {
				sql.append(" AND accountType like '" + bean.getAccountType() + "%'");
			}

		/*	if (bean.getMobile() != null && bean.getMobile().length() > 0) {
				sql.append(" AND MOBILE like '" + bean.getMobile() + "%'");
			*/}

			/*
			 * if (bean.getDob() != null && bean.getDob().getTime() > 0) { Date d = new
			 * Date(bean.getDob().getTime()); sql.append("AND DOB = '" +
			 * DataUtility.getDateString(d) + "'"); }
			 */
		/* } */
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
			// sql.append(" limit " + pageNo + "," + pageSize);
		}

		System.out.println(sql);
		List list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new BankBean();
				bean.setId(rs.getLong(1));
				bean.setBankName(rs.getString(2));
				bean.setAccountNumber(rs.getLong(3));
				bean.setOpenDate(rs.getDate(4));
				bean.setAccountType(rs.getString(5));
				list.add(bean);

			}
			rs.close();
		} catch (Exception e) {

			throw new ApplicationException("Exception: Exception in Search User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {

		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_bank");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		System.out.println("preload........" + sql);
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				BankBean bean = new BankBean();
				bean.setId(rs.getLong(1));
				bean.setBankName(rs.getString(2));
				bean.setAccountNumber(rs.getLong(3));
				bean.setOpenDate(rs.getDate(4));
				bean.setAccountType(rs.getString(5));
				list.add(bean);

			}
			rs.close();
		} catch (Exception e) {

			throw new ApplicationException("Exception : Exception in getting list of users");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

}
