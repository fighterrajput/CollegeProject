package com.rays.pro4.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.WishListBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Model.WishListModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "WishListListCtl", urlPatterns = { "/ctl/WishListListCtl" })

public class WishListListCtl extends BaseCtl{


	@Override
	protected void preload(HttpServletRequest request) {
	
		HashMap map1 = new HashMap();
		map1.put("Phone", "Phone");
		map1.put("Laptop", "Laptop");
		map1.put("AC", "AC");
		map1.put("TV", "TV");

		request.setAttribute("map1", map1);
		
		
	}

	

		
		
		@Override
		protected BaseBean populateBean(HttpServletRequest request) {
			WishListBean bean = new WishListBean();

			bean.setId(DataUtility.getLong(request.getParameter("id")));

			bean.setProduct(DataUtility.getString(request.getParameter("product")));

			bean.setDate(DataUtility.getDate(request.getParameter("date")));

			bean.setUserName(DataUtility.getString(request.getParameter("username")));

			bean.setRemark(DataUtility.getString(request.getParameter("remark")));

			return bean;
		}
		
		

		protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			List list = null;
			List nextList = null;

			int pageNo = 1;
			int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

			WishListBean bean = (WishListBean) populateBean(request);
			String op = DataUtility.getString(request.getParameter("operation"));

			WishListModel model = new WishListModel();

			try {
				list = model.search(bean, pageNo, pageSize);
				System.out.println("list" + list);

				nextList = model.search(bean, pageNo + 1, pageSize);

				request.setAttribute("nextlist", nextList.size());

				ServletUtility.setList(list, request);

				if (list == null || list.size() == 0) {
					ServletUtility.setErrorMessage("No record found", request);
				}

				ServletUtility.setList(list, request);
				ServletUtility.setPageNo(pageNo, request);
				ServletUtility.setPageSize(pageSize, request);
				// ServletUtility.setBean(bean, request);
				ServletUtility.forward(getView(), request, response);
			} catch (ApplicationException e) {

				ServletUtility.handleException(e, request, response);
				return;
			}

		}

		@Override
		protected void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			List list;
			List nextList = null;

			int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
			int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
			pageNo = (pageNo == 0) ? 1 : pageNo;
			pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

			String op = DataUtility.getString(request.getParameter("operation"));

			WishListBean bean = (WishListBean) populateBean(request);

			String[] ids = request.getParameterValues("ids");

			WishListModel model = new WishListModel();

			if (OP_SEARCH.equalsIgnoreCase(op)) {
				pageNo = 1;
			} else if (OP_NEXT.equalsIgnoreCase(op)) {
				pageNo++;
			} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
				pageNo--;
			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.WISHLIST_CTL, request, response);
				return;
			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.WISHLIST_LIST_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					WishListBean deletebean = new WishListBean();
					for (String id : ids) {
						deletebean.setId(DataUtility.getInt(id));
						try {
							model.delete(deletebean);
						} catch (ApplicationException e) {

							ServletUtility.handleException(e, request, response);
							return;
						}

						ServletUtility.setSuccessMessage("WishList is Deleted Successfully", request);
					}
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}
			}
			try {

				list = model.search(bean, pageNo, pageSize);
				nextList = model.search(bean, pageNo + 1, pageSize);

				request.setAttribute("nextlist", nextList.size());

			} catch (ApplicationException e) {

				ServletUtility.handleException(e, request, response);
				return;
			}
			if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setBean(bean, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);

		}

		@Override
		protected String getView() {
			return ORSView.WISHLIST_LIST_VIEW;
		}

	}


