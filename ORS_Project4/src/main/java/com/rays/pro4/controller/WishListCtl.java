package com.rays.pro4.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.WishListBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.WishListModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "WishListCtl", urlPatterns = { "/ctl/WishListCtl" })

public class WishListCtl extends BaseCtl {

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
	protected boolean validate(HttpServletRequest request) {
		System.out.println("uctl Validate");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("product"))) {
			request.setAttribute("product", PropertyReader.getValue("error.require", "Product"));
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("product"))) {
			request.setAttribute("product", "Product name must contains alphabet only");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("username"))) {
			request.setAttribute("username", PropertyReader.getValue("error.require", "Username"));
			pass = false;

		} else if (DataValidator.isTooLong(request.getParameter("username"), 15)){
			request.setAttribute("username", "Username should not contain more than 15 characters");
			pass = false;
		}
		

		if (DataValidator.isNull(request.getParameter("remark"))) {
			request.setAttribute("remark", PropertyReader.getValue("error.require", "Remark"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("remark"))) {
			request.setAttribute("remark", "Remark must contains alphabet only");
			pass = false;
		}else if (DataValidator.isTooLong(request.getParameter("remark"), 20)){
			request.setAttribute("remark", "Remark should not contain more than 20 characters");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("date"))) {
			request.setAttribute("date", PropertyReader.getValue("error.require", "Date"));
			pass = false;
		}

		return pass;

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
		System.out.println("WishList ctl do get 1111111");
		String op = DataUtility.getString(request.getParameter("operation"));

		WishListModel model = new WishListModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {

			WishListBean bean;
			try {
				bean = model.findByPK(id);
				System.out.println(bean);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {

				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println(">>>><<<<>><<><<><>**********" + id + op);

		WishListModel model = new WishListModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			WishListBean bean = (WishListBean) populateBean(request);
			try {
				if (id > 0) {

					model.update(bean);
					ServletUtility.setBean(bean, request);
					System.out.println(" WishList ctl DoPost 222222");
					ServletUtility.setSuccessMessage("WishList is successfully Updated", request);

				} else {

					long pk = model.add(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("WishList is successfully Added ", request);

					// bean.setId(pk);
				}
				ServletUtility.setBean(bean, request);

			} catch (ApplicationException e) {

				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				System.out.println(" WishList D post 4444444");
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("WishList exists", request);
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			System.out.println(" WishList ctl D p 5555555");

			WishListBean bean = (WishListBean) populateBean(request);
			try {
				model.delete(bean);
				System.out.println(" salary ctl D Post  6666666");
				ServletUtility.redirect(ORSView.WISHLIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {

				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println(" WishList  ctl Do post 77777");

			ServletUtility.redirect(ORSView.WISHLIST_LIST_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		return ORSView.WISHLIST_VIEW;
	}
}
