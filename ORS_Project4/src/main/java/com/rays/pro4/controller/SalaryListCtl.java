package com.rays.pro4.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.SalaryBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Model.SalaryModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "SalaryListCtl", urlPatterns = { "/ctl/SalaryListCtl" })

public class SalaryListCtl extends BaseCtl{


	@Override
	protected void preload(HttpServletRequest request) {
	
		HashMap map1 = new HashMap();
		map1.put("Riya", "Riya");
		map1.put("Ankit", "Ankit");
		map1.put("Pooja", "Pooja");
		map1.put("Dharam", "Dharam");


		request.setAttribute("map1", map1);
		
		HashMap map = new HashMap();

		map.put("Active", "Active");
		map.put("Inactive", "Inactive");
		request.setAttribute("map", map);
		
	}

	

		
		
		@Override
		protected BaseBean populateBean(HttpServletRequest request) {
			SalaryBean bean = new SalaryBean();

			bean.setId(DataUtility.getLong(request.getParameter("id")));

			bean.setEmployee(DataUtility.getString(request.getParameter("employee")));

			bean.setStatus(DataUtility.getString(request.getParameter("status")));

			bean.setAmount(DataUtility.getLong(request.getParameter("amount")));

			bean.setDate(DataUtility.getDate(request.getParameter("date")));

			return bean;
		}
		
		

		protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			List list = null;
			List nextList = null;

			int pageNo = 1;
			int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

			SalaryBean bean = (SalaryBean) populateBean(request);
			String op = DataUtility.getString(request.getParameter("operation"));

			SalaryModel model = new SalaryModel();

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

			SalaryBean bean = (SalaryBean) populateBean(request);

			String[] ids = request.getParameterValues("ids");

			SalaryModel model = new SalaryModel();

			if (OP_SEARCH.equalsIgnoreCase(op)) {
				pageNo = 1;
			} else if (OP_NEXT.equalsIgnoreCase(op)) {
				pageNo++;
			} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
				pageNo--;
			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.SALARY_CTL, request, response);
				return;
			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.SALARY_LIST_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					SalaryBean deletebean = new SalaryBean();
					for (String id : ids) {
						deletebean.setId(DataUtility.getInt(id));
						try {
							model.delete(deletebean);
						} catch (ApplicationException e) {

							ServletUtility.handleException(e, request, response);
							return;
						}

						ServletUtility.setSuccessMessage("Salary is Deleted Successfully", request);
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
			return ORSView.SALARY_LIST_VIEW;
		}

	}


