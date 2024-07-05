
package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.ProductBean;
import com.rays.pro4.Bean.RoleBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Util.JDBCDataSource;

public class ProductModel {

	public Integer nextPK() throws Exception {

		int pk = 0;

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_product");

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			pk = rs.getInt(1);
		}

		rs.close();

		return pk + 1;
	}

	public long add(ProductBean bean) throws Exception {

		int pk = 0;

		Connection conn = JDBCDataSource.getConnection();

		pk = nextPK();

		conn.setAutoCommit(false);

		PreparedStatement pstmt = conn.prepareStatement("insert into st_product values(?,?,?,?,?)");

		pstmt.setInt(1, pk);
		pstmt.setString(2, bean.getProductName());
		pstmt.setString(3, bean.getProductAmmount());
		pstmt.setDate(4, new java.sql.Date(bean.getPurchaseDate().getTime()));
		pstmt.setString(5, bean.getProductCategory());

		int i = pstmt.executeUpdate();
		System.out.println("Product Add Successfully " + i);
		conn.commit();
		pstmt.close();

		return pk;
	}

	public void delete(ProductBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		conn.setAutoCommit(false);

		PreparedStatement pstmt = conn.prepareStatement("delete from st_product where id = ?");

		pstmt.setLong(1, bean.getId());

		int i = pstmt.executeUpdate();
		System.out.println("Product delete successfully " + i);
		conn.commit();

		pstmt.close();
	}

	public void update(ProductBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		conn.setAutoCommit(false); // Begin transaction

		PreparedStatement pstmt = conn.prepareStatement(
				"update st_product set productName = ?, productAmmount = ?, purchaseDate = ?, productCategory = ? where id = ?");

		pstmt.setString(1, bean.getProductName());
		pstmt.setString(2, bean.getProductAmmount());
		pstmt.setDate(3, new java.sql.Date(bean.getPurchaseDate().getTime()));
		pstmt.setString(4, bean.getProductCategory());
		pstmt.setLong(5, bean.getId());

		int i = pstmt.executeUpdate();

		System.out.println("product update successfully " + i);

		conn.commit();
		pstmt.close();

	}

	public ProductBean findByPK(long pk) throws Exception {

		String sql = "select * from st_product where id = ?";
		ProductBean bean = null;

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setLong(1, pk);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			bean = new ProductBean();
			bean.setId(rs.getLong(1));
			bean.setProductName(rs.getString(2));
			bean.setProductAmmount(rs.getString(3));
			bean.setPurchaseDate(rs.getDate(4));

		}

		rs.close();

		return bean;
	}

	public List search(ProductBean bean, int pageNo, int pageSize) throws Exception {

		StringBuffer sql = new StringBuffer("select * from st_product where 1=1");
		if (bean != null) {

			if (bean.getProductName() != null && bean.getProductName().length() > 0) {
				sql.append(" AND productName like '" + bean.getProductName() + "%'");
			}

			if (bean.getProductAmmount() != null && bean.getProductAmmount().length() > 0) {
				sql.append(" AND productAmmount like '" + bean.getProductAmmount() + "%'");
			}

			if (bean.getProductCategory() != null && bean.getProductCategory().length() > 0) {
				sql.append(" AND productCategory like '" + bean.getProductCategory() + "%'");
			}

			if (bean.getPurchaseDate() != null && bean.getPurchaseDate().getTime() > 0) {
				Date d = new Date(bean.getPurchaseDate().getTime());
				sql.append(" AND purchaseDate = '" + d + "'");
				System.out.println("done");
			}

			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}

		}

		if (pageSize > 0) {

			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);

		}

		System.out.println("sql query search >>= " + sql.toString());
		List list = new ArrayList();

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			bean = new ProductBean();
			bean.setId(rs.getLong(1));
			bean.setProductName(rs.getString(2));
			bean.setProductAmmount(rs.getString(3));
			bean.setPurchaseDate(rs.getDate(4));
			bean.setProductCategory(rs.getString(5));

			list.add(bean);

		}
		rs.close();

		return list;

	}

	public List list() throws Exception {

		ArrayList list = new ArrayList();

		StringBuffer sql = new StringBuffer("select * from st_product");

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			ProductBean bean = new ProductBean();

			bean.setId(rs.getLong(1));
			bean.setProductName(rs.getString(2));
			bean.setProductAmmount(rs.getString(3));
			bean.setPurchaseDate(rs.getDate(4));
			bean.setProductCategory(rs.getString(5));

			list.add(bean);

		}

		rs.close();

		return list;
	}

}
