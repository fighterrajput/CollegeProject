package com.rays.pro4.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.ATMBean;
import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Model.ATMModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "ATMListCtl", urlPatterns = { "/ctl/ATMListCtl" })

public class ATMListCtl extends BaseCtl {


		
		protected void preload(HttpServletRequest request) {

			HashMap map = new HashMap();
			map.put("indore", "indore");
			map.put("ujjain", "ujjain");
			map.put("mhow", "mhow");

			request.setAttribute("map", map);
				
				

		
			
			
		}

		

		@Override
		protected BaseBean populateBean(HttpServletRequest request) {
			ATMBean bean = new ATMBean();

			bean.setId(DataUtility.getLong(request.getParameter("id")));

			bean.setDate(DataUtility.getDate(request.getParameter("date")));

			bean.setCash(DataUtility.getLong(request.getParameter("cash")));

			bean.setLocation(DataUtility.getString(request.getParameter("location")));

			bean.setRemark(DataUtility.getString(request.getParameter("remark")));

			return bean;
		}

	 	
		protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
		
			List list = null;
			List nextList = null;

			int pageNo = 1;
			int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

			ATMBean bean = (ATMBean) populateBean(request);
			String op = DataUtility.getString(request.getParameter("operation"));

		ATMModel model = new ATMModel();

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

			ATMBean bean = (ATMBean) populateBean(request);

			

			String[] ids = request.getParameterValues("ids");

			ATMModel model = new ATMModel();

			if (OP_SEARCH.equalsIgnoreCase(op)) {
				pageNo = 1;
			} else if (OP_NEXT.equalsIgnoreCase(op)) {
				pageNo++;
			} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
				pageNo--;
			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.ATM_CTL, request, response);
				return;
			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.ATM_LIST_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					ATMBean deletebean = new ATMBean();
					for (String id : ids) {
						deletebean.setId(DataUtility.getInt(id));
						try {
							model.delete(deletebean);
						} catch (ApplicationException e) {
							
							ServletUtility.handleException(e, request, response);
							return;
						}

						ServletUtility.setSuccessMessage("ABC is Deleted Successfully", request);
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
			return ORSView.ATM_LIST_VIEW;
		}

	}


