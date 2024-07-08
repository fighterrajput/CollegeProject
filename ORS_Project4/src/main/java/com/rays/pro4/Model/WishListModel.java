package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.WishListBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Util.JDBCDataSource;

public class WishListModel {

	public int nextPK() throws Exception {

		String sql = "SELECT MAX(ID) FROM st_wishlist";
		Connection conn = null;
		int pk = 0;

		conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			pk = rs.getInt(1);
		}
		rs.close();

		return pk + 1;

	}

	public long add(WishListBean bean) throws ApplicationException, DuplicateRecordException {

		String sql = "INSERT INTO st_wishlist VALUES(?,?,?,?,?)";

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();

			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getProduct());
			pstmt.setDate(3, new java.sql.Date(bean.getDate().getTime()));
			pstmt.setString(4, bean.getUserName());
			pstmt.setString(5, bean.getRemark());

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

	public void delete(WishListBean bean) throws ApplicationException {

		String sql = "DELETE FROM st_wishlist WHERE ID=?";
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

	public WishListBean findByPK(long pk) throws ApplicationException {

		String sql = "SELECT * FROM st_wishlist WHERE ID=?";
		WishListBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new WishListBean();
				bean.setId(rs.getLong(1));
				bean.setProduct(rs.getString(2));
				bean.setDate(rs.getDate(3));
				bean.setUserName(rs.getString(4));
				bean.setRemark(rs.getString(5));

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

	public void update(WishListBean bean) throws ApplicationException, DuplicateRecordException {

		String sql = "UPDATE st_wishlist SET product=?,date=?, username=?,remark=? WHERE id=?";
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, bean.getProduct());
			pstmt.setDate(2, new java.sql.Date(bean.getDate().getTime()));
			pstmt.setString(3, bean.getUserName());
			pstmt.setString(4, bean.getRemark());
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

	public List search(WishListBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	public List search(WishListBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM st_wishlist where 1=1");
		if (bean != null) {

			if (bean.getProduct() != null && bean.getProduct().length() > 0) {
				sql.append(" AND product like '" + bean.getProduct() + "%'");
			}

			if (bean.getDate() != null && bean.getDate().getTime() > 0) {
				Date d = new java.sql.Date(bean.getDate().getTime());
				sql.append(" AND date like '" + d + "%'");
			}
		}
		if (bean.getUserName() != null && bean.getUserName().length() > 0) {
			sql.append(" AND username like '" + bean.getUserName() + "%'");
		}
		if (bean.getRemark() != null && bean.getRemark().length() > 0) {
			sql.append(" AND remark like '" + bean.getRemark() + "%'");
		}

		// if page size is greater than zero then apply pagination
		if (pageSize > 0)

		{
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
				bean = new WishListBean();
				bean.setId(rs.getLong(1));
				bean.setProduct(rs.getString(2));
				bean.setDate(rs.getDate(3));
				bean.setUserName(rs.getString(4));
				bean.setRemark(rs.getString(5));
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

	public List list(int pageNo, int pageSize) throws Exception {

		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_wishlist");

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
				WishListBean bean = new WishListBean();
				bean.setId(rs.getLong(1));
				bean.setProduct(rs.getString(2));
				bean.setDate(rs.getDate(3));
				bean.setUserName(rs.getString(4));
				bean.setRemark(rs.getString(5));

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
